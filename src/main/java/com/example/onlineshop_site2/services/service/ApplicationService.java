package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.UserNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.CreateApplicationReq;
import com.example.onlineshop_site2.models.dtos.responses.ApplicationRes;
import com.example.onlineshop_site2.models.entities.Application;
import com.example.onlineshop_site2.models.entities.GoodItem;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.models.entities.UserBag;
import com.example.onlineshop_site2.models.enums.ApplicationStatus;
import com.example.onlineshop_site2.repositories.ApplicationRepo;
import com.example.onlineshop_site2.repositories.GoodItemRepo;
import com.example.onlineshop_site2.repositories.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final GoodItemRepo goodItemRepo;
    private final UserRepository userRepo;

    public ApplicationRes createApplication(String email, CreateApplicationReq req){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException(email));

        Set<UserBag> bag = user.getBag();
        Application application = mapUserBagSetToApplication(bag);
        application.setUser(user);
        application.setClientComment(req.getClientComment());
        applicationRepo.save(application);

        ApplicationRes res = ApplicationRes.mapFromEntity(application);
        return res;
    }

    private Application mapUserBagSetToApplication(Set<UserBag> bag){
        Application application = new Application();
        applicationRepo.save(application);
        List<GoodItem> goodItems = new ArrayList<>();
        bag.forEach(o->{
            GoodItem goodItem = new GoodItem();
            goodItem.setApplication(application);
            goodItem.setGood(o.getGood());
            goodItem.setSize(o.getSize().getSize());
            goodItem.setAmount(o.getAmount());
            goodItem.setColorName(o.getSize().getColor().getName());
            goodItem.setColorCode(o.getSize().getColor().getCode());
            goodItemRepo.save(goodItem);
            goodItems.add(goodItem);
        });

        application.setTime(LocalDateTime.now());
        application.setStatus(ApplicationStatus.FREE);
        application.setGoodItems(goodItems);
        return application;
    }

}

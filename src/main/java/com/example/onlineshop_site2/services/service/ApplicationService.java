package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.ApplicationNotFoundException;
import com.example.onlineshop_site2.exceptions.BagIsEmpty;
import com.example.onlineshop_site2.exceptions.UserNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.CreateApplicationReq;
import com.example.onlineshop_site2.models.dtos.requests.UpdateApplicationReq;
import com.example.onlineshop_site2.models.dtos.requests.UserBagIdReq;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final UserService userService;

    private final int limit = 20;

    public ApplicationRes createApplication(String email, CreateApplicationReq req){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException(email));

        Set<UserBag> bag = user.getBag();
        if(bag.isEmpty()){
            throw new BagIsEmpty();
        }
        Application application = mapUserBagSetToApplication(bag);
        application.setUser(user);
        application.setClientComment(req.getClientComment());
        applicationRepo.save(application);
        user.getBag().clear();
        userRepo.save(user);
        ApplicationRes res = ApplicationRes.mapFromEntity(application);
        return res;
    }

    public ApplicationRes updateApplication(Long id, UpdateApplicationReq req){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFoundException(id));
        if(!req.getAdminComment().isEmpty()){
            application.setAdminComment(req.getAdminComment());
        }
        if(req.getStatus() != null){
            application.setStatus(req.getStatus());
        }
        applicationRepo.save(application);
        ApplicationRes res = ApplicationRes.mapFromEntity(application);
        return res;
    }

    public List<ApplicationRes> getMyApplications(String email, Integer page){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException(email));

        Page<ApplicationRes> res1 = applicationRepo.findAllByUser_id(user.getId(), PageRequest.of(page,limit))
                        .map(ApplicationRes::mapFromEntity);
        List<ApplicationRes> res = new ArrayList<>();
        res1.forEach(o->{
            if(!o.getStatus().equals(ApplicationStatus.NOT_PAID)){
                res.add(o);
            }
        });
        return res;
    }

    public Page<ApplicationRes> getAllApplications(ApplicationStatus applicationStatus,
                                                   Integer page){
        Page<ApplicationRes> res;

        if(applicationStatus == null){
            res = applicationRepo.findAll(PageRequest.of(page, limit))
                    .map(ApplicationRes::mapFromEntity);
        }else{
            res = applicationRepo.findAllByStatus(applicationStatus, PageRequest.of(page, limit))
                    .map(ApplicationRes::mapFromEntity);
        }

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
        application.setStatus(ApplicationStatus.NOT_PAID);
        application.setGoodItems(goodItems);
        return application;
    }

}

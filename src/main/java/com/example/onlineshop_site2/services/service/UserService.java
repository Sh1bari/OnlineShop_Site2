package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.GoodIdNotUniqueException;
import com.example.onlineshop_site2.exceptions.GoodNotFoundException;
import com.example.onlineshop_site2.exceptions.UserNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.GoodIdsReq;
import com.example.onlineshop_site2.models.dtos.requests.UserBagIdReq;
import com.example.onlineshop_site2.models.dtos.requests.UserBagReq;
import com.example.onlineshop_site2.models.dtos.responses.GoodIdsRes;
import com.example.onlineshop_site2.models.dtos.responses.UserBagRes;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.models.entities.UserBag;
import com.example.onlineshop_site2.repositories.GoodRepo;
import com.example.onlineshop_site2.repositories.UserBagRepo;
import com.example.onlineshop_site2.repositories.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final GoodRepo goodRepo;
    private final UserBagRepo userBagRepo;

    public UserResDto updateUser(Long id, UpdateUserReq req){
        User user = userRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("Username with id " + id + " not found"));
        user = userRepo.save(req.mapToEntity(user));
        return UserResDto.mapFromEntity(user);
    }

    public UserResDto getUserById(Long id){
        User user = userRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("Username with id " + id + " not found"));
        return UserResDto.mapFromEntity(user);
    }
    public UserResDto getMe(String email){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("Username with email " + email + " not found"));
        return UserResDto.mapFromEntity(user);
    }
    public UserResDto updateMe(String email, UpdateUserReq req){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("Username with email " + email + " not found"));
        user = userRepo.save(req.mapToEntity(user));
        return UserResDto.mapFromEntity(user);
    }
    public List<GoodIdsRes> addWishlist(String email, GoodIdsReq req){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("Username with email " + email + " not found"));
        Good good = goodRepo.findById(req.getId())
                .orElseThrow(()->new GoodNotFoundException(req.getId()));
        user.getWishlist().add(good);
        userRepo.save(user);
        return GoodIdsRes.mapFromEntitySet(user.getWishlist());
    }

    public List<GoodIdsRes> deleteWishlistItem(String email, GoodIdsReq req){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("Username with email " + email + " not found"));
        Good good = goodRepo.findById(req.getId())
                .orElseThrow(()->new GoodNotFoundException(req.getId()));
        user.getWishlist().parallelStream().forEachOrdered(o->{
            if(o.getId().equals(good.getId())){
                user.getWishlist().remove(o);
            }
        });
        userRepo.save(user);
        return GoodIdsRes.mapFromEntitySet(user.getWishlist());
    }

    public List<UserBagRes> addBag(String email, UserBagReq req) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Username with email " + email + " not found"));

        Good good = goodRepo.findById(req.getGoodId())
                .orElseThrow(() -> new GoodNotFoundException(req.getGoodId()));

        Optional<UserBag> existingUserBagOptional = user.getBag().stream()
                .filter(userBag -> userBag.getGood().getId().equals(req.getGoodId()))
                .findFirst();

        if (existingUserBagOptional.isPresent()) {
            // Если запись существует, обновляем значения
            UserBag existingUserBag = existingUserBagOptional.get();
            existingUserBag.setAmount(req.getAmount());
            userBagRepo.save(existingUserBag);
        } else {
            // Если записи нет, добавляем новую запись
            UserBag userBag = new UserBag();
            userBag.setUser(user);
            userBag.setGood(good);
            userBag.setAmount(req.getAmount());
            userBagRepo.save(userBag);
            user.getBag().add(userBag);
            userRepo.save(user);
        }
        return UserBagRes.mapFromEntitySet(user.getBag());
    }
    public List<UserBagRes> deleteBag(String email, UserBagIdReq req){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("Username with email " + email + " not found"));

        Optional<UserBag> userBagOptional = user.getBag().stream()
                .filter(userBag -> userBag.getGood().getId().equals(req.getGoodId()))
                .findFirst();

        userBagOptional.ifPresent(userBag -> {
            // Удаляем запись из базы данных через репозиторий
            userBagRepo.deleteById(userBag.getId());
            // Удаляем запись из коллекции пользователя
            user.getBag().remove(userBag);
        });

        userRepo.save(user);

        return UserBagRes.mapFromEntitySet(user.getBag());
    }
}

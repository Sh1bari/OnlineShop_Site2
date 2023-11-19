package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.UserNotFoundException;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.repositories.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public UserResDto updateUser(Long id, UpdateUserReq req){
        User user = userRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("Username with id " + id + " not found"));
        user = userRepo.save(req.mapToEntity(user));
        return UserResDto.mapFromEntity(user);
    }
}

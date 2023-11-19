package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import com.example.onlineshop_site2.models.entities.Role;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.repositories.RoleRepository;
import com.example.onlineshop_site2.repositories.UserRepository;
import com.example.onlineshop_site2.services.service.UpdateUserReq;
import com.example.onlineshop_site2.services.service.UserResDto;
import com.example.onlineshop_site2.services.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Validated
@CrossOrigin
public class UserController {

    private final UserService userService;
    @Operation(summary = "Изменить инфу о юзере")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "изменено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResDto.class))})
    })
    @PutMapping("/user/{id}")
    public ResponseEntity<UserResDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserReq req){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(id, req));
    }
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    @GetMapping("/makeMeAdmin")
    @Secured("ROLE_USER")
    public ResponseEntity<?> make(Principal principal){
        User user = userRepo.findByEmail(principal.getName()).get();
        user.getRoles().add(roleRepo.findByName("ROLE_ADMIN").get());
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }
}

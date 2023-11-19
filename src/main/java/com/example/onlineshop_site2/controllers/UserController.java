package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.requests.GoodIdsReq;
import com.example.onlineshop_site2.models.dtos.requests.UserBagIdReq;
import com.example.onlineshop_site2.models.dtos.requests.UserBagReq;
import com.example.onlineshop_site2.models.dtos.responses.GoodIdsRes;
import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import com.example.onlineshop_site2.models.dtos.responses.UserBagRes;
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
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    @Operation(summary = "Получить инфу о юзере по айди")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResDto.class))})
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResDto> userById(@PathVariable(name = "id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }
    @Operation(summary = "Получить инфу о себе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResDto.class))})
    })
    @GetMapping("/user/me")
    @Secured("ROLE_USER")
    public ResponseEntity<UserResDto> getMe(Principal principal){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getMe(principal.getName()));
    }
    @Operation(summary = "Изменить инфу о себе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResDto.class))})
    })
    @PutMapping("/user/me")
    @Secured("ROLE_USER")
    public ResponseEntity<UserResDto> updateMe(Principal principal,
                                               @RequestBody UpdateUserReq req){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateMe(principal.getName(), req));
    }

    @Operation(summary = "Добавить в вишлист айтем")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodIdsRes.class))})
    })
    @PostMapping("/user/wishlist")
    @Secured("ROLE_USER")
    public ResponseEntity<List<GoodIdsRes>> addWishlist(Principal principal,
                                                        @RequestBody GoodIdsReq req){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.addWishlist(principal.getName(), req));
    }

    @Operation(summary = "Удалить айтем из вишлиста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodIdsRes.class))})
    })
    @DeleteMapping("/user/wishlist")
    @Secured("ROLE_USER")
    public ResponseEntity<List<GoodIdsRes>> deleteWishlistItem(Principal principal,
                                                        @RequestBody GoodIdsReq req){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteWishlistItem(principal.getName(), req));
    }

    @Operation(summary = "Добавить айтем в корзину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserBagRes.class))})
    })
    @PostMapping("/user/bag")
    @Secured("ROLE_USER")
    public ResponseEntity<List<UserBagRes>> addBag(Principal principal,
                                                   @RequestBody @Valid UserBagReq req){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.addBag(principal.getName(), req));
    }
    @Operation(summary = "Удалить айтем из корзины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserBagRes.class))})
    })
    @DeleteMapping("/user/bag")
    @Secured("ROLE_USER")
    public ResponseEntity<List<UserBagRes>> deleteBag(Principal principal,
                                                   @RequestBody @Valid UserBagIdReq req){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteBag(principal.getName(), req));
    }
}

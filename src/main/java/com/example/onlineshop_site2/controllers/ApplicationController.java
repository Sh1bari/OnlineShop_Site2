package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.CategoryDto;
import com.example.onlineshop_site2.models.dtos.requests.CategoryCreateReq;
import com.example.onlineshop_site2.models.dtos.requests.CreateApplicationReq;
import com.example.onlineshop_site2.models.dtos.responses.ApplicationRes;
import com.example.onlineshop_site2.repositories.ApplicationRepo;
import com.example.onlineshop_site2.services.service.ApplicationService;
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

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "Создать заявку по корзине клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationRes.class))})
    })
    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<ApplicationRes> createApplication(
            Principal principal,
            @RequestBody CreateApplicationReq req){
        ApplicationRes res = applicationService.createApplication(principal.getName(), req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}

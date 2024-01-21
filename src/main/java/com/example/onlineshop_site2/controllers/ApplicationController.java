package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.CategoryDto;
import com.example.onlineshop_site2.models.dtos.requests.CategoryCreateReq;
import com.example.onlineshop_site2.models.dtos.requests.CreateApplicationReq;
import com.example.onlineshop_site2.models.dtos.requests.UpdateApplicationReq;
import com.example.onlineshop_site2.models.dtos.responses.ApplicationRes;
import com.example.onlineshop_site2.models.dtos.responses.BillUrlDto;
import com.example.onlineshop_site2.models.enums.ApplicationStatus;
import com.example.onlineshop_site2.repositories.ApplicationRepo;
import com.example.onlineshop_site2.services.service.ApplicationService;
import com.example.onlineshop_site2.services.service.BillService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Page;
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
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    private final BillService billService;

    /*@Operation(summary = "Создать заявку по корзине клиента")
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
    }*/

    @Operation(summary = "Создать заявку по корзине клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BillUrlDto.class))})
    })
    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<BillUrlDto> createApplication(
            Principal principal,
            @RequestBody CreateApplicationReq req) throws JsonProcessingException {
        BillUrlDto res = billService.createBill(principal.getName(), req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Все заявки пользователя (пагинация)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationRes.class))})
    })
    @GetMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<List<ApplicationRes>> getMyApplications(
            Principal principal,
            @RequestParam(value = "page", required = false, defaultValue = "0")Integer page){
        List<ApplicationRes> res = applicationService.getMyApplications(principal.getName(), page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Все заявки пользователей (пагинация)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationRes.class))})
    })
    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<ApplicationRes>> getAllApplications(
            Principal principal,
            @RequestParam(value = "status", required = false, defaultValue = "FREE") ApplicationStatus status,
            @RequestParam(value = "page", required = false, defaultValue = "0")Integer page){
        Page<ApplicationRes> res = applicationService.getAllApplications(status, page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Изменить админ коммент")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationRes.class))})
    })
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApplicationRes> updateApplication(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid UpdateApplicationReq req){
        ApplicationRes res = applicationService.updateApplication(id, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}

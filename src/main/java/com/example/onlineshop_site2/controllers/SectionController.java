package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.SectionDtoRes;
import com.example.onlineshop_site2.models.dtos.requests.CreateNewSectionReq;
import com.example.onlineshop_site2.services.service.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@Validated
@RequiredArgsConstructor
@RequestMapping("/sections")
@SecurityRequirement(name = "bearerAuth")
public class SectionController {

    private final SectionService sectionService;
    @Operation(summary = "Получить все секции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SectionDtoRes.class))})
    })
    @GetMapping("")
    public ResponseEntity<List<SectionDtoRes>> getSections(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sectionService.getSections());
    }
    @Operation(summary = "Добавить секцию (админ)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SectionDtoRes.class))})
    })
    @Secured("ROLE_ADMIN")
    @PostMapping("")
    public ResponseEntity<SectionDtoRes> addNewSection(@RequestBody @Valid CreateNewSectionReq req){
        SectionDtoRes res = sectionService.addSection(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

}
package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.exceptions.AppError;
import com.example.onlineshop_site2.models.dtos.GoodDto;
import com.example.onlineshop_site2.models.dtos.SectionDtoRes;
import com.example.onlineshop_site2.models.dtos.requests.CreateNewSectionReq;
import com.example.onlineshop_site2.services.service.GoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Validated
public class GoodController {

    private final GoodService goodService;
    @Operation(summary = "Получить товары по категории (пагинация)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodDto.class))})
    })
    @GetMapping("/goods")
    public ResponseEntity<Page<GoodDto>> getGoods(@RequestParam("categoryId")@Min(value = 1L, message = "Id cant be less than 1") Long categoryId,
                                                  @RequestParam(name = "page", defaultValue = "0")@Min(value = 0, message = "Page cant be less than 0") Integer page){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(goodService.getGoodsByCategoryIdWithPage(categoryId, page));
    }
}

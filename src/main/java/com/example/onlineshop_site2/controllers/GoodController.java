package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.requests.GoodCreateReq;
import com.example.onlineshop_site2.models.dtos.responses.GoodResDto;
import com.example.onlineshop_site2.models.entities.Good;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Validated
public class GoodController {

    private final GoodService goodService;
    @Operation(summary = "Получить товары по категории (пагинация)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все товары",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @GetMapping("/category/{id}/goods")
    public ResponseEntity<Page<GoodResDto>> getGoods(@PathVariable(value = "id")@Min(value = 1L, message = "Id cant be less than 1") Long id,
                                                  @RequestParam(name = "page", defaultValue = "0")@Min(value = 0, message = "Page cant be less than 0") Integer page){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(goodService.getGoodsByCategoryIdWithPage(id, page));
    }
    @Operation(summary = "Создать товар")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар создан",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @PostMapping("/good")
    public ResponseEntity<GoodResDto> addGoodToSection(
            @RequestBody @Valid GoodCreateReq req){
        GoodResDto res = goodService.createGood(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}

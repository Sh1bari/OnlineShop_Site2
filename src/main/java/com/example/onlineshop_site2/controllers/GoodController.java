package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.requests.*;
import com.example.onlineshop_site2.models.dtos.responses.*;
import com.example.onlineshop_site2.models.enums.RecordState;
import com.example.onlineshop_site2.services.GoodServiceImpl;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Validated
@CrossOrigin
public class GoodController {

    private final GoodServiceImpl goodService;
    @Operation(summary = "Получить товары по категории (пагинация)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все товары",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResGood.class))})
    })
    @GetMapping("/category/{id}/goods")
    public ResponseEntity<CategoryResGood> getGoods(@PathVariable(value = "id")@Min(value = 1L, message = "Id cant be less than 1") Long id,
                                                    @RequestParam(name = "page", defaultValue = "0")@Min(value = 0, message = "Page cant be less than 0") Integer page,
                                                    @RequestParam(name = "state",required = false, defaultValue = "ACTIVE")RecordState state){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(goodService.getGoodsByCategoryIdWithPage(id,state, page));
    }
    @Operation(summary = "Получить товары")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все товары",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @GetMapping("/goods")
    public ResponseEntity<Page<GoodResDto>> getGoods(@RequestParam(name = "page", defaultValue = "0")@Min(value = 0, message = "Page cant be less than 0") Integer page,
                                                     @RequestParam(name = "state", required = false, defaultValue = "ACTIVE")RecordState state){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(goodService.getGoods(state, page));
    }
    @Operation(summary = "Получить товары по айди")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все товары",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @GetMapping("/good/{id}")
    public ResponseEntity<GoodResDto> getGood(
            @PathVariable(value = "id")@Min(value = 1L, message = "Id cant be less than 1") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(goodService.getGoodById(id));
    }
    @Operation(summary = "Создать товар")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар создан",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @PostMapping("/good")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GoodResDto> addGoodToSection(
            @RequestBody @Valid GoodCreateReq req){
        GoodResDto res = goodService.createGood(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @Operation(summary = "Получить товары без категории (пагинация)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все товары",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @GetMapping("/goods/free")
    public ResponseEntity<Page<GoodResDto>> getFreeGoods(
            @RequestParam(name = "page", defaultValue = "0")@Min(value = 0, message = "Page cant be less than 0") Integer page){
        Page<GoodResDto> res = goodService.getFreeGoodsPage(page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Обновить инфу о товаре")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар обновлен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @PutMapping("/good/{id}/info")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GoodResDto> updateGoodInfo(
            @PathVariable(name = "id")Long id,
            @RequestBody @Valid GoodUpdateReq req){
        GoodResDto res = goodService.updateGood(id, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Активировать товар")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @PostMapping("/good/{id}/activate")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GoodResDto> activateGood(
            @PathVariable(name = "id")Long id){
        GoodResDto res = goodService.activateGood(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Удалить товар")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "гуд",
                    content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping("/good/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GoodResDto> deleteGood(
            @PathVariable(name = "id")Long id){
        GoodResDto res = goodService.deleteGood(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Добавить фото к товару")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @PostMapping("/good/{id}/photo")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GoodResDto> addPhotoToGood(
            @RequestParam(value = "file", required = true) MultipartFile file,
            @PathVariable(name = "id")Long id,
            @RequestParam(value = "position") Integer position){
        GoodResDto res = goodService.addPhotoToGood(id, file, position);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Удалить фото")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "гуд",
                    content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping("/photo/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deletePhoto(
            @PathVariable(name = "id")Long id){
        goodService.deletePhoto(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Удалить фото")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "гуд",
                    content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("/background")
    public ResponseEntity<PhotoRes> updatePhoto(
            MultipartFile file){
        PhotoRes res = goodService.updatePhoto(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @GetMapping("/background")
    public ResponseEntity<PhotoRes> getPhoto(){
        PhotoRes res = goodService.getBackground();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Изменить позицию фото")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhotoIdRes.class))})
    })
    @PutMapping("/photo/{id}/moveToPos/{pos}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PhotoIdRes> updatePhoto(
            @PathVariable(name = "id")Long id,
            @PathVariable(name = "pos")Integer pos){
        PhotoIdRes res = goodService.updatePhotoPos(id, pos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Обновить размеры товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар обновлен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @PutMapping("/good/{id}/sizes")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GoodResDto> updateGoodSizes(
            @PathVariable(name = "id")Long id,
            @RequestBody @Valid GoodAddSizeReq req){
        GoodResDto res = goodService.putSizes(id, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Обновить цвета товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар обновлен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoodResDto.class))})
    })
    @PutMapping("/good/{id}/colors")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GoodResDto> updateGoodColors(
            @PathVariable(name = "id")Long id,
            @RequestBody @Valid GoodAddColorReq req) throws InterruptedException {
        GoodResDto res = goodService.putColors(id, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }


    @Operation(summary = "Привязать товар к категории")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "айди товаров",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryIdRes.class))})
    })
    @PutMapping("/good/{id}/categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<CategoryIdRes>> connectCategoryToGood(
            @PathVariable(name = "id") Long id,
            @RequestBody List<CategoryIdReq> req){
        List<CategoryIdRes> res = goodService.connectCategoryToGood(id, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

}

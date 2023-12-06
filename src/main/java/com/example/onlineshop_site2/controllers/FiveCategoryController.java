package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.CategoryDto;
import com.example.onlineshop_site2.models.dtos.requests.FiveCategoryUpdateReq;
import com.example.onlineshop_site2.models.dtos.responses.FiveCategoryItemRes;
import com.example.onlineshop_site2.models.entities.FiveCategory;
import com.example.onlineshop_site2.services.service.FiveCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
public class FiveCategoryController {
    private final FiveCategoryService fiveCategoryService;

    @Operation(summary = "Получить 5 категорий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FiveCategoryItemRes.class))})
    })
    @GetMapping("/fiveCategories")
    public ResponseEntity<List<FiveCategoryItemRes>> getAll(){
        List<FiveCategoryItemRes> res = fiveCategoryService.getList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PutMapping("/fiveCategory/{id}")
    public ResponseEntity<FiveCategoryItemRes> getById(
            @PathVariable(value = "id")@Min(value = 1L, message = "Id cant be less than 1") Long id,
            @RequestBody FiveCategoryUpdateReq req){
        FiveCategoryItemRes res = fiveCategoryService.updateById(id,req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PostMapping("/fiveCategory/{id}/photo")
    public ResponseEntity<?> updatePhoto(
            @RequestParam(value = "file", required = true) MultipartFile file,
            @PathVariable(value = "id")@Min(value = 1L, message = "Id cant be less than 1") Long id){
        fiveCategoryService.updatePhoto(id, file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


}

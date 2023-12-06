package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.requests.FirstPageReq;
import com.example.onlineshop_site2.models.dtos.responses.FirstPageItemRes;
import com.example.onlineshop_site2.models.dtos.responses.FiveCategoryItemRes;
import com.example.onlineshop_site2.services.service.FirstPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@RequiredArgsConstructor
public class FirstPageController {

    private final FirstPageService firstPageService;
    @Operation(summary = "Получить первую страницу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FirstPageItemRes.class))})
    })
    @GetMapping("/firstPage")
    public ResponseEntity<FirstPageItemRes> getItem(){
        FirstPageItemRes res = firstPageService.getItem();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Обновить первую страницу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FirstPageItemRes.class))})
    })
    @PutMapping("/firstPage")
    public ResponseEntity<FirstPageItemRes> updateItem(@RequestBody FirstPageReq req){
        FirstPageItemRes res = firstPageService.updateItem(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Обновить фото первой страницы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @PostMapping("/firstPage/photo")
    public ResponseEntity<?> updatePhoto(@RequestParam(value = "file", required = true) MultipartFile file){
        firstPageService.updatePhoto(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}

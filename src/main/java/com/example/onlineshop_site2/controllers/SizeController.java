package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.responses.SizeColorRes;
import com.example.onlineshop_site2.models.dtos.responses.SizeDtoRes;
import com.example.onlineshop_site2.models.dtos.responses.UserBagRes;
import com.example.onlineshop_site2.services.service.SizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@RestController
@Validated
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/size")
@SecurityRequirement(name = "bearerAuth")
public class SizeController {

    private final SizeService sizeService;
    @Operation(summary = "Получить размер и цвет по айди size")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "гуд",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SizeColorRes.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<SizeColorRes> getSizeWithColor(
            @PathVariable(value = "id")@Min(value = 1L, message = "Id cant be less than 1") Long id){
        SizeColorRes res = sizeService.getSizeById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}

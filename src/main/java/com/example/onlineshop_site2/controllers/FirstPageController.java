package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.requests.FirstPageReq;
import com.example.onlineshop_site2.models.dtos.responses.FirstPageItemRes;
import com.example.onlineshop_site2.services.service.FirstPageService;
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
    @GetMapping("/firstPage")
    public ResponseEntity<FirstPageItemRes> getItem(){
        FirstPageItemRes res = firstPageService.getItem();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PutMapping("/firstPage")
    public ResponseEntity<FirstPageItemRes> updateItem(@RequestBody FirstPageReq req){
        FirstPageItemRes res = firstPageService.updateItem(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PostMapping("/firstPage/photo")
    public ResponseEntity<?> updatePhoto(@RequestParam(value = "file", required = true) MultipartFile file){
        firstPageService.updatePhoto(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}

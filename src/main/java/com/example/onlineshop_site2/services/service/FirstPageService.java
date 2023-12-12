package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.GoodNotFoundException;
import com.example.onlineshop_site2.exceptions.PhotoNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.FirstPageReq;
import com.example.onlineshop_site2.models.dtos.responses.FirstPageItemRes;
import com.example.onlineshop_site2.models.entities.FirstPage;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.Photo;
import com.example.onlineshop_site2.repositories.FirstPageRepo;
import com.example.onlineshop_site2.repositories.GoodRepo;
import com.example.onlineshop_site2.repositories.PhotoRepo;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class FirstPageService {
    private final FirstPageRepo firstPageRepo;
    private final MinioService minioService;
    private final PhotoRepo photoRepo;
    private final GoodRepo goodRepo;

    public FirstPageItemRes getItem(){
        FirstPage page = firstPageRepo.findById(1L).get();
        return FirstPageItemRes.mapFromEntity(page);
    }

    public FirstPageItemRes updateItem(FirstPageReq req){
        FirstPage page = firstPageRepo.findById(1L).get();
        Good good = goodRepo.findById(req.getGoodId())
                .orElseThrow(()-> new GoodNotFoundException(req.getGoodId()));
        page.setDescription(req.getDescription());
        page.setName(req.getName());
        page.setGood(good);
        return FirstPageItemRes.mapFromEntity(firstPageRepo.save(page));
    }

    public boolean updatePhoto(MultipartFile file){
        FirstPage page = firstPageRepo.findById(1L).get();
        if(page.getPhoto().getPath() != null) {
            minioService.deleteFile("photos", page.getPhoto().getPath());
        }
        Photo photo = photoRepo.findById(page.getPhoto().getId())
                .orElseThrow(()->new PhotoNotFoundException(page.getPhoto().getId()));
        photo.setPath(minioService.saveFile(file));
        photoRepo.save(photo);
        return true;
    }
}

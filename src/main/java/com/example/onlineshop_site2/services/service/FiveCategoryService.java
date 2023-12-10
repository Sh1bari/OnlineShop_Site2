package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.CategoryNotFoundException;
import com.example.onlineshop_site2.exceptions.FiveCategoryNotFoundException;
import com.example.onlineshop_site2.exceptions.PhotoNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.FiveCategoryUpdateReq;
import com.example.onlineshop_site2.models.dtos.responses.FiveCategoryItemRes;
import com.example.onlineshop_site2.models.entities.Category;
import com.example.onlineshop_site2.models.entities.FiveCategory;
import com.example.onlineshop_site2.models.entities.Photo;
import com.example.onlineshop_site2.repositories.CategoryRepo;
import com.example.onlineshop_site2.repositories.FiveCategoryRepo;
import com.example.onlineshop_site2.repositories.PhotoRepo;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class FiveCategoryService {
    private final MinioService minioService;
    private final PhotoRepo photoRepo;
    private final FiveCategoryRepo fiveCategoryRepo;
    private final CategoryRepo categoryRepo;

    public List<FiveCategoryItemRes> getList(){
        List<FiveCategoryItemRes> res = new ArrayList<>();
        List<FiveCategory> fiveCategories = (List<FiveCategory>) fiveCategoryRepo.findAll();
        fiveCategories.forEach(o->res.add(FiveCategoryItemRes.mapFromEntity(o)));
        return res;
    }

    public FiveCategoryItemRes updateById(Long id, FiveCategoryUpdateReq req){
        FiveCategory fiveCategory = fiveCategoryRepo.findById(id)
                .orElseThrow(()->new FiveCategoryNotFoundException(id));
        Category category = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(()-> new CategoryNotFoundException(req.getCategoryId()));
        fiveCategory.setCategory(category);
        FiveCategory res = fiveCategoryRepo.save(fiveCategory);
        return FiveCategoryItemRes.mapFromEntity(res);
    }

    public boolean updatePhoto(Long id, MultipartFile file){
        FiveCategory fiveCategory = fiveCategoryRepo.findById(id)
                .orElseThrow(()->new FiveCategoryNotFoundException(id));
        if(fiveCategory.getPhoto().getPath() != null) {
            minioService.deleteFile("photos", fiveCategory.getPhoto().getPath());
        }
        Photo photo = photoRepo.findById(fiveCategory.getPhoto().getId())
                        .orElseThrow(()->new PhotoNotFoundException(fiveCategory.getPhoto().getId()));
        photo.setPath(minioService.saveFile(file));
        photoRepo.save(photo);
        return true;
    }

}

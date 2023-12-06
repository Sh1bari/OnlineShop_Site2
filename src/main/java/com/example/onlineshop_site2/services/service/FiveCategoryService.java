package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.models.dtos.responses.FiveCategoryItemRes;
import com.example.onlineshop_site2.models.entities.FiveCategory;
import com.example.onlineshop_site2.repositories.FiveCategoryRepo;
import com.example.onlineshop_site2.repositories.PhotoRepo;
import lombok.*;
import org.springframework.stereotype.Service;

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

    public List<FiveCategoryItemRes> getList(){
        List<FiveCategoryItemRes> res = new ArrayList<>();
        List<FiveCategory> fiveCategories = (List<FiveCategory>) fiveCategoryRepo.findAll();
        fiveCategories.forEach(o->res.add(FiveCategoryItemRes.mapFromEntity(o)));
        return res;
    }
}

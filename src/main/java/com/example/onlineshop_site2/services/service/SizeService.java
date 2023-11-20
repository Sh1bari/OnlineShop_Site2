package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.SizeNotFoundException;
import com.example.onlineshop_site2.models.dtos.responses.SizeColorRes;
import com.example.onlineshop_site2.models.entities.Size;
import com.example.onlineshop_site2.repositories.SizeRepo;
import lombok.*;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeRepo sizeRepo;

    public SizeColorRes getSizeById(Long id){
        Size size = sizeRepo.findById(id)
                .orElseThrow(()->new SizeNotFoundException(id));

        SizeColorRes res = SizeColorRes.mapFromEntity(size);
        return res;

    }

}

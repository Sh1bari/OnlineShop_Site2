package com.example.onlineshop_site2.services.service;


import com.example.onlineshop_site2.models.dtos.responses.SectionDtoRes;
import com.example.onlineshop_site2.models.dtos.requests.CreateNewSectionReq;

import java.util.List;

public interface SectionService {

    List<SectionDtoRes> getSections();
    SectionDtoRes addSection(CreateNewSectionReq req);
}

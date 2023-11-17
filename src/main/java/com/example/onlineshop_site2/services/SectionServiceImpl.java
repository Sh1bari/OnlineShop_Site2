package com.example.onlineshop_site2.services;

import com.example.onlineshop_site2.models.dtos.responses.SectionDtoRes;
import com.example.onlineshop_site2.models.dtos.requests.CreateNewSectionReq;
import com.example.onlineshop_site2.models.entities.Section;
import com.example.onlineshop_site2.repositories.SectionRepo;
import com.example.onlineshop_site2.services.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepo sectionRepo;

    @Override
    public List<SectionDtoRes> getSections() {
        Iterable<Section> sectionList = sectionRepo.findAll();
        List<SectionDtoRes> response = new ArrayList<>();
        sectionList.forEach(section -> {
            response.add(SectionDtoRes.mapFromEntity(section));
        });
        return response;
    }

    @Override
    public SectionDtoRes addSection(CreateNewSectionReq req) {
        Section res = new Section();
        res.setName(req.getName());
        sectionRepo.save(res);
        return SectionDtoRes.mapFromEntity(res);
    }
}

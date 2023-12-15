package com.example.onlineshop_site2.services;

import com.example.onlineshop_site2.exceptions.PhotoNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.GoodAddColorReq;
import com.example.onlineshop_site2.models.dtos.requests.GoodAddSizeReq;
import com.example.onlineshop_site2.models.dtos.requests.GoodUpdateReq;
import com.example.onlineshop_site2.models.dtos.requests.CategoryIdReq;
import com.example.onlineshop_site2.exceptions.CategoryNotFoundException;
import com.example.onlineshop_site2.exceptions.GoodNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.GoodCreateReq;
import com.example.onlineshop_site2.models.dtos.responses.*;
import com.example.onlineshop_site2.models.entities.*;
import com.example.onlineshop_site2.models.enums.ColorType;
import com.example.onlineshop_site2.models.enums.RecordState;
import com.example.onlineshop_site2.repositories.*;
import com.example.onlineshop_site2.services.service.GoodService;
import com.example.onlineshop_site2.services.service.MinioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {

    private final GoodRepo goodRepo;
    private final CategoryRepo categoryRepo;
    private final SizeRepo sizeRepo;
    private final UserBagRepo userBagRepo;
    private final Integer limit = 30;
    private final ColorRepo colorRepo;


    @Override
    public CategoryResGood getGoodsByCategoryIdWithPage(Long categoryId, RecordState state, Integer page) {
        CategoryResGood res = new CategoryResGood();
        Pageable pageable = PageRequest.of(page, limit);
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException(categoryId));
        Page<Good> goodPage = goodRepo.findAllByCategories_idAndState(categoryId,state, pageable);
        res.setGoods(goodPage.map(GoodResDto::mapFromEntity));
        res.setName(category.getName());
        return res;
    }

    public Page<GoodResDto> getGoods(RecordState state, Integer page) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Good> goodPage = goodRepo.findAllByState(state, pageable);
        return goodPage.map(GoodResDto::mapFromEntity);
    }
    public Page<GoodResDto> getFreeGoodsPage(Integer page) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Good> goodPage = goodRepo.findByCategoriesIsEmpty(pageable);
        return goodPage.map(GoodResDto::mapFromEntity);
    }
    public GoodResDto getGoodById(Long id) {
        Good good = goodRepo.findById(id)
                .orElseThrow(()-> new GoodNotFoundException(id));
        return GoodResDto.mapFromEntity(good);
    }

    @Override
    public Integer getAmountOfGoodPages() {
        return (int) Math.ceil((double) goodRepo.count() / limit);
    }

    @Override
    public GoodResDto createGood(GoodCreateReq req) {
        Good res = goodRepo.save(req.mapToEntity());
        res.getColors().forEach(o->o.getSizes().forEach(s->{
            s.setColor(o);
            s.setGood(res);
            sizeRepo.save(s);
        }));
        return GoodResDto.mapFromEntity(res);
    }

    public byte[] getFileByPath(String path){
        InputStream in = minioService.getFile(path);
        byte[] res = null;
        try {
            res = IOUtils.toByteArray(in);
        } catch (IOException e) {
            // Обработка ошибки, если не удается прочитать изображение
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public GoodResDto updateGood(Long id, GoodUpdateReq req) {
        // Найти товар по id
        Good existingGood = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));

        // Удалить все Color с типом BACKGROUND
        existingGood.getColors().removeIf(o -> o.getColorType().equals(ColorType.BACKGROUND));

        // Создать новый Color из запроса
        Color color = req.getBackColor().mapToEntity();
        color.setGood(existingGood);
        color.setGoodBackColor(existingGood);

        // Добавить новый Color в коллекцию
        existingGood.getColors().add(color);

        // Обновить остальные поля товара
        existingGood.setName(req.getName());
        existingGood.setDescription(req.getDescription());
        existingGood.setCompound(req.getCompound());
        existingGood.setCost(req.getCost());
        existingGood.setOnModel(req.getOnModel());
        existingGood.setVendorCode(req.getVendorCode());
        existingGood.setRecommendations(req.getRecommendations());

        // Сохранить изменения в базе данных
        goodRepo.save(existingGood);

        return GoodResDto.mapFromEntity(existingGood);
    }

    @Override
    public GoodResDto activateGood(Long id) {
        Good good = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        good.setState(RecordState.ACTIVE);
        goodRepo.save(good);
        return GoodResDto.mapFromEntity(good);
    }


    public GoodResDto deleteGood(Long id) {
        Good good = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        good.setState(RecordState.DELETED);
        goodRepo.save(good);
        return GoodResDto.mapFromEntity(good);
    }
    private final MinioService minioService;
    public GoodResDto addPhotoToGood(Long id, MultipartFile file, Integer position) {
        Good good = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        Photo photo = new Photo();
        photo.setGood(good);
        photo.setPosition(position);
        photo.setPath(minioService.saveFile(file));
        good.getPhotos().add(photo);
        photoRepo.save(photo);
        goodRepo.save(good);
        return GoodResDto.mapFromEntity(good);
    }

    public PhotoRes updatePhoto(MultipartFile file) {
        Photo photo = photoRepo.findById(260L)
                .orElseThrow(()->new PhotoNotFoundException(260L));
        if(photo.getPath() != null){
            minioService.deleteFile("photos",photo.getPath());
        }
        photo.setPath(minioService.saveFile(file));
        photoRepo.save(photo);
        return new PhotoRes(photo.getPath());
    }

    public PhotoRes getBackground(){
        Photo photo = photoRepo.findById(260L)
                .orElseThrow(()->new PhotoNotFoundException(260L));
        return new PhotoRes(photo.getPath());
    }


    public boolean deletePhoto(Long id) {
        Photo photo = photoRepo.findById(id)
                        .orElseThrow(()->new PhotoNotFoundException(id));
        minioService.deleteFile("photos",photo.getPath());
        photoRepo.deleteByIdNative(id);
        return true;
    }


    public PhotoIdRes updatePhotoPos(Long id ,Integer pos) {
        Photo photo = photoRepo.findById(id)
                .orElseThrow(()->new PhotoNotFoundException(id));
        photo.setPosition(pos);
        photoRepo.save(photo);
        return PhotoIdRes.mapFromEntity(photo);
    }
    @Transactional
    public GoodResDto putSizes(Long id, GoodAddSizeReq req){
        Good existingGood = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        Good newGood = req.mapToEntity();
        sizeRepo.saveAll(newGood.getSizes());
        existingGood.getSizes().clear();
        existingGood.getSizes().addAll(newGood.getSizes());
        existingGood.getSizes().forEach(o->o.setGood(existingGood));
        goodRepo.save(existingGood);
        return GoodResDto.mapFromEntity(existingGood);
    }
    @PersistenceContext
    private EntityManager entityManager;
    private final PhotoRepo photoRepo;

    @Transactional
    public GoodResDto putColors(Long id, GoodAddColorReq req) throws InterruptedException {
        Good existingGood = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
        Good newGood = req.mapToEntity();

        existingGood.getColors().forEach(o->{
            if(o.getColorType().equals(ColorType.WITH_SIZE)) {
                o.getSizes().forEach(s->{
                    userBagRepo.deleteBySizeId(s.getId());
                    sizeRepo.deleteByIdNative(s.getId());
                });
                colorRepo.deleteByIdNative(o.getId());
            }
        });

        entityManager.clear();

        Good res = goodRepo.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));

        newGood.getColors().forEach(o->{
            o.setGood(res);
            Color saved = colorRepo.save(o);
            o.getSizes().forEach(s->{
                Size size = s;
                size.setColor(saved);
                size.setGood(res);
                sizeRepo.save(size);
            });
        });


        return GoodResDto.mapFromEntity(res);
    }


    @Override
    public List<CategoryIdRes> connectCategoryToGood(Long id, List<CategoryIdReq> req) {
        Good good = goodRepo.findById(id)
                .orElseThrow(()->new GoodNotFoundException(id));
        good.setCategories(new ArrayList<>());
        List<Category> categories = req.stream()
                .map(o->{
                    Category cat = categoryRepo.findById(o.getId())
                        .orElseThrow(()->new CategoryNotFoundException(o.getId()));
                    good.getCategories().add(cat);
                    return cat;
                })
                .collect(Collectors.toList());
        goodRepo.save(good);

        return categories.stream()
                .map(CategoryIdRes::mapFromEntity)
                .collect(Collectors.toList());
    }
}

package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.CantSaveFileException;
import com.example.onlineshop_site2.exceptions.PhotoNotFoundException;
import com.example.onlineshop_site2.models.entities.Photo;
import com.example.onlineshop_site2.repositories.PhotoRepo;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.DeleteRequest;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class MinioService{
    private final MinioClient minioClient;
    private final PhotoRepo photoRepo;
    public boolean deletePhoto(Long id) {
        Photo photo = photoRepo.findById(id)
                .orElseThrow(()->new PhotoNotFoundException(id));
        deleteFile("photos",photo.getPath());
        photoRepo.deleteByIdNative(id);
        return true;
    }
    public InputStream getFile(String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("photos")
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteFile(String bucketName, String objectPath) {
        try {
            // Подготовка аргументов для удаления
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectPath)
                    .build();

            // Выполнение операции удаления
            minioClient.removeObject(removeObjectArgs);

            System.out.println("Файл успешно удален.");
        } catch (MinioException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveFile(MultipartFile file) {
        try {
            // Генерируем уникальное имя файла
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

            // Открываем поток для загрузки файла в MinIO
            InputStream fileStream = file.getInputStream();

            // Сохраняем файл в MinIO
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("photos")
                                .object(uniqueFileName)
                                .stream(fileStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }catch (Exception e){
                throw new CantSaveFileException();
            }

            // Закрываем поток
            fileStream.close();

            // Возвращаем уникальное имя файла
            return uniqueFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String saveTestFile(MultipartFile file) {
        try {
            // Генерируем уникальное имя файла
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

            // Открываем поток для загрузки файла в MinIO
            InputStream fileStream = file.getInputStream();

            // Сохраняем файл в MinIO
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("test")
                                .object(uniqueFileName)
                                .stream(fileStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }catch (Exception e){
                throw new CantSaveFileException();
            }

            // Закрываем поток
            fileStream.close();

            // Возвращаем уникальное имя файла
            return uniqueFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateUniqueFileName(String name) {
        String currentDate = getCurrentDateTime();
        // Генерируем UUID и добавляем оригинальное расширение файла
        return currentDate + name;
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            String res = fileName.substring(lastDotIndex);
            return res;
        }
        return "";
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy-HH-mm-ss");
        return dateFormat.format(new Date());
    }
}

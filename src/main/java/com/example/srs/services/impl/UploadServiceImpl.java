package com.example.srs.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.srs.services.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {

    public final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        if(originalFilename.contains(".") && originalFilename != null) {
            String nameWithoutExtension = originalFilename.substring(0,originalFilename.lastIndexOf(".")); // tanphat.png -> tanphat
            fileName = nameWithoutExtension.replace(" ", "-")+"-"+UUID.randomUUID();
        }
        Map options = ObjectUtils.asMap(
                "public_id",fileName
        );

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),options);
            log.info("Uploaded file successfully");
            log.warn("Quota full");
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            log.error("upload file error",e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String url) {
        String publicId = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
        try{
            Map result =
                    cloudinary.uploader().destroy(
                            publicId,
                            ObjectUtils.emptyMap()
                    );
            log.info(
                    "Cloudinary delete result: {}",
                    result.get("result")
            );
        } catch (IOException e){
            log.error("Xoá ảnh Cloudinary bị lỗi", e);
            throw new RuntimeException(
                    "Không thể xóa ảnh",
                    e
            );
        }
    }

}

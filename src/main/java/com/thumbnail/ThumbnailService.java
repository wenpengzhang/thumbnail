package com.thumbnail;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ThumbnailService {
    public static final int WIDTH=500;
    public static final int HEIGHT=500;
    public String thumbnail(CommonsMultipartFile file,String uploadPath,String realUploadPath){
        try {
            String des = realUploadPath+"/thum_"+file.getOriginalFilename();
            Thumbnails.of(file.getInputStream()).size(WIDTH, HEIGHT).toFile(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadPath+"/thum_"+file.getOriginalFilename();
    }
}

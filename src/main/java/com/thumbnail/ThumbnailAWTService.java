package com.thumbnail;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
@Service
public class ThumbnailAWTService {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public String thumbnail(CommonsMultipartFile file, String uploadPath, String realUploadPath) {
        OutputStream os = null;
        try {
            String des = realUploadPath + "/thum_" + file.getOriginalFilename();
            os = new FileOutputStream(des);

            Image image = ImageIO.read(file.getInputStream());
            int width = image.getWidth(null);// 原图宽度
            int height = image.getHeight(null);// 原图高度

            // 宽度缩略比例
            int rate1 = width / WIDTH;
            // 高度缩略比例
            int rate2 = height / HEIGHT;

            int rate = 0;
            if (rate1 > rate2) {// 宽度缩略比例大于高度缩略比例，使用宽度缩略比例
                rate = rate1;
            } else {
                rate = rate2;
            }
            // 计算缩略图最终的宽度和高度
            int newWidth = width / rate;
            int newHeight = height / rate;

            BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, image.SCALE_SMOOTH), 0, 0, null);

            // image/jpeg
            String imageType = file.getContentType().substring(file.getContentType().indexOf("/") + 1);
            ImageIO.write(bufferedImage, imageType, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uploadPath + "/" + file.getOriginalFilename();
    }

}

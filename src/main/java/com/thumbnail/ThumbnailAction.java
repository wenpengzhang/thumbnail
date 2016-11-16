package com.thumbnail;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ThumbnailAction {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ThumbnailService thumbnailService;
    @Autowired
    private ThumbnailAWTService thumbnailAWTService;

    @RequestMapping(value = "/thumbnail")
    public ModelAndView thumbnail(@RequestParam("image") CommonsMultipartFile file, HttpSession session) throws Exception {
        System.out.println("进入执行！");
        // 上传相对路径
        String uploadPath = "/images";
        // 上传的绝对路径
        String realUploadPath = session.getServletContext().getRealPath(uploadPath);
        // 原图的路径
        String imageUrl = uploadService.uploadImage(file, uploadPath, realUploadPath);
        // 缩略图的路径
        String thumImageUrl = thumbnailService.thumbnail(file, uploadPath, realUploadPath);

        ModelAndView ret = new ModelAndView();
        ret.addObject("imageURL", imageUrl);
        ret.addObject("thumbImageURL", thumImageUrl);
        ret.setViewName("/thumbnail");
        return ret;
    }
}

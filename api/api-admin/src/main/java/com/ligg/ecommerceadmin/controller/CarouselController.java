package com.ligg.ecommerceadmin.controller;

import com.ligg.dto.admin.CarouselDto;
import com.ligg.ecommerceadmin.service.CarouselService;
import com.ligg.service.FileService;
import com.ligg.statuEnum.BusinessStates;
import com.ligg.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Ligg
 * @Time 2025/9/23
 *
 * 轮播图控制器
 **/
@RestController
@RequestMapping("/api/admin/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private FileService fileService;
    /**
     * 上传轮播图数据
     */
    @PostMapping("/upload")
    public Response<String> upload(@RequestBody CarouselDto carousel) {

        int res = carouselService.upload(carousel);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 上传轮播图图片
     */
    @PostMapping("/file")
    public Response<String> uploadFile(MultipartFile file) {
        String url = fileService.uploadImage(file);
        return Response.success(BusinessStates.SUCCESS,url);
    }
}

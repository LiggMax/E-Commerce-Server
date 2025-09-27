/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.dto.FeaturedDto;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.service.FileService;
import com.ligg.common.statuEnum.BusinessStates;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.FeaturedVo;
import com.ligg.common.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 精选商品接口
 */
@RestController
@RequestMapping("/api/admin/featured")
public class AdminFeaturedController {


    @Autowired
    private FileService fileService;

    @Autowired
    private FeaturedService featuredService;

    /**
     * 上传精选产品数据
     */
    @PostMapping
    public Response<String> uploadFeatured(FeaturedDto featured,
                                           MultipartFile imageFile
    ) {
        if (imageFile.getSize() > 1024 * 1024 * 2) {
            return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
        }
        String imagePath = fileService.uploadImage(imageFile, "/Featured");
        FeaturedEntity featuredEntity = new FeaturedEntity();
        BeanUtils.copyProperties(featured, featuredEntity);
        featuredEntity.setRating(new Random().nextInt(5,11));
        featuredEntity.setImagePath(imagePath);
        featuredEntity.setCreatedAt(LocalDateTime.now());
        featuredService.saveFeatured(featuredEntity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 获取精选商品列表
     */
    @GetMapping
    public Response<PageVo<FeaturedVo>> getFeatured(
            Long pageNumber,
            Long pageSize
    ) {
        PageVo<FeaturedEntity> featuredList = featuredService.Pagelist(pageNumber,pageSize);
        PageVo<FeaturedVo> pageVo = new PageVo<>();
        pageVo.setPages(featuredList.getPages());
        pageVo.setTotal(featuredList.getTotal());
        pageVo.setList(featuredList.getList().stream().map(featured -> {
            FeaturedVo featuredVo = new FeaturedVo();
            BeanUtils.copyProperties(featured, featuredVo);
            return featuredVo;
        }).collect(Collectors.toList()));
        return Response.success(BusinessStates.SUCCESS, pageVo);
    }
}

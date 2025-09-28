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
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.FeaturedVo;
import com.ligg.common.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
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

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    @Autowired
    private ImageUtil imageUtil;

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
        featuredEntity.setRating(new Random().nextInt(5, 11));
        featuredEntity.setImagePath(imagePath);
        featuredEntity.setCreatedAt(LocalDateTime.now());
        featuredEntity.setUpdatedAt(LocalDateTime.now());
        featuredService.saveFeatured(featuredEntity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 编辑精选产品数据
     */
    @PutMapping
    public Response<String> updateFeatured(FeaturedDto featured,
                                           MultipartFile imageFile) {
        FeaturedEntity featuredEntity = new FeaturedEntity();
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 1024 * 1024 * 2) {
                return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
            }
            String imagePath = fileService.uploadImage(imageFile, "/Featured");

            //上传封面成功后异步删除旧图片
            if (imagePath != null) {
                FeaturedEntity featuredData = featuredService.getById(featured.getId());
                String dataImagePath = IMAGE_PATH + featuredData.getImagePath().replace("/api/image", "");
                fileService.deleteFileAsync(dataImagePath);
            }
            featuredEntity.setImagePath(imagePath);
        }
        BeanUtils.copyProperties(featured, featuredEntity);
        featuredEntity.setUpdatedAt(LocalDateTime.now());
        featuredService.updateById(featuredEntity);
        //featuredService.updateFeaturedById(featuredEntity);
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
        PageVo<FeaturedEntity> featuredList = featuredService.Pagelist(pageNumber, pageSize);
        PageVo<FeaturedVo> pageVo = new PageVo<>();
        pageVo.setPages(featuredList.getPages());
        pageVo.setTotal(featuredList.getTotal());
        pageVo.setList(featuredList.getList().stream().map(featured -> {
            FeaturedVo featuredVo = new FeaturedVo();
            BeanUtils.copyProperties(featured, featuredVo);
            featuredVo.setImages(imageUtil.getImagePath(featured.getImagePath()));
            return featuredVo;
        }).collect(Collectors.toList()));
        return Response.success(BusinessStates.SUCCESS, pageVo);
    }

    /**
     * 删除精选商品
     */
    @DeleteMapping("/{id}")
    public Response<String> deleteFeatured(@PathVariable Long id) {
        FeaturedEntity featured = featuredService.getById(id);
        if (featured == null) {
            return Response.error(BusinessStates.NOT_FOUND);
        }
        String imagePath = IMAGE_PATH + featured.getImagePath().replace("/api/image", "");
        //异步删除
        fileService.deleteFileAsync(imagePath);
        featuredService.removeById(id);
        return Response.success(BusinessStates.SUCCESS);
    }
}

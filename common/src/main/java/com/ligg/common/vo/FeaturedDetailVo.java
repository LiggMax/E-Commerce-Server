package com.ligg.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/30
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeaturedDetailVo extends FeaturedVo{
    private String description;
//    private List<Images> images;

//    @Getter
//    @Setter
//    public static class Images {
//        private Integer sort;
//        private String imageUrl;
//    }
}

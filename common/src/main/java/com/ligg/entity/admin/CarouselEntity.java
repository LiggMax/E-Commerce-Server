package com.ligg.entity.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/9/23
 * 轮播图实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("carousel")
public class CarouselEntity {
    private Integer id;
}

/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
package com.ligg.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecDto {

    /**
     * 商品id
     */
    @NotNull
    private String productId;

    /**
     * 规格名称
     */
    @NotNull
    private String name;

    /**
     * 排序
     */
    @NotNull
    private Integer sort;
}

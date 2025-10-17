/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.PayType;
import com.ligg.common.module.entity.OrderEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDto extends OrderEntity {
   private Integer quantity;
}

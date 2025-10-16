/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("area")
public class AreaEntity {
    @TableId
    private Integer areaId;
    private Integer parentId;
    private String name;
}

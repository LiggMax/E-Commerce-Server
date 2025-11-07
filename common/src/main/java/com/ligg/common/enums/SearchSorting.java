package com.ligg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ligg
 * @create_time 2025/11/6 18:48
 * @update_time 2025/11/6 18:48
 **/
@Getter
@AllArgsConstructor
public enum SearchSorting {
    DEFAULT("默认排序"),
    PRICE("价格排序"),
    RATING("评分排序"),
    HOT("热度排序");

    private final String value;
}

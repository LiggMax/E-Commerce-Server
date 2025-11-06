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
    //价格排序
    PRICE_ASC("价格升序"),
    RATING_ASC("评分升序"),
    HOT_ASC("热度升序");

    private final String value;
}

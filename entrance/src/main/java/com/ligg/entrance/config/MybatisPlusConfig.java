/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
package com.ligg.entrance.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlus 配置类
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 阻止全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(500L);//最大单页限制数量
        paginationInnerInterceptor.setOverflow(true);// 超出最大页数后回到第一页
        interceptor.addInnerInterceptor(paginationInnerInterceptor);// 如果配置多个插件, 切记分页最后添加
        return interceptor;
    }
}

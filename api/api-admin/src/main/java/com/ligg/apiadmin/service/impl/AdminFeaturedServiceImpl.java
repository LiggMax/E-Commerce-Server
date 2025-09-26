/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.apiadmin.service.impl;

import com.ligg.apiadmin.mapper.AdminFeaturedMapper;
import com.ligg.apiadmin.service.AdminFeaturedService;
import com.ligg.common.entity.FeaturedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminFeaturedServiceImpl implements AdminFeaturedService {

    @Autowired
    private AdminFeaturedMapper adminFeaturedMapper;
    @Override
    public void saveFeatured(FeaturedEntity featuredEntity) {
        adminFeaturedMapper.insert(featuredEntity);
    }
}

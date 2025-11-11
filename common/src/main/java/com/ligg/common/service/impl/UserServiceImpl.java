/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.constants.Constant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.exception.OrderException;
import com.ligg.common.mapper.product.productFavoriteMapper;
import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.module.entity.ProductFavoriteEntity;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.module.vo.FavoriteVo;
import com.ligg.common.service.FileService;
import com.ligg.common.service.UserService;
import com.ligg.common.service.product.ProductService;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.RedisUtil;
import com.ligg.common.module.vo.UserInfoVo;
import com.ligg.common.utils.ThreadLocalUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    private final RedisUtil redisUtil;

    private final UserMapper userMapper;

    private final productFavoriteMapper productFavoriteMapper;

    private final ProductService productService;

    private final FileService fileService;

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    /**
     * 注册账号
     *
     * @param account  账号
     * @param password 密码
     */
    @Override
    public void register(String account, String password) {

        String encryptPassword = BCryptUtil.encrypt(password);
        String nickName = "user_" + UUID.randomUUID().toString().substring(0, 6);
        LocalDateTime now = LocalDateTime.now();

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(encryptPassword);
        userEntity.setAccount(account);
        userEntity.setNickName(nickName);
        userEntity.setCreateTime(now);
        userEntity.setAvatar("/");
        userMapper.insert(userEntity);
    }

    /**
     * 根据账号获取用户信息
     */
    @Override
    public UserEntity getUserInfoByEmail(String email) {
        return userMapper.selectOne(new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getEmail, email));
    }

    /**
     * 根据用户id获取用户信息
     */
    @Override
    public UserInfoVo getUserInfo() {
        Map<String, Object> userObject = ThreadLocalUtil.get();
        String userId = (String) userObject.get(UserConstant.USER_ID);
        UserEntity redisUserInfo = getRedisUserInfo(userId);
        UserInfoVo userInfoVo = new UserInfoVo();

        if (redisUserInfo == null) {
            UserEntity userEntity = userMapper.selectById(userId);
            if (userEntity != null) {
                redisUtil.set(UserConstant.USER_INFO + ":" + userId, userEntity, 1);
                BeanUtils.copyProperties(userEntity, userInfoVo);
            }
            return userInfoVo;
        }

        BeanUtils.copyProperties(redisUserInfo, userInfoVo);
        return userInfoVo;
    }

    /**
     * 扣减用户余额
     */
    @Override
    @Transactional
    public void debit(@NotNull BigDecimal amount) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        UserEntity userEntity = userMapper.selectById(userId);
        BigDecimal accountBalance = userEntity.getAccountBalance();
        if (accountBalance.compareTo(amount) < 0) {
            throw new OrderException("余额不足");
        }
        if (!(userMapper.debitBalance(userId, amount) > 0)) {
            throw new RuntimeException("扣减用户余额失败");
        }
    }

    /**
     * 更新用户头像
     *
     * @return 头像访问路径
     */
    @Override
    public String updateAvatar(MultipartFile avatarFile, String userId) {
        UserEntity userInfo = userMapper.selectById(userId);
        String userAvatarUrl = userInfo.getAvatar();
        //如果头像不 != null || "/" 则删除旧头像
        if (userAvatarUrl != null && !userAvatarUrl.equals("/")) {
            fileService.deleteMinioFile(userAvatarUrl);
        }
        return fileService.minioFileUpload(avatarFile, Constant.AVATAR_FILE_PATH);
    }

    /**
     * 更新用户信息
     */
    @Override
    public int updateUserInfo(UserEntity userEntity) {
        if (userEntity.getPassword() != null) {
            userEntity.setPassword(BCryptUtil.encrypt(userEntity.getPassword()));
        }
        int number = userMapper.updateUserInfo(userEntity);
        if (number > 0) {
            String userKey = UserConstant.USER_INFO + ":" + userEntity.getUserId();
            redisUtil.del(userKey);
        }
        return number;
    }

    /**
     * 充值
     *
     * @param amount 金额
     * @param userId 用户id
     * @return 充值结果
     */
    @Override
    public int recharge(BigDecimal amount, String userId) {
        String userKey = UserConstant.USER_INFO + ":" + userId;
        redisUtil.del(userKey);
        return userMapper.recharge(amount, userId);
    }

    /**
     * 商品收藏
     *
     * @param productId  商品id
     * @param isFavorite 是否收藏
     * @return 添加结果
     */
    @Override
    public int productFavorite(Long productId, boolean isFavorite) {
        ProductEntity product = productService.getById(productId);
        if (product == null) {
            throw new OrderException("商品不存在");
        }

        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);

        if (isFavorite) {
            if (productFavoriteMapper.selectOne(new LambdaQueryWrapper<ProductFavoriteEntity>()
                    .eq(ProductFavoriteEntity::getUserId, userId)
                    .eq(ProductFavoriteEntity::getProductId, productId)) != null) {
                throw new OrderException("商品已收藏");
            }
            ProductFavoriteEntity productFavorite = new ProductFavoriteEntity();
            productFavorite.setProductId(productId);
            productFavorite.setUserId(userId);
            productFavorite.setCreateTime(LocalDateTime.now());
            return productFavoriteMapper.insert(productFavorite);
        } else {
            return productFavoriteMapper.delete(new LambdaUpdateWrapper<ProductFavoriteEntity>()
                    .eq(ProductFavoriteEntity::getUserId, userId)
                    .eq(ProductFavoriteEntity::getProductId, productId));
        }
    }

    /**
     * 根据用户id查询商品收藏是否已存在
     * 临时方案
     */
    @Override
    public boolean isProductFavoriteByUserId(String productId, String userId) {
        return productFavoriteMapper.selectOne(new LambdaQueryWrapper<ProductFavoriteEntity>()
                .eq(ProductFavoriteEntity::getProductId, productId)
                .eq(ProductFavoriteEntity::getUserId, userId)) != null;
    }

    /**
     * 获取用户收藏商品
     */
    @Override
    public List<FavoriteVo> getUserFavorite(String userId) {
        return userMapper.selectUserFavorite(userId);
    }


    private UserEntity getRedisUserInfo(String userId) {
        return (UserEntity) redisUtil.get(UserConstant.USER_INFO + ":" + userId);
    }


}

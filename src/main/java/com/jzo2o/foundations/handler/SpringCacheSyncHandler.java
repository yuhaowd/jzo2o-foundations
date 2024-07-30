package com.jzo2o.foundations.handler;

import com.jzo2o.api.foundations.dto.response.RegionSimpleResDTO;
import com.jzo2o.foundations.constants.RedisConstants;
import com.jzo2o.foundations.service.HomeService;
import com.jzo2o.foundations.service.IRegionService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SpringCacheSyncHandler
 * @Description
 * @Author lidaopang
 * @Date 2024/7/29 下午4:33
 * @Version 1.0
 */

@Slf4j
@Component
public class SpringCacheSyncHandler {


    @Resource
    private IRegionService regionService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private HomeService homeService;


    @XxlJob(value = "activeRegionCacheSync")
    public void activeRegionCacheSync() {
        // 1.清理缓存
        log.info(">>>>>>>>>>开始进行缓存同步，更新已启动区域");
        String key = RedisConstants.CacheName.JZ_CACHE + "::ACTIVE_REGIONS";
        redisTemplate.delete(key);

        // 2.刷新缓存
        List<RegionSimpleResDTO> regionSimpleResDTOS = regionService.queryActiveRegionListCache();

        regionSimpleResDTOS.forEach(item -> {
            //区域id
            Long regionId = item.getId();
            //删除该区域下的首页服务列表
            String serve_type_key = RedisConstants.CacheName.SERVE_ICON + "::" + regionId;
            redisTemplate.delete(serve_type_key);
            homeService.queryServeIconCategoryByRegionIdCache(regionId);
            //删除该区域下的服务类型列表
            String serve_type_list_key = RedisConstants.CacheName.SERVE_TYPE + "::" + regionId;
            redisTemplate.delete(serve_type_list_key);
            homeService.queryServeTypeListByRegionIdCache(regionId);
        });
        log.info(">>>>>>>>更新已启用区域完成");
    }
}

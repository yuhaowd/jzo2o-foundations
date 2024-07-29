package com.jzo2o.foundations.handler;

import com.jzo2o.foundations.constants.RedisConstants;
import com.jzo2o.foundations.service.IRegionService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.cache.ExpirableValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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


    @XxlJob(value = "activeRegionCacheSync")
    public void activeRegionCacheSync() {


        // 1.清理缓存
        log.info(">>>>>>>>>>开始进行缓存同步，更新已启动区域");
        String key = RedisConstants.CacheName.JZ_CACHE + "::ACTIVE_REGIONS";
        redisTemplate.delete(key);

        // 2.刷新缓存
        regionService.queryActiveRegionListCache();
        log.info(">>>>>>>>更新已启用区域完成");




    }
}

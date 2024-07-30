package com.jzo2o.foundations.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jzo2o.common.expcetions.BadRequestException;
import com.jzo2o.foundations.constants.RedisConstants;
import com.jzo2o.foundations.enums.FoundationStatusEnum;
import com.jzo2o.foundations.mapper.ServeMapper;
import com.jzo2o.foundations.mapper.ServeTypeMapper;
import com.jzo2o.foundations.model.domain.Region;
import com.jzo2o.foundations.model.dto.response.ServeAggregationSimpleResDTO;
import com.jzo2o.foundations.model.dto.response.ServeCategoryResDTO;
import com.jzo2o.foundations.model.dto.response.ServeTypeListDTO;
import com.jzo2o.foundations.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName HomeServiceImpl
 * @Description
 * @Author lidaopang
 * @Date 2024/7/29 下午5:38
 * @Version 1.0
 */


@Service
@Slf4j
public class HomeServiceImpl implements HomeService {


    @Resource
    private IServeService service;

    @Resource
    private ServeMapper serveMapper;

    @Resource
    private IRegionService regionService;

    @Resource
    private IServeTypeService serveTypeService;

    @Resource
    private ServeTypeMapper serveTypeMapper;

    @Resource
    private IServeItemService serveItemService;

    @GetMapping("/firstPageServeList")
    @ApiOperation("首页服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionId", value = "区域id", required = true, dataTypeClass = Long.class)
    })
    public List<ServeCategoryResDTO> serveCategory(@RequestParam("regionId") Long regionId) {
        if (regionId == null) {
            throw new RuntimeException("区域id不能为空");
        }

        return service.serveCategory(regionId);


    }

    @Override
    @Caching(
            cacheable = {
                    //result为null时,属于缓存穿透情况，缓存时间30分钟
                    @Cacheable(value = RedisConstants.CacheName.SERVE_ICON, key = "#regionId", unless = "#result.size() != 0", cacheManager = RedisConstants.CacheManager.THIRTY_MINUTES),
                    //result不为null时,永久缓存
                    @Cacheable(value = RedisConstants.CacheName.SERVE_ICON, key = "#regionId", unless = "#result.size() == 0", cacheManager = RedisConstants.CacheManager.FOREVER)
            }
    )
    public List<ServeCategoryResDTO> queryServeIconCategoryByRegionIdCache(Long regionId) {
        //1.校验当前城市是否为启用状态
        Region region = regionService.getById(regionId);
        if (ObjectUtil.isEmpty(region) || ObjectUtil.equal(FoundationStatusEnum.DISABLE.getStatus(), region.getActiveStatus())) {
            return Collections.emptyList();
        }
        List<ServeCategoryResDTO> list = serveMapper.queryServeCategory(regionId);

        // 使用stream api来获得list中的前两个元素，每个元素只要前四条数据
        return list.stream().limit(2).map(item -> {
            item.setServeResDTOList(item.getServeResDTOList().stream().limit(4).collect(Collectors.toList()));
            item.setServeResDTOList(item.getServeResDTOList().stream().limit(4).collect(Collectors.toList()));
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = RedisConstants.CacheName.SERVE_TYPE, key = "#regionId", cacheManager = RedisConstants.CacheManager.FOREVER)
    public List<ServeTypeListDTO> queryServeTypeListByRegionIdCache(Long regionId) {

        return serveTypeMapper.queryServeTypeListByRegionId(regionId);
    }

    @Override
    public List<ServeAggregationSimpleResDTO> queryHotServeList(Long regionId) {

        List<ServeAggregationSimpleResDTO> serveAggregationSimpleResDTOS = service.queryHotServe(regionId);

        List<ServeAggregationSimpleResDTO> res = serveItemService.queryHotServeItem(serveAggregationSimpleResDTOS);

        return res;
    }

    @Override
    @Cacheable(value = RedisConstants.CacheName.SERVE, key = "#id", cacheManager = RedisConstants.CacheManager.ONE_DAY)
    public ServeAggregationSimpleResDTO queryServeAggregationSimpleResDTOById(Long id) {


        ServeAggregationSimpleResDTO serveAggregationSimpleResDTO = serveMapper.queryServeAggregationSimpleResDTOById(id);

        if (serveAggregationSimpleResDTO == null) {
            throw new BadRequestException("服务不存在");
        }

        return serveAggregationSimpleResDTO;



    }
}

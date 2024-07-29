package com.jzo2o.foundations.service.impl;

import com.jzo2o.foundations.model.dto.response.ServeCategoryResDTO;
import com.jzo2o.foundations.service.HomeService;
import com.jzo2o.foundations.service.IServeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

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


}

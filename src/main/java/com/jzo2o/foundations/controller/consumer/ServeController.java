package com.jzo2o.foundations.controller.consumer;

import com.jzo2o.foundations.model.dto.response.ServeAggregationSimpleResDTO;
import com.jzo2o.foundations.model.dto.response.ServeCategoryResDTO;
import com.jzo2o.foundations.model.dto.response.ServeTypeListDTO;
import com.jzo2o.foundations.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName FirstPageServeController
 * @Description
 * @Author lidaopang
 * @Date 2024/7/29 下午5:37
 * @Version 1.0
 */

@RestController
@RequestMapping("/customer/serve")
public class ServeController {


    @Resource
    private HomeService homeService;

    @GetMapping("/firstPageServeList")
    public List<ServeCategoryResDTO> serveCategory(Long regionId) {

        if (regionId == null) {
            throw new RuntimeException("区域id不能为空");
        }
        return homeService.queryServeIconCategoryByRegionIdCache(regionId);
    }


    @GetMapping("/serveTypeList")
    public List<ServeTypeListDTO> serveTypeList(Long regionId) {
        if (regionId == null) {
            throw new RuntimeException("区域id不能为空");
        }
        return homeService.queryServeTypeListByRegionIdCache(regionId);
    }


    @GetMapping("/hotServeList")
    public List<ServeAggregationSimpleResDTO> queryHotServeList(Long regionId) {

        if (regionId == null) {
            throw new RuntimeException("区域id不能为空");
        }
        return homeService.queryHotServeList(regionId);
    }

    @GetMapping("/{id}")
    public ServeAggregationSimpleResDTO queryServeAggregationSimpleResDTOById(@PathVariable("id") Long id) {
        if (id == null) {
            throw new RuntimeException("服务id不能为空");
        }
        return homeService.queryServeAggregationSimpleResDTOById(id);
    }
}

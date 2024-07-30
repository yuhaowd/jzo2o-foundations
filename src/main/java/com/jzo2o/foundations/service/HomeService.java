package com.jzo2o.foundations.service;

import com.jzo2o.foundations.model.dto.response.ServeAggregationSimpleResDTO;
import com.jzo2o.foundations.model.dto.response.ServeCategoryResDTO;
import com.jzo2o.foundations.model.dto.response.ServeTypeListDTO;
import com.jzo2o.foundations.model.dto.response.ServeTypeResDTO;

import java.util.List;

/**
 * @ClassName HomeService
 * @Description
 * @Author lidaopang
 * @Date 2024/7/29 下午5:37
 * @Version 1.0
 */

public interface HomeService {


    List<ServeCategoryResDTO> queryServeIconCategoryByRegionIdCache(Long regionId);


    List<ServeTypeListDTO> queryServeTypeListByRegionIdCache(Long regionId);

    List<ServeAggregationSimpleResDTO> queryHotServeList(Long regionId);

    ServeAggregationSimpleResDTO queryServeAggregationSimpleResDTOById(Long id);
}

package com.jzo2o.foundations.mapper;

import com.jzo2o.foundations.model.domain.Region;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzo2o.foundations.model.dto.request.RegionPageQueryReqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 区域表 Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2023-07-03
 */
public interface RegionMapper extends BaseMapper<Region> {

    int countRegionByCityCode(String cityCode);

    void updateBySelf(Long id, String managerName, String managerPhone);

    List<Region> pageQuery(RegionPageQueryReqDTO regionPageQueryReqDTO);
}

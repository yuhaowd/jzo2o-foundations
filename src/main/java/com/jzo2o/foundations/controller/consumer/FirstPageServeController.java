package com.jzo2o.foundations.controller.consumer;

import com.jzo2o.foundations.model.dto.response.ServeCategoryResDTO;
import com.jzo2o.foundations.service.IServeService;
import org.springframework.web.bind.annotation.GetMapping;
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
public class FirstPageServeController {


    @Resource
    private IServeService service;

    @GetMapping("/firstPageServeList")
    public List<ServeCategoryResDTO> serveCategory(Long regionId) {
        if (regionId == null) {
            throw new RuntimeException("区域id不能为空");
        }
        return service.serveCategory(regionId);
    }


}

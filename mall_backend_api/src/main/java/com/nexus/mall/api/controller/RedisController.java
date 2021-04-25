package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.service.RedisService;
import com.nexus.mall.service.backend.BackendResourceService;
import com.nexus.mall.util.BloomFilterHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class RedisController {
    @Autowired
    private BloomFilterHelper bloomFilterHelper;

    @Autowired
    private BackendResourceService resourceService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/redis/bloomFilter")
    @ApiOperation("redis布隆过滤器数据添加")
    public Object redisBloomFilter(){
        List<Long> allResourceId = (List<Long>) resourceService.listAll().stream().map(s -> s.getId());
        for (Long id : allResourceId) {
            //将所有的资源id放入到布隆过滤器中
            redisService.addByBloomFilter(bloomFilterHelper,"bloom",id);
        }
        return ServerResponse.success();
    }

    @GetMapping("/redis/bloomFilter/resourceId")
    @ApiOperation("redis布隆过滤器资源测试")
    public ServerResponse redisBloomFilterResourceId(@RequestParam("resourceId")String resourceId){
        boolean mightContain = redisService.includeByBloomFilter(bloomFilterHelper,"bloom",resourceId);
        if (!mightContain){
            return ServerResponse.failed();
        }
        return ServerResponse.success();
    }
}

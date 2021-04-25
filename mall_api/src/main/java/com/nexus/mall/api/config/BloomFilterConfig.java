package com.nexus.mall.api.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.nexus.mall.util.BloomFilterHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterConfig {
    //  初始化布隆过滤器，放入到spring容器里面
    @Bean
    public BloomFilterHelper<String> initBloomFilterHelper() {
        return new BloomFilterHelper<>(
                (Funnel<String>) (from, into) ->
                        into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8),
                1000000, 0.01
        );
    }
}

package com.nexus.mall.service.impl;

import com.nexus.mall.dao.CarouselMapper;
import com.nexus.mall.pojo.Carousel;
import com.nexus.mall.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**

* @Description:    轮播图Service实现类

* @Author:         Nexus

* @CreateDate:     2020/9/13 21:54

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/13 21:54

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * queryAll
     *
     * @param isShow
     * @return : void
     * @Author : Nexus
     * @Description : 查询轮播图列表
     * @Date : 2020/9/13 21:55
     * @Param : isShow
     */
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        return carouselMapper.selectByExample(example);
    }
}

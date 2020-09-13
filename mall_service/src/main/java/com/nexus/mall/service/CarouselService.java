package com.nexus.mall.service;

import com.nexus.mall.pojo.Carousel;

import java.util.List;

/**

* @Description:    轮播图Service类

* @Author:         Nexus

* @CreateDate:     2020/9/13 21:54

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/13 21:54

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface CarouselService {
    /**
     * queryAll
     * @Author : Nexus
     * @Description : 查询轮播图列表
     * @Date : 2020/9/13 21:55
     * @Param : isShow
     * @return : void
     **/
    public List<Carousel> queryAll(Integer isShow);


    
}

package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.pojo.Carousel;
import com.nexus.mall.pojo.Category;
import com.nexus.mall.pojo.vo.CategoryVO;
import com.nexus.mall.pojo.vo.NewItemsVO;
import com.nexus.mall.service.CarouselService;
import com.nexus.mall.service.CategoryService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**

* @Description:    首页相关接口

* @Author:         Nexus

* @CreateDate:     2020/9/13 22:01

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/13 22:01

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Slf4j
@RestController
@RequestMapping("index")
@Api(value = "首页相关接口",tags = {"首页展示的相关接口"})
public class IndexController {
    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public ServerResponse carousel() {
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return ServerResponse.success(list);
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */

    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public ServerResponse cats() {
        List<Category> list = categoryService.queryAllRootLevelCat();
        return ServerResponse.success(list);
    }

    @ApiOperation(value = "根据一级分类id查询下一层子分类信息", notes = "根据一级分类id查询下一层子分类信息", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public ServerResponse subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return ServerResponse.failed("分类不存在");
        }

        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return ServerResponse.success(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public ServerResponse sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return ServerResponse.failed("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return ServerResponse.success(list);
    }
}

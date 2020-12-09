package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.common.util.RedisOperator;
import com.nexus.mall.pojo.Carousel;
import com.nexus.mall.pojo.Category;
import com.nexus.mall.pojo.vo.user.CategoryVO;
import com.nexus.mall.pojo.vo.user.NewItemsVO;
import com.nexus.mall.service.CarouselService;
import com.nexus.mall.service.CategoryService;
import com.nexus.mall.util.JsonUtils;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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
@Validated
@RestController
@RequestMapping("index")
@Api(value = "首页相关接口",tags = {"首页展示的相关接口"})
public class IndexController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisOperator redisOperator;

    /**
     * 1. 后台运营系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
     * 2. 定时重置，比如每天凌晨三点重置
     * 3. 每个轮播图都有可能是一个广告，每个广告都会有一个过期时间，过期了，再重置
     */
    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping(value = "/carousel")
    public ServerResponse carousel() {
        //引入redis缓存
        List<Carousel> list;
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)) {
            list = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(list));
        }else {
            list = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }
        return ServerResponse.success(list);
    }



    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */

    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping(value = "/cats")
    public ServerResponse cats() {
        //引入redis缓存
        List<Category> list;
        String catStr = redisOperator.get("cat");
        if (StringUtils.isBlank(catStr)){
            list = categoryService.queryAllRootLevelCat();
            redisOperator.set("cat", JsonUtils.objectToJson(list));
        }else {
            list = JsonUtils.jsonToList(catStr, Category.class);
        }
        return ServerResponse.success(list);
    }

    @ApiOperation(value = "根据一级分类id查询下一层子分类信息", notes = "根据一级分类id查询下一层子分类信息", httpMethod = "GET")
    @GetMapping(value = "/subCat/{rootCatId}")
    public ServerResponse subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable
            @NotNull(message = "分类不存在")
                    Integer rootCatId) {

        List<CategoryVO> list;
        String subCatStr = redisOperator.get("subCat:" + rootCatId);
        if (StringUtils.isBlank(subCatStr)){
            list = categoryService.getSubCatList(rootCatId);
            /* Redis 防止缓存穿透
              查询的key在redis中不存在，
              对应的id在数据库也不存在，
              此时被非法用户进行攻击，大量的请求会直接打在db上，
              造成宕机，从而影响整个系统，
              这种现象称之为缓存穿透。
              解决方案：把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             */
            if (list != null && list.size() > 0) {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list));
            } else {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list), 5*60);
            }
        }else {
            list = JsonUtils.jsonToList(subCatStr, CategoryVO.class);
        }
        return ServerResponse.success(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping(value = "/sixNewItems/{rootCatId}")
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

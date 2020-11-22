package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.Items;
import com.nexus.mall.pojo.ItemsImg;
import com.nexus.mall.pojo.ItemsParam;
import com.nexus.mall.pojo.ItemsSpec;
import com.nexus.mall.pojo.vo.user.CommentLevelCountsVO;
import com.nexus.mall.pojo.vo.user.ItemInfoVO;
import com.nexus.mall.pojo.vo.user.ShopcartVO;
import com.nexus.mall.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**

* @Description:    商品详情接口

* @Author:         Nexus

* @CreateDate:     2020/9/14 22:00

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/14 22:00

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@Validated
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController{
    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public ServerResponse info(@NotBlank(message = "商品不能为空") @ApiParam(name = "itemId", value = "商品id", required = true) @PathVariable String itemId) {
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return ServerResponse.success(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public ServerResponse commentLevel(@NotBlank @ApiParam(name = "itemId", value = "商品id", required = true) @RequestParam String itemId) {
        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
        return ServerResponse.success(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public ServerResponse comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam
                    String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam(value = "level",required = false)
                    Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page",defaultValue = "1",required = false)
                    Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)
                    Integer pageSize) {

        if (StringUtils.isBlank(itemId)) { return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR); }
        if (page == null) { page = 1; }
        if (pageSize == null) { pageSize = COMMON_PAGE_SIZE; }
        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);
        return ServerResponse.success(grid);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public ServerResponse search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam (value = "keywords") String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam(value = "sort",required = false) String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize",defaultValue = "20",required = false) Integer pageSize) {

        if (StringUtils.isBlank(keywords)) { return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR); }
        if (page == null) { page = 1; }
        if (pageSize == null) { pageSize = PAGE_SIZE; }
        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return ServerResponse.success(grid);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public ServerResponse catItems(
            @ApiParam(name = "catId", value = "三级分类id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam(value = "sort",required = false) String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {

        if (catId == null) { return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR); }
        if (page == null) { page = 1; }
        if (pageSize == null) { pageSize = PAGE_SIZE; }
        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);
        return ServerResponse.success(grid);
    }

    /**
     * refresh
     * @Author : Nexus
     * @Description : 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
     * @Date : 2020/9/16 20:55
     * @Param : itemSpecIds
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public ServerResponse refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
            @RequestParam String itemSpecIds) {

        if (StringUtils.isBlank(itemSpecIds)) {
            return ServerResponse.success();
        }

        List<ShopcartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);

        return ServerResponse.success(list);
    }
}

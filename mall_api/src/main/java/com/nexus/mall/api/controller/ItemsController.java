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
import com.nexus.mall.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController{
    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public ServerResponse info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR);
        }

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
    public ServerResponse commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR);
        }

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return ServerResponse.success(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public ServerResponse comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(itemId)) {
            return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId,
                level,
                page,
                pageSize);

        return ServerResponse.success(grid);
    }
}

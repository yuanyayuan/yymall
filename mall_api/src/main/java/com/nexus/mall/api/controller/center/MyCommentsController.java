package com.nexus.mall.api.controller.center;

import com.nexus.mall.api.controller.BaseController;
import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.pojo.OrderItems;
import com.nexus.mall.pojo.Orders;
import com.nexus.mall.pojo.bo.user.center.OrderItemsCommentBO;
import com.nexus.mall.service.center.MyCommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController extends BaseController {
    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public ServerResponse pending(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam
                    String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam
                    String orderId) {

        // 判断用户和订单是否关联
        ServerResponse checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断该笔订单是否已经评价过，评价过了就不再继续
        Orders myOrder = (Orders)checkResult.getData();
        if (myOrder.getIsComment().equals(YesOrNo.YES.type)) {
            return ServerResponse.failed("该笔订单已经评价");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return ServerResponse.success(list);
    }


    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public ServerResponse saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {
        // 判断用户和订单是否关联
        ServerResponse checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return ServerResponse.failed("评论内容不能为空！");
        }
        myCommentsService.saveComments(orderId, userId, commentList);
        return ServerResponse.success();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    public ServerResponse query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @NotBlank(message = "用户Id不能为空")
            @RequestParam
                    String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page",defaultValue = "1",required = false)
                    Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)
                    Integer pageSize) {
        PagedGridResult grid = myCommentsService.queryMyComments(userId, page, pageSize);
        return ServerResponse.success(grid);
    }
}

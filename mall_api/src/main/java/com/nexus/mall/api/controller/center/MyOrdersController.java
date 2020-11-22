package com.nexus.mall.api.controller.center;

import com.nexus.mall.api.controller.BaseController;
import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.pojo.vo.user.OrderStatusCountsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(value = "用户中心我的订单", tags = {"用户中心我的订单相关接口"})
@Validated
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {
    @ApiOperation(value = "获得订单状态数概况", notes = "获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public ServerResponse statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @NotBlank(message = "用户id不能为空")
            @RequestParam
                    String userId) {

        OrderStatusCountsVO result = myOrdersService.getOrderStatusCounts(userId);

        return ServerResponse.success(result);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public ServerResponse query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @NotBlank(message = "用户id不能为空")
            @RequestParam
                    String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam
                    Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page",defaultValue = "1",required = false)
                    Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)
                    Integer pageSize) {

        PagedGridResult grid = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);

        return ServerResponse.success(grid);
    }


    /**
     * 商家发货没有后端，所以这个接口仅仅只是用于模拟
     * @Author : Nexus
     * @Description : 商家发货没有后端，所以这个接口仅仅只是用于模拟
     * @Date : 2020/11/22 23:17
     * @Param : orderId
     * @return : IMOOCJSONResult
     **/
    @ApiOperation(value="商家发货", notes="商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public ServerResponse deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @NotBlank(message = "订单ID不能为空")
            @RequestParam String orderId) throws Exception {

        myOrdersService.updateDeliverOrderStatus(orderId);
        return ServerResponse.success();
    }


    @ApiOperation(value="用户确认收货", notes="用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public ServerResponse confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        ServerResponse checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return ServerResponse.failed("订单确认收货失败！");
        }

        return ServerResponse.success();
    }

    @ApiOperation(value="用户删除订单", notes="用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public ServerResponse delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        ServerResponse checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return ServerResponse.failed("订单删除失败！");
        }

        return ServerResponse.success();
    }



    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
//    private IMOOCJSONResult checkUserOrder(String userId, String orderId) {
//        Orders order = myOrdersService.queryMyOrder(userId, orderId);
//        if (order == null) {
//            return IMOOCJSONResult.errorMsg("订单不存在！");
//        }
//        return IMOOCJSONResult.ok();
//    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public ServerResponse trend(
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


        PagedGridResult grid = myOrdersService.getOrdersTrend(userId, page, pageSize);

        return ServerResponse.success(grid);
    }
}

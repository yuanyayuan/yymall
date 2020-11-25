package com.nexus.mall.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.nexus.mall.common.api.PagedGridResult;
import com.nexus.mall.common.enums.YesOrNo;
import com.nexus.mall.dao.protal.ItemsCommentsMapperCustom;
import com.nexus.mall.dao.protal.OrderItemsMapper;
import com.nexus.mall.dao.protal.OrderStatusMapper;
import com.nexus.mall.dao.protal.OrdersMapper;
import com.nexus.mall.pojo.OrderItems;
import com.nexus.mall.pojo.OrderStatus;
import com.nexus.mall.pojo.Orders;
import com.nexus.mall.pojo.bo.user.center.OrderItemsCommentBO;
import com.nexus.mall.pojo.vo.user.MyCommentVO;
import com.nexus.mall.service.center.MyCommentsService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Autowired
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private Sid sid;

    /**
     * 根据订单id查询关联的商品
     *
     * @param orderId
     * @return : java.util.List<com.nexus.mall.pojo.OrderItems>
     * @Author : Nexus
     * @Description : 根据订单id查询关联的商品
     * @Date : 2020/10/15 22:41
     * @Param : orderId
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    /**
     * 保存用户的评论
     *
     * @param orderId
     * @param userId
     * @param commentList
     * @return : void
     * @Author : Nexus
     * @Description : 保存用户的评论
     * @Date : 2020/10/15 22:41
     * @Param : orderId
     * @Param : userId
     * @Param : commentList
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    /**
     * 我的评价查询 分页
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return : com.nexus.mall.common.api.PagedGridResult
     * @Author : Nexus
     * @Description : 我的评价查询 分页
     * @Date : 2020/10/15 22:41
     * @Param : userId
     * @Param : page
     * @Param : pageSize
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = RuntimeException.class)
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setterPagedGrid(list, page);
    }
}

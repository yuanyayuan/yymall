package com.nexus.mall.api.controller;

import com.nexus.mall.common.api.ResultCode;
import com.nexus.mall.common.api.ServerResponse;
import com.nexus.mall.common.util.RedisOperator;
import com.nexus.mall.pojo.Users;
import com.nexus.mall.pojo.bo.user.ShopcartBO;
import com.nexus.mall.pojo.bo.user.UserCreatBO;
import com.nexus.mall.pojo.bo.user.UserLoginBO;
import com.nexus.mall.pojo.vo.user.UsersVO;
import com.nexus.mall.service.UserService;
import com.nexus.mall.util.CookieUtils;
import com.nexus.mall.util.JsonUtils;
import com.nexus.mall.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/8 21:29

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/8 21:29

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "通行相关接口",tags = {"用于注册登录的相关接口"})
@Validated
@Slf4j
@RestController
@RequestMapping("/passport")
public class PassportController extends BaseController{
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private UserService userService;
    /**
     * UsernameIsExist
     * @Author : Nexus
     * @Description : 用户名唯一校验
     * @Date : 2020/9/6 22:01
     * @Param : username 用户名
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ApiOperation(value = "用户名唯一校验", notes = "用户名唯一校验", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ServerResponse usernameIsExist(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username){
        //查找注册的用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(username);
        if(usernameIsExist){
            return ServerResponse.failed(ResultCode.REGISTER_DUP_FAIL);
        }
        //请求成功，用户名没有重复
        return ServerResponse.success(null,"该用户不存在");
    }
    /**
     * register
     * @Author : Nexus
     * @Description : 前台用户注册
     * @Date : 2020/9/10 23:58
     * @Param : userBO
     * @Param : request
     * @Param : response
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public ServerResponse register(@Validated @RequestBody UserCreatBO userBO,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        //查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return ServerResponse.failed("用户名已经存在");
        }

        //判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return ServerResponse.failed("两次密码输入不一致");
        }

        //实现注册
        Users userResult = userService.createUser(userBO);

        //引入UserVO 不需要进行脱敏
        //setNullProperty(userResult);
        // 实现用户的redis会话
        UsersVO usersVO = conventUsersVO(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);
        synchShopcartData(userResult.getId(), request, response);
        return ServerResponse.success(null,"注册成功");
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public ServerResponse login(@Validated @RequestBody UserLoginBO userBO, HttpServletRequest request, HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        //实现登录
        Users userResult = null;
        try {
            userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            log.warn("[PassportController.login] ,{}", ExceptionUtils.getStackTrace(e));
        }
        if(userResult == null){
            return ServerResponse.loginFail();
        }

        //引入UserVO 不需要进行脱敏
        //setNullProperty(userResult);

        //redis 存入用户Token
        // 实现用户的redis会话
        UsersVO usersVO = conventUsersVO(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);
        //同步购物车数据
        synchShopcartData(userResult.getId(), request, response);
        return ServerResponse.success(userResult);
    }


    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     * @Author : Nexus
     * @Description : 注册登录成功后，同步cookie和redis中的购物车数据
     * @Date : 2020/11/30 21:49
     * @Param : userId
     * @Param : request
     * @Param : response
     * @return : void
     **/
    private void synchShopcartData(String userId, HttpServletRequest request,
                                   HttpServletResponse response) {

        /*
          1. redis中无数据，如果cookie中的购物车为空，那么这个时候不做任何处理
                          如果cookie中的购物车不为空，此时直接放入redis中
          2. redis中有数据，如果cookie中的购物车为空，那么直接把redis的购物车覆盖本地cookie
                          如果cookie中的购物车不为空，
                               如果cookie中的某个商品在redis中存在，
                               则以cookie为主，删除redis中的，
                               把cookie中的商品直接覆盖redis中（参考京东）
          3. 同步到redis中去了以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的
         */

        // 从redis中获取购物车
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);

        // 从cookie中获取购物车
        String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);

        if (StringUtils.isBlank(shopcartJsonRedis)) {
            // redis为空，cookie不为空，直接把cookie中的数据放入redis
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartStrCookie);
            }
        } else {
            // redis不为空，cookie不为空，合并cookie和redis中购物车的商品数据（同一商品则覆盖redis）
            if (StringUtils.isNotBlank(shopcartStrCookie)) {

                /*
                  1. 已经存在的，把cookie中对应的数量，覆盖redis（参考京东）
                  2. 该项商品标记为待删除，统一放入一个待删除的list
                  3. 从cookie中清理所有的待删除list
                  4. 合并redis和cookie中的数据
                  5. 更新到redis和cookie中
                 */

                List<ShopcartBO> shopcartListRedis = JsonUtils.jsonToList(shopcartJsonRedis, ShopcartBO.class);
                List<ShopcartBO> shopcartListCookie = JsonUtils.jsonToList(shopcartStrCookie, ShopcartBO.class);

                // 定义一个待删除list
                List<ShopcartBO> pendingDeleteList = new ArrayList<>();

                assert shopcartListRedis != null;
                for (ShopcartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();
                    assert shopcartListCookie != null;
                    for (ShopcartBO cookieShopcart : shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();

                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量，不累加，参考京东
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            // 把cookieShopcart放入待删除列表，用于最后的删除与合并
                            pendingDeleteList.add(cookieShopcart);
                        }

                    }
                }

                // 从现有cookie中删除对应的覆盖过的商品数据
                assert shopcartListCookie != null;
                shopcartListCookie.removeAll(pendingDeleteList);

                // 合并两个list
                shopcartListRedis.addAll(shopcartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartListRedis));
            } else {
                // redis不为空，cookie为空，直接把redis覆盖cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis, true);
            }

        }
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public ServerResponse logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // 用户退出登录，清除redis中user的会话信息
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);

        // 分布式会话中需要清除用户数据
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);
        return ServerResponse.success();
    }

    private void setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
    }
}

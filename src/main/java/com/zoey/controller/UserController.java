package com.zoey.controller;

import com.alibaba.fastjson.JSONObject;
import com.zoey.domain.User;
import com.zoey.reps.CommonResp;
import com.zoey.reps.PageResp;
import com.zoey.reps.UserLoginResp;
import com.zoey.reps.UserQueryResp;
import com.zoey.req.UserLoginReq;
import com.zoey.req.UserQueryReq;
import com.zoey.req.UserResetPasswordReq;
import com.zoey.req.UserSaveReq;
import com.zoey.service.UserService;
import com.zoey.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

// @RestController("/user") You should put it in RequestMapping
// @RequestMapping("/user") // It is also OK
@RequestMapping("user")
@RestController
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SnowFlake snowFlake;

    @GetMapping("userList")
    public CommonResp getList(@Validated UserQueryReq userQueryReq) { // 加上@Validated 开启校验参数
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        //List<UserResp> list = userService.getList(userReq);
        PageResp<UserQueryResp> list = userService.getList(userQueryReq);
        resp.setContent(list);
        resp.setMessage("Get all the users");
        return resp;
    }

    @PostMapping("save")
    public CommonResp save(@RequestBody @Validated UserSaveReq req) {
        // 保存时对密码加密，DigestUtils是spring内置的
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));

        CommonResp resp = new CommonResp();
        //List<UserResp> list = userService.getList(userReq);
        userService.save(req);
        resp.setMessage("Save the users");
        return resp;
    }

    @DeleteMapping("delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp();
        //List<UserResp> list = userService.getList(userReq);
        userService.delete(id);
        resp.setMessage("delete the users");
        return resp;
    }

    @RequestMapping("getUser")
    public User getUser(){
        return userService.getUserById(1);
    }

    // 参数要改？不使用UserSaveReq?
    @PostMapping("reset-password")
    public CommonResp resetPassword(@RequestBody @Validated UserResetPasswordReq req) {
        // 保存时对密码加密，DigestUtils是spring内置的
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));

        CommonResp resp = new CommonResp();
        //List<UserResp> list = userService.getList(userReq);
        userService.resetPassword(req);
        resp.setMessage("reset password");
        return resp;
    }


    @PostMapping("login")
    public CommonResp login(@RequestBody @Validated UserLoginReq req) {
        // 保存时对密码加密，DigestUtils是spring内置的
        // 登录的时候也应该对密码再次加密，这样跟数据库对比的时候就是一致的，否则不一致
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));

        CommonResp<UserLoginResp> resp = new CommonResp();
        UserLoginResp userLoginResp = userService.login(req);


        // 登录成功后，返回Token给前端，Token保证唯一，可以使用UUID或雪花算法或者其他算法
        // long 和 Long有什么区别，long是基本类型，Long是Object??
        Long token = snowFlake.nextId();
        LOG.info("开始生成Token{}, 放入redis ",token);
        userLoginResp.setToken(token.toString());
        // 把Token 放入redis,
        // 问题1 : redis的数据放在哪里了？为什么这里使用redis而不是放在数据库呢？
        // 问题2 : 当然redis可以设置过期时间，这是一个优点，那么思考下还有其他原因吗？因为之前听说redis缓存数据库
        // 问题3 : 序列化，那么我们应该序列化哪一个类？视频中是UserLoginResp, tried, it works. 也可以通过JSON实现
        redisTemplate.opsForValue().set(token, JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);
        // redisTemplate.opsForValue().set(token, userLoginResp, 3600 * 24, TimeUnit.SECONDS);
        resp.setContent(userLoginResp);
        resp.setMessage("login");
        return resp;
    }

}

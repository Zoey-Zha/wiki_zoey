package com.zoey.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// @RestController("/user") You should put it in RequestMapping
// @RequestMapping("/user") // It is also OK
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

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
        //List<UserResp> list = userService.getList(userReq);
        UserLoginResp userLoginResp = userService.login(req);
        resp.setContent(userLoginResp);
        resp.setMessage("login");
        return resp;
    }

}

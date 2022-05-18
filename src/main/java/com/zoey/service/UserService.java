package com.zoey.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoey.domain.User;
import com.zoey.domain.UserExample;
import com.zoey.exception.BusinessException;
import com.zoey.exception.BusinessExceptionCode;
import com.zoey.mapper.UserMapper;
import com.zoey.reps.PageResp;
import com.zoey.reps.UserLoginResp;
import com.zoey.reps.UserQueryResp;
import com.zoey.req.UserLoginReq;
import com.zoey.req.UserQueryReq;
import com.zoey.req.UserResetPasswordReq;
import com.zoey.req.UserSaveReq;
import com.zoey.util.CopyUtil;
import com.zoey.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    // private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    //    @Autowired
    @Resource
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;
    

    public PageResp<UserQueryResp> getList(UserQueryReq userQueryReq) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        // 增加动态SQL
        if (!ObjectUtils.isEmpty(userQueryReq.getLoginName())) {
            criteria.andNameLike("%" + userQueryReq.getLoginName() + "%");
        }

        // 后端分页，写在要分页的查询前面，因为它只对一个sql查询生效
        PageHelper.startPage(userQueryReq.getPage(), userQueryReq.getSize());
        List<User> userList = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        logger.info("Total rows: {}", pageInfo.getTotal());
        logger.info("Total pages: {}", pageInfo.getPages());
        logger.info("Page num: {}", pageInfo.getPageNum());
        logger.info("The whole list" + userList);

        // List<UserResp> userResps = new ArrayList<>();
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotalNum(pageInfo.getTotal());

        return pageResp;
    }

    public void save(UserSaveReq req){
        // 如何把UserSaveReq转换成User, 我一下子没有到这种方法，只想到必须转换
        User user = CopyUtil.copy(req,User.class);
        // System.out.println(user.toString());

        // 复用save实现新增新增
        if (ObjectUtils.isEmpty(req.getId())){
            // 先检查是否已存在，跟视频中用的判断方法不一样
            // ObjectUtils.isEmpty(select);
            User select = selectByLoginName(req.getLoginName());
            if (select == null) {
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // 用户已存在，这个function没有返回值，这里抛出异常，这里是自定义异常
                // 关注下为什么不用try catch,其实我不太理解, Q: 要不然太多try catch，不好统一管理
                // 抛出RuntimeException不需要try catch?
                // 我们这种方法是为了统一管理异常？是的
                // BusinessExceptionCode是这样用的啊，这种枚举真的不知道，作者说的这种方法是一个好方法吗
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // update
            // userMapper.updateByPrimaryKey(user);
            user.setLoginName(null);
            user.setPassword(null);
            // 其实不太理解：只有当user属性不为空才会被更新，否则不会更新
            // 由于上面清空了LoginName，所以它是null,无论如何也不会被更新
            userMapper.updateByPrimaryKeySelective(user);
        }

    }

    public void delete(long id){
        userMapper.deleteByPrimaryKey(id);
    }

    public User getUserById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User selectByLoginName (String loginName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        // 所以查询条件写到criteria
        criteria.andLoginNameEqualTo(loginName);
        // 固定的范式
        List<User> userList = userMapper.selectByExample(example);
        // 视频中用的方法,我是觉得没必要那么麻烦
        // CollectionUtils.isEmpty(userList);
        return userList.isEmpty()? null : userList.get(0);
    }


    public void resetPassword(UserResetPasswordReq req){
        // 如何把UserSaveReq转换成User, 我一下子没有到这种方法，只想到必须转换
        User user = CopyUtil.copy(req,User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    public UserLoginResp login (UserLoginReq userLoginReq) {
        User userDb = this.selectByLoginName(userLoginReq.getLoginName());
        if (userDb == null) {
            // 找不到用户
            // new LoggerFactory();
            // LOG.info("用户不存在")；
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            // userLoginReq.getPassword().equals(userDb.getPassword());
            // userLoginReq.getPassword() == userDb.getPassword(),这样写不行？？
            // 比较的是两个字符串地址是否相等
            String password = userDb.getPassword();
            String password1 = userLoginReq.getPassword();
            if (userLoginReq.getPassword().equals(userDb.getPassword())) {
                // 登录成功
                // 这样new 一个对象有什么不好的地方吗？为什么使用copy方法？
//                UserLoginResp userLoginResp = new UserLoginResp();
                UserLoginResp userLoginResp = CopyUtil.copy(userDb, UserLoginResp.class);
                // userLoginResp.setLoginName(userLoginReq.getLoginName());
                return userLoginResp;
            } else {
                // 密码错误
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}

package com.zoey.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoey.domain.User;
import com.zoey.domain.UserExample;
import com.zoey.mapper.UserMapper;
import com.zoey.reps.PageResp;
import com.zoey.reps.UserQueryResp;
import com.zoey.req.UserQueryReq;
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
//        for (User user : userList) {
//            UserResp userResp = new UserResp();
//            BeanUtils.copyProperties(user,userResp);
////            userResp.setId(1234L);
//            userResps.add(userResp);
//        }
//        UserResp userResp = new UserResp();
//        Page page = new Page(pageInfo.getTotal(),pageInfo.getPageNum());
//        userResp.setPageInfo(page);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotalNum(pageInfo.getTotal());

        return pageResp;
    }

    public void save(UserSaveReq req){
        // 如何把UserSaveReq转换成User, 我一下子没有到这种方法，只想到必须转换
        User user = CopyUtil.copy(req,User.class);
        System.out.println(user.toString());

        // 复用save实现新增新增
        if (ObjectUtils.isEmpty(req.getId())){
            // insert
            //  = new SnowFlake(1, 1);
            user.setId(snowFlake.nextId());
            userMapper.insert(user);

        } else {
            // update
            userMapper.updateByPrimaryKey(user);
        }

    }

    public void delete(long id){
        userMapper.deleteByPrimaryKey(id);
    }

    public User getUserById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}

package com.zoey.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoey.domain.Category;
import com.zoey.domain.CategoryExample;
import com.zoey.mapper.CategoryMapper;
import com.zoey.reps.CategoryQueryResp;
import com.zoey.reps.PageResp;
import com.zoey.req.CategoryQueryReq;
import com.zoey.req.CategorySaveReq;
import com.zoey.util.CopyUtil;
import com.zoey.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryService {

    // private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SnowFlake snowFlake;

//    public List<Category> getList() {
//        return categoryMapper.selectByExample(null);
//        //return categoryMapper.selectByPrimaryKey();
//    }

    public PageResp<CategoryQueryResp> getList(CategoryQueryReq categoryQueryReq) {

        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        // 增加动态SQL
//        if (!ObjectUtils.isEmpty(categoryQueryReq.getName())) {
//            criteria.andNameLike("%" + categoryQueryReq.getName() + "%");
//        }

        // 后端分页，写在要分页的查询前面，因为它只对一个sql查询生效
        PageHelper.startPage(categoryQueryReq.getPage(), categoryQueryReq.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(example);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        logger.info("Total rows: {}", pageInfo.getTotal());
        logger.info("Total pages: {}", pageInfo.getPages());
        logger.info("Page num: {}", pageInfo.getPageNum());
        logger.info("The whole list" + categoryList);

        // List<CategoryResp> categoryResps = new ArrayList<>();
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
//        for (Category category : categoryList) {
//            CategoryResp categoryResp = new CategoryResp();
//            BeanUtils.copyProperties(category,categoryResp);
////            categoryResp.setId(1234L);
//            categoryResps.add(categoryResp);
//        }
//        CategoryResp categoryResp = new CategoryResp();
//        Page page = new Page(pageInfo.getTotal(),pageInfo.getPageNum());
//        categoryResp.setPageInfo(page);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotalNum(pageInfo.getTotal());

        return pageResp;
    }

    public void save(CategorySaveReq req){
        // 如何把CategorySaveReq转换成Category, 我一下子没有到这种方法，只想到必须转换
        Category category = CopyUtil.copy(req,Category.class);
        System.out.println(category.toString());

        // 复用save实现新增新增
        if (ObjectUtils.isEmpty(req.getId())){
            // insert
            //  = new SnowFlake(1, 1);
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);

        } else {
            // update
            categoryMapper.updateByPrimaryKey(category);
        }

    }

    public void delete(long id){
        categoryMapper.deleteByPrimaryKey(id);
    }

    public Category getCategoryById(long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}

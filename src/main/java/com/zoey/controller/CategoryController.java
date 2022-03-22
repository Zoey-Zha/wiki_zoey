package com.zoey.controller;

import com.zoey.domain.Category;
import com.zoey.reps.CommonResp;
import com.zoey.reps.CategoryQueryResp;
import com.zoey.reps.PageResp;
import com.zoey.req.CategoryQueryReq;
import com.zoey.req.CategorySaveReq;
import com.zoey.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// @RestController("/category") You should put it in RequestMapping
// @RequestMapping("/category") // It is also OK
@RequestMapping("category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

/*
    @RequestMapping("categoryList")
    public List<category> getList() {
        return categoryService.getList();
    }
*/
    /*
    @RequestMapping("categoryList")
    public CommonResp getList() {
        CommonResp resp = new CommonResp();
        List<category> list = categoryService.getList();
        resp.setContent(list);
        resp.setMessage("Get all the categorys");
        return resp;
    }
     */

    @GetMapping("categoryList")
    public CommonResp getList(@Validated CategoryQueryReq categoryQueryReq) { // 加上@Validated 开启校验参数
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        //List<CategoryResp> list = categoryService.getList(categoryReq);
        PageResp<CategoryQueryResp> list = categoryService.getList(categoryQueryReq);
        resp.setContent(list);
        resp.setMessage("Get all the categorys");
        return resp;
    }

    @PostMapping("save")
    public CommonResp save(@RequestBody @Validated CategorySaveReq req) {
        CommonResp resp = new CommonResp();
        //List<CategoryResp> list = categoryService.getList(categoryReq);
        categoryService.save(req);
        resp.setMessage("Save the categorys");
        return resp;
    }

    @DeleteMapping("delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp();
        //List<CategoryResp> list = categoryService.getList(categoryReq);
        categoryService.delete(id);
        resp.setMessage("delete the categorys");
        return resp;
    }

    @RequestMapping("getCategory")
    public Category getCategory(){
        return categoryService.getCategoryById(1);
    }
}

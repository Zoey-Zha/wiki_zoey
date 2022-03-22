package com.zoey.req;

public class CategoryQueryReq extends PageReq{

    @Override
    public String toString() {
        // toString 类型，选择了super后，父类的属性也可以打印出来
        return "CategoryQueryReq{} " + super.toString();
    }
}
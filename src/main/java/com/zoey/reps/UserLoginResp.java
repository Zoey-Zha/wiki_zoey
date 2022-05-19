package com.zoey.reps;

public class UserLoginResp {
    // implements Serializable
    // 登录成功应该返回什么字段呢？
    private Long id;

    private String loginName;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // 虽然snowlake生成的是long 类型的数据，但是使用String更通用？
    private String token;

    // @Length(min = 6, max = 20, message = "密码6～20位")
    // @Pattern(regexp = "^")
    // message = "你是谁"，原来的消息"需要。。。"就没有了

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "UserLoginResp{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
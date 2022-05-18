package com.zoey.reps;

public class UserLoginResp {
    // 登录成功应该返回什么字段呢？
    private Long id;

    private String loginName;

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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append("]");
        return sb.toString();
    }
}
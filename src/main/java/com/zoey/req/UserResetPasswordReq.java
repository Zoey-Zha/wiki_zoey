package com.zoey.req;

import javax.validation.constraints.NotNull;

public class UserResetPasswordReq {
    private Long id;

    @NotNull(message = "密码不能为空")
    // @Length(min = 6, max = 20, message = "密码6～20位")
    // @Pattern(regexp = "^")
    // message = "你是谁"，原来的消息"需要。。。"就没有了
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }
}
package com.cyb.test.validate.bean;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class ChildBean {
    @NotNull(message = "ChildBean的childName不能为空！")
    String childName;
}

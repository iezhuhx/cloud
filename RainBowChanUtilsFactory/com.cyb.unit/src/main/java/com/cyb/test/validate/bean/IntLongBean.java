package com.cyb.test.validate.bean;

import com.cyb.util.validate.jsr.annotation.IntegerAnn;
import com.cyb.util.validate.jsr.annotation.LongAnn;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/*
  IntegerAnn & LongAnn 校验不可用
 */
@Data
public class IntLongBean {
     // @NotNull(message = "integer不能为空！")
     //@IntegerAnn(message = "Integer不能为空！")
    Integer integer;
    //@NotNull(message = "Long不能为空！")
    //@LongAnn(message = "Long不能为空！")
    Long lang;
    @Pattern(regexp = "(^$|[\\s?]|([0-9]{6}))")
    String postCode;
}

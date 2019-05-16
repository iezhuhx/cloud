package com.cyb.test.lombok.bean;

import lombok.*;

import java.io.Serializable;

@Data
public class BeanAnnData implements Serializable {
    @NonNull
    private String name;
    private long age;

}

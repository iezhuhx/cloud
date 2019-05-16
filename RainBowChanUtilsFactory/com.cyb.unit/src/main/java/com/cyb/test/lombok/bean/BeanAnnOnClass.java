package com.cyb.test.lombok.bean;

import lombok.*;
import lombok.extern.java.Log;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BeanAnnOnClass {
    private String name;
    private long age;
}

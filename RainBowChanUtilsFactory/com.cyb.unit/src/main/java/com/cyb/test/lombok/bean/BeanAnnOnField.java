package com.cyb.test.lombok.bean;

import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BeanAnnOnField
{
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private String name;
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private long age;
}

package com.cyb.utils.mail;

/**
 *作者 : iechenyb<br>
 *类描述: 建造者模式<br>
 *创建时间: 2019年1月15日
 */
public class Person {
	public static void main(String[] args) {
		new Person
		.Builder()
		.name("StephenHe")
		.age(20)
		.sex(true)
		.build();
	}
    private String name;
    private int age;
    private boolean sex;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isSex() {
        return sex;
    }

    public static class Builder {
        private String name;
        private int age;
        private boolean sex;

        public Builder name(String n) {
            name = n;
            return this;
        }

        public Builder age(int a) {
            age = a;
            return this;
        }

        public Builder sex(boolean s) {
            sex = s;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    private Person(Builder builder) {
        name = builder.name;
        age = builder.age;
        sex = builder.sex;
    }
}

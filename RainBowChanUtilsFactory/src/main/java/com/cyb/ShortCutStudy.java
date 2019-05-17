package com.cyb;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ShortCutStudy {

    public static final HashMap<String, String> MYA = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("aaa");
        log.info("aaa");
        //光标放到类后边，ctrl+alt+c 抽取常量，然后自定义名称
        Map<String,String> a = MYA; //Ctrl+Alt+V 抽取变量
         //ctrl+shift+t 全局搜索类包括jar
        //ctrl+shift+r 全局搜索文件
        String sf = new String();// new String() alt+enter 补全自定义变量
        HashMap<String, String> sfs = new HashMap<>();
        do {
        } while (true);//ctrl+alt+enter 自动补全
    }
}

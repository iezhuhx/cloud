package com.cyb.test.lombok;

import com.cyb.test.lombok.bean.BeanAnnData;
import com.cyb.test.lombok.bean.BeanAnnOnClass;
import com.cyb.test.lombok.bean.BeanAnnOnField;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用父类项目的lombok包
 */
@Slf4j
public class LombokTest
{
    public static void main(String[] args) {
        List a = new ArrayList<String>();
        BeanAnnOnClass bean = new BeanAnnOnClass();
        bean.setName("iechenyb");
        bean.setAge(18);
        log.info(bean.toString());
        BeanAnnOnField bean1 = new BeanAnnOnField();
        bean1.setName("iechenyb");
        bean1.setAge(18);
        BeanAnnData bean3 = new BeanAnnData(null);//nonnull注解测试
        bean3.setName("iechenyb");
        bean3.setAge(18);

    }

    /**
     * 需要抛出异常的同时，lombok帮助关闭流
     * @throws IOException
     */
    public void fileCleanUpTest() throws IOException {
        String file = "D:/data/test.txt";
        @Cleanup
        InputStream in = new FileInputStream(new File(""));
    }
}

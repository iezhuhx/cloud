package com.cyb.app.properties;

import org.apache.poi.ss.formula.functions.T;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class ApplicationConfig {
    static ResourceBundle resource ;
    public static void  initProperties (){
        resource = ResourceBundle.getBundle("application");
    }

    /**
     *
     * @param name
     * @throws FileNotFoundException
     */
    public static void  initYaml(String name) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        URL url = ApplicationConfig.class.getClassLoader().getResource(name);
        //Bean 为自定义的对应的属性对象
        Bean me = yaml.loadAs(new FileInputStream(url.getFile()), Bean.class);
        System.out.println(me);
    }

    public  static <T> T  initYaml(String name,T bean) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        URL url = ApplicationConfig.class.getClassLoader().getResource(name);
        //Bean 为自定义的对应的属性对象
        T me = (T)yaml.loadAs(new FileInputStream(url.getFile()), Bean.class);
        System.out.println(me);
        return me;
    }

    /**
     * 默认map类型
     */
    public static void  initYaml(){
        try {
        Yaml yaml = new Yaml();
        URL url = ApplicationConfig.class.getClassLoader().getResource("application.yml");
        if (url != null) {
            //获取test.yaml文件中的配置数据，然后转换为obj，
            Object obj =yaml.load(new FileInputStream(url.getFile()));
            System.out.println(obj);
            //也可以将值转换为Map
            Map map =(Map)yaml.load(new FileInputStream(url.getFile()));
            System.out.println(map);
            //通过map我们取值就可以了
            }
        } catch (Exception e) {
                e.printStackTrace(); }
    }


    public static void main(String[] args) throws FileNotFoundException {
        initProperties();
        System.out.println(resource.getString("name"));
        initYaml();//读取默认的对象Map
        initYaml("bean.yml");
        initYaml("bean.yml",Bean.class);//指定的bean
        initYaml("yml/bean.yml",Bean.class);//指定目录，指定bean
    }
}

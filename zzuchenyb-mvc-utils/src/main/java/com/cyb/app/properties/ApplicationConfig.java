package com.cyb.app.properties;

import org.apache.poi.ss.formula.functions.T;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
        resource = ResourceBundle.getBundle("config/application");
    }

    /**
     *
     * @param name
     * @throws FileNotFoundException
     */
    public static void  initYaml(String name) throws FileNotFoundException {
        Yaml yaml = new Yaml();
       /* URL url = ApplicationConfig.class.getClassLoader().getResource(name);
        //Bean 为自定义的对应的属性对象
        T me = (T)yaml.loadAs(new FileInputStream(url.getFile()), Bean.class);
        打成jar后，不能读取config文件
        */
        //InputStream in = ApplicationConfig.class.getClassLoader().getResourceAsStream(name);
        //Bean 为自定义的对应的属性对象
        Bean me = yaml.loadAs(getInputStream(name), Bean.class);
        System.out.println(me);
    }

    public  static <T> T  initYaml(String name,T bean) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        //Bean 为自定义的对应的属性对象
        T me = (T)yaml.loadAs(getInputStream(name), Bean.class);
        System.out.println(me);
        return me;
    }

    public static InputStream getInputStream(String name){
        InputStream in = ApplicationConfig.class.getClassLoader().getResourceAsStream(name);
        return in;
    }

    /**
     * 默认map类型
     */
    public static void  initYaml(){
        try {
            Yaml yaml = new Yaml();
            String name ="config/application.yml";
            //获取test.yaml文件中的配置数据，然后转换为obj，
            Object obj =yaml.load(getInputStream(name));
            System.out.println(obj);
            //也可以将值转换为Map
            Map map =(Map)yaml.load(getInputStream(name));
            System.out.println(map);
            //通过map我们取值就可以了
        } catch (Exception e) {
                e.printStackTrace();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        initProperties();//配置最顶层属性，全局的，yaml单独jar执行无法找到yml类
        System.out.println(System.getProperty("user.dir"));
        System.out.println(resource.getString("name"));
        initYaml();//读取默认的对象Map
        initYaml("config/bean.yml");
        initYaml("config/bean.yml",Bean.class);//指定的bean
        initYaml("config/yml/bean.yml",Bean.class);//指定目录，指定bean
    }
}

package cn.lqdev.learning.springboot.chapter9;

import cn.lqdev.learning.springboot.chapter9.biz.entity.User;
import cn.lqdev.learning.springboot.chapter9.biz.service.impl.UserServiceImpl;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WrapperTest {

    @Resource
    UserServiceImpl mapper ;

    @Test
    public void delete(){
       /* Wrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper
                .isNull("name")
                .ge("age", 12)
                .isNotNull("email");
        //mapper.
        boolean delete = mapper.remove(queryWrapper);
       // int delete = mapper.delete(queryWrapper);
        mapper.delete();
        System.out.println("delete return result = " + delete);*/
    }
}

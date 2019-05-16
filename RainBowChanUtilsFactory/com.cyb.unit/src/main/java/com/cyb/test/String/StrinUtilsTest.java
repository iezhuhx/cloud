package com.cyb.test.String;
import com.cyb.util.string.StringUtil;
import org.junit.jupiter.api.Test;

public class StrinUtilsTest {

   @Test
   protected void testString(){
       assert StringUtil.isEmpty("") == true;
       assert StringUtil.isEmpty(null) == true;
       assert StringUtil.isEmpty("123") == false;
       assert StringUtil.isEmptyObject(new Object()) == false;
    }
}

package com.cyb.web.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月21日
 */
@Controller
public class GlobalController {
	Log log = LogFactory.getLog(GlobalController.class);
	 /**    
     * 用于处理异常的    
     * @return    
     */      
    @ExceptionHandler({MyException.class})       
    public String exception(MyException e) {       
        System.out.println(e.getMessage());       
        e.printStackTrace();       
        return "exception";       
    }       

    @RequestMapping("test")       
    public void test() throws MyException {       
        throw new MyException("出错了！");    //可以被捕获   
    }  
}

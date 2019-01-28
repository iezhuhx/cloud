import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年11月28日
 * http://blog.csdn.net/Message_lx/article/details/77686031
 */
public class Main {
	private final static Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		MDC.put("reaIp", "123.123.123.123");
		for(int i=0;i<1;i++)
		logger.warn("logback 成功了00000[]"+i+(1));
		logger.info("logback 成功了");
		logger.error("logback 成功了");
		logger.debug("logback 成功了");
		
	}
}

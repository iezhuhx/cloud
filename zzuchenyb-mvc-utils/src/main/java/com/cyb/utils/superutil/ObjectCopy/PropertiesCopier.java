package com.cyb.utils.superutil.ObjectCopy;

/**
 * https://mp.weixin.qq.com/s?__biz=MzA4NjgxMjQ5Mg==&mid=2665762276&idx=1&sn=e4c11058b4ab4898a94f90a3f6d3ecc4&chksm=84d21fc7b3a596d14738ca21eacea47c34e955adf4a6d8b7482cabc346c91c2dcac644e7ccca&mpshare=1&scene=1&srcid=0117pp1S8KpfjgRzgiSbDAAE&sharer_sharetime=1579222323348&sharer_shareid=e18aacbbd8c157de63a26265883833cf&exportkey=AU5eYe9TdHyjZfsZFtJ7hco%3D&pass_ticket=D63z26sDdrAu7BEWP7ld4d5zaoGOCc9K0TQ62yZU9dlpVvISTn8A0JVwq4RoIE%2Br#rd
 * @author Administrator
 *
 */
public interface PropertiesCopier {
	void copyProperties(Object source, Object target) throws Exception;
}
// 全局静态 BeanCopier，避免每次都生成新的对象
//效率低，不考虑使用
/*class CommonsPropertyUtilsPropertiesCopier
	implements PropertiesCopier
{
	@Override
	public void copyProperties(Object source,
					Object target) throws Exception
	{
		org.apache.commons.beanutils.PropertyUtils.copyProperties(target, source);
	}
}*/

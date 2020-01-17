package com.cyb.utils.superutil.ObjectCopy;

public class SpringBeanUtilsPropertiesCopier implements PropertiesCopier

{
	@Override
	public	void copyProperties(Object source,Object target)
	throws Exception
	{
		org.springframework.beans.BeanUtils.copyProperties(source, target);
	}
}
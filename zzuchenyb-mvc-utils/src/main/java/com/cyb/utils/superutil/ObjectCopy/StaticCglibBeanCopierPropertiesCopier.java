package com.cyb.utils.superutil.ObjectCopy;

import net.sf.cglib.beans.BeanCopier;

public class StaticCglibBeanCopierPropertiesCopier implements PropertiesCopier
{
	private	static	BeanCopier copier =
     BeanCopier.create(Object.class,Object.class,false);
	@Override
	public	void copyProperties(Object source,Object target)					throws	Exception
	{
		copier.copy(source, target,	null);
	}
}
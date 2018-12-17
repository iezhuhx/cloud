package com.kiiik.config.quartz;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.quartz.SchedulerAccessor;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月10日
 */
public class SchedulerFactoryBean  extends SchedulerAccessor implements
FactoryBean<Scheduler>, BeanNameAware, ApplicationContextAware,
InitializingBean, DisposableBean, SmartLifecycle {
	Log log = LogFactory.getLog(SchedulerFactoryBean.class);

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		/*try {
			this.scheduler = createScheduler(schedulerFactory, this.schedulerName);
			populateSchedulerContext();
 
			if (!this.jobFactorySet && !(this.scheduler instanceof RemoteScheduler)) {
				// Use AdaptableJobFactory as default for a local Scheduler, unless when
				// explicitly given a null value through the "jobFactory" bean property.
				this.jobFactory = new AdaptableJobFactory();
			}
			if (this.jobFactory != null) {
				if (this.jobFactory instanceof SchedulerContextAware) {
					((SchedulerContextAware) this.jobFactory).setSchedulerContext(this.scheduler.getContext());
				}
				this.scheduler.setJobFactory(this.jobFactory);
			}
		}
		*/
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
	}

	@Override
	public void setBeanName(String name) {
		
	}

	@Override
	public Scheduler getObject() throws Exception {
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	protected Scheduler getScheduler() {
		return null;
	}
}

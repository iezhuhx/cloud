package com.ww.activiti.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

//@Configurationhttps://www.jianshu.com/p/3f976a47114c
public class CustomActivitiConfiguration /*implements ProcessEngineConfigurationConfigurer*/ {
	
	/*@Autowired
	private CustomUserEntityManagerFactory customUserEntityManagerFactory;

	@Autowired
	private CustomGroupEntityManagerFactory customGroupEntityManagerFactory;

	@Autowired
	private ProcessHistoryManagerSessionFactory processHistoryManagerSessionFactory;*/

	@Autowired
	private CustomProcessDiagramGeneratorImpl customProcessDiagramGenerator;

	public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
		// processEngineConfiguration.setDataSource(dataSource);
		processEngineConfiguration.setDatabaseSchemaUpdate("none");// none true
		processEngineConfiguration.setDatabaseType("mysql");

		// processEngineConfiguration.setTransactionManager(transactionManager);

		// 流程图字体
		processEngineConfiguration.setActivityFontName("宋体");
		processEngineConfiguration.setAnnotationFontName("宋体");
		processEngineConfiguration.setLabelFontName("宋体");

		processEngineConfiguration.setJpaHandleTransaction(false);
		processEngineConfiguration.setJpaCloseEntityManager(false);
		//
		// processEngineConfiguration.setMailServerHost(mailProperty.getMailServerHost());
		// processEngineConfiguration.setMailServerUsername(mailProperty.getMailServerUsername());
		// processEngineConfiguration.setMailServerPassword(mailProperty.getMailServerPassword());
		// processEngineConfiguration.setMailServerPort(mailProperty.getMailServerPort());
		//
		processEngineConfiguration.setJobExecutorActivate(false);
		processEngineConfiguration.setAsyncExecutorEnabled(false);
		// processEngineConfiguration.setAsyncExecutorActivate(false);
		// 自定义用户和组
		/*List<SessionFactory> customSessionFactories = new ArrayList<>();
		customSessionFactories.add(customUserEntityManagerFactory);
		customSessionFactories.add(customGroupEntityManagerFactory);
		customSessionFactories.add(processHistoryManagerSessionFactory);
		processEngineConfiguration.setCustomSessionFactories(customSessionFactories);*/

		// 自定义流程图样式
		processEngineConfiguration.setProcessDiagramGenerator(customProcessDiagramGenerator);
	}
}

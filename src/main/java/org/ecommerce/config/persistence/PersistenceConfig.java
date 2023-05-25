package org.ecommerce.config.persistence;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(value = { DataSourceConfig.class, VendorAdapterConfig.class, BeanValidationConfiguration.class })
@EnableTransactionManagement
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class, transactionManagerRef = "transactionManager", basePackages = "org.ecommerce.persistence.repositories")
@ComponentScan(value = "org.ecommerce.persistence.populator")
public class PersistenceConfig {

	private static final String PACKAGE_TO_SCAN_MODELS = "org.ecommerce.persistence.models";

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean provideEntityManagerFactory(DataSource datasource,
			JpaVendorAdapter jpaVendorAdapter, Properties jpaProperties) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		System.out.println("******************************** datasource  krishna  " );

		System.out.println("******************************** datasource    " + datasource);

		emfb.setDataSource(datasource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan(PACKAGE_TO_SCAN_MODELS);
		// emfb.setJpaProperties(jpaProperties);
		emfb.setJpaProperties(this.additionalProperties());
		System.out.println("******************************** datasource   end " );

		return emfb;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager provideTransactionManager(
			LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		EntityManagerFactory factory = entityManagerFactory.getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		//properties.setProperty("spring.jpa.generate-ddl", "false");
       // properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("spring.jpa.generate-ddl", "true");

		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		return properties;
	}
}

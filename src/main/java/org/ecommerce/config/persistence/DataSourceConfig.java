package org.ecommerce.config.persistence;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class DataSourceConfig {

	@Autowired
	private Environment env;

	//@Profile("development11") // Datasource for development environment
	//@Bean(name = "dataSource")
	//public DataSource provideEmbeddedDataSource() {
	//	return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY).build();
	//}

	// @Profile("production") // Datasource for production environment
	// @Bean(name = "dataSource")
	public DataSource provideDataSourceProduction() {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		System.out.println("krishna");
		System.out
				.println("******************************** production" + env.getProperty("datasource.production.jndi"));
		jndiObjectFactoryBean.setJndiName(env.getProperty("datasource.development.jndi"));
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setProxyInterface(DataSource.class);
		return (DataSource) jndiObjectFactoryBean.getObject();
	}

	@Profile("development") // Datasource for production environment
	@Bean(name = "dataSource")
	public DataSource provideDataSourcedev() {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName(env.getProperty("datasource.production.jndi"));
		System.out.println(
				"******************************** development" + env.getProperty("datasource.production.jndi"));

		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setProxyInterface(DataSource.class);
		return (DataSource) jndiObjectFactoryBean.getObject();
	}

	@Profile("production")
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		System.out.println("******************************** production");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("ecommerce");
		dataSource.setPassword("ecommerce");
		//dataSource.setUrl("jdbc:mysql://192.168.9.115:3307/ecommerce?useUnicode=true");
		//dataSource.setUrl("jdbc:mysql://localhost:3306/ecommerce?useUnicode=true");
		dataSource.setUrl("jdbc:mysql://192.168.9.115:3306/ecommerce?useUnicode=true");


		return dataSource;
	}
}

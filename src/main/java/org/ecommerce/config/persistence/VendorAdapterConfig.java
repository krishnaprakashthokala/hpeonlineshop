package org.ecommerce.config.persistence;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author sergio
 */
@Configuration
public class VendorAdapterConfig {

	@Autowired
	private Environment env;

	@Profile("development")
	@Bean(name = "jpaVendorAdapter")
	public JpaVendorAdapter provideJpaVendorAdapterDev() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.DERBY);
		adapter.setShowSql(env.getProperty("hibernate.dev.show_sql", Boolean.class));
		adapter.setGenerateDdl(env.getProperty("hibernate.dev.hbm2ddl.auto", Boolean.class));
		adapter.setDatabasePlatform(env.getProperty("hibernate.dev.dialect"));
		return adapter;
	}

	@Profile("production")
	@Bean(name = "jpaVendorAdapter")
	public JpaVendorAdapter provideJpaVendorAdapterProd() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(env.getProperty("hibernate.prod.show_sql", Boolean.class));
		adapter.setGenerateDdl(env.getProperty("hibernate.prod.hbm2ddl.auto", Boolean.class));
		adapter.setDatabasePlatform(env.getProperty("hibernate.prod.dialect"));
		return adapter;
	}

	@Profile("development")
	@Bean(name = "jpaProperties")
	public Properties provideJpaProperties() {
		Properties props = new Properties();
		props.put("hibernate.search.default.directory_provider", "filesystem");
		props.put("hibernate.search.default.indexBase",
				env.getProperty("hibernate.dev.search.default.indexBase", String.class));
		return props;
	}

	@Profile("production")
	@Bean(name = "jpaProperties")
	public Properties provideProdJpaProperties() {
		Properties props = new Properties();
		props.put("hibernate.search.default.directory_provider", "filesystem");
		props.put("hibernate.search.default.indexBase",
				env.getProperty("hibernate.dev.search.default.indexBase", String.class));
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		props.put("spring.jpa.generate-ddl", "false");
		props.put("spring.jpa.show-sql", "true");
		props.put("spring.jpa.properties.hibernate.format_sql", "true");

//MySQL5Dialect
		return props;
	}

}

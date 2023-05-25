package org.ecommerce.config.root;

import org.ecommerce.config.mahout.RecommenderConfig;
import org.ecommerce.config.persistence.PersistenceConfig;
import org.ecommerce.config.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author sergio
 */
@Configuration
@PropertySource(value = { "classpath:application.properties" })
@Import(value = { PersistenceConfig.class, SecurityConfig.class, RecommenderConfig.class })
public class RootConfig {
}

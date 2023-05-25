package org.ecommerce.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.ecommerce.persistence.models.Address;
import org.ecommerce.web.frontend.flows.actions.AddressFormAction;

/**
 * Web Flow Bean Actions
 * 
 * @author sergio
 */
@Configuration
public class WebFlowActionsConfig {

	@Autowired
	private LocalValidatorFactoryBean validator;

	/**
	 * Bean for create Address on Flow Scope
	 * 
	 * @return AddressFormAction
	 */
	@Bean(name = "addressFormAction")
	public AddressFormAction provideAddressFormAction() {
		AddressFormAction addressFormAction = new AddressFormAction();
		addressFormAction.setFormObjectClass(Address.class);
		addressFormAction.setFormObjectName("addressForm");
		addressFormAction.setValidator(validator);
		return addressFormAction;
	}

}

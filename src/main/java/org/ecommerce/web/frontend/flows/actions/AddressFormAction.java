package org.ecommerce.web.frontend.flows.actions;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.action.FormAction;
import org.ecommerce.persistence.models.Country;
import org.ecommerce.web.editors.CountryEditor;

/**
 * @author sergio
 */

public class AddressFormAction extends FormAction {

	@Autowired
	private CountryEditor countryEditor;

	@Override
	protected void registerPropertyEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Country.class, countryEditor);
	}
}

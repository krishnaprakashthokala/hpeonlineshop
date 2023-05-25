package org.ecommerce.web.admin.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ecommerce.persistence.models.ProductCategory;

/**
 * @author sergio
 */
@Component
public class ProductCategoryStringConverter implements Converter<ProductCategory, String> {

	@Override
	public String convert(ProductCategory productCategory) {
		return productCategory != null ? productCategory.getId().toString() : null;
	}

}

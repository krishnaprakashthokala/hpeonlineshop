package org.ecommerce.web.admin.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ecommerce.persistence.models.Authority;
import org.ecommerce.persistence.models.AuthorityEnum;
import org.ecommerce.persistence.repositories.AuthorityRepository;

/**
 *
 * @author sergio
 */
@Component
public class StringAuthorityConverter implements Converter<String, Authority> {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority convert(String name) {
		AuthorityEnum authorityType = AuthorityEnum.valueOf(name);
		return authorityRepository.findByType(authorityType);
	}

}

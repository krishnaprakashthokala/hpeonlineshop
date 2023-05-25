package org.ecommerce.web.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ecommerce.persistence.models.Country;
import org.ecommerce.persistence.repositories.CountryRepository;
import org.ecommerce.web.services.CountryService;

/**
 * @author sergio
 */
@Service("countryService")
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public List<Country> getAll() {
		return countryRepository.findAll();
	}
}

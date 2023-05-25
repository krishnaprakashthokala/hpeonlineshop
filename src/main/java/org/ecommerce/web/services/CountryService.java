package org.ecommerce.web.services;

import java.util.List;
import org.ecommerce.persistence.models.Country;

/**
 * @author sergio
 */
public interface CountryService {
	List<Country> getAll();
}

package org.ecommerce.persistence.repositories;

import org.ecommerce.persistence.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sergio
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
	Country findByCode(String code);
}

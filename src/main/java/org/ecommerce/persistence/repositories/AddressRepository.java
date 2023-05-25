package org.ecommerce.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ecommerce.persistence.models.Address;

/**
 * @author sergio
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}

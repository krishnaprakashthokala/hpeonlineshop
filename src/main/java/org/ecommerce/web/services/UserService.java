package org.ecommerce.web.services;

import java.util.Set;
import org.springframework.web.multipart.MultipartFile;
import org.ecommerce.persistence.models.Address;
import org.ecommerce.persistence.models.User;

/**
 * @author sergio
 */
public interface UserService {
	void updatePassword(User user);

	void create(User user);

	void create(User user, MultipartFile avatarFile);

	boolean hasAddresses(String username);

	Set<Address> getAddresses(String username);

	Long getTotalPurchases(Long id);

	Double getTotalSpent(Long id);

	Double getTotalSpentThisMonth(Long id);

	boolean isAuthenticated();

	Long getCurrentUserId();
}

package org.ecommerce.persistence.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import org.ecommerce.persistence.models.Product;
import org.ecommerce.persistence.projection.ProductReport;

/**
 * @author sergio
 */
public interface ProductRepository extends DataTablesRepository<Product, Long>, SearchableRepository<Product> {
	Product findByName(String name);

	List<Product> findFirst10ByOrderByCreateAtDesc();

	List<Product> findFirst3ByOrderByCreateAtDesc();

	Page<Product> findByNameIgnoreCaseContaining(String name, Pageable pageable);

	Page<Product> findByCategorySlug(String slug, Pageable pageable);

	List<ProductReport> findByOrderByCreateAt();

	@Query(value = "SELECT p FROM Product p WHERE EXISTS (SELECT p.id, AVG(r.rating) AS average FROM Product p JOIN p.reviews r GROUP BY p.id)")
	Page<Product> findFeaturedProducts(Pageable pageable);
	/*
	 * @Query(value = " SELECT p FROM Product p " + " JOIN p.productLines pl " +
	 * " LEFT JOIN pl.orderLines ol " + " GROUP BY p ORDER BY COUNT(p.id) DESC ")
	 * Page<Product> findBestsellersProducts(Pageable pageable);
	 */
}

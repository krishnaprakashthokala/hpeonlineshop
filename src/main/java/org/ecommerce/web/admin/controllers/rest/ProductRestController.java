package org.ecommerce.web.admin.controllers.rest;

import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ecommerce.persistence.models.Product;
import org.ecommerce.persistence.models.Review;
import org.ecommerce.persistence.projection.ReviewDetail;
import org.ecommerce.persistence.repositories.ProductRepository;
import org.ecommerce.persistence.repositories.ReviewRepository;
import org.ecommerce.persistence.specifications.ProductFilterSpecification;
import org.ecommerce.persistence.specifications.ReviewFilterSpecification;
import org.ecommerce.web.models.datatables.DataTableProductInput;
import org.ecommerce.web.models.datatables.DataTableReviewInput;
import org.ecommerce.web.models.datatables.FilterReview;

/**
 * @author sergio
 */
@RestController
@RequestMapping("/admin/products")
public class ProductRestController {

	private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ReviewRepository reviewRepository;

	@JsonView(DataTablesOutput.View.class)
	@GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
	public DataTablesOutput<Product> all(final @Valid DataTableProductInput input) {
		return productRepository.findAll(input, new ProductFilterSpecification(input.getFilter()));
	}

	@JsonView(DataTablesOutput.View.class)
	@GetMapping("/{id}/reviews")
	public DataTablesOutput<Review> reviews(@PathVariable Long id, final @Valid DataTableReviewInput input) {
		FilterReview filter = input.getFilter();
		filter.setProduct(id);
		return reviewRepository.findAll(input, new ReviewFilterSpecification(filter));
	}

	@GetMapping("/reviews/{id}")
	public ReviewDetail review(@PathVariable Long id) {
		return reviewRepository.findById(id);
	}
}

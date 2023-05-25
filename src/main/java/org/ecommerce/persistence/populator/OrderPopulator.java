package org.ecommerce.persistence.populator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.ecommerce.persistence.models.OrderLine;
import org.ecommerce.persistence.models.OrderStatusEnum;
import org.ecommerce.persistence.models.User;
import org.ecommerce.persistence.repositories.OrderRepository;
import org.ecommerce.persistence.repositories.ProductLineRepository;
import org.ecommerce.persistence.repositories.UserRepository;
import org.ecommerce.persistence.models.Address;
import org.ecommerce.persistence.models.ProductLine;

/**
 * @author sergio
 */
@Component("OrderPop")
//@Profile("development")
@Profile("production")
//krishna
public class OrderPopulator implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(OrderPopulator.class);

	@Autowired
	private ProductLineRepository productLineRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

    public static boolean booleanOrder= true;

	@Order(value = 5)
	// @DependsOn("productComp")

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		logger.info("SETUP ORDER INIT DATA");

		List<org.ecommerce.persistence.models.Order> orders = new ArrayList();

		User customer = userRepository.findByUsername("sergio11");

		org.ecommerce.persistence.models.Order order1 = new org.ecommerce.persistence.models.Order();
		order1.setCustomer(customer);
		order1.setBasePrice(150.0);
		order1.setPurchasedPrice(150.0 + 3.0);
		order1.setPurchasedOn(new Date());
		order1.setStatus(OrderStatusEnum.PENDING);
		Address address = customer.getAddresses().iterator().next();
		order1.setBillTo(address);
		order1.setShipTo(address);

		List<ProductLine> lines = productLineRepository.findByProductName("Nike Air Max 2017");
		ProductLine line = lines.get(0);
		OrderLine orderLine = new OrderLine(order1, line, 2, line.getProduct().getPrice() * 2, null);

		order1.addOrderLine(orderLine);
		line.addOrderLine(orderLine);

		logger.info("Order : " + order1.toString());

		orders.add(order1);

		try {
			if(booleanOrder) {
			orderRepository.deleteAll();
			orderRepository.save(orders);
			booleanOrder = false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}

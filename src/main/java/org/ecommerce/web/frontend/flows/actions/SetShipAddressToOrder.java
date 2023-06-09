package org.ecommerce.web.frontend.flows.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.ecommerce.persistence.models.Address;
import org.ecommerce.persistence.models.Order;
import org.ecommerce.persistence.repositories.AddressRepository;

/**
 * @author sergio
 */
@Component
public class SetShipAddressToOrder extends AbstractAction {

	private static Logger logger = LoggerFactory.getLogger(SetShipAddressToOrder.class);

	@Autowired
	private AddressRepository addressRepository;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		try {
			Long idAddressSelected = context.getRequestParameters().getLong("idAddressSelected");
			Order order = (Order) context.getFlowScope().get("order");
			logger.info("idAddressSelected: " + idAddressSelected);
			logger.info("order: " + order.toString());
			Address address = addressRepository.findOne(idAddressSelected);
			logger.info("address: " + address.toString());
			order.setShipTo(address);
			context.getFlowScope().put("order", order);
			return success();
		} catch (Exception e) {
			MessageContext messageContext = context.getMessageContext();
			MessageBuilder builder = new MessageBuilder();
			messageContext
					.addMessage(builder.error().code("frontend.checkout.set.ship.address.to.order.failed").build());
			e.printStackTrace();
			return error();
		}
	}
}

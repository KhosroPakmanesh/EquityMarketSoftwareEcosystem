package emSeco.brokerUnit.core.modules.orderValidator.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IOrderValidator {
    List<BooleanResultMessage> validateRetailOrder(RetailOrder retailOrder);
    List<BooleanResultMessage> validateInstitutionalOrder(InstitutionalOrder institutionalOrder);
}

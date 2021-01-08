package emSeco.exchangeUnit.core.modules.orderValidator.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public interface IOrderValidator {
    List<BooleanResultMessage> validateRetailOrder(Order retailOrder);
    List<BooleanResultMessage> validateInstitutionalOrder(Order institutionalOrder);
}

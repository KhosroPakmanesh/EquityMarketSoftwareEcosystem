package emSeco.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.implementations;

//#if QuantityDisclosureOrderPrecedenceRule
//@import stp.exchangeUnit.core.entities.order.Order;
//@import stp.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.interfaces.ISecondaryOrderPrecedenceRule;
//@
//@public class QuantityDisclosureOrderPrecedenceRule implements ISecondaryOrderPrecedenceRule {
//@    @Override
//@    public int compareOrders(Order baseOrder, Order otherOrder) {
//@        if (baseOrder.getTradingInformation().getQuantityDisclosureStatus() &&
//@                otherOrder.getTradingInformation().getQuantityDisclosureStatus()) {
//@            return 0;
//@        } else if (baseOrder.getTradingInformation().getQuantityDisclosureStatus() &&
//@                !otherOrder.getTradingInformation().getQuantityDisclosureStatus()) {
//@            return 1;
//@        } else if (otherOrder.getTradingInformation().getQuantityDisclosureStatus() &&
//@                !baseOrder.getTradingInformation().getQuantityDisclosureStatus()) {
//@            return -1;
//@        } else {
//@            return 0;
//@        }
//@    }
//@}
//#endif

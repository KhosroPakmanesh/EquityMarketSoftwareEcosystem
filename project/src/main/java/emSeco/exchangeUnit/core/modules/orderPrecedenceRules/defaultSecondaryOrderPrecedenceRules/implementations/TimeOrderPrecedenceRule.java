//Reference:
//https://github.com/Apress/practical-.net-for-financial-markets/blob/master/CodeExample/Chpt2/Framework/PriceTimePriority.cs

package emSeco.exchangeUnit.core.modules.orderPrecedenceRules.defaultSecondaryOrderPrecedenceRules.implementations;

//#if TimeOrderPrecedenceRule
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.defaultSecondaryOrderPrecedenceRules.interfaces.IDefaultSecondaryOrderPrecedenceRule;

public class TimeOrderPrecedenceRule implements IDefaultSecondaryOrderPrecedenceRule {
    @Override
    public int compareOrders(Order baseOrder, Order otherOrder) {
        return baseOrder.getOrderTimeStamp().compareTo(otherOrder.getOrderTimeStamp());
    }
}
//#endif

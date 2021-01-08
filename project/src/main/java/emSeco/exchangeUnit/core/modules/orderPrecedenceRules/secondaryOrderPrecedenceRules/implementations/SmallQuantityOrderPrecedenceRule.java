package emSeco.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.implementations;

//#if SmallQuantityOrderPrecedenceRule
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.interfaces.ISecondaryOrderPrecedenceRule;

public class SmallQuantityOrderPrecedenceRule  implements ISecondaryOrderPrecedenceRule {
    final int threshold= 50000;

    @Override
    public int compareOrders(Order baseOrder, Order otherOrder) {
        if (baseOrder.getTerm().getQuantity() <= threshold &&
                otherOrder.getTerm().getQuantity() <= threshold)
        {
            return 0;
        }
        else if(baseOrder.getTerm().getQuantity() <= threshold && otherOrder.getTerm().getQuantity() > threshold){
            return 1;
        }
        else if(otherOrder.getTerm().getQuantity() <= threshold && baseOrder.getTerm().getQuantity() > threshold ){
            return -1;
        }
        else {
            return 0;
        }
    }
}
//#endif

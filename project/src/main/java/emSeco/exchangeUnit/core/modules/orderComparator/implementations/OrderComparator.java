package emSeco.exchangeUnit.core.modules.orderComparator.implementations;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.shared.SideName;
import emSeco.exchangeUnit.core.modules.orderComparator.interfaces.IOrderComparator;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.defaultSecondaryOrderPrecedenceRules.interfaces.IDefaultSecondaryOrderPrecedenceRule;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.interfaces.ISecondaryOrderPrecedenceRule;

import java.util.Comparator;

public class OrderComparator implements Comparator<Order>, IOrderComparator {

    private final IDefaultSecondaryOrderPrecedenceRule defaultSecondaryOrderPrecedenceRule;
    private final ISecondaryOrderPrecedenceRule secondaryOrderPrecedenceRule;

    @Inject
    public OrderComparator(
            IDefaultSecondaryOrderPrecedenceRule defaultSecondaryOrderPrecedenceRule,
            ISecondaryOrderPrecedenceRule secondaryOrderPrecedenceRule) {
        this.defaultSecondaryOrderPrecedenceRule = defaultSecondaryOrderPrecedenceRule;
        this.secondaryOrderPrecedenceRule = secondaryOrderPrecedenceRule;
    }

    @Override
    public int compare(Order baseOrder, Order otherOrder) {

        if ( baseOrder.getTradingInformation().getSideName() == SideName.buy )
        {
            return CompareOrders(baseOrder, otherOrder, -1);
        }
        else
        {
            return CompareOrders(baseOrder, otherOrder, 1);
        }
    }

    private int CompareOrders(Order baseOrder, Order otherOrder, int sortingOrder)
    {
        int priceComparisonResult = Double.compare(baseOrder.getTerm().getPrice(),otherOrder.getTerm().getPrice());
        if ( priceComparisonResult != 0 )
        {
            return priceComparisonResult * sortingOrder;
        }

        int secondaryOrderPrecedenceRuleComparisonResult=
                secondaryOrderPrecedenceRule.compareOrders(baseOrder,otherOrder);
        if (secondaryOrderPrecedenceRuleComparisonResult != 0){
            return secondaryOrderPrecedenceRuleComparisonResult * sortingOrder;
        }

        return defaultSecondaryOrderPrecedenceRule.
                compareOrders(baseOrder,otherOrder) * sortingOrder;
    }

}

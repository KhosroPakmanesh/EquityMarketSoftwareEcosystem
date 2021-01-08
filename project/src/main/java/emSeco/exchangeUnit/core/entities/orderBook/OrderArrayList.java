package emSeco.exchangeUnit.core.entities.orderBook;

import emSeco.exchangeUnit.core.entities.order.Order;

import java.util.ArrayList;
import java.util.Comparator;

public class OrderArrayList extends ArrayList<Order> {

    private double marketPrice;

    public double getMarketPrice() {
        return marketPrice;
    }

    public boolean add(Order order,Comparator<Order> orderComparator)
    {
        super.add(order);
        super.sort(orderComparator);

        Order marketPriceOrder = super.stream().findFirst().orElse(null);
        if (marketPriceOrder != null)
        {
            marketPrice = marketPriceOrder.getTerm().getPrice();
        }

        return true;
    }
}

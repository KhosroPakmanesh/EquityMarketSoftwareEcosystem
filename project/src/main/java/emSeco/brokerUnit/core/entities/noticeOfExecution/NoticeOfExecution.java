package emSeco.brokerUnit.core.entities.noticeOfExecution;


import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.entities.shared.Side;

import java.util.Date;
import java.util.UUID;


public class NoticeOfExecution extends Trade {
    public NoticeOfExecution(UUID exchangeId, UUID tradeId, Date tradeTimestamp, Side buySide, Side sellSide) {
        super(exchangeId, tradeId, tradeTimestamp, buySide, sellSide);
    }
}

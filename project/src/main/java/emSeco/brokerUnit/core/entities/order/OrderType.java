package emSeco.brokerUnit.core.entities.order;

public enum  OrderType {
    //LimitPriceOrder
	//#if LimitPriceOrder
	LIM,
	//#endif
	
    //MarketPriceOrder
	//#if MarketPriceOrder
    MP,
	//#endif

    //FakeAllOrNothing
    //#if FakeAllOrNothing
//@    AON,
    //#endif


    //FakeFillOrKillOrder
    //#if FakeFillOrKillOrder
//@    FOK,
    //#endif

    //FakeGoodTillCancelled
    //#if FakeGoodTillCancelled
//@    GTC,
    //#endif
    
    //FakeGoodTillDate
    //#if FakeGoodTillDate
//@    GTD,
    //#endif
    
    //FakeImmediateOrCancel
    //#if FakeImmediateOrCancel
//@    IOC,
    //#endif
   
    //FakeStopLoss
    //#if FakeStopLoss
//@    SL
    //#endif
}

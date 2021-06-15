package emSeco.brokerUnit.core.entities.shared;

public enum EquityTransferMethod {
    //#if BrokerInternalDematAccountMoneyTransferMethod
    brokerInternalAccount,
    //#endif
    //#if BrokerDepositoryDematAccountEquityTransferMethod
    depositoryDematAccount,
    //#endif
}

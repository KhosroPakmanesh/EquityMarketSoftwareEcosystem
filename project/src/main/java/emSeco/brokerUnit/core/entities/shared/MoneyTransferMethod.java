package emSeco.brokerUnit.core.entities.shared;

public enum MoneyTransferMethod {
    //#if BrokerInternalBankAccountMoneyTransferMethod
    brokerInternalAccount,
    //#endif
    //#if BrokerClearingBankAccountMoneyTransferMethod
    clearingBankAccount
    //#endif
}

package emSeco.custodianUnit.core.entities.shared;

public enum MoneyTransferMethod {
	//#if CustodianInternalBankAccountMoneyTransferMethod
	custodianInternalAccount,
	//#endif
	//#if CustodianClearingBankAccountMoneyTransferMethod
	clearingBankAccount
	//#endif  
}

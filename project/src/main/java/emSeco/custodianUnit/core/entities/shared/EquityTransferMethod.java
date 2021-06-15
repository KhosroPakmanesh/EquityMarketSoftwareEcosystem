package emSeco.custodianUnit.core.entities.shared;

public enum EquityTransferMethod {
	//#if CustodianInternalDematAccountMoneyTransferMethod
	custodianInternalAccount,
	//#endif
	//#if CustodianDepositoryDematAccountEquityTransferMethod
	depositoryDematAccount
	//#endif   
}

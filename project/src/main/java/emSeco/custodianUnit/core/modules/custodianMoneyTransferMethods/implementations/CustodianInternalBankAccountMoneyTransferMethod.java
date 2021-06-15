package emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.implementations;

//#if CustodianInternalBankAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.custodianUnit.core.entities.custodianBankAccount.CustodianBankAccount;
import emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.interfaces.ICustodianMoneyTransferMethod;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.UUID;

public class CustodianInternalBankAccountMoneyTransferMethod implements ICustodianMoneyTransferMethod {
    private final ICustodianUnitRepositories custodianUnitRepositories;
    private final ICustodianUnitApiGateways custodianUnitApiGateways;

    @Inject
    public CustodianInternalBankAccountMoneyTransferMethod(ICustodianUnitRepositories custodianUnitRepositories,
                                                           ICustodianUnitApiGateways custodianUnitApiGateways) {
        this.custodianUnitRepositories = custodianUnitRepositories;
        this.custodianUnitApiGateways = custodianUnitApiGateways;
    }

    @Override
    public MoneyTransferMethod getMoneyTransferMethod() {
        return MoneyTransferMethod.custodianInternalAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToCustodian
            (UUID custodianClearingBankId, UUID custodianClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID custodianInternalBankAccountNumber,UUID clientInternalBankAccountNumber,
             double totalPrice) {
        CustodianBankAccount clientCustodianBankAccount = custodianUnitRepositories.
                getCustodianBankAccountRepository().get(clientInternalBankAccountNumber);

        if (clientCustodianBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Client's custodian bank accounts was not found!"));
        }

        Boolean hasEnoughBalance = clientCustodianBankAccount.
                HasEnoughBalance(totalPrice);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        clientCustodianBankAccount.debit(totalPrice);
        BooleanResultMessage creditAccountResultMessage =
                custodianUnitApiGateways.getCustodianToClearingBankUnitApiGateway().
                        credit(custodianClearingBankId, custodianClearingBankAccountNumber, totalPrice);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromCustodianToClient
            (UUID custodianClearingBankId, UUID custodianClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID custodianInternalBankAccountNumber, UUID clientInternalBankAccountNumber,
             double totalPrice) {
        CustodianBankAccount clientCustodianBankAccount = custodianUnitRepositories.
                getCustodianBankAccountRepository().get(clientInternalBankAccountNumber);

        if (clientCustodianBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Client's custodian bank accounts was not found!"));
        }
        BooleanResultMessage debitAccountResultMessage =
                custodianUnitApiGateways.getCustodianToClearingBankUnitApiGateway().
                        debit(custodianClearingBankId, custodianClearingBankAccountNumber, totalPrice);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }
        clientCustodianBankAccount.credit(totalPrice);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif

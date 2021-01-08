package emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.implementations;

//#if CustodianInternalBankAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.custodianUnit.core.entities.custodianBankAccount.CustodianBankAccount;
import emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.interfaces.ICustodianMoneyTransferMethod;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.UUID;

public class CustodianInternalBankAccountMoneyTransferMethod implements ICustodianMoneyTransferMethod {
    private final ICustodianUnitRepositories custodianUnitRepositories;

    @Inject
    public CustodianInternalBankAccountMoneyTransferMethod(ICustodianUnitRepositories custodianUnitRepositories) {
        this.custodianUnitRepositories = custodianUnitRepositories;
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

        CustodianBankAccount custodianBankAccount = custodianUnitRepositories.
                getCustodianBankAccountRepository().get(custodianInternalBankAccountNumber);

        CustodianBankAccount clientCustodianBankAccount = custodianUnitRepositories.
                getCustodianBankAccountRepository().get(clientInternalBankAccountNumber);

        if (custodianBankAccount == null || clientCustodianBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("One or both of clearing bank accounts were not found!"));
        }

        Boolean hasEnoughBalance = clientCustodianBankAccount.
                HasEnoughBalance(totalPrice);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        clientCustodianBankAccount.debit(totalPrice);
        custodianBankAccount.credit(totalPrice);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromCustodianToClient
            (UUID brokerClearingBankId, UUID brokerClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID custodianInternalBankAccountNumber, UUID clientInternalBankAccountNumber,
             double totalPrice) {

        CustodianBankAccount custodianBankAccount = custodianUnitRepositories.
                getCustodianBankAccountRepository().get(custodianInternalBankAccountNumber);

        CustodianBankAccount clientCustodianBankAccount = custodianUnitRepositories.
                getCustodianBankAccountRepository().get(clientInternalBankAccountNumber);

        if (custodianBankAccount == null || clientCustodianBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("One or both of clearing bank accounts were not found!"));
        }

        Boolean hasEnoughBalance = custodianBankAccount.
                HasEnoughBalance(totalPrice);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        custodianBankAccount.debit(totalPrice);
        clientCustodianBankAccount.credit(totalPrice);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif

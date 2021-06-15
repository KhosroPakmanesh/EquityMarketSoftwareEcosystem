package emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.implementations;

//#if BrokerInternalBankAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.brokerBankAccount.BrokerBankAccount;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.interfaces.IBrokerMoneyTransferMethod;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class BrokerInternalBankAccountMoneyTransferMethod implements IBrokerMoneyTransferMethod {
    private final IBrokerUnitRepositories brokerRepositories;
    private final IBrokerUnitApiGateways brokerUnitApiGateways;

    @Inject
    public BrokerInternalBankAccountMoneyTransferMethod(IBrokerUnitRepositories brokerRepositories,
                                                        IBrokerUnitApiGateways brokerUnitApiGateways) {
        this.brokerRepositories = brokerRepositories;
        this.brokerUnitApiGateways = brokerUnitApiGateways;
    }

    @Override
    public MoneyTransferMethod getMoneyTransferMethod() {
        return MoneyTransferMethod.brokerInternalAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToBroker
            (UUID brokerClearingBankId, UUID brokerClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID brokerInternalBankAccountNumber, UUID clientInternalBankAccountNumber,
             double totalPrice) {
        BrokerBankAccount clientBrokerBankAccount = brokerRepositories.
                getBrokerBankAccountRepository().get(clientInternalBankAccountNumber);

        if (clientBrokerBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("One or both of clearing bank accounts were not found!"));
        }

        Boolean hasEnoughBalance = clientBrokerBankAccount.
                HasEnoughBalance(totalPrice);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        clientBrokerBankAccount.debit(totalPrice);
        BooleanResultMessage creditAccountResultMessage =
                brokerUnitApiGateways.getBrokerToClearingBankUnitApiGateway().
                        credit(brokerClearingBankId, brokerClearingBankAccountNumber, totalPrice);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromBrokerToClient
            (UUID brokerClearingBankId, UUID brokerClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID brokerInternalBankAccountNumber, UUID clientInternalBankAccountNumber,
             double totalPrice) {
        BrokerBankAccount clientBrokerBankAccount = brokerRepositories.
                getBrokerBankAccountRepository().get(clientInternalBankAccountNumber);

        if (/*brokerBankAccount == null ||*/ clientBrokerBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Client's internal bank account was not found!"));
        }

        BooleanResultMessage debitAccountResultMessage =
                brokerUnitApiGateways.getBrokerToClearingBankUnitApiGateway().
                        debit(brokerClearingBankId, brokerClearingBankAccountNumber, totalPrice);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }
        clientBrokerBankAccount.credit(totalPrice);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif

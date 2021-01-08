package emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.implementations;

//#if BrokerInternalDematAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.brokerDematAccount.BrokerDematAccount;
import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.interfaces.IBrokerEquityTransferMethod;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class BrokerInternalDematAccountMoneyTransferMethod implements IBrokerEquityTransferMethod {
    private final IBrokerUnitRepositories brokerRepositories;

    @Inject
    public BrokerInternalDematAccountMoneyTransferMethod(IBrokerUnitRepositories brokerRepositories) {
        this.brokerRepositories = brokerRepositories;
    }

    @Override
    public EquityTransferMethod getEquityTransferMethod() {
        return EquityTransferMethod.brokerInternalAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToBroker(
            UUID brokerDepositoryId, UUID brokerDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID brokerInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity
    ) {
        BrokerDematAccount brokerDematAccount = brokerRepositories.
                getBrokerDematAccountRepository().get(brokerInternalDematAccountNumber);

        BrokerDematAccount clientBrokerDematAccount = brokerRepositories.
                getBrokerDematAccountRepository().get(clientInternalDematAccountNumber);

        if (brokerDematAccount == null || clientBrokerDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("One or both of clearing bank accounts were not found!"));
        }

        Boolean hasEnoughBalance = clientBrokerDematAccount.
                HasEnoughBalance(instrumentName, quantity);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        clientBrokerDematAccount.debit(instrumentName, quantity);
        brokerDematAccount.credit(instrumentName, quantity);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromBrokerToClient(
            UUID brokerDepositoryId, UUID brokerDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID brokerInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity) {

        BrokerDematAccount brokerDematAccount = brokerRepositories.
                getBrokerDematAccountRepository().get(brokerInternalDematAccountNumber);

        BrokerDematAccount clientBrokerDematAccount = brokerRepositories.
                getBrokerDematAccountRepository().get(clientInternalDematAccountNumber);

        if (brokerDematAccount == null || clientBrokerDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("One or both of clearing bank accounts were not found!"));
        }

        Boolean hasEnoughBalance = brokerDematAccount.
                HasEnoughBalance(instrumentName, quantity);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        brokerDematAccount.debit(instrumentName, quantity);
        clientBrokerDematAccount.credit(instrumentName, quantity);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
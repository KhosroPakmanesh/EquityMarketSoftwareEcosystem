package emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.implementations;

//#if BrokerInternalDematAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.brokerDematAccount.BrokerDematAccount;
import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.interfaces.IBrokerEquityTransferMethod;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class BrokerInternalDematAccountMoneyTransferMethod implements IBrokerEquityTransferMethod {
    private final IBrokerUnitRepositories brokerRepositories;
    private final IBrokerUnitApiGateways brokerUnitApiGateways;

    @Inject
    public BrokerInternalDematAccountMoneyTransferMethod(IBrokerUnitRepositories brokerRepositories,
                                                         IBrokerUnitApiGateways brokerUnitApiGateways) {
        this.brokerRepositories = brokerRepositories;
        this.brokerUnitApiGateways = brokerUnitApiGateways;
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
        BooleanResultMessage creditAccountResultMessage =
                brokerUnitApiGateways.getBrokerToDepositoryUnitApiGateway().
                        credit(brokerDepositoryId, brokerDematAccountNumber,
                                instrumentName, quantity);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromBrokerToClient(
            UUID brokerDepositoryId, UUID brokerDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID brokerInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity) {

        BrokerDematAccount clientBrokerDematAccount = brokerRepositories.
                getBrokerDematAccountRepository().get(clientInternalDematAccountNumber);

        if (clientBrokerDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Client's internal account was not found!"));
        }

        BooleanResultMessage debitAccountResultMessage =
                brokerUnitApiGateways.getBrokerToDepositoryUnitApiGateway().
                        debit(brokerDepositoryId, brokerDematAccountNumber,
                                instrumentName, quantity);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }
        clientBrokerDematAccount.credit(instrumentName, quantity);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif

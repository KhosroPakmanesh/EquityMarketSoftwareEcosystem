package emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.implementations;

//#if BrokerDepositoryDematAccountEquityTransferMethod
import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.interfaces.IBrokerEquityTransferMethod;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class DepositoryDematAccountEquityTransferMethod implements IBrokerEquityTransferMethod {

    private final IBrokerUnitApiGateways brokerUnitApiGateways;

    @Inject
    public DepositoryDematAccountEquityTransferMethod(IBrokerUnitApiGateways brokerUnitApiGateways) {
        this.brokerUnitApiGateways = brokerUnitApiGateways;
    }

    @Override
    public EquityTransferMethod getEquityTransferMethod() {
        return EquityTransferMethod.depositoryDematAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToBroker(
            UUID brokerDepositoryId, UUID brokerDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID brokerInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity) {

        BooleanResultMessage debitAccountResultMessage =
                brokerUnitApiGateways.getBrokerToDepositoryUnitApiGateway().
                        debit(clientDepositoryId, clientDematAccountNumber,
                                instrumentName, quantity);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

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
            String instrumentName, int quantity
    ) {
        BooleanResultMessage debitAccountResultMessage =
                brokerUnitApiGateways.getBrokerToDepositoryUnitApiGateway().
                        debit(brokerDepositoryId, brokerDematAccountNumber,
                                instrumentName, quantity);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

        BooleanResultMessage creditAccountResultMessage =
                brokerUnitApiGateways.getBrokerToDepositoryUnitApiGateway().
                        credit(clientDepositoryId,
                                clientDematAccountNumber,
                                instrumentName,
                                quantity);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif

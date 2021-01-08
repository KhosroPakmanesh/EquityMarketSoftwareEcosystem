package emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.implementations;

//#if BrokerClearingBankAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.interfaces.IBrokerMoneyTransferMethod;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;



import java.util.UUID;

public class ClearingBankAccountMoneyTransferMethod implements IBrokerMoneyTransferMethod {
    private final IBrokerUnitApiGateways brokerUnitApiGateways;

    @Inject
    public ClearingBankAccountMoneyTransferMethod(IBrokerUnitApiGateways brokerUnitApiGateways) {
        this.brokerUnitApiGateways = brokerUnitApiGateways;
    }

    @Override
    public MoneyTransferMethod getMoneyTransferMethod() {
        return MoneyTransferMethod.clearingBankAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToBroker
            (UUID brokerClearingBankId, UUID brokerClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID brokerInternalBankAccountNumber, UUID clientInternalBankAccountNumber,
             double totalPrice) {

        BooleanResultMessage debitAccountResultMessage =
                brokerUnitApiGateways.getBrokerToClearingBankUnitApiGateway().
                        debit(clientClearingBankId, clientClearingBankAccountNumber, totalPrice);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

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

        BooleanResultMessage debitAccountResultMessage =
                brokerUnitApiGateways.getBrokerToClearingBankUnitApiGateway().
                        debit(brokerClearingBankId, brokerClearingBankAccountNumber, totalPrice);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

        BooleanResultMessage creditAccountResultMessage =
                brokerUnitApiGateways.getBrokerToClearingBankUnitApiGateway().
                        credit(clientClearingBankId, clientClearingBankAccountNumber, totalPrice);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif

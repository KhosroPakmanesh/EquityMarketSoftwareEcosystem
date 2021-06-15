package emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.implementations;

//#if BalanceEquitySufficiency
import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.entities.shared.SideName;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.interfaces.IRetailOrderRisk;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class BalanceEquitySufficiency implements IRetailOrderRisk {
    private final IBrokerUnitApiGateways brokerUnitApiGateways;

    @Inject
    public BalanceEquitySufficiency(IBrokerUnitApiGateways brokerUnitApiGateways) {
        this.brokerUnitApiGateways = brokerUnitApiGateways;
    }

    public BooleanResultMessage ManageRisk(RetailOrder RetailOrder) {

        if (RetailOrder.getTradingInformation().getSideName() == SideName.buy &&
            RetailOrder.getMoneyTransferMethod() != MoneyTransferMethod.brokerInternalAccount){
            return brokerUnitApiGateways.getBrokerToClearingBankUnitApiGateway()
                    .checkBalance(RetailOrder.getAccountsInformation().getClearingBankId(),
                            RetailOrder.getAccountsInformation().getClearingBankAccountNumber(),
                            RetailOrder.getTerm().getTotalPrice());

        }else if (RetailOrder.getTradingInformation().getSideName() == SideName.sell &&
                RetailOrder.getEquityTransferMethod() != EquityTransferMethod.brokerInternalAccount)
        {
            return brokerUnitApiGateways.getBrokerToDepositoryUnitApiGateway()
                    .checkBalance(RetailOrder.getAccountsInformation().getDepositoryId(),
                            RetailOrder.getAccountsInformation().getDematAccountNumber(),
                            RetailOrder.getTerm().getInstrumentName(),
                            RetailOrder.getTerm().getQuantity());
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
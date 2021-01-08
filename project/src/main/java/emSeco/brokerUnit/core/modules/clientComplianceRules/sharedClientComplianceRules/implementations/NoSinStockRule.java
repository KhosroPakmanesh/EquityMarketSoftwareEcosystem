package emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.implementations;


import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.equityInformation.EquityInformation;
import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.interfaces.ISharedClientComplianceRule;
import emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.BrokerUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class NoSinStockRule implements ISharedClientComplianceRule {
    private final BrokerUnitRepositories brokerRepositories;

    @Inject
    public NoSinStockRule(BrokerUnitRepositories brokerRepositories) {
        this.brokerRepositories = brokerRepositories;
    }

    @Override
    public BooleanResultMessage checkRule(Order order) {
        EquityInformation equityInformation =
                brokerRepositories.getEquityInformationRepository().
                        get(order.getTerm().getInstrumentName());

        if (equityInformation == null) {
            return new BooleanResultMessage(false, OperationMessage.
                    Create("The share " + order.getTerm().getInstrumentName() + " not found!"));
        }

        if (equityInformation.isSinEquity()) {
            return new BooleanResultMessage(false, OperationMessage.
                    Create("The share " + order.getTerm().getInstrumentName() + " is a sin share!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}

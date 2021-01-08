package emSeco.brokerUnit.core.modules.governmentalComplianceRules.retailGovernmentalComplianceRules.implementations;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.retailGovernmentalComplianceRules.interfaces.IRetailGovernmentalComplianceRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class RetailWashSaleRule implements IRetailGovernmentalComplianceRule {
    @Override
    public BooleanResultMessage checkRule(RetailOrder retailOrder) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}

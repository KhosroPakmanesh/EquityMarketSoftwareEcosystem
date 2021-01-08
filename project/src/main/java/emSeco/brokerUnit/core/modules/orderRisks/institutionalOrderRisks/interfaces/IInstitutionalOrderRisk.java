package emSeco.brokerUnit.core.modules.orderRisks.institutionalOrderRisks.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IInstitutionalOrderRisk {
    BooleanResultMessage ManageRisk(InstitutionalOrder institutionalOrder);
}

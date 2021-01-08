package emSeco.brokerUnit.core.modules.orderRiskManager.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.orderRiskManager.interfaces.IOrderRiskManager;
import emSeco.brokerUnit.core.modules.orderRisks.sharedOrderRisks.interfaces.ISharedOrderRisk;
import emSeco.brokerUnit.core.modules.orderRisks.institutionalOrderRisks.interfaces.IInstitutionalOrderRisk;
import emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.interfaces.IRetailOrderRisk;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderRiskManager implements IOrderRiskManager {
    private final Set<IRetailOrderRisk> retailOrderRisks;
    private final Set<IInstitutionalOrderRisk> institutionalOrderRisks;
    private final Set<ISharedOrderRisk> sharedOrderRisks;

    @Inject
    public OrderRiskManager(Set<IRetailOrderRisk> retailOrderRisks,
                            Set<IInstitutionalOrderRisk> institutionalOrderRisks,
                            Set<ISharedOrderRisk> sharedOrderRisks) {
        this.retailOrderRisks = retailOrderRisks;
        this.institutionalOrderRisks = institutionalOrderRisks;
        this.sharedOrderRisks = sharedOrderRisks;
    }

    public List<BooleanResultMessage> ManageRetailOrderRisks(RetailOrder retailOrder) {
        List<BooleanResultMessage> resultMessages = new ArrayList<>();

        for (IRetailOrderRisk retailOrderRisk :retailOrderRisks)
        {
            BooleanResultMessage riskManagementResultMessage =
                    retailOrderRisk.ManageRisk(retailOrder);
            if (!riskManagementResultMessage.getOperationResult()){
                resultMessages.add(riskManagementResultMessage);
            }
        }

        for (ISharedOrderRisk sharedOrderRisk : sharedOrderRisks)
        {
            BooleanResultMessage sharedRiskManagementResultMessage =
                    sharedOrderRisk.ManageRisk(retailOrder);
            if (!sharedRiskManagementResultMessage.getOperationResult()){
                resultMessages.add(sharedRiskManagementResultMessage);
            }
        }

        return resultMessages;
    }

    @Override
    public List<BooleanResultMessage> ManageInstitutionalOrderRisks(InstitutionalOrder institutionalOrder) {
        List<BooleanResultMessage> resultMessages = new ArrayList<>();

        for (IInstitutionalOrderRisk institutionalOrderRisk :institutionalOrderRisks)
        {
            BooleanResultMessage riskManagementResultMessage =
                    institutionalOrderRisk.ManageRisk(institutionalOrder);
            if (!riskManagementResultMessage.getOperationResult()){
                resultMessages.add(riskManagementResultMessage);
            }
        }

        for (ISharedOrderRisk sharedOrderRisk : sharedOrderRisks)
        {
            BooleanResultMessage sharedRiskManagementResultMessage =
                    sharedOrderRisk.ManageRisk(institutionalOrder);
            if (!sharedRiskManagementResultMessage.getOperationResult()){
                resultMessages.add(sharedRiskManagementResultMessage);
            }
        }

        return resultMessages;
    }
}

package emSeco.custodianUnit.core.helpers;

import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.shared.*;


import java.util.ArrayList;
import java.util.List;

public class CustodianToBrokerEntitiesMapper {
    public static List<Contract> mapFromCustodianContractToBrokerContract
            (List<emSeco.custodianUnit.core.entities.contract.Contract> custodianContracts) {
        List<emSeco.brokerUnit.core.entities.contract.Contract> brokerContracts = new ArrayList<>();

        for (emSeco.custodianUnit.core.entities.contract.Contract custodianContract : custodianContracts) {
            brokerContracts.add(new emSeco.brokerUnit.core.entities.contract.Contract(
                    custodianContract.getExchangeId(),
                    custodianContract.getInitialTradeId(),
                    custodianContract.getContractId(),
                    custodianContract.getTradeTimestamp(),
                    mapBrokerSideToCustodianSide(custodianContract.getBuySide()),
                    mapBrokerSideToCustodianSide(custodianContract.getSellSide()))
            );
        }

        return brokerContracts;
    }

    private static emSeco.brokerUnit.core.entities.shared.Side mapBrokerSideToCustodianSide
            (emSeco.custodianUnit.core.entities.shared.Side side) {
        InitiatorType initiatorType;
        if (side.getTradingInformation().getInitiatorType() ==
                emSeco.custodianUnit.core.entities.shared.InitiatorType.institutional) {
            initiatorType = InitiatorType.institutional;
        } else {
            initiatorType = InitiatorType.retail;
        }

        return new Side(
                new RoutingInformation(
                        side.getRoutingInformation().getBrokerId(),
                        side.getRoutingInformation().getCustodianId()
                ),
                new AccountsInformation(
                        side.getAccountsInformation().getClearingBankId(),
                        side.getAccountsInformation().getClearingBankAccountNumber(),
                        side.getAccountsInformation().getDepositoryId(),
                        side.getAccountsInformation().getDematAccountNumber()
                ),
                new AllocationDetailInformation(
                        side.getAllocationDetailInformation().getAllocationDetailBlockId(),
                        side.getAllocationDetailInformation().getAllocationDetailId()
                ),
                new TradingInformation(
                        side.getTradingInformation().getInitialOrderId(),
                        initiatorType,
                        side.getTradingInformation().getClientTradingCode(),
                        side.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        side.getTerm().getPrice(),
                        side.getTerm().getQuantity(),
                        side.getTerm().getInstrumentName()
                )
        );
    }
}

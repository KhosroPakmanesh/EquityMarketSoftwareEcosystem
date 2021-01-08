package emSeco.brokerUnit.core.modules.broker.interfaces;

import emSeco.brokerUnit.core.entities.noticeOfExecution.NoticeOfExecution;
import emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair;
import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.shared.*;
import emSeco.brokerUnit.core.entities.order.OrderType;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.entities.settlementResult.SettlementResult;
import emSeco.brokerUnit.core.modules.broker.models.*;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;
import java.util.UUID;

public interface IBroker {
    void setBrokerInfo(
            UUID brokerId,
            AccountsInformation brokerAccountsInformation,
            InternalAccountsInformation brokerInternalAccountsInformation,
            int initialInternalBankAccountCredit,
            List<InstrumentQuantityPair> initialInternalDematAccountCredits
    );

    UUID getBrokerId();

    InitiateRetailOrderOutputClass initiateRetailOrder_UI
            (UUID exchangeId, UUID clearingBankId, UUID clearingBankAccountNumber, UUID depositoryId,
             UUID dematAccountNumber, UUID clientTradeCode, SideName orderSide, Term orderTerm,
             OrderType orderType, MoneyTransferMethod moneyTransferMethod, EquityTransferMethod equityTransferMethod);

    InitiateInstitutionalOrderOutputClass initiateInstitutionalOrder_UI
            (UUID custodianId, UUID exchangeId, UUID registeredCode, SideName orderSide, Term orderTerm,
             OrderType orderType, MoneyTransferMethod moneyTransferMethod, EquityTransferMethod equityTransferMethod);

    List<NoticeOfExecution> getSettlementResults_UI(UUID orderId);

    SubmitAllocationDetailsOutputClass submitAllocationDetails_UI
            (List<SubmitAllocationDetailsInputClass> inputClass);

    BooleanResultMessages submitSettlementResults_API(List<SettlementResult> settlementResults);

    BooleanResultMessages submitTrades_API(List<Trade> trades);

    BooleanResultMessage submitContractAnalysisResult_API
            (UUID orderId, List<Contract> affirmedContracts, List<Contract> rejectedContracts);

    BooleanResultMessages generateContracts_REC();

    BooleanResultMessages submitTrades_REC();

    List<BooleanResultMessage> dischargeObligationsAgainstClients_REC();
}

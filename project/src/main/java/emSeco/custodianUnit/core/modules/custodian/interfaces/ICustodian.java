package emSeco.custodianUnit.core.modules.custodian.interfaces;

import emSeco.custodianUnit.core.entities.shared.AccountsInformation;
import emSeco.custodianUnit.core.entities.shared.EquityTransferMethod;
import emSeco.custodianUnit.core.entities.shared.InternalAccountsInformation;
import emSeco.custodianUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.entities.custodianDematAccount.InstrumentQuantityPair;
import emSeco.custodianUnit.core.entities.trade.Trade;
import emSeco.custodianUnit.core.entities.settlementResult.SettlementResult;
import emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsInputClass;
import emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsOutputClass;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;
import java.util.UUID;

public interface ICustodian {
    void setCustodianInfo(
            UUID custodianId,
            AccountsInformation accountsInformation,
            InternalAccountsInformation internalAccountsInformation,
            int initialCustodianInternalBankAccountCredit,
            List<InstrumentQuantityPair> initialCustodianInternalDematAccountCredits
    );

    UUID getCustodianId();

    BooleanResultMessages submitContracts_API(List<Contract> contracts);

    SubmitAllocationDetailsOutputClass submitAllocationDetails_UI
            (List<SubmitAllocationDetailsInputClass> inputClass);

    BooleanResultMessage depositMoney_UI
            (UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID clientInternalBankAccountNumber, double paymentAmount,
             MoneyTransferMethod moneyTransferMethod);

    BooleanResultMessage depositEquities_UI
            (UUID clientDepositoryId, UUID clientDematAccountNumber,
             UUID clientInternalDematAccountNumber, String instrumentName, int quantity,
             EquityTransferMethod equityTransferMethod);

    BooleanResultMessages submitTrades_API(List<Trade> trades);

    BooleanResultMessages submitSettlementResults_API(List<SettlementResult> settlementResults);

    BooleanResultMessages affirmContracts_REC();

    List<BooleanResultMessages> submitInstitutionalTrades_REC();

    List<BooleanResultMessage> dischargeObligationsAgainstClients_REC();
}

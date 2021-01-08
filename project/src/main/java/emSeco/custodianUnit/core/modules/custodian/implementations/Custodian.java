package emSeco.custodianUnit.core.modules.custodian.implementations;

import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.shared.*;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.entities.custodianBankAccount.CustodianBankAccount;
import emSeco.custodianUnit.core.entities.custodianDematAccount.CustodianDematAccount;
import emSeco.custodianUnit.core.entities.custodianDematAccount.InstrumentQuantityPair;
import emSeco.custodianUnit.core.entities.trade.Trade;
import emSeco.custodianUnit.core.entities.settlementResult.SettlementResult;
import emSeco.custodianUnit.core.entities.trade.TradeSide;
import emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.interfaces.IAllocationDetailAnalyzer;
import emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.models.AnalyzeAllocationDetailOutputClass;
import emSeco.custodianUnit.core.modules.allocationDetailFactory.interfaces.IAllocationDetailFactory;
import emSeco.custodianUnit.core.modules.allocationDetailFactory.models.ConstructAllocationDetailOutputClass;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.custodianUnit.core.entities.custodianUnitInfo.CustodianUnitInfo;
import emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsInputClass;
import emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsOutputClass;
import emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.interfaces.ICustodianMoneyTransferMethod;
import emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.interfaces.ICustodianEquityTransferMethod;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianUnitApiGateways;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.SendContractAnalysisResultInputClass;
import emSeco.shared.architecturalConstructs.*;
import emSeco.shared.exceptions.InconsistentProductGenerationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static emSeco.shared.architecturalConstructs.BooleanResultMessages.aggregateListOfResultMessages;

public class Custodian implements ICustodian {
    private UUID custodianId;
    private AccountsInformation custodianAccountsInformation;
    private InternalAccountsInformation custodianInternalAccountsInformation;
    private final ICustodianUnitRepositories custodianUnitRepositories;
    private final ICustodianUnitApiGateways custodianUnitApiGateways;
    private final IAllocationDetailFactory allocationDetailFactory;
    private final IAllocationDetailAnalyzer allocationDetailAnalyzer;
    private final Set<ICustodianMoneyTransferMethod> custodianMoneyTransferMethods;
    private final Set<ICustodianEquityTransferMethod> custodianEquityTransferMethods;

    @Inject
    public Custodian(ICustodianUnitRepositories custodianUnitRepositories,
                     ICustodianUnitApiGateways custodianUnitApiGateways,
                     IAllocationDetailFactory allocationDetailFactory,
                     IAllocationDetailAnalyzer allocationDetailAnalyzer,
                     Set<ICustodianMoneyTransferMethod> custodianMoneyTransferMethods,
                     Set<ICustodianEquityTransferMethod> custodianEquityTransferMethods) {
        this.custodianUnitRepositories = custodianUnitRepositories;
        this.custodianUnitApiGateways = custodianUnitApiGateways;
        this.allocationDetailFactory = allocationDetailFactory;
        this.allocationDetailAnalyzer = allocationDetailAnalyzer;
        this.custodianMoneyTransferMethods = custodianMoneyTransferMethods;
        this.custodianEquityTransferMethods = custodianEquityTransferMethods;
    }

    @Override
    public void setCustodianInfo(
            UUID custodianId,
            AccountsInformation custodianAccountsInformation,
            InternalAccountsInformation custodianInternalAccountsInformation,
            int initialCustodianInternalBankAccountCredit,
            List<InstrumentQuantityPair> initialCustodianInternalDematAccountCredits
    ) {
        this.custodianId = custodianId;
        this.custodianAccountsInformation = custodianAccountsInformation;
        this.custodianInternalAccountsInformation = custodianInternalAccountsInformation;

        custodianUnitRepositories.getCustodianUnitInfoRepository().
                add(new CustodianUnitInfo(custodianId, custodianAccountsInformation));

        custodianUnitRepositories.getCustodianBankAccountRepository().add(
                new CustodianBankAccount(custodianInternalAccountsInformation.getInternalBankAccountNumber(),
                        initialCustodianInternalBankAccountCredit));
        custodianUnitRepositories.getCustodianDematAccountRepository().add
                (new CustodianDematAccount(custodianInternalAccountsInformation.getInternalDematAccountNumber(),
                        initialCustodianInternalDematAccountCredits));
    }

    @Override
    public UUID getCustodianId() {
        return custodianId;
    }

    @Override
    public SubmitAllocationDetailsOutputClass submitAllocationDetails_UI
            (List<SubmitAllocationDetailsInputClass> inputClasses) {
        SubmitAllocationDetailsOutputClass outputClass = new SubmitAllocationDetailsOutputClass();

        ConstructAllocationDetailOutputClass constructAllocationDetailOutputClass =
                allocationDetailFactory.constructAllocationDetail(inputClasses);

        if (constructAllocationDetailOutputClass.hasErrors()) {
            outputClass.setAllocationDetailConstructionResultMessages
                    (constructAllocationDetailOutputClass.getAllocationDetailsValidationResultMessages());
            return outputClass;
        }

        custodianUnitRepositories.getAllocationDetailRepository().
                add(constructAllocationDetailOutputClass.getAllocationDetails());

        return outputClass;
    }

    @Override
    public BooleanResultMessage depositMoney_UI
            (UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID clientInternalBankAccountNumber, double paymentAmount,
             MoneyTransferMethod moneyTransferMethod) {

        ICustodianMoneyTransferMethod custodianMoneyTransferMethod =
                custodianMoneyTransferMethods.stream().filter(cusMoneyTransferMethod ->
                        cusMoneyTransferMethod.getMoneyTransferMethod() ==
                                moneyTransferMethod)
                        .findAny().orElse(null);

        if (custodianMoneyTransferMethod == null) {
            throw new InconsistentProductGenerationException
                    ("The product does not support this money transfer method!");
        }

        return custodianMoneyTransferMethod.transferFromClientToCustodian(
                custodianAccountsInformation.getClearingBankId(),
                custodianAccountsInformation.getClearingBankAccountNumber(),
                clientClearingBankId, clientClearingBankAccountNumber,
                custodianInternalAccountsInformation.getInternalBankAccountNumber(),
                clientInternalBankAccountNumber,
                paymentAmount);
    }

    @Override
    public BooleanResultMessage depositEquities_UI
            (UUID clientDepositoryId, UUID clientDematAccountNumber,
             UUID clientInternalDematAccountNumber, String instrumentName, int quantity,
             EquityTransferMethod equityTransferMethod) {

        ICustodianEquityTransferMethod custodianEquityTransferMethod =
                custodianEquityTransferMethods.stream().filter(brkEquityTransferMethod ->
                        brkEquityTransferMethod.getEquityTransferMethod() ==
                                equityTransferMethod)
                        .findAny().orElse(null);

        if (custodianEquityTransferMethod == null) {
            throw new InconsistentProductGenerationException
                    ("The product does not support this equity transfer method!");
        }

        return custodianEquityTransferMethod.transferFromClientToCustodian(
                custodianAccountsInformation.getDepositoryId(),
                custodianAccountsInformation.getDematAccountNumber(),
                clientDepositoryId, clientDematAccountNumber,
                custodianInternalAccountsInformation.getInternalDematAccountNumber(),
                clientInternalDematAccountNumber,
                instrumentName, quantity);
    }

    @Override
    public BooleanResultMessages submitContracts_API(List<Contract> contracts) {
        //Here we could have a factory to reconstruct and validate contracts.
        custodianUnitRepositories.getContractRepository().add(contracts);
        return new BooleanResultMessages(true, new ArrayList<>());
    }

    @Override
    public BooleanResultMessages submitTrades_API(List<Trade> groupedTrades) {
        custodianUnitRepositories.getTradeRepository().add(groupedTrades);
        return new BooleanResultMessages(true, new ArrayList<>());
    }

    @Override
    public BooleanResultMessages submitSettlementResults_API(List<SettlementResult> settlementResults) {
        custodianUnitRepositories.getSettlementResultRepository().add(settlementResults);
        return new BooleanResultMessages(true, new ArrayList<>());
    }

    @Override
    public BooleanResultMessages affirmContracts_REC() {
        List<BooleanResultMessages> listOfResultMessages = new ArrayList<>();

        List<Contract> contracts = custodianUnitRepositories.getContractRepository().get();
        List<AllocationDetail> allocationDetails = custodianUnitRepositories.getAllocationDetailRepository().get();

        allocationDetails.stream().collect(Collectors.
                groupingBy(allocationDetail -> allocationDetail.getTradingInformation().getInitialOrderId())).
                forEach((initialOrderId, groupedAllocationDetails) -> {

                    List<Contract> matchedBuyContracts = contracts.stream().
                            filter(contract -> contract.getBuySide().getTradingInformation().
                                    getInitialOrderId() == initialOrderId).
                            collect(Collectors.toList());

                    if (matchedBuyContracts.size() > 0) {
                        AnalyzeAllocationDetailOutputClass allocationDetailOutputClass =
                                allocationDetailAnalyzer.analyzeAllocationDetail
                                        (matchedBuyContracts, groupedAllocationDetails, SideName.buy);

                        listOfResultMessages.add(
                                custodianUnitApiGateways.getCustodianToBrokerUnitApiGateway().
                                        sendContractAnalysisResult(new SendContractAnalysisResultInputClass
                                                (initialOrderId, allocationDetailOutputClass.getAffirmedContracts(),
                                                        allocationDetailOutputClass.getNotAffirmedContracts())));
                    }

                    List<Contract> matchedSellContracts = contracts.stream().
                            filter(contract -> contract.getSellSide().getTradingInformation()
                                    .getInitialOrderId() == initialOrderId).
                            collect(Collectors.toList());
                    if (matchedSellContracts.size() > 0) {

                        AnalyzeAllocationDetailOutputClass allocationDetailOutputClass =
                                allocationDetailAnalyzer.analyzeAllocationDetail
                                        (matchedSellContracts, groupedAllocationDetails, SideName.sell);

                        listOfResultMessages.add
                                (custodianUnitApiGateways.getCustodianToBrokerUnitApiGateway().
                                        sendContractAnalysisResult(new SendContractAnalysisResultInputClass
                                                (initialOrderId, allocationDetailOutputClass.getAffirmedContracts(),
                                                        allocationDetailOutputClass.getNotAffirmedContracts())));
                    }
                });

        return aggregateListOfResultMessages(listOfResultMessages);
    }

    @Override
    public List<BooleanResultMessages> submitInstitutionalTrades_REC() {
        List<Trade> trades = custodianUnitRepositories.getTradeRepository().get();
        return custodianUnitApiGateways.getCustodianToClearingCorpUnitApiGateway().
                submitInstitutionalTrades(trades);
    }

    @Override
    public List<BooleanResultMessage> dischargeObligationsAgainstClients_REC() {
        List<SettlementResult> settlementResults =
                custodianUnitRepositories.getSettlementResultRepository().get();

        List<BooleanResultMessage> dischargeObligationsResultMessages = new ArrayList<>();
        for (SettlementResult settlementResult : settlementResults) {
            BooleanResultMessage buySideresultMessage =
                    dischargeBuySideObligations(settlementResult);
            if (!buySideresultMessage.getOperationResult()) {
                dischargeObligationsResultMessages.add(buySideresultMessage);
            }

            BooleanResultMessage sellSideresultMessage =
                    dischargeSellSideObligations(settlementResult);

            if (!sellSideresultMessage.getOperationResult()) {
                dischargeObligationsResultMessages.add(sellSideresultMessage);
            }

        }

        return dischargeObligationsResultMessages;
    }

    private BooleanResultMessage dischargeBuySideObligations(SettlementResult settlementResult) {
        BooleanResultMessage normalExecutionFlowResultMessage =
                new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());

        TradeSide buyTradeSide = settlementResult.getTrade().getBuySide();
        if (!(buyTradeSide.getRoutingInformation().getCustodianId() == custodianId &&
                !buyTradeSide.getClientObligationsDischarged())) {
            return normalExecutionFlowResultMessage;
        }

        AllocationDetail allocationDetail =
                custodianUnitRepositories.getAllocationDetailRepository().
                        get(buyTradeSide.getAllocationDetailInformation().getAllocationDetailId());

        if (allocationDetail == null) {
            return normalExecutionFlowResultMessage;
        }

        ICustodianEquityTransferMethod custodianEquityTransferMethod =
                custodianEquityTransferMethods.stream().filter(brkMoneyTransferMethod ->
                        brkMoneyTransferMethod.getEquityTransferMethod() ==
                                allocationDetail.getEquityTransferMethod())
                        .findAny().orElse(null);

        if (custodianEquityTransferMethod == null) {
            throw new InconsistentProductGenerationException
                    ("The product does not support this equity transfer method!");
        }

        BooleanResultMessage equityTransferResultMessage =
                custodianEquityTransferMethod.transferFromCustodianToClient(
                        custodianAccountsInformation.getDepositoryId(),
                        custodianAccountsInformation.getDematAccountNumber(),
                        allocationDetail.getAccountsInformation().getDepositoryId(),
                        allocationDetail.getAccountsInformation().getDematAccountNumber(),
                        custodianInternalAccountsInformation.getInternalDematAccountNumber(),
                        buyTradeSide.getTradingInformation().getClientTradingCode(),
                        buyTradeSide.getTerm().getInstrumentName(), buyTradeSide.getTerm().getQuantity());

        if (equityTransferResultMessage.getOperationResult()) {
            buyTradeSide.setClientObligationsDischarged();
        } else {
            return equityTransferResultMessage;
        }

        return equityTransferResultMessage;
    }

    private BooleanResultMessage dischargeSellSideObligations(SettlementResult settlementResult) {
        BooleanResultMessage normalExecutionFlowResultMessage =
                new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());

        TradeSide sellTradeSide = settlementResult.getTrade().getSellSide();
        if (!(sellTradeSide.getRoutingInformation().getCustodianId() == custodianId &&
                !sellTradeSide.getClientObligationsDischarged())) {
            return normalExecutionFlowResultMessage;
        }

        AllocationDetail allocationDetail = custodianUnitRepositories.getAllocationDetailRepository().
                get(sellTradeSide.getAllocationDetailInformation().getAllocationDetailId());

        if (allocationDetail == null) {
            return normalExecutionFlowResultMessage;
        }

        ICustodianMoneyTransferMethod custodianMoneyTransferMethod =
                custodianMoneyTransferMethods.stream().filter(cusMoneyTransferMethod ->
                        cusMoneyTransferMethod.getMoneyTransferMethod() ==
                                allocationDetail.getMoneyTransferMethod())
                        .findAny().orElse(null);

        if (custodianMoneyTransferMethod == null) {
            throw new InconsistentProductGenerationException
                    ("The product does not support this money transfer method!");
        }

        BooleanResultMessage moneyTransferResultMessage =
                custodianMoneyTransferMethod.transferFromCustodianToClient(
                        custodianAccountsInformation.getClearingBankId(),
                        custodianAccountsInformation.getClearingBankAccountNumber(),
                        allocationDetail.getAccountsInformation().getClearingBankId(),
                        allocationDetail.getAccountsInformation().getClearingBankAccountNumber(),
                        custodianInternalAccountsInformation.getInternalBankAccountNumber(),
                        allocationDetail.getTradingInformation().getClientTradingCode(),
                        sellTradeSide.getTerm().getTotalPrice());

        if (moneyTransferResultMessage.getOperationResult()) {
            sellTradeSide.setClientObligationsDischarged();
        } else {
            return moneyTransferResultMessage;
        }

        return moneyTransferResultMessage;
    }
}

package emSeco.brokerUnit.core.modules.broker.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.brokerUnitInfo.BrokerUnitInfo;
import emSeco.brokerUnit.core.entities.noticeOfExecution.NoticeOfExecution;
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.entities.brokerBankAccount.BrokerBankAccount;
import emSeco.brokerUnit.core.entities.brokerDematAccount.BrokerDematAccount;
import emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair;
import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.order.*;
import emSeco.brokerUnit.core.entities.shared.*;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.entities.settlementResult.SettlementResult;
import emSeco.brokerUnit.core.modules.broker.models.*;
import emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.interfaces.IBrokerMoneyTransferMethod;
import emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.interfaces.IBrokerEquityTransferMethod;
import emSeco.brokerUnit.core.modules.contractFactory.interfaces.IContractFactory;
import emSeco.brokerUnit.core.modules.allocationDetailFactory.interfaces.IAllocationDetailFactory;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.brokerUnit.core.modules.orderFactory.models.ConstructInstitutionalOrderOutputClass;
import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.brokerUnit.core.modules.orderFactory.models.ConstructRetailOrderOutputClass;
import emSeco.brokerUnit.core.modules.orderFactory.interfaces.IOrderFactory;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.shared.architecturalConstructs.*;
import emSeco.shared.exceptions.InconsistentProductGenerationException;
import emSeco.shared.services.dateTimeService.interfaces.IDateTimeService;

import java.util.*;
import java.util.stream.Collectors;

import static emSeco.brokerUnit.core.helpers.BrokerToBrokerEntitiesMapper.convertContractsToTrades;
import static emSeco.brokerUnit.core.helpers.BrokerToBrokerEntitiesMapper.mapFromTradesToNoticeOfExecutions;

public class Broker implements IBroker {
    private UUID brokerId;
    private AccountsInformation brokerAccountsInformation;
    private InternalAccountsInformation brokerInternalAccountsInformation;
    private final IBrokerUnitApiGateways brokerUnitApiGateways;
    private final IBrokerUnitRepositories brokerUnitRepositories;
    private final IOrderFactory orderFactory;
    private final IAllocationDetailFactory allocationDetailFactory;
    private final IContractFactory contractFactory;
    private final Set<IBrokerMoneyTransferMethod> brokerMoneyTransferMethods;
    private final Set<IBrokerEquityTransferMethod> brokerEquityTransferMethods;
    private final IDateTimeService dateTimeService;

    @Inject
    public Broker(
            IBrokerUnitRepositories brokerUnitRepositories,
            IBrokerUnitApiGateways brokerUnitApiGateways,
            IOrderFactory orderFactory,
            IAllocationDetailFactory allocationDetailFactory,
            IContractFactory contractFactory,
            Set<IBrokerMoneyTransferMethod> brokerMoneyTransferMethods,
            Set<IBrokerEquityTransferMethod> brokerEquityTransferMethods,
            IDateTimeService dateTimeService) {
        this.brokerUnitRepositories = brokerUnitRepositories;
        this.brokerUnitApiGateways = brokerUnitApiGateways;
        this.orderFactory = orderFactory;
        this.allocationDetailFactory = allocationDetailFactory;
        this.contractFactory = contractFactory;
        this.brokerMoneyTransferMethods = brokerMoneyTransferMethods;
        this.brokerEquityTransferMethods = brokerEquityTransferMethods;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public UUID getBrokerId() {
        return this.brokerId;
    }

    @Override
    public void setBrokerInfo(
            UUID brokerId,
            AccountsInformation brokerAccountsInformation,
            InternalAccountsInformation brokerInternalAccountsInformation,
            int initialInternalBankAccountCredit,
            List<InstrumentQuantityPair> initialInternalDematAccountCredits) {

        this.brokerId = brokerId;
        this.brokerAccountsInformation = brokerAccountsInformation;
        this.brokerInternalAccountsInformation = brokerInternalAccountsInformation;

        brokerUnitRepositories.getBrokerUnitInfoRepository().add
                (new BrokerUnitInfo(brokerId, brokerAccountsInformation));

        brokerUnitRepositories.getBrokerBankAccountRepository().add(
                new BrokerBankAccount(
                        brokerInternalAccountsInformation.getInternalBankAccountNumber()
                        , initialInternalBankAccountCredit));
        brokerUnitRepositories.getBrokerDematAccountRepository().add
                (new BrokerDematAccount(
                        brokerInternalAccountsInformation.getInternalDematAccountNumber(),
                        initialInternalDematAccountCredits));
    }

    public InitiateRetailOrderOutputClass initiateRetailOrder_UI
            (UUID exchangeId, UUID clientClearingBankId, UUID clientClearingBankAccountNumber, UUID clientDepositoryId,
             UUID clientDematAccountNumber, UUID clientTradingCode, SideName orderSideName, Term orderTerm,
             OrderType orderType, MoneyTransferMethod moneyTransferMethod, EquityTransferMethod equityTransferMethod) {

        InitiateRetailOrderOutputClass outputClass = new InitiateRetailOrderOutputClass();

        ConstructRetailOrderOutputClass constructRetailOrderOutputClass =
                orderFactory.constructRetailOrder(
                        new OrderRoutingInformation(
                                this.brokerId,
                                exchangeId,
                                null
                        ),
                        new AccountsInformation(
                                clientClearingBankId,
                                clientClearingBankAccountNumber,
                                clientDepositoryId,
                                clientDematAccountNumber
                        ),
                        new OrderTradingInformation(
                                orderSideName,
                                orderType,
                                dateTimeService.getDateTime()
                        ),
                        orderTerm, clientTradingCode,
                        moneyTransferMethod,
                        equityTransferMethod);

        if (constructRetailOrderOutputClass.hasErrors()) {
            outputClass.setOrderConstructionResultMessages
                    (constructRetailOrderOutputClass.getOrderConstructionResultMessages());
            return outputClass;
        }

        RetailOrder retailOrder = constructRetailOrderOutputClass.getRetailOrder();

        if (orderSideName == SideName.buy) {
            IBrokerMoneyTransferMethod brokerMoneyTransferMethod =
                    brokerMoneyTransferMethods.stream().filter(brkMoneyTransferMethod ->
                            brkMoneyTransferMethod.getMoneyTransferMethod() == moneyTransferMethod)
                            .findAny().orElse(null);

            if (brokerMoneyTransferMethod == null) {
                throw new InconsistentProductGenerationException
                        ("The product does not support this money transfer method!");
            }

            BooleanResultMessage moneyTransferResultMessage =
                    brokerMoneyTransferMethod.transferFromClientToBroker(
                            brokerAccountsInformation.getClearingBankId(),
                            brokerAccountsInformation.getClearingBankAccountNumber(),
                            clientClearingBankId,
                            clientClearingBankAccountNumber,
                            brokerInternalAccountsInformation.getInternalBankAccountNumber(),
                            clientTradingCode, orderTerm.getTotalPrice());

            if (!moneyTransferResultMessage.getOperationResult()) {
                outputClass.setMoneyTransferResultMessage(moneyTransferResultMessage);
                return outputClass;
            }

        } else {

            IBrokerEquityTransferMethod brokerEquityTransferMethod =
                    brokerEquityTransferMethods.stream().filter(brkMoneyTransferMethod ->
                            brkMoneyTransferMethod.getEquityTransferMethod() == equityTransferMethod)
                            .findAny().orElse(null);

            if (brokerEquityTransferMethod == null) {
                throw new InconsistentProductGenerationException
                        ("The product does not support this equity transfer method!");
            }

            BooleanResultMessage equityTransferResultMessage =
                    brokerEquityTransferMethod.transferFromClientToBroker(
                            brokerAccountsInformation.getDepositoryId(),
                            brokerAccountsInformation.getDematAccountNumber(),
                            clientDepositoryId,
                            clientDematAccountNumber,
                            brokerInternalAccountsInformation.getInternalDematAccountNumber(),
                            clientTradingCode,
                            orderTerm.getInstrumentName(), orderTerm.getQuantity());

            if (!equityTransferResultMessage.getOperationResult()) {
                outputClass.setMoneyTransferResultMessage(equityTransferResultMessage);
                return outputClass;
            }
        }

        BooleanResultMessages orderSubmissionResultMessage =
                brokerUnitApiGateways.getBrokerToExchangeUnitApiGateway()
                        .submitRetailOrder(retailOrder);

        if (!orderSubmissionResultMessage.getOperationResult()) {
            return outputClass;
        }

        brokerUnitRepositories.getRetailOrdersRepository().add(retailOrder);

        outputClass.setOrderId(retailOrder.getOrderId());
        outputClass.orderSubmitted();
        return outputClass;
    }

    public InitiateInstitutionalOrderOutputClass initiateInstitutionalOrder_UI(
            UUID custodianId, UUID exchangeId, UUID registeredCode, SideName orderSideName, Term orderTerm,
            OrderType orderType, MoneyTransferMethod moneyTransferMethod, EquityTransferMethod equityTransferMethod) {

        InitiateInstitutionalOrderOutputClass outputClass = new InitiateInstitutionalOrderOutputClass();

        ConstructInstitutionalOrderOutputClass constructInstitutionalOrderOutputClass =
                orderFactory.constructInstitutionalOrder(
                        new OrderRoutingInformation(
                                this.brokerId,
                                exchangeId,
                                custodianId
                        ),
                        new OrderTradingInformation(
                                orderSideName,
                                orderType,
                                dateTimeService.getDateTime()
                        ),
                        orderTerm,
                        registeredCode,
                        moneyTransferMethod,
                        equityTransferMethod
                );

        if (constructInstitutionalOrderOutputClass.hasErrors()) {
            outputClass.setOrderConstructionResultMessages(
                    constructInstitutionalOrderOutputClass.getOrderConstructionResultMessages());
            return outputClass;
        }

        InstitutionalOrder institutionalOrder =
                constructInstitutionalOrderOutputClass.getInstitutionalOrder();

        BooleanResultMessages orderSubmissionResult =
                brokerUnitApiGateways.getBrokerToExchangeUnitApiGateway()
                        .submitInstitutionalOrder(institutionalOrder);

        if (!orderSubmissionResult.getOperationResult()) {
            return outputClass;
        }

        brokerUnitRepositories.getInstitutionalOrdersRepository().add(institutionalOrder);

        outputClass.setOrderId(institutionalOrder.getOrderId());
        outputClass.orderSubmitted();
        return outputClass;
    }

    @Override
    public List<NoticeOfExecution> getSettlementResults_UI(UUID orderId) {
        List<Trade> trades = brokerUnitRepositories.getTradeRepository().get(orderId);
        return mapFromTradesToNoticeOfExecutions(trades);
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

        brokerUnitRepositories.getAllocationDetailRepository().
                add(constructAllocationDetailOutputClass.getAllocationDetails());

        return outputClass;
    }

    @Override
    public BooleanResultMessages submitSettlementResults_API(List<SettlementResult> settlementResults) {
        //Here we could have a factory to reconstruct and validate trade results.
        brokerUnitRepositories.getSettlementResultRepository().add(settlementResults);
        return new BooleanResultMessages(true, new ArrayList<>());
    }

    @Override
    public BooleanResultMessages submitTrades_API(List<Trade> trades) {
        //Here we could have a factory to reconstruct and validate trades.
        brokerUnitRepositories.getTradeRepository().add(trades);
        return new BooleanResultMessages(true, new ArrayList<>());
    }

    @Override
    public BooleanResultMessage submitContractAnalysisResult_API(
            UUID orderId, List<Contract> affirmedContracts, List<Contract> rejectedContracts) {

        List<UUID> affirmedContractIds = affirmedContracts.
                stream().map(Contract::getContractId).collect(Collectors.toList());

        if (affirmedContractIds.size() > 0) {
            List<Contract> retrievedAffirmedContracts = brokerUnitRepositories.
                    getContractRepository().get(affirmedContractIds);

            //Since we have reference to objects, and we do not have permanent storage mechanism,
            //we prefer not to send back contracts to repository.
            retrievedAffirmedContracts.forEach(contract -> contract.setIsAffirmed(true));

            return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
        }

        return new BooleanResultMessage(false,
                OperationMessage.Create("No affirmed contracts received!"));
    }

    @Override
    public BooleanResultMessages generateContracts_REC() {
        List<Contract> contracts = new ArrayList<>();
        List<Trade> trades = brokerUnitRepositories.getTradeRepository().
                getInstitutionalTrades();

        List<Trade> tradesWithSameBrokers = trades.stream()
                .filter(tr -> (tr.getBuySide().getRoutingInformation().getBrokerId() ==
                        tr.getSellSide().getRoutingInformation().getBrokerId()) &&
                        tr.getBuySide().getRoutingInformation().getBrokerId() == this.brokerId).
                        collect(Collectors.toList());

        //Change side for buy side
        tradesWithSameBrokers.stream().collect(
                Collectors.groupingBy(tr -> tr.getBuySide().getTradingInformation().getInitialOrderId())).
                forEach((orderId, groupedTrades) -> {
                    List<AllocationDetail> allocationDetails = brokerUnitRepositories.
                            getAllocationDetailRepository().getAllocationDetails(orderId);

                    if (allocationDetails.size() > 0 && groupedTrades.size() > 0) {
                        contracts.addAll(
                                contractFactory.constructContracts(
                                        groupedTrades, allocationDetails, SideName.buy));
                    }
                });

        //Change side for sell side
        tradesWithSameBrokers.stream().collect(
                Collectors.groupingBy(tr -> tr.getSellSide().getTradingInformation().getInitialOrderId())).
                forEach((orderId, groupedTrades) -> {
                    List<AllocationDetail> allocationDetails = brokerUnitRepositories.
                            getAllocationDetailRepository().getAllocationDetails(orderId);

                    if (allocationDetails.size() > 0 && groupedTrades.size() > 0) {
                        contracts.addAll(
                                contractFactory.constructContracts(
                                        groupedTrades, allocationDetails, SideName.sell));
                    }
                });

        List<Trade> tradesWithDifferentBrokers = trades.stream()
                .filter(tr -> (tr.getBuySide().getRoutingInformation().getBrokerId() !=
                        tr.getSellSide().getRoutingInformation().getBrokerId())).collect(Collectors.toList());

        //Change side for buy side
        tradesWithDifferentBrokers.stream().
                filter(trade -> trade.getBuySide().getRoutingInformation().getBrokerId() == this.brokerId).
                collect(Collectors.groupingBy(trade -> trade.getBuySide().getTradingInformation().getInitialOrderId()))
                .forEach((orderId, groupedTrades) -> {

                    List<AllocationDetail> allocationDetails = brokerUnitRepositories.
                            getAllocationDetailRepository().getAllocationDetails(orderId);

                    if (allocationDetails.size() > 0 && groupedTrades.size() > 0) {
                        contracts.addAll(
                                contractFactory.constructContracts(
                                        groupedTrades, allocationDetails, SideName.buy));
                    }
                });

        //Change side for sell side
        tradesWithDifferentBrokers.stream().
                filter(trade -> trade.getSellSide().getRoutingInformation().getBrokerId() == this.brokerId).
                collect(Collectors.groupingBy(trade -> trade.getSellSide().getTradingInformation().getInitialOrderId()))
                .forEach((orderId, groupedTrades) -> {
                    List<AllocationDetail> allocationDetails = brokerUnitRepositories.
                            getAllocationDetailRepository().getAllocationDetails(orderId);

                    if (allocationDetails.size() > 0 && groupedTrades.size() > 0) {
                        contracts.addAll(
                                contractFactory.constructContracts(
                                        groupedTrades, allocationDetails, SideName.sell));
                    }
                });

        brokerUnitRepositories.getContractRepository().add(contracts);
        return brokerUnitApiGateways.getBrokerToCustodianUnitApiGateway().submitContracts(contracts);
    }

    @Override
    public BooleanResultMessages submitTrades_REC() {
        List<Contract> affirmedContracts = brokerUnitRepositories.getContractRepository()
                .getAffirmedContracts();

        List<Trade> trades = convertContractsToTrades(affirmedContracts, dateTimeService.getDateTime());
        return brokerUnitApiGateways.getBrokerToCustodianUnitApiGateway().submitTrades(trades);
    }

    @Override
    public List<BooleanResultMessage> dischargeObligationsAgainstClients_REC() {
        List<SettlementResult> settlementResults = brokerUnitRepositories.getSettlementResultRepository().get();

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

        Side buyTradeSide = settlementResult.getTrade().getBuySide();
        if (!(buyTradeSide.getRoutingInformation().getBrokerId() == brokerId &&
                !buyTradeSide.isClientObligationsDischarged())) {
            return normalExecutionFlowResultMessage;
        }

        RetailOrder retailOrder = brokerUnitRepositories.getRetailOrdersRepository().
                get(buyTradeSide.getTradingInformation().getInitialOrderId());
        if (retailOrder == null) {
            return normalExecutionFlowResultMessage;
        }

        IBrokerEquityTransferMethod brokerEquityTransferMethod =
                brokerEquityTransferMethods.stream().filter(brkMoneyTransferMethod ->
                        brkMoneyTransferMethod.getEquityTransferMethod() ==
                                retailOrder.getEquityTransferMethod())
                        .findAny().orElse(null);

        if (brokerEquityTransferMethod == null) {
            throw new InconsistentProductGenerationException
                    ("The product does not support this equity transfer method!");
        }

        BooleanResultMessage equityTransferResultMessage =
                brokerEquityTransferMethod.transferFromBrokerToClient(
                        brokerAccountsInformation.getDepositoryId(),
                        brokerAccountsInformation.getDematAccountNumber(),
                        retailOrder.getAccountsInformation().getDepositoryId(),
                        retailOrder.getAccountsInformation().getDematAccountNumber(),
                        brokerInternalAccountsInformation.getInternalDematAccountNumber(),
                        buyTradeSide.getTradingInformation().getClientTradingCode(),
                        buyTradeSide.getTerm().getInstrumentName(), buyTradeSide.getTerm().getQuantity());

        if (equityTransferResultMessage.getOperationResult()) {
            buyTradeSide.clientObligationsDischarged();
        } else {
            return equityTransferResultMessage;
        }

        return equityTransferResultMessage;
    }

    private BooleanResultMessage dischargeSellSideObligations(SettlementResult settlementResult) {
        BooleanResultMessage normalExecutionFlowResultMessage =
                new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());

        Side sellTradeSide = settlementResult.getTrade().getSellSide();
        if (!(sellTradeSide.getRoutingInformation().getBrokerId() == brokerId &&
                !sellTradeSide.isClientObligationsDischarged())) {
            return normalExecutionFlowResultMessage;
        }

        RetailOrder retailOrder = brokerUnitRepositories.getRetailOrdersRepository().
                get(sellTradeSide.getTradingInformation().getInitialOrderId());

        if (retailOrder == null) {
            return normalExecutionFlowResultMessage;
        }

        IBrokerMoneyTransferMethod brokerMoneyTransferMethod =
                brokerMoneyTransferMethods.stream().filter(brkMoneyTransferMethod ->
                        brkMoneyTransferMethod.getMoneyTransferMethod() ==
                                retailOrder.getMoneyTransferMethod())
                        .findAny().orElse(null);

        if (brokerMoneyTransferMethod == null) {
            throw new InconsistentProductGenerationException
                    ("The product does not support this money transfer method!");
        }

        BooleanResultMessage moneyTransferResultMessage =
                brokerMoneyTransferMethod.transferFromBrokerToClient(
                        brokerAccountsInformation.getClearingBankId(),
                        brokerAccountsInformation.getClearingBankAccountNumber(),
                        retailOrder.getAccountsInformation().getClearingBankId(),
                        retailOrder.getAccountsInformation().getClearingBankAccountNumber(),
                        brokerInternalAccountsInformation.getInternalBankAccountNumber(),
                        retailOrder.getClientTradingCode(),
                        sellTradeSide.getTerm().getTotalPrice());

        if (moneyTransferResultMessage.getOperationResult()) {
            sellTradeSide.clientObligationsDischarged();
        } else {
            return moneyTransferResultMessage;
        }

        return moneyTransferResultMessage;
    }
}
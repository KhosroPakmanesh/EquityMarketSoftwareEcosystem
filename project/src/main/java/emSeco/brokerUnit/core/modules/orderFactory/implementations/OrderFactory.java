package emSeco.brokerUnit.core.modules.orderFactory.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.order.*;
import emSeco.brokerUnit.core.entities.shared.AccountsInformation;
import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.entities.shared.Term;
import emSeco.brokerUnit.core.modules.bestVenueAnalysisAlgorithms.interfaces.IBestVenueAnalysisAlgorithm;
import emSeco.brokerUnit.core.modules.clientComplianceRulesChecker.interfaces.IClientComplianceRulesChecker;
import emSeco.brokerUnit.core.modules.governmentalComplianceRulesCheker.interfaces.IGovernmentalComplianceRulesChecker;
import emSeco.brokerUnit.core.modules.orderFactory.models.ConstructInstitutionalOrderOutputClass;
import emSeco.brokerUnit.core.modules.orderFactory.models.ConstructRetailOrderOutputClass;
import emSeco.brokerUnit.core.modules.orderRiskManager.interfaces.IOrderRiskManager;
import emSeco.brokerUnit.core.modules.orderValidator.interfaces.IOrderValidator;
import emSeco.brokerUnit.core.modules.orderFactory.interfaces.IOrderFactory;
import emSeco.brokerUnit.core.modules.portfolioOptimizationAlgorithms.interfaces.IPortfolioOptimizationAlgorithm;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.services.uuidGenerator.interfaces.IUUIDGenerator;


import java.util.List;
import java.util.UUID;

public class OrderFactory implements IOrderFactory {
    private final IOrderValidator orderValidator;
    private final IClientComplianceRulesChecker clientComplianceRulesChecker;
    private final IGovernmentalComplianceRulesChecker governmentalComplianceRulesChecker;
    private final IPortfolioOptimizationAlgorithm portfolioOptimizationAlgorithm;
    private final IBestVenueAnalysisAlgorithm bestVenueAnalysisAlgorithm;
    private final IOrderRiskManager orderRiskManager;
    private final IUUIDGenerator uuidGenerator;

    @Inject
    public OrderFactory(IOrderValidator orderValidator,
                        IClientComplianceRulesChecker clientComplianceRulesChecker,
                        IGovernmentalComplianceRulesChecker governmentalComplianceRulesChecker,
                        IPortfolioOptimizationAlgorithm portfolioOptimizationAlgorithm,
                        IBestVenueAnalysisAlgorithm bestVenueAnalysisAlgorithm,
                        IOrderRiskManager orderRiskManager,
                        IUUIDGenerator uuidGenerator
    ) {
        this.orderValidator = orderValidator;
        this.clientComplianceRulesChecker = clientComplianceRulesChecker;
        this.governmentalComplianceRulesChecker = governmentalComplianceRulesChecker;
        this.portfolioOptimizationAlgorithm = portfolioOptimizationAlgorithm;
        this.bestVenueAnalysisAlgorithm = bestVenueAnalysisAlgorithm;
        this.orderRiskManager = orderRiskManager;
        this.uuidGenerator = uuidGenerator;
    }

    @Override
    public ConstructRetailOrderOutputClass constructRetailOrder(
            OrderRoutingInformation orderRoutingInformation,
            AccountsInformation accountsInformation,
            OrderTradingInformation orderTradingInformation,
            Term orderTerm,
            UUID clientTradingCode,
            MoneyTransferMethod moneyTransferMethod,
            EquityTransferMethod equityTransferMethod) {

        RetailOrder retailOrder = new RetailOrder(
                orderRoutingInformation,
                uuidGenerator.generateUUID(),
                accountsInformation,
                orderTradingInformation,
                orderTerm,
                clientTradingCode,
                moneyTransferMethod,
                equityTransferMethod
        );

        if (retailOrder.getRoutingInformation().getExchangeId() == null) {
            retailOrder.getRoutingInformation().setExchangeId
                    (bestVenueAnalysisAlgorithm.ChooseBestVenue());
        }

        if (retailOrder.getTerm().getInstrumentName().equals("")) {
            retailOrder.getTerm().setInstrumentName
                    (portfolioOptimizationAlgorithm.optimizePortfolio());
        }

        List<BooleanResultMessage> orderValidationResultMessages =
                orderValidator.validateRetailOrder(retailOrder);

        List<BooleanResultMessage> clientComplianceCheckingResultMessages =
                clientComplianceRulesChecker.checkRetailClientComplianceRules(retailOrder);

        List<BooleanResultMessage> governmentalComplianceCheckingResultMessages
                = governmentalComplianceRulesChecker.checkRetailGovernmentalComplianceRules(retailOrder);

        List<BooleanResultMessage> riskManagementResultMessages =
                orderRiskManager.ManageRetailOrderRisks(retailOrder);

        return new ConstructRetailOrderOutputClass
                (orderValidationResultMessages, clientComplianceCheckingResultMessages,
                        governmentalComplianceCheckingResultMessages, riskManagementResultMessages, retailOrder);
    }

    @Override
    public ConstructInstitutionalOrderOutputClass constructInstitutionalOrder(
            OrderRoutingInformation orderRoutingInformation,
            OrderTradingInformation orderTradingInformation,
            Term orderTerm,
            UUID registeredCode,
            MoneyTransferMethod moneyTransferMethod,
            EquityTransferMethod equityTransferMethod) {

        InstitutionalOrder institutionalOrder = new InstitutionalOrder
                (
                        orderRoutingInformation,
                        uuidGenerator.generateUUID(),
                        orderTradingInformation,
                        orderTerm,
                        registeredCode,
                        moneyTransferMethod,
                        equityTransferMethod
                );

        if (institutionalOrder.getRoutingInformation().getExchangeId() == null) {
            institutionalOrder.getRoutingInformation().setExchangeId
                    (bestVenueAnalysisAlgorithm.ChooseBestVenue());
        }

        if (institutionalOrder.getTerm().getInstrumentName().equals("")) {
            institutionalOrder.getTerm().setInstrumentName
                    (portfolioOptimizationAlgorithm.optimizePortfolio());
        }

        List<BooleanResultMessage> orderValidationResultMessages =
                orderValidator.validateInstitutionalOrder(institutionalOrder);

        List<BooleanResultMessage> clientComplianceCheckingResultMessages =
                clientComplianceRulesChecker.checkInstitutionalClientComplianceRules(institutionalOrder);

        List<BooleanResultMessage> governmentalComplianceCheckingResultMessages
                = governmentalComplianceRulesChecker.checkInstitutionalGovernmentalComplianceRules(institutionalOrder);

        List<BooleanResultMessage> riskManagementResultMessages =
                orderRiskManager.ManageInstitutionalOrderRisks(institutionalOrder);

        return new ConstructInstitutionalOrderOutputClass
                (orderValidationResultMessages, clientComplianceCheckingResultMessages,
                        governmentalComplianceCheckingResultMessages, riskManagementResultMessages,
                        institutionalOrder);
    }
}
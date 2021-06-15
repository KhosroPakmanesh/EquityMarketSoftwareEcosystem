package emSeco.injectorConfigurations;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import emSeco.brokerUnit.core.modules.allocationDetailFactory.implementations.AllocationDetailFactory;
import emSeco.brokerUnit.core.modules.allocationDetailFactory.interfaces.IAllocationDetailFactory;
import emSeco.brokerUnit.core.modules.allocationDetailValidationRules.implementations.*;
import emSeco.brokerUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.brokerUnit.core.modules.allocationDetailValidator.implementations.AllocationDetailValidator;
import emSeco.brokerUnit.core.modules.allocationDetailValidator.interfaces.IAllocationDetailValidator;
import emSeco.brokerUnit.core.modules.bestVenueAnalysisAlgorithms.implementations.*;
import emSeco.brokerUnit.core.modules.bestVenueAnalysisAlgorithms.interfaces.IBestVenueAnalysisAlgorithm;
import emSeco.brokerUnit.core.modules.broker.implementations.Broker;
import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.implementations.BrokerInternalDematAccountMoneyTransferMethod;
import emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.implementations.BrokerInternalBankAccountMoneyTransferMethod;
import emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.implementations.ClearingBankAccountMoneyTransferMethod;
import emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.interfaces.IBrokerMoneyTransferMethod;
import emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.implementations.DepositoryDematAccountEquityTransferMethod;
import emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.interfaces.IBrokerEquityTransferMethod;
import emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.implementations.FakeSharedClientComplianceRuleNum1;
import emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.implementations.NoSinStockRule;
import emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.interfaces.ISharedClientComplianceRule;
import emSeco.brokerUnit.core.modules.clientComplianceRules.institutionalClientComplianceRules.implementations.FakeInstitutionalClientComplianceRuleNum1;
import emSeco.brokerUnit.core.modules.clientComplianceRules.institutionalClientComplianceRules.implementations.FakeInstitutionalClientComplianceRuleNum2;
import emSeco.brokerUnit.core.modules.clientComplianceRules.institutionalClientComplianceRules.interfaces.IInstitutionalClientComplianceRule;
import emSeco.brokerUnit.core.modules.clientComplianceRules.retailClientComplianceRules.implementations.FakeRetailClientComplianceRuleNum1;
import emSeco.brokerUnit.core.modules.clientComplianceRules.retailClientComplianceRules.implementations.FakeRetailClientComplianceRuleNum2;
import emSeco.brokerUnit.core.modules.clientComplianceRules.retailClientComplianceRules.interfaces.IRetailClientComplianceRule;
import emSeco.brokerUnit.core.modules.clientComplianceRulesChecker.implementations.ClientComplianceRulesChecker;
import emSeco.brokerUnit.core.modules.clientComplianceRulesChecker.interfaces.IClientComplianceRulesChecker;
import emSeco.brokerUnit.core.modules.contractFactory.implementations.ContractFactory;
import emSeco.brokerUnit.core.modules.contractFactory.interfaces.IContractFactory;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.institutionalGovernmentalComplianceRules.implementations.InstitutionalWashSaleRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.sharedlGovernmentalComplianceRules.implementations.FakeSharedGovernmentalComplianceRuleNum1;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.sharedlGovernmentalComplianceRules.implementations.FakeSharedGovernmentalComplianceRuleNum2;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.sharedlGovernmentalComplianceRules.interfaces.ISharedGovernmentalComplianceRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.institutionalGovernmentalComplianceRules.implementations.FakeInstitutionalGovernmentalComplianceRuleNum1;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.institutionalGovernmentalComplianceRules.interfaces.IInstitutionalGovernmentalComplianceRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.retailGovernmentalComplianceRules.implementations.FakeRetailGovernmentalComplianceRuleNum1;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.retailGovernmentalComplianceRules.implementations.RetailWashSaleRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.retailGovernmentalComplianceRules.interfaces.IRetailGovernmentalComplianceRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRulesCheker.implementations.GovernmentalComplianceRulesChecker;
import emSeco.brokerUnit.core.modules.governmentalComplianceRulesCheker.interfaces.IGovernmentalComplianceRulesChecker;
import emSeco.brokerUnit.core.modules.orderFactory.implementations.OrderFactory;
import emSeco.brokerUnit.core.modules.orderFactory.interfaces.IOrderFactory;
import emSeco.brokerUnit.core.modules.orderRiskManager.implementations.OrderRiskManager;
import emSeco.brokerUnit.core.modules.orderRiskManager.interfaces.IOrderRiskManager;
import emSeco.brokerUnit.core.modules.orderRisks.sharedOrderRisks.implementations.*;
import emSeco.brokerUnit.core.modules.orderRisks.sharedOrderRisks.interfaces.ISharedOrderRisk;
import emSeco.brokerUnit.core.modules.orderRisks.institutionalOrderRisks.implementations.FakeInstitutionalOrderRiskNum1;
import emSeco.brokerUnit.core.modules.orderRisks.institutionalOrderRisks.implementations.FakeInstitutionalOrderRiskNum2;
import emSeco.brokerUnit.core.modules.orderRisks.institutionalOrderRisks.interfaces.IInstitutionalOrderRisk;
import emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.implementations.*;
import emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.interfaces.IRetailOrderRisk;
import emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations.InstitutionalOrderRoutingInformationValidation;
import emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations.InstitutionalOrderTradingInformationValidation;
import emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.implementations.*;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations.SharedOrderMoneyEquityTransferMethodValidation;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations.SharedOrderRoutingInformationValidation;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations.SharedOrderTermInformationValidation;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations.SharedOrderTradingInformationValidation;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces.IRetailOrderValidationRule;
import emSeco.brokerUnit.core.modules.orderValidator.implementations.OrderValidator;
import emSeco.brokerUnit.core.modules.orderValidator.interfaces.IOrderValidator;
//#if FakePortfolioOptimizationAlgorithmNum1
//@import emSeco.brokerUnit.core.modules.portfolioOptimizationAlgorithms.implementations.FakePortfolioOptimizationAlgorithmNum1;
//#endif
import emSeco.brokerUnit.core.modules.portfolioOptimizationAlgorithms.implementations.FakePortfolioOptimizationAlgorithmNum2;
import emSeco.brokerUnit.core.modules.portfolioOptimizationAlgorithms.interfaces.IPortfolioOptimizationAlgorithm;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.implementations.ObjectReferenceBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.*;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToClearingBankUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToCustodianUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToDepositoryUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToExchangeUnitApiGateway;
import emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.BrokerUnitRepositories;
import emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories.*;
import emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.BrokerUnitApiGateways;
import emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways.BrokerToClearingBankUnitApiGateway;
import emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways.BrokerToCustodianUnitApiGateway;
import emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways.BrokerToDepositoryUnitApiGateway;
import emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways.BrokerToExchangeUnitApiGateway;

public class BrokerUnitConfigurations extends AbstractModule {

    @Override
    protected void configure() {
        bind(IBrokerToExchangeUnitApiGateway.class).to(BrokerToExchangeUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IBrokerToCustodianUnitApiGateway.class).to(BrokerToCustodianUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IBrokerToClearingBankUnitApiGateway.class).to(BrokerToClearingBankUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IBrokerToDepositoryUnitApiGateway.class).to(BrokerToDepositoryUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IBrokerUnitApiGateways.class).to(BrokerUnitApiGateways.class).in(Scopes.SINGLETON);
        bind(IBrokerServiceRegistry.class).to(ObjectReferenceBrokerServiceRegistry.class).in(Scopes.SINGLETON);


        bind(IContractRepository.class).to(ContractRepository.class).in(Scopes.SINGLETON);
        bind(IInstitutionalOrdersRepository.class).to(InstitutionalOrdersRepository.class).in(Scopes.SINGLETON);
        bind(IRetailOrdersRepository.class).to(RetailOrdersRepository.class).in(Scopes.SINGLETON);
        bind(IAllocationDetailRepository.class).to(AllocationDetailRepository.class).in(Scopes.SINGLETON);
        bind(ISettlementResultRepository.class).to(SettlementResultRepository.class).in(Scopes.SINGLETON);
        bind(ITradeRepository.class).to(TradeRepository.class).in(Scopes.SINGLETON);
        bind(IBrokerUnitInfoRepository.class).to(BrokerUnitInfoRepository.class).in(Scopes.SINGLETON);
        bind(IBrokerBankAccountRepository.class).to(BrokerBankAccountRepository.class).in(Scopes.SINGLETON);
        bind(IBrokerDematAccountRepository.class).to(BrokerDematAccountRepository.class).in(Scopes.SINGLETON);
        bind(IEquityInformationRepository.class).to(EquityInformationRepository.class).in(Scopes.SINGLETON);
        bind(IBrokerUnitRepositories.class).to(BrokerUnitRepositories.class).in(Scopes.SINGLETON);


        Multibinder<IRetailOrderValidationRule> retailOrderValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IRetailOrderValidationRule.class);
        //#if BrokerRetailOrderAccountsInformationValidation
        retailOrderValidationRuleMultibinder.addBinding().to(RetailOrderAccountsInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerRetailOrderTradingInformationValidation
        retailOrderValidationRuleMultibinder.addBinding().to(RetailOrderTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<IInstitutionalOrderValidationRule> institutionalOrderValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IInstitutionalOrderValidationRule.class);
        //#if BrokerInstitutionalOrderRoutingInformationValidation
        institutionalOrderValidationRuleMultibinder.addBinding().to(InstitutionalOrderRoutingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerInstitutionalOrderTradingInformationValidation
        institutionalOrderValidationRuleMultibinder.addBinding().to(InstitutionalOrderTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ISharedOrderValidationRule> sharedOrderValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), ISharedOrderValidationRule.class);
        //#if BrokerSharedOrderRoutingInformationValidation
        sharedOrderValidationRuleMultibinder.addBinding().to(SharedOrderRoutingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerSharedOrderTermInformationValidation
        sharedOrderValidationRuleMultibinder.addBinding().to(SharedOrderTermInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerSharedOrderTradingInformationValidation
        sharedOrderValidationRuleMultibinder.addBinding().to(SharedOrderTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if SharedOrderMoneyEquityTransferMethodValidation
        sharedOrderValidationRuleMultibinder.addBinding().to(SharedOrderMoneyEquityTransferMethodValidation.class).in(Scopes.SINGLETON);
        //#endif       
        bind(IOrderValidator.class).to(OrderValidator.class).in(Scopes.SINGLETON);


        Multibinder<IRetailClientComplianceRule> retailClientComplianceRuleMultibinder =
                Multibinder.newSetBinder(binder(), IRetailClientComplianceRule.class);
        //#if FakeRetailClientComplianceRuleNum1
        retailClientComplianceRuleMultibinder.addBinding().to(FakeRetailClientComplianceRuleNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeRetailClientComplianceRuleNum2
        retailClientComplianceRuleMultibinder.addBinding().to(FakeRetailClientComplianceRuleNum2.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<IInstitutionalClientComplianceRule> institutionalClientComplianceRuleMultibinder =
                Multibinder.newSetBinder(binder(), IInstitutionalClientComplianceRule.class);
        //#if FakeInstitutionalClientComplianceRuleNum1
        institutionalClientComplianceRuleMultibinder.addBinding().to(FakeInstitutionalClientComplianceRuleNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeInstitutionalClientComplianceRuleNum2
        institutionalClientComplianceRuleMultibinder.addBinding().to(FakeInstitutionalClientComplianceRuleNum2.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ISharedClientComplianceRule> sharedClientComplianceRuleMultibinder =
                Multibinder.newSetBinder(binder(), ISharedClientComplianceRule.class);
        //#if FakeSharedClientComplianceRuleNum1
        sharedClientComplianceRuleMultibinder.addBinding().to(FakeSharedClientComplianceRuleNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if NoSinStockRule
        sharedClientComplianceRuleMultibinder.addBinding().to(NoSinStockRule.class).in(Scopes.SINGLETON);
        //#endif
        bind(IClientComplianceRulesChecker.class).to(ClientComplianceRulesChecker.class).in(Scopes.SINGLETON);


        Multibinder<IRetailGovernmentalComplianceRule> retailGovernmentalComplianceRuleMultibinder =
                Multibinder.newSetBinder(binder(), IRetailGovernmentalComplianceRule.class);
        //#if FakeRetailGovernmentalComplianceRuleNum1
        retailGovernmentalComplianceRuleMultibinder.addBinding().to(FakeRetailGovernmentalComplianceRuleNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if RetailWashSaleRule
        retailGovernmentalComplianceRuleMultibinder.addBinding().to(RetailWashSaleRule.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<IInstitutionalGovernmentalComplianceRule> institutionalGovernmentalComplianceRuleMultibinder =
                Multibinder.newSetBinder(binder(), IInstitutionalGovernmentalComplianceRule.class);
        //#if FakeInstitutionalGovernmentalComplianceRuleNum1
        institutionalGovernmentalComplianceRuleMultibinder.addBinding().to(FakeInstitutionalGovernmentalComplianceRuleNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if InstitutionalWashSaleRule
        institutionalGovernmentalComplianceRuleMultibinder.addBinding().to(InstitutionalWashSaleRule.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ISharedGovernmentalComplianceRule> sharedGovernmentalComplianceRuleMultibinder =
                Multibinder.newSetBinder(binder(), ISharedGovernmentalComplianceRule.class);
        //#if FakeSharedGovernmentalComplianceRuleNum1
        sharedGovernmentalComplianceRuleMultibinder.addBinding().to(FakeSharedGovernmentalComplianceRuleNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeSharedGovernmentalComplianceRuleNum2
        sharedGovernmentalComplianceRuleMultibinder.addBinding().to(FakeSharedGovernmentalComplianceRuleNum2.class).in(Scopes.SINGLETON);
        //#endif
        bind(IGovernmentalComplianceRulesChecker.class).to(GovernmentalComplianceRulesChecker.class).in(Scopes.SINGLETON);


        Multibinder<IRetailOrderRisk> retailOrderRiskMultibinder =
                Multibinder.newSetBinder(binder(), IRetailOrderRisk.class);
        //#if BalanceEquitySufficiency
        retailOrderRiskMultibinder.addBinding().to(BalanceEquitySufficiency.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeRetailOrderRiskNum1
        retailOrderRiskMultibinder.addBinding().to(FakeRetailOrderRiskNum1.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<IInstitutionalOrderRisk> institutionalOrderRiskMultibinder =
                Multibinder.newSetBinder(binder(), IInstitutionalOrderRisk.class);
        //#if FakeInstitutionalOrderRiskNum1
        institutionalOrderRiskMultibinder.addBinding().to(FakeInstitutionalOrderRiskNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeInstitutionalOrderRiskNum2
        institutionalOrderRiskMultibinder.addBinding().to(FakeInstitutionalOrderRiskNum2.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ISharedOrderRisk> sharedOrderRiskMultibinder =
                Multibinder.newSetBinder(binder(), ISharedOrderRisk.class);
        //#if FakeSharedOrderRiskNum1
        sharedOrderRiskMultibinder.addBinding().to(FakeSharedOrderRiskNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeSharedOrderRiskNum2
        sharedOrderRiskMultibinder.addBinding().to(FakeSharedOrderRiskNum2.class).in(Scopes.SINGLETON);
        //#endif
        bind(IOrderRiskManager.class).to(OrderRiskManager.class).in(Scopes.SINGLETON);
        bind(IOrderFactory.class).to(OrderFactory.class).in(Scopes.SINGLETON);


        Multibinder<IAllocationDetailValidationRule> allocationDetailValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IAllocationDetailValidationRule.class);
        //#if BrokerAllocationDetailsAgreement
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailsAgreement.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerTradesAndAllocationDetailsTotalPriceMatching
        allocationDetailValidationRuleMultibinder.addBinding().to(TradesAndAllocationDetailsTotalPriceMatching.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerAllocationDetailInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerAllocationDetailRoutingInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailRoutingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerAllocationDetailTermInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailTermInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerAllocationDetailTradingInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        bind(IAllocationDetailValidator.class).to(AllocationDetailValidator.class).in(Scopes.SINGLETON);
        bind(IAllocationDetailFactory.class).to(AllocationDetailFactory.class).in(Scopes.SINGLETON);


        Multibinder<IBrokerMoneyTransferMethod> brokerMoneyTransferMethodMultibinder =
                Multibinder.newSetBinder(binder(), IBrokerMoneyTransferMethod.class);
        //#if BrokerClearingBankAccountMoneyTransferMethod
        brokerMoneyTransferMethodMultibinder.addBinding().to(ClearingBankAccountMoneyTransferMethod.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerInternalBankAccountMoneyTransferMethod
        brokerMoneyTransferMethodMultibinder.addBinding().to(BrokerInternalBankAccountMoneyTransferMethod.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<IBrokerEquityTransferMethod> brokerEquityTransferMethodMultibinder =
                Multibinder.newSetBinder(binder(), IBrokerEquityTransferMethod.class);
        //#if BrokerDepositoryDematAccountEquityTransferMethod
        brokerEquityTransferMethodMultibinder.addBinding().to(DepositoryDematAccountEquityTransferMethod.class).in(Scopes.SINGLETON);
        //#endif
        //#if BrokerInternalDematAccountMoneyTransferMethod
        brokerEquityTransferMethodMultibinder.addBinding().to(BrokerInternalDematAccountMoneyTransferMethod.class).in(Scopes.SINGLETON);
        //#endif


        //#if FakeBestVenueAnalysisAlgorithmNum1
//@        bind(IBestVenueAnalysisAlgorithm.class).to(FakeBestVenueAnalysisAlgorithmNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeBestVenueAnalysisAlgorithmNum2
        bind(IBestVenueAnalysisAlgorithm.class).to(FakeBestVenueAnalysisAlgorithmNum2.class).in(Scopes.SINGLETON);
        //#endif


        //#if FakePortfolioOptimizationAlgorithmNum1
//@        bind(IPortfolioOptimizationAlgorithm.class).to(FakePortfolioOptimizationAlgorithmNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakePortfolioOptimizationAlgorithmNum2
        bind(IPortfolioOptimizationAlgorithm.class).to(FakePortfolioOptimizationAlgorithmNum2.class).in(Scopes.SINGLETON);
        //#endif

        bind(IContractFactory.class).to(ContractFactory.class).in(Scopes.SINGLETON);
        bind(IBroker.class).to(Broker.class).in(Scopes.SINGLETON);
    }
}

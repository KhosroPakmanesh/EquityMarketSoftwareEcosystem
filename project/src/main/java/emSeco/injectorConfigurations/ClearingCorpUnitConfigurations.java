package emSeco.injectorConfigurations;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import emSeco.clearingCropUnit.core.modules.clearingCorp.implementations.ClearingCorp;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.implementations.MoneyAndEquityTransferUnit;
import emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.interfaces.IMoneyAndEquityTransferUnit;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.oneSideInstitutionalTradeClearingRules.implementations.OneSideInstitutionalAllocationDetailInformationEquality;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.oneSideInstitutionalTradeClearingRules.implementations.OneSideInstitutionalTradingInformationEquality;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.oneSideInstitutionalTradeClearingRules.interfaces.IOneSideInstitutionalTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.implementations.*;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.interfaces.ISharedTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.implementations.TwoSideInstitutionalAllocationDetailInformationEquality;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.implementations.TwoSideInstitutionalTradingInformationEquality;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.interfaces.ITwoSideInstitutionalTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideRetailTradeClearingRules.implementations.FakeTwoSideRetailTradeClearingRuleNum1;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideRetailTradeClearingRules.implementations.FakeTwoSideRetailTradeClearingRuleNum2;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideRetailTradeClearingRules.interfaces.ITwoSideRetailTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRulesEvaluator.implementations.TradeClearingRulesEvaluator;
import emSeco.clearingCropUnit.core.modules.tradeClearingRulesEvaluator.interfaces.ITradeClearingRulesEvaluator;
import emSeco.clearingCropUnit.core.modules.tradeFactory.implementations.TradeFactory;
import emSeco.clearingCropUnit.core.modules.tradeFactory.interfaces.ITradeFactory;
import emSeco.clearingCropUnit.core.modules.tradeSettler.implementations.TradeSettler;
import emSeco.clearingCropUnit.core.modules.tradeSettler.interfaces.ITradeSettler;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.implementations.OneSideInstitutionalAllocationDetailInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.implementations.OneSideInstitutionalTradingInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.interfaces.IOneSideInstitutionalTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.implementations.SharedTradeRoutingInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.implementations.SharedTradeTermInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.implementations.OneSideInstitutionalTradeAccountsInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.interfaces.ISharedTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.implementations.TwoSideInstitutionalTradeAccountsInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.implementations.TwoSideInstitutionalTradingInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.implementations.TwoSideInstitutionalAllocationDetailInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.interfaces.ITwoSideInstitutionalTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.implementations.TwoSideRetailTradingInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.implementations.TwoSideRetailTradeAccountsInformationValidation;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.interfaces.ITwoSideRetailTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidator.implementations.TradeValidator;
import emSeco.clearingCropUnit.core.modules.tradeValidator.interfaces.ITradeValidator;
import emSeco.clearingCropUnit.core.modules.twoInstitutionalTradesMatcher.implementations.TwoInstitutionalTradesMatcher;
import emSeco.clearingCropUnit.core.modules.twoInstitutionalTradesMatcher.interfaces.ITwoInstitutionalTradesMatcher;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.implementations.ObjectReferenceClearingCorpServiceRegistry;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.IClearingCorpUnitRepositories;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.IClearingCorpUnitInfoRepository;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ITradeRepository;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ISettlementResultRepository;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.IClearingCorUnitpApiGateways;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToClearingBankUnitApiGateway;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToCustodianUnitApiGateway;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToDepositoryUnitApiGateway;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToExchangeUnitApiGateway;
import emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations.ClearingCorpUnitRepositories;
import emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations.repositories.ClearingCorpUnitInfoRepository;
import emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations.repositories.TradeRepository;
import emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations.repositories.SettlementResultRepository;
import emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.ClearingCorUnitpApiGateways;
import emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways.ClearingCorpToClearingBankUnitApiGateway;
import emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways.ClearingCorpToCustodianUnitApiGateway;
import emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways.ClearingCorpToDepositoryUnitApiGateway;
import emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways.ClearingCorpToExchangeUnitApiGateway;


public class ClearingCorpUnitConfigurations extends AbstractModule {

    @Override
    protected void configure() {
        bind(IClearingCorpToExchangeUnitApiGateway.class).to(ClearingCorpToExchangeUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IClearingCorpToClearingBankUnitApiGateway.class).to(ClearingCorpToClearingBankUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IClearingCorpToDepositoryUnitApiGateway.class).to(ClearingCorpToDepositoryUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IClearingCorpToCustodianUnitApiGateway.class).to(ClearingCorpToCustodianUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IClearingCorUnitpApiGateways.class).to(ClearingCorUnitpApiGateways.class).in(Scopes.SINGLETON);
        bind(IClearingCorpServiceRegistry.class).to(ObjectReferenceClearingCorpServiceRegistry.class).in(Scopes.SINGLETON);


        bind(ITradeRepository.class).to(TradeRepository.class).in(Scopes.SINGLETON);
        bind(ISettlementResultRepository.class).to(SettlementResultRepository.class).in(Scopes.SINGLETON);
        bind(IClearingCorpUnitInfoRepository.class).to(ClearingCorpUnitInfoRepository.class).in(Scopes.SINGLETON);
        bind(IClearingCorpUnitRepositories.class).to(ClearingCorpUnitRepositories.class).in(Scopes.SINGLETON);


        Multibinder<ITwoSideInstitutionalTradeValidationRule> twoSideInstitutionalTradeValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), ITwoSideInstitutionalTradeValidationRule.class);
        //#if TwoSideInstitutionalAllocationDetailInformationValidation
        twoSideInstitutionalTradeValidationRuleMultibinder.addBinding().to(TwoSideInstitutionalAllocationDetailInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if TwoSideInstitutionalTradeAccountsInformationValidation
        twoSideInstitutionalTradeValidationRuleMultibinder.addBinding().to(TwoSideInstitutionalTradeAccountsInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if TwoSideInstitutionalTradingInformationValidation
        twoSideInstitutionalTradeValidationRuleMultibinder.addBinding().to(TwoSideInstitutionalTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif


        Multibinder<IOneSideInstitutionalTradeValidationRule> oneSideInstitutionalTradeValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IOneSideInstitutionalTradeValidationRule.class);
        //#if OneSideInstitutionalAllocationDetailInformationValidation
        oneSideInstitutionalTradeValidationRuleMultibinder.addBinding().to(OneSideInstitutionalAllocationDetailInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if OneSideInstitutionalTradingInformationValidation
        oneSideInstitutionalTradeValidationRuleMultibinder.addBinding().to(OneSideInstitutionalTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if OneSideInstitutionalTradeAccountsInformationValidation
        oneSideInstitutionalTradeValidationRuleMultibinder.addBinding().to(OneSideInstitutionalTradeAccountsInformationValidation.class).in(Scopes.SINGLETON);
        //#endif


        Multibinder<ITwoSideRetailTradeValidationRule> twoSideRetailTradeValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), ITwoSideRetailTradeValidationRule.class);
        //#if TwoSideRetailTradingInformationValidation
        twoSideRetailTradeValidationRuleMultibinder.addBinding().to(TwoSideRetailTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if TwoSideRetailTradeAccountsInformationValidation
        twoSideRetailTradeValidationRuleMultibinder.addBinding().to(TwoSideRetailTradeAccountsInformationValidation.class).in(Scopes.SINGLETON);
        //#endif


        Multibinder<ISharedTradeValidationRule> sharedTradeValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), ISharedTradeValidationRule.class);
        //#if SharedTradeRoutingInformationValidation
        sharedTradeValidationRuleMultibinder.addBinding().to(SharedTradeRoutingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if SharedTradeTermInformationValidation
        sharedTradeValidationRuleMultibinder.addBinding().to(SharedTradeTermInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        bind(ITradeValidator.class).to(TradeValidator.class).in(Scopes.SINGLETON);
        bind(ITradeFactory.class).to(TradeFactory.class).in(Scopes.SINGLETON);


        Multibinder<ITwoSideInstitutionalTradeClearingRule> twoSideInstitutionalTradeClearingRuleMultibinder =
                Multibinder.newSetBinder(binder(), ITwoSideInstitutionalTradeClearingRule.class);
        //#if TwoSideInstitutionalAllocationDetailInformationEquality
        twoSideInstitutionalTradeClearingRuleMultibinder.addBinding().to(TwoSideInstitutionalAllocationDetailInformationEquality.class).in(Scopes.SINGLETON);
        //#endif
        //#if TwoSideInstitutionalTradingInformationEquality
        twoSideInstitutionalTradeClearingRuleMultibinder.addBinding().to(TwoSideInstitutionalTradingInformationEquality.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<IOneSideInstitutionalTradeClearingRule> oneSideInstitutionalTradeClearingRuleMultibinder =
                Multibinder.newSetBinder(binder(), IOneSideInstitutionalTradeClearingRule.class);
        //#if OneSideInstitutionalAllocationDetailInformationEquality
        oneSideInstitutionalTradeClearingRuleMultibinder.addBinding().to(OneSideInstitutionalAllocationDetailInformationEquality.class).in(Scopes.SINGLETON);
        //#endif
        //#if OneSideInstitutionalTradingInformationEquality
        oneSideInstitutionalTradeClearingRuleMultibinder.addBinding().to(OneSideInstitutionalTradingInformationEquality.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ITwoSideRetailTradeClearingRule> twoSideRetailTradeClearingRuleMultibinder =
                Multibinder.newSetBinder(binder(), ITwoSideRetailTradeClearingRule.class);
        //#if FakeTwoSideRetailTradeClearingRuleNum1
        twoSideRetailTradeClearingRuleMultibinder.addBinding().to(FakeTwoSideRetailTradeClearingRuleNum1.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeTwoSideRetailTradeClearingRuleNum2
        twoSideRetailTradeClearingRuleMultibinder.addBinding().to(FakeTwoSideRetailTradeClearingRuleNum2.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ISharedTradeClearingRule> sharedTradeClearingRuleMultibinder =
                Multibinder.newSetBinder(binder(), ISharedTradeClearingRule.class);
        //#if SharedExchangeInformationEquality
        sharedTradeClearingRuleMultibinder.addBinding().to(SharedExchangeInformationEquality.class).in(Scopes.SINGLETON);
        //#endif
        //#if SharedOrderInitiatorsInequality
        sharedTradeClearingRuleMultibinder.addBinding().to(SharedOrderInitiatorsInequality.class).in(Scopes.SINGLETON);
        //#endif
        //#if SharedTradeTermEquality
        sharedTradeClearingRuleMultibinder.addBinding().to(SharedTradeTermEquality.class).in(Scopes.SINGLETON);
        //#endif
        //#if SharedInitialOrderInformationEquality
        sharedTradeClearingRuleMultibinder.addBinding().to(SharedInitialOrderInformationEquality.class).in(Scopes.SINGLETON);
        //#endif
        bind(ITradeClearingRulesEvaluator.class).to(TradeClearingRulesEvaluator.class).in(Scopes.SINGLETON);


        bind(ITwoInstitutionalTradesMatcher.class).to(TwoInstitutionalTradesMatcher.class).in(Scopes.SINGLETON);


        bind(ITradeSettler.class).to(TradeSettler.class).in(Scopes.SINGLETON);


        bind(IMoneyAndEquityTransferUnit.class).to(MoneyAndEquityTransferUnit.class).in(Scopes.SINGLETON);


        bind(IClearingCorp.class).to(ClearingCorp.class).in(Scopes.SINGLETON);
    }
}
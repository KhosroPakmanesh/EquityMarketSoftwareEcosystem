package emSeco.injectorConfigurations;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.exchange.implementations.Exchange;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;
import emSeco.exchangeUnit.core.modules.orderComparator.implementations.OrderComparator;
import emSeco.exchangeUnit.core.modules.orderComparator.interfaces.IOrderComparator;
import emSeco.exchangeUnit.core.modules.orderFactory.implementations.OrderFactory;
import emSeco.exchangeUnit.core.modules.orderFactory.interfaces.IOrderFactory;
import emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.implementations.*;
import emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.interfaces.IOrderMatchingAlgorithm;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.defaultSecondaryOrderPrecedenceRules.implementations.TimeOrderPrecedenceRule;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.defaultSecondaryOrderPrecedenceRules.interfaces.IDefaultSecondaryOrderPrecedenceRule;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.implementations.SmallQuantityOrderPrecedenceRule;
import emSeco.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.interfaces.ISecondaryOrderPrecedenceRule;
import emSeco.exchangeUnit.core.modules.orderProcessor.implementations.OrderProcessor;
import emSeco.exchangeUnit.core.modules.orderProcessor.interfaces.IOrderProcessor;
import emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations.InstitutionalOrderRoutingInformationValidation;
import emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations.InstitutionalOrderTradingInformationValidation;
import emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.exchangeUnit.core.modules.orderValidationRules.retailOrderValidationRules.implementations.RetailOrderAccountsInformationValidation;
import emSeco.exchangeUnit.core.modules.orderValidationRules.retailOrderValidationRules.implementations.RetailOrderTradingInformationValidation;
import emSeco.exchangeUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces.IRetailOrderValidationRule;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations.SharedOrderRoutingInformationValidation;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations.SharedOrderTermInformationValidation;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations.SharedOrderTradingInformationValidation;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.exchangeUnit.core.modules.orderValidator.implementations.OrderValidator;
import emSeco.exchangeUnit.core.modules.orderValidator.interfaces.IOrderValidator;
import emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.implementations.ObjectReferenceExchangeServiceRegistry;
import emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.interfaces.IExchangeServiceRegistry;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.IExchangeUnitRepositories;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.IExchangeUnitInfoRepository;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ITradeRepository;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ISettlementResultRepository;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.IExchangeUnitApiGateways;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways.IExchangeToBrokerUnitApiGateway;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways.IExchangeToClearingCorpUnitApiGateway;
import emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.ExchangeUnitRepositories;
import emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.repositories.ExchangeUnitInfoRepository;
import emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.repositories.TradeRepository;
import emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.repositories.SettlementResultRepository;
import emSeco.exchangeUnit.infrastructure.services.gateways.exchangeUnitApiGateways.implementations.ExchangeUnitApiGateways;
import emSeco.exchangeUnit.infrastructure.services.gateways.exchangeUnitApiGateways.implementations.gateways.ExchangeToBrokerUnitApiGateway;
import emSeco.exchangeUnit.infrastructure.services.gateways.exchangeUnitApiGateways.implementations.gateways.ExchangeToClearingCorpUnitApiGateway;

import java.util.Comparator;

public class ExchangeUnitConfigurations extends AbstractModule {

    @Override
    protected void configure() {
        bind(IExchangeToClearingCorpUnitApiGateway.class).to(ExchangeToClearingCorpUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IExchangeToBrokerUnitApiGateway.class).to(ExchangeToBrokerUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(IExchangeUnitApiGateways.class).to(ExchangeUnitApiGateways.class).in(Scopes.SINGLETON);
        bind(IExchangeServiceRegistry.class).to(ObjectReferenceExchangeServiceRegistry.class).in(Scopes.SINGLETON);


        bind(ITradeRepository.class).to(TradeRepository.class).in(Scopes.SINGLETON);
        bind(ISettlementResultRepository.class).to(SettlementResultRepository.class).in(Scopes.SINGLETON);
        bind(IExchangeUnitInfoRepository.class).to(ExchangeUnitInfoRepository.class).in(Scopes.SINGLETON);
        bind(IExchangeUnitRepositories.class).to(ExchangeUnitRepositories.class).in(Scopes.SINGLETON);


        Multibinder<IRetailOrderValidationRule> retailOrderValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IRetailOrderValidationRule.class);
        //#if ExchangeRetailOrderAccountsInformationValidation
        retailOrderValidationRuleMultibinder.addBinding().to(RetailOrderAccountsInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if ExchangeRetailOrderTradingInformationValidation
        retailOrderValidationRuleMultibinder.addBinding().to(RetailOrderTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<IInstitutionalOrderValidationRule> institutionalOrderValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IInstitutionalOrderValidationRule.class);
        //#if ExchangeInstitutionalOrderRoutingInformationValidation
        institutionalOrderValidationRuleMultibinder.addBinding().to(InstitutionalOrderRoutingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if ExchangeInstitutionalOrderTradingInformationValidation
        institutionalOrderValidationRuleMultibinder.addBinding().to(InstitutionalOrderTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ISharedOrderValidationRule> sharedOrderValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), ISharedOrderValidationRule.class);
        //#if ExchangeSharedOrderRoutingInformationValidation
        sharedOrderValidationRuleMultibinder.addBinding().to(SharedOrderRoutingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if ExchangeSharedOrderTermInformationValidation
        sharedOrderValidationRuleMultibinder.addBinding().to(SharedOrderTermInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if ExchangeSharedOrderTradingInformationValidation
        sharedOrderValidationRuleMultibinder.addBinding().to(SharedOrderTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        bind(IOrderValidator.class).to(OrderValidator.class).in(Scopes.SINGLETON);
        bind(IOrderFactory.class).to(OrderFactory.class).in(Scopes.SINGLETON);


        Multibinder<IOrderMatchingAlgorithm> orderMatchingAlgorithmMultibinder =
                Multibinder.newSetBinder(binder(), IOrderMatchingAlgorithm.class);
        //#if MarketPriceOrderAlgorithm
        orderMatchingAlgorithmMultibinder.addBinding().to(MarketPriceAlgorithm.class).in(Scopes.SINGLETON);
        //#endif

        //#if LimitPriceOrderAlgorithm
        orderMatchingAlgorithmMultibinder.addBinding().to(LimitPriceOrderAlgorithm.class).in(Scopes.SINGLETON);
        //#endif

        //#if FakeAllOrNothingAlgorithm
        //@orderMatchingAlgorithmMultibinder.addBinding().to(FakeAllOrNothingAlgorithm.class).in(Scopes.SINGLETON);
        //#endif

        //#if FakeFillOrKillOrderAlgorithm
        //@orderMatchingAlgorithmMultibinder.addBinding().to(FakeFillOrKillOrderAlgorithm.class).in(Scopes.SINGLETON);
        //#endif

        //#if FakeGoodTillCancelledAlgorithm
        //@orderMatchingAlgorithmMultibinder.addBinding().to(FakeGoodTillCancelledAlgorithm.class).in(Scopes.SINGLETON);
        //#endif

        //#if FakeGoodTillDateAlgorithm
        //@orderMatchingAlgorithmMultibinder.addBinding().to(FakeGoodTillDateAlgorithm.class).in(Scopes.SINGLETON);
        //#endif

        //#if FakeImmediateOrCancelAlgorithm
        //@orderMatchingAlgorithmMultibinder.addBinding().to(FakeImmediateOrCancelAlgorithm.class).in(Scopes.SINGLETON);
        //#endif

        //#if FakeStopLossAlgorithm
        //@orderMatchingAlgorithmMultibinder.addBinding().to(FakeStopLossAlgorithm.class).in(Scopes.SINGLETON);
        //#endif


        //#if TimeOrderPrecedenceRule
        bind(IDefaultSecondaryOrderPrecedenceRule.class).to(TimeOrderPrecedenceRule.class).in(Scopes.SINGLETON);
        //#endif
        //#if FakeDefaultSecondaryOrderPrecedenceRuleNum1
        //@bind(IDefaultSecondaryOrderPrecedenceRule.class).to(FakeDefaultSecondaryOrderPrecedenceRuleNum1.class).in(Scopes.SINGLETON);
        //#endif


        //#if LargeQuantityOrderPrecedenceRule
    	//@bind(ISecondaryOrderPrecedenceRule.class).to(LargeQuantityOrderPrecedenceRule.class).in(Scopes.SINGLETON);
        //#endif
        //#if QuantityDisclosureOrderPrecedenceRule
    	//@bind(ISecondaryOrderPrecedenceRule.class).to(QuantityDisclosureOrderPrecedenceRule.class).in(Scopes.SINGLETON);
        //#endif
        //#if SmallQuantityOrderPrecedenceRule
        bind(ISecondaryOrderPrecedenceRule.class).to(SmallQuantityOrderPrecedenceRule.class).in(Scopes.SINGLETON);
        //#endif


        bind(new TypeLiteral<Comparator<Order>>() {
        }).to(OrderComparator.class).in(Scopes.SINGLETON);
        bind(IOrderComparator.class).to(OrderComparator.class).in(Scopes.SINGLETON);


        bind(IOrderProcessor.class).to(OrderProcessor.class).in(Scopes.SINGLETON);
        bind(IExchange.class).to(Exchange.class).in(Scopes.SINGLETON);
    }
}

package emSeco.injectorConfigurations;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;



import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.implementations.ContractAllocationDetailInstrumentEquality;
import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.implementations.ContractAllocationDetailPriceEquality;
import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.implementations.ContractAllocationDetailQuantityEquality;
import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.interfaces.IAllocationDetailAffirmationRule;
import emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.implementations.AllocationDetailAnalyzer;
import emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.interfaces.IAllocationDetailAnalyzer;
import emSeco.custodianUnit.core.modules.allocationDetailFactory.implementations.AllocationDetailFactory;
import emSeco.custodianUnit.core.modules.allocationDetailFactory.interfaces.IAllocationDetailFactory;
import emSeco.custodianUnit.core.modules.allocationDetailValidationRules.implementations.*;
import emSeco.custodianUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.custodianUnit.core.modules.allocationDetailValidator.implementations.AllocationDetailValidator;
import emSeco.custodianUnit.core.modules.allocationDetailValidator.interfaces.IAllocationDetailValidator;
import emSeco.custodianUnit.core.modules.custodian.implementations.Custodian;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.implementations.ClearingBankAccountMoneyTransferMethod;
import emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.implementations.CustodianInternalBankAccountMoneyTransferMethod;
import emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.interfaces.ICustodianMoneyTransferMethod;
import emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.implementations.CustodianInternalDematAccountMoneyTransferMethod;
import emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.implementations.DepositoryDematAccountEquityTransferMethod;
import emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.interfaces.ICustodianEquityTransferMethod;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.implementations.ObjectReferenceCustodianServiceRegistry;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces.ICustodianServiceRegistry;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.*;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.*;
import emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.CustodianUnitRepositories;
import emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories.*;
import emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.CustodianUnitApiGateways;
import emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways.CustodianToBrokerUnitApiGateway;
import emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways.CustodianToClearingBankUnitApiGateway;
import emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways.CustodianToClearingCorpUnitApiGateway;
import emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways.CustodianToDepositoryUnitApiGateway;

public class CustodianUnitConfigurations extends AbstractModule {
    @Override
    protected void configure() {
        bind(ICustodianToBrokerUnitApiGateway.class).to(CustodianToBrokerUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(ICustodianToClearingCorpUnitApiGateway.class).to(CustodianToClearingCorpUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(ICustodianToClearingBankUnitApiGateway.class).to(CustodianToClearingBankUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(ICustodianToDepositoryUnitApiGateway.class).to(CustodianToDepositoryUnitApiGateway.class).in(Scopes.SINGLETON);
        bind(ICustodianUnitApiGateways.class).to(CustodianUnitApiGateways.class).in(Scopes.SINGLETON);
        bind(ICustodianServiceRegistry.class).to(ObjectReferenceCustodianServiceRegistry.class).in(Scopes.SINGLETON);


        bind(IContractRepository.class).to(ContractRepository.class).in(Scopes.SINGLETON);
        bind(IAllocationDetailRepository.class).to(AllocationDetailRepository.class).in(Scopes.SINGLETON);
        bind(ITradeRepository.class).to(TradeRepository.class).in(Scopes.SINGLETON);
        bind(ICustodianUnitInfoRepository.class).to(CustodianUnitInfoRepository.class).in(Scopes.SINGLETON);
        bind(ISettlementResultRepository.class).to(SettlementResultRepository.class).in(Scopes.SINGLETON);
        bind(ICustodianDematAccountRepository.class).to(CustodianDematAccountRepository.class).in(Scopes.SINGLETON);
        bind(ICustodianBankAccountRepository.class).to(CustodianBankAccountRepository.class).in(Scopes.SINGLETON);
        bind(ICustodianUnitRepositories.class).to(CustodianUnitRepositories.class).in(Scopes.SINGLETON);


        Multibinder<IAllocationDetailValidationRule> allocationDetailValidationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IAllocationDetailValidationRule.class);
        //#if CustodianAllocationDetailsAgreement
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailsAgreement.class).in(Scopes.SINGLETON);
        //#endif
        //#if CustodianAllocationDetailInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if CustodianAllocationDetailTermInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailTermInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if CustodianAllocationDetailTradingInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailTradingInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        //#if CustodianAllocationDetailAccountsInformationValidation
        allocationDetailValidationRuleMultibinder.addBinding().to(AllocationDetailAccountsInformationValidation.class).in(Scopes.SINGLETON);
        //#endif
        bind(IAllocationDetailValidator.class).to(AllocationDetailValidator.class).in(Scopes.SINGLETON);
        bind(IAllocationDetailFactory.class).to(AllocationDetailFactory.class).in(Scopes.SINGLETON);


        Multibinder<IAllocationDetailAffirmationRule> allocationDetailAffirmationRuleMultibinder =
                Multibinder.newSetBinder(binder(), IAllocationDetailAffirmationRule.class);
        //#if ContractAllocationDetailInstrumentEquality
        allocationDetailAffirmationRuleMultibinder.addBinding().to(ContractAllocationDetailInstrumentEquality.class).in(Scopes.SINGLETON);
        //#endif
        //#if ContractAllocationDetailPriceEquality
        allocationDetailAffirmationRuleMultibinder.addBinding().to(ContractAllocationDetailPriceEquality.class).in(Scopes.SINGLETON);
        //#endif
        //#if ContractAllocationDetailQuantityEquality
        allocationDetailAffirmationRuleMultibinder.addBinding().to(ContractAllocationDetailQuantityEquality.class).in(Scopes.SINGLETON);
        //#endif
        bind(IAllocationDetailAnalyzer.class).to(AllocationDetailAnalyzer.class).in(Scopes.SINGLETON);


        Multibinder<ICustodianMoneyTransferMethod> custodianMoneyTransferMethodMultibinder =
                Multibinder.newSetBinder(binder(), ICustodianMoneyTransferMethod.class);
        //#if CustodianClearingBankAccountMoneyTransferMethod
        custodianMoneyTransferMethodMultibinder.addBinding().to(ClearingBankAccountMoneyTransferMethod.class).in(Scopes.SINGLETON);
        //#endif
        //#if CustodianInternalBankAccountMoneyTransferMethod
        custodianMoneyTransferMethodMultibinder.addBinding().to(CustodianInternalBankAccountMoneyTransferMethod.class).in(Scopes.SINGLETON);
        //#endif

        Multibinder<ICustodianEquityTransferMethod> custodianEquityTransferMethodMultibinder =
                Multibinder.newSetBinder(binder(), ICustodianEquityTransferMethod.class);
        //#if CustodianDepositoryDematAccountEquityTransferMethod
        custodianEquityTransferMethodMultibinder.addBinding().to(DepositoryDematAccountEquityTransferMethod.class).in(Scopes.SINGLETON);
        //#endif
        //#if CustodianInternalDematAccountMoneyTransferMethod
        custodianEquityTransferMethodMultibinder.addBinding().to(CustodianInternalDematAccountMoneyTransferMethod.class).in(Scopes.SINGLETON);
        //#endif


        bind(ICustodian.class).to(Custodian.class).in(Scopes.SINGLETON);
    }
}

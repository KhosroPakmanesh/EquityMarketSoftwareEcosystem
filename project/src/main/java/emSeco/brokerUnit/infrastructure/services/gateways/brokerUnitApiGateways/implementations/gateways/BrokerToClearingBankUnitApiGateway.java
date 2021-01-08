package emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToClearingBankUnitApiGateway;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.UUID;

public class BrokerToClearingBankUnitApiGateway implements IBrokerToClearingBankUnitApiGateway {
    private final IBrokerServiceRegistry brokerServiceRegistry;

    @Inject
    public BrokerToClearingBankUnitApiGateway(IBrokerServiceRegistry brokerServiceRegistry) {
        this.brokerServiceRegistry = brokerServiceRegistry;
    }

    @Override
    public BooleanResultMessage debit
            (UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount){

        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null)
        {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }

        return foundClearingBank.debit_API(clearingBankAccountNumber,totalAmount);
    }

    @Override
    public BooleanResultMessage credit
            (UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount){

        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null)
        {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }

        return foundClearingBank.credit_API(clearingBankAccountNumber,totalAmount);
    }

    @Override
    public BooleanResultMessage checkBalance
            (UUID clearingBankId, UUID clearingBankAccountNumber,double totalAmount) {

        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null)
        {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }

        return foundClearingBank.checkBalance_API(clearingBankAccountNumber,totalAmount);
    }

    private IClearingBank getClearingBank(UUID clearingBankId) {
        return brokerServiceRegistry.getClearingBanks().stream().
                filter(clearingBank -> clearingBank.getClearingBankId() == clearingBankId).
                findAny().orElse(null);
    }
}
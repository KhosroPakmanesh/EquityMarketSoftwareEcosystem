package emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.entities.brokerUnitInfo.BrokerUnitInfo;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToExchangeUnitApiGateway;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;
import emSeco.shared.exceptions.DataPersistenceMalfunctionException;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;
import emSeco.shared.services.dateTimeService.interfaces.IDateTimeService;

import java.util.Date;
import java.util.List;

import static emSeco.brokerUnit.core.helpers.BrokerToExchangeEntitiesMapper.MapFromBrokerInstitutionalOrderAndBrokerUnitInfoToExchangeOrder;
import static emSeco.brokerUnit.core.helpers.BrokerToExchangeEntitiesMapper.MapFromBrokerRetailOrderAndBrokerUnitInfoToExchangeOrder;

public class BrokerToExchangeUnitApiGateway implements IBrokerToExchangeUnitApiGateway {
    private final IBrokerServiceRegistry brokerServiceRegistry;
    private final IBrokerUnitRepositories brokerRepositories;
    private final IDateTimeService dateTimeService;

    @Inject
    public BrokerToExchangeUnitApiGateway(IBrokerServiceRegistry brokerServiceRegistry,
                                          IBrokerUnitRepositories brokerRepositories,
                                          IDateTimeService dateTimeService) {
        this.brokerServiceRegistry = brokerServiceRegistry;
        this.brokerRepositories = brokerRepositories;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public BooleanResultMessages submitRetailOrder(RetailOrder retailOrder) {
        List<IExchange> exchanges = brokerServiceRegistry.getExchanges();
        IExchange exchange = exchanges.stream()
                .filter(eae -> eae.getExchangeId() == retailOrder.getRoutingInformation().getExchangeId())
                .findAny().orElse(null);

        if (exchange == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }

        BrokerUnitInfo brokerUnitInfo = brokerRepositories.
                getBrokerUnitInfoRepository().get();
        if (brokerUnitInfo == null) {
            throw new DataPersistenceMalfunctionException
                    ("Broker's data persistence mechanism has not stored the data!");
        }

        Date orderSubmissionDateTime = dateTimeService.getDateTime();

        return exchange.submitOrder_API
                (MapFromBrokerRetailOrderAndBrokerUnitInfoToExchangeOrder
                        (retailOrder, brokerUnitInfo, orderSubmissionDateTime));
    }

    @Override
    public BooleanResultMessages submitInstitutionalOrder(InstitutionalOrder institutionalOrder) {
        List<IExchange> exchanges = brokerServiceRegistry.getExchanges();
        IExchange exchange = exchanges.stream()
                .filter(eae -> eae.getExchangeId() == institutionalOrder.getRoutingInformation().getExchangeId())
                .findAny().orElse(null);

        if (exchange == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }

        Date orderSubmissionDateTime = dateTimeService.getDateTime();

        return exchange.submitOrder_API
                (MapFromBrokerInstitutionalOrderAndBrokerUnitInfoToExchangeOrder
                        (institutionalOrder, orderSubmissionDateTime));
    }
}

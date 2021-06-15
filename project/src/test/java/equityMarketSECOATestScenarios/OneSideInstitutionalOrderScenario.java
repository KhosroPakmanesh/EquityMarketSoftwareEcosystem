package equityMarketSECOATestScenarios;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import emSeco.brokerUnit.core.entities.noticeOfExecution.NoticeOfExecution;
import emSeco.brokerUnit.core.entities.equityInformation.EquityInformation;
import emSeco.brokerUnit.core.entities.order.*;
import emSeco.brokerUnit.core.entities.shared.*;
import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.brokerUnit.core.modules.broker.models.InitiateInstitutionalOrderOutputClass;
import emSeco.brokerUnit.core.modules.broker.models.InitiateRetailOrderOutputClass;
import emSeco.brokerUnit.core.modules.broker.models.SubmitAllocationDetailsInputClass;
import emSeco.brokerUnit.core.modules.broker.models.SubmitAllocationDetailsOutputClass;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IEquityInformationRepository;
import emSeco.custodianUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.custodianUnit.core.entities.shared.EquityTransferMethod;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTradesOutputClass;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingbankUnit.core.entities.bankAccount.BankAccount;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.IClearingBankUnitRepositories;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.repositories.IBankAccountRepository;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces.ICustodianServiceRegistry;
import emSeco.depositoryUnit.core.entities.dematAccount.DematAccount;
import emSeco.depositoryUnit.core.entities.dematAccount.InstrumentQuantityPair;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.IDepositoryUnitRepositories;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.repositories.IDematAccountRepository;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;
import emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.interfaces.IExchangeServiceRegistry;
import emSeco.injectorConfigurations.*;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OneSideInstitutionalOrderScenario {

    @Test
    public void runScenario() {
        //try {
        //----------------------------------------Injector Initialization----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        Injector injector = Guice.createInjector(
                new SharedServicesConfigurations(),
                new BrokerUnitConfigurations(),
                new CustodianUnitConfigurations(),
                new ExchangeUnitConfigurations(),
                new ClearingCorpUnitConfigurations(),
                new ClearingBankUnitConfigurations(),
                new DepositoryUnitConfigurations()
        );
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //-----------------------------------------Creating Accounts---------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        /*1-Imagine that five retail clients and an institutional client want to trade in an equity market. Five retail
        clients are sellers, and the institutional client is a buyer. The institutional client wants to buy 1000
        equities with a price of 20 dollars for each equity. Each retail seller wants to sell 200 equities with a price of
        20 dollars for each equity. they make their orders with the following sequence:

        retail seller1 -> retail seller2 -> retail seller3 -> retail seller4 -> retail seller5 -> institutional buyer1

        The initial balances of all clients' clearing bank and demat accounts are as follows:

            side                  | bank account balance 	side                  | demat account balance
            --------------------------------------------    ---------------------------------------------
            retail seller 1       | 0	                    retail seller 1       | 200
            retail seller 2       | 0	                    retail seller 2       | 200
            retail seller 3       | 0	                    retail seller 3       | 200
            retail seller 4       | 0	                    retail seller 4       | 200
            retail seller 5       | 0	                    retail seller 5       | 200
            institutional buyer 1 | 20000	                institutional buyer 1 | 0
         */

        //buyer institutional Client 1
        UUID institutionalClient1ClearingBankAccountNumber = UUID.fromString("cb000000-0000-c100-0000-000000000000");
        UUID institutionalClient1DematAccountNumber = UUID.fromString("d0000000-0000-c100-0000-000000000000");
        UUID institutionalClient1RegisteredCode = UUID.fromString("e0000000-0000-c1bd-0000-000000000000");
        BankAccount institutionalClient1ClearingBankAccount =
                new BankAccount(institutionalClient1ClearingBankAccountNumber, 20000);
        DematAccount institutionalClient1DematAccount =
                new DematAccount(institutionalClient1DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller retail client 1
        UUID retailClient1ClearingBankAccountNumber = UUID.fromString("cb000000-c100-0000-0000-000000000000");
        UUID retailClient1DematAccountNumber = UUID.fromString("d0000000-c100-0000-0000-000000000000");
        UUID retailClient1TradingCode = UUID.fromString("e0000000-c1bd-0000-0000-000000000000");
        BankAccount retailClient1ClearingBankAccount =
                new BankAccount(retailClient1ClearingBankAccountNumber, 0);
        DematAccount retailClient1DematAccount =
                new DematAccount(retailClient1DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 200));
                        }});

        //seller retail client 2
        UUID retailClient2ClearingBankAccountNumber = UUID.fromString("cb000000-c200-0000-0000-000000000000");
        UUID retailClient2DematAccountNumber = UUID.fromString("d0000000-c200-0000-0000-000000000000");
        UUID retailClient2TradingCode = UUID.fromString("e0000000-c2bd-0000-0000-000000000000");
        BankAccount retailClient2ClearingBankAccount =
                new BankAccount(retailClient2ClearingBankAccountNumber, 0);
        DematAccount retailClient2DematAccount =
                new DematAccount(retailClient2DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 200));
                        }});

        //seller retail client 3
        UUID retailClient3ClearingBankAccountNumber = UUID.fromString("cb000000-c300-0000-0000-000000000000");
        UUID retailClient3DematAccountNumber = UUID.fromString("d0000000-c300-0000-0000-000000000000");
        UUID retailClient3TradingCode = UUID.fromString("e0000000-c3bd-0000-0000-000000000000");
        BankAccount retailClient3ClearingBankAccount =
                new BankAccount(retailClient3ClearingBankAccountNumber, 0);
        DematAccount retailClient3DematAccount =
                new DematAccount(retailClient3DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 200));
                        }});

        //seller retail client 4
        UUID retailClient4ClearingBankAccountNumber = UUID.fromString("cb000000-c400-0000-0000-000000000000");
        UUID retailClient4DematAccountNumber = UUID.fromString("d0000000-c400-0000-0000-000000000000");
        UUID retailClient4TradingCode = UUID.fromString("e0000000-c4bd-0000-0000-000000000000");
        BankAccount retailClient4ClearingBankAccount =
                new BankAccount(retailClient4ClearingBankAccountNumber, 0);
        DematAccount retailClient4DematAccount =
                new DematAccount(retailClient4DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 200));
                        }});

        //seller retail client 5
        UUID retailClient5ClearingBankAccountNumber = UUID.fromString("cb000000-c500-0000-0000-000000000000");
        UUID retailClient5DematAccountNumber = UUID.fromString("d0000000-c500-0000-0000-000000000000");
        UUID retailClient5TradingCode = UUID.fromString("e0000000-c5bd-0000-0000-000000000000");
        BankAccount retailClient5ClearingBankAccount =
                new BankAccount(retailClient5ClearingBankAccountNumber, 0);
        DematAccount retailClient5DematAccount =
                new DematAccount(retailClient5DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 200));
                        }});

        /*2-The accounts' information of the ten clients of the institutional client, which we are going to talk about
        in later stages, are as follows:*/

        //buyer institutional Client 1 - allocation detail client 1
        UUID allocationDetailClient1ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c100-000000000000");
        UUID allocationDetailClient1DematAccountNumber = UUID.fromString("d0000000-0000-0000-c100-000000000000");
        UUID allocationDetailClient1TradingCode = UUID.fromString("e0000000-0000-0000-c1bd-000000000000");
        BankAccount allocationDetailClient1ClearingBankAccount =
                new BankAccount(allocationDetailClient1ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient1DematAccount =
                new DematAccount(allocationDetailClient1DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 2
        UUID allocationDetailClient2ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c200-000000000000");
        UUID allocationDetailClient2DematAccountNumber = UUID.fromString("d0000000-0000-0000-c200-000000000000");
        UUID allocationDetailClient2TradingCode = UUID.fromString("e0000000-0000-0000-c2bd-000000000000");
        BankAccount allocationDetailClient2ClearingBankAccount =
                new BankAccount(allocationDetailClient2ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient2DematAccount =
                new DematAccount(allocationDetailClient2DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 3
        UUID allocationDetailClient3ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c300-000000000000");
        UUID allocationDetailClient3DematAccountNumber = UUID.fromString("d0000000-0000-0000-c300-000000000000");
        UUID allocationDetailClient3TradingCode = UUID.fromString("e0000000-0000-0000-c3bd-000000000000");
        BankAccount allocationDetailClient3ClearingBankAccount =
                new BankAccount(allocationDetailClient3ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient3DematAccount =
                new DematAccount(allocationDetailClient3DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 4
        UUID allocationDetailClient4ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c400-000000000000");
        UUID allocationDetailClient4DematAccountNumber = UUID.fromString("d0000000-0000-0000-c400-000000000000");
        UUID allocationDetailClient4TradingCode = UUID.fromString("e0000000-0000-0000-c4bd-000000000000");
        BankAccount allocationDetailClient4ClearingBankAccount =
                new BankAccount(allocationDetailClient4ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient4DematAccount =
                new DematAccount(allocationDetailClient4DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 5
        UUID allocationDetailClient5ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c500-000000000000");
        UUID allocationDetailClient5DematAccountNumber = UUID.fromString("d0000000-0000-0000-c500-000000000000");
        UUID allocationDetailClient5TradingCode = UUID.fromString("e0000000-0000-0000-c5bd-000000000000");
        BankAccount allocationDetailClient5ClearingBankAccount =
                new BankAccount(allocationDetailClient5ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient5DematAccount =
                new DematAccount(allocationDetailClient5DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 6
        UUID allocationDetailClient6ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c600-000000000000");
        UUID allocationDetailClient6DematAccountNumber = UUID.fromString("d0000000-0000-0000-c600-000000000000");
        UUID allocationDetailClient6TradingCode = UUID.fromString("e0000000-0000-0000-c6bd-000000000000");
        BankAccount allocationDetailClient6ClearingBankAccount =
                new BankAccount(allocationDetailClient6ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient6DematAccount =
                new DematAccount(allocationDetailClient6DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 7
        UUID allocationDetailClient7ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c700-000000000000");
        UUID allocationDetailClient7DematAccountNumber = UUID.fromString("d0000000-0000-0000-c700-000000000000");
        UUID allocationDetailClient7TradingCode = UUID.fromString("e0000000-0000-0000-c7bd-000000000000");
        BankAccount allocationDetailClient7ClearingBankAccount =
                new BankAccount(allocationDetailClient7ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient7DematAccount =
                new DematAccount(allocationDetailClient7DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 8
        UUID allocationDetailClient8ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c800-000000000000");
        UUID allocationDetailClient8DematAccountNumber = UUID.fromString("d0000000-0000-0000-c800-000000000000");
        UUID allocationDetailClient8TradingCode = UUID.fromString("e0000000-0000-0000-c8bd-000000000000");
        BankAccount allocationDetailClient8ClearingBankAccount =
                new BankAccount(allocationDetailClient8ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient8DematAccount =
                new DematAccount(allocationDetailClient8DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 9
        UUID allocationDetailClient9ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c900-000000000000");
        UUID allocationDetailClient9DematAccountNumber = UUID.fromString("d0000000-0000-0000-c900-000000000000");
        UUID allocationDetailClient9TradingCode = UUID.fromString("e0000000-0000-0000-c9bd-000000000000");
        BankAccount allocationDetailClient9ClearingBankAccount =
                new BankAccount(allocationDetailClient9ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient9DematAccount =
                new DematAccount(allocationDetailClient9DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client 1 - allocation detail client 10
        UUID allocationDetailClient10ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-0c10-000000000000");
        UUID allocationDetailClient10DematAccountNumber = UUID.fromString("d0000000-0000-0000-0c10-000000000000");
        UUID allocationDetailClient10TradingCode = UUID.fromString("e0000000-0000-0000-0c10-bd0000000000");
        BankAccount allocationDetailClient10ClearingBankAccount =
                new BankAccount(allocationDetailClient10ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient10DematAccount =
                new DematAccount(allocationDetailClient10DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //broker
        UUID brokerClearingBankAccountNumber = UUID.fromString("cbb00000-0000-0000-0000-000000000000");
        UUID brokerDematAccountNumber = UUID.fromString("db000000-0000-0000-0000-000000000000");
        UUID brokerInternalBankAccountNumber = UUID.fromString("bb000000-0000-0000-0000-000000000000");
        UUID brokerInternalDematAccountNumber = UUID.fromString("bd000000-0000-0000-0000-000000000000");
        BankAccount brokerClearingBankAccount =
                new BankAccount(brokerClearingBankAccountNumber, 0);
        DematAccount brokerDematAccount = new DematAccount(brokerDematAccountNumber,
                new ArrayList<InstrumentQuantityPair>() {{
                    add(new InstrumentQuantityPair("MSFT", 0));
                }});

        //custodian
        UUID custodianClearingBankAccountNumber = UUID.fromString("cbc00000-0000-0000-0000-000000000000");
        UUID custodianDematAccountNumber = UUID.fromString("dc000000-0000-0000-0000-000000000000");
        UUID custodianInternalBankAccountNumber = UUID.fromString("cb000000-0000-0000-0000-000000000000");
        UUID custodianInternalDematAccountNumber = UUID.fromString("cd000000-0000-0000-0000-000000000000");
        BankAccount custodianClearingBankAccount =
                new BankAccount(custodianClearingBankAccountNumber, 0);
        DematAccount custodianDematAccount =
                new DematAccount(custodianDematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //clearing corporation
        UUID clearingCorpClearingBankAccountNumber = UUID.fromString("cbcc0000-0000-0000-0000-000000000000");
        UUID clearingCorpDematAccountNumber = UUID.fromString("dcc00000-0000-0000-0000-000000000000");
        BankAccount clearingCorpClearingBankAccount =
                new BankAccount(clearingCorpClearingBankAccountNumber, 20000);
        DematAccount clearingCorpDematAccount =
                new DematAccount(clearingCorpDematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 1000));
                        }});
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //--------------------------------------ClearingBank Initialization--------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        /*3-Market participants are initialized with some initial values to become ready for simulation*/
        UUID clearingBankId = UUID.fromString("cb000000-0000-0000-0000-000000000000");
        IClearingBank clearingBank = injector.getInstance(IClearingBank.class);
        clearingBank.setClearingBankUnitInfo(clearingBankId);

        IClearingBankUnitRepositories clearingBankUnitRepositories =
                injector.getInstance(IClearingBankUnitRepositories.class);

        IBankAccountRepository bankAccountRepository =
                clearingBankUnitRepositories.getBankAccountRepository();

        bankAccountRepository.add(brokerClearingBankAccount);
        bankAccountRepository.add(custodianClearingBankAccount);
        bankAccountRepository.add(clearingCorpClearingBankAccount);

        bankAccountRepository.add(institutionalClient1ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient1ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient2ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient3ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient4ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient5ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient6ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient7ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient8ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient9ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient10ClearingBankAccount);

        bankAccountRepository.add(retailClient1ClearingBankAccount);
        bankAccountRepository.add(retailClient2ClearingBankAccount);
        bankAccountRepository.add(retailClient3ClearingBankAccount);
        bankAccountRepository.add(retailClient4ClearingBankAccount);
        bankAccountRepository.add(retailClient5ClearingBankAccount);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //--------------------------------------Depository Initialization----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID depositoryId = UUID.fromString("d0000000-0000-0000-0000-000000000000");
        IDepository depository = injector.getInstance(IDepository.class);
        depository.setDepositoryInfo(depositoryId);

        IDepositoryUnitRepositories depositoryUnitRepositories =
                injector.getInstance(IDepositoryUnitRepositories.class);

        IDematAccountRepository dematAccountRepository =
                depositoryUnitRepositories.getDematAccountRepository();

        dematAccountRepository.add(brokerDematAccount);
        dematAccountRepository.add(custodianDematAccount);
        dematAccountRepository.add(clearingCorpDematAccount);

        dematAccountRepository.add(institutionalClient1DematAccount);
        dematAccountRepository.add(allocationDetailClient1DematAccount);
        dematAccountRepository.add(allocationDetailClient2DematAccount);
        dematAccountRepository.add(allocationDetailClient3DematAccount);
        dematAccountRepository.add(allocationDetailClient4DematAccount);
        dematAccountRepository.add(allocationDetailClient5DematAccount);
        dematAccountRepository.add(allocationDetailClient6DematAccount);
        dematAccountRepository.add(allocationDetailClient7DematAccount);
        dematAccountRepository.add(allocationDetailClient8DematAccount);
        dematAccountRepository.add(allocationDetailClient9DematAccount);
        dematAccountRepository.add(allocationDetailClient10DematAccount);

        dematAccountRepository.add(retailClient1DematAccount);
        dematAccountRepository.add(retailClient2DematAccount);
        dematAccountRepository.add(retailClient3DematAccount);
        dematAccountRepository.add(retailClient4DematAccount);
        dematAccountRepository.add(retailClient5DematAccount);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //-----------------------------------------Broker Initialization-----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID brokerId = UUID.fromString("b0000000-0000-0000-0000-000000000000");
        IBroker broker = injector.getInstance(IBroker.class);
        broker.setBrokerInfo(
                brokerId,
                new AccountsInformation(
                        clearingBankId,
                        brokerClearingBankAccountNumber,
                        depositoryId,
                        brokerDematAccountNumber
                ),
                new InternalAccountsInformation(
                        brokerInternalBankAccountNumber,
                        brokerInternalDematAccountNumber
                ),
                0,
                new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                    add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair
                            ("MSFT", 0));
                }}
        );

        IBrokerUnitRepositories brokerUnitRepositories =
                injector.getInstance(IBrokerUnitRepositories.class);

        IEquityInformationRepository equityInformationRepository =
                brokerUnitRepositories.getEquityInformationRepository();

        equityInformationRepository.add(new EquityInformation("MSFT", false));
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //---------------------------------------Custodian Initialization----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID custodianId = UUID.fromString("c0000000-0000-0000-0000-000000000000");
        ICustodian custodian = injector.getInstance(ICustodian.class);
        custodian.setCustodianInfo(
                custodianId,
                new emSeco.custodianUnit.core.entities.shared.AccountsInformation(
                        clearingBankId,
                        custodianClearingBankAccountNumber,
                        depositoryId,
                        custodianDematAccountNumber
                ),
                new emSeco.custodianUnit.core.entities.shared.InternalAccountsInformation(
                        custodianInternalBankAccountNumber,
                        custodianInternalDematAccountNumber
                ),
                0,
                new ArrayList<emSeco.custodianUnit.core.entities.custodianDematAccount.InstrumentQuantityPair>() {{
                    add(new emSeco.custodianUnit.core.entities.custodianDematAccount.InstrumentQuantityPair
                            ("MSFT", 0));
                }}
        );
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //----------------------------------------Exchange Initialization----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID exchangeId = UUID.fromString("e0000000-0000-0000-0000-000000000000");
        IExchange exchange = injector.getInstance(IExchange.class);
        exchange.setExchangeInfo(exchangeId);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //--------------------------------------ClearingCorp Initialization--------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        IClearingCorp clearingCorp = injector.getInstance(IClearingCorp.class);
        clearingCorp.setClearingCorpInfo(
                new emSeco.clearingCropUnit.core.entities.shared.AccountsInformation(
                        clearingBankId,
                        clearingCorpClearingBankAccountNumber,
                        depositoryId,
                        clearingCorpDematAccountNumber
                ));
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //----------------------------------Connectivity mechanisms initialization-------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        IBrokerServiceRegistry brokerServiceRegistry =
                injector.getInstance(IBrokerServiceRegistry.class);
        brokerServiceRegistry.initializeRegistry(
                new ArrayList<ICustodian>() {{
                    add(custodian);
                }},
                new ArrayList<IExchange>() {{
                    add(exchange);
                }},
                new ArrayList<IClearingBank>() {{
                    add(clearingBank);
                }},
                depository);

        ICustodianServiceRegistry custodianServiceRegistry =
                injector.getInstance(ICustodianServiceRegistry.class);
        custodianServiceRegistry.initializeRegistry(
                new ArrayList<IBroker>() {{
                    add(broker);
                }},
                clearingCorp,
                new ArrayList<IClearingBank>() {{
                    add(clearingBank);
                }},
                depository
        );

        IExchangeServiceRegistry exchangeServiceRegistry =
                injector.getInstance(IExchangeServiceRegistry.class);
        exchangeServiceRegistry.initializeRegistry(
                new ArrayList<IBroker>() {{
                    add(broker);
                }}, clearingCorp
        );

        IClearingCorpServiceRegistry clearingCorpServiceRegistry =
                injector.getInstance(IClearingCorpServiceRegistry.class);
        clearingCorpServiceRegistry.initializeRegistry(
                new ArrayList<ICustodian>() {{
                    add(custodian);
                }},
                new ArrayList<IExchange>() {{
                    add(exchange);
                }}
                , new ArrayList<IClearingBank>() {{
                    add(clearingBank);
                }},
                depository
        );
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //-------------------------------------------Simulation start--------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        /*4-Orders are created by the seller and buyer clients and then submitted to a broker. If each order passes
        all checks, it is routed from the broker to an exchange.*/
        InitiateRetailOrderOutputClass initiateRetailOrderOutputClass1 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, retailClient1ClearingBankAccountNumber, depositoryId,
                        retailClient1DematAccountNumber, retailClient1TradingCode, SideName.sell,
                        new Term(20, 200, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateRetailOrderOutputClass2 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, retailClient2ClearingBankAccountNumber, depositoryId,
                        retailClient2DematAccountNumber, retailClient2TradingCode, SideName.sell,
                        new Term(20, 200, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateRetailOrderOutputClass3 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, retailClient3ClearingBankAccountNumber, depositoryId,
                        retailClient3DematAccountNumber, retailClient3TradingCode, SideName.sell,
                        new Term(20, 200, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateRetailOrderOutputClass4 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, retailClient4ClearingBankAccountNumber, depositoryId,
                        retailClient4DematAccountNumber, retailClient4TradingCode, SideName.sell,
                        new Term(20, 200, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateRetailOrderOutputClass5 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, retailClient5ClearingBankAccountNumber, depositoryId,
                        retailClient5DematAccountNumber, retailClient5TradingCode, SideName.sell,
                        new Term(20, 200, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);


        InitiateInstitutionalOrderOutputClass initiateInstitutionalOrderOutputClass =
                broker.initiateInstitutionalOrder_UI(
                        custodianId, exchangeId, institutionalClient1RegisteredCode, SideName.buy,
                        new Term(20, 1000, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);

        /*5-The institutional client deposits the required money for the trades settlement stage with a custodian*/
        BooleanResultMessage depositMoneyResultMessage =
                custodian.depositMoney_UI(clearingBankId, institutionalClient1ClearingBankAccountNumber,
                        institutionalClient1RegisteredCode, 20000,
                        MoneyTransferMethod.clearingBankAccount);

        /*6-After processing orders in the exchange, five trades, which are pairs of buy and sell orders, have been
        created. The trades with their quantities are as follows:

            side             |  quantity   |   side                   |  quantity
            --------------------------------------------------------------------
            retail seller 1  |  200 	  |    institutional buyer 1  |  200
            retail seller 2  |  200	      |    institutional buyer 1  |  200
            retail seller 3  |  200	      |    institutional buyer 1  |  200
            retail seller 4  |  200	      |    institutional buyer 1  |  200
            retail seller 5  |  200	      |    institutional buyer 1  |  200
         */
        List<BooleanResultMessages> listOfOrderProcessingResultMessages = exchange.processOrders_REC();

        /*7-Next, trades are sent from the exchange to the broker of the buyer and seller sides.*/
        BooleanResultMessages sendTradesResultMessages = exchange.sendTrades_REC();

        /*8-At this stage, the institutional client can see the result of conducting trades.*/
        List<NoticeOfExecution> noticeOfExecutions =
                broker.getSettlementResults_UI(initiateInstitutionalOrderOutputClass.getOrderId());

        /*9-After conducting trades, the institutional client decides to allocate its trades to ten retail clients.
        Since they have paid the institutional client the costs of buying equities beforehand, they do not need to
        pay any extra money. The initial balances of the ten retail buyers' clearing bank and demat accounts are as follows:

            side                              |  bank account balance     side                              | demat account balance
            ---------------------------------------------------------     ---------------------------------------------------------
            allocation detail retail buyer 1  | 0	                      allocation detail retail buyer 1  | 0
            allocation detail retail buyer 2  | 0	                      allocation detail retail buyer 2  | 0
            allocation detail retail buyer 3  | 0	                      allocation detail retail buyer 3  | 0
            allocation detail retail buyer 4  | 0	                      allocation detail retail buyer 4  | 0
            allocation detail retail buyer 5  | 0	                      allocation detail retail buyer 5  | 0
            allocation detail retail buyer 6  | 0	                      allocation detail retail buyer 6  | 0
            allocation detail retail buyer 7  | 0	                      allocation detail retail buyer 7  | 0
            allocation detail retail buyer 8  | 0	                      allocation detail retail buyer 8  | 0
            allocation detail retail buyer 9  | 0	                      allocation detail retail buyer 9  | 0
            allocation detail retail buyer 10 | 0	                      allocation detail retail buyer 10 | 0
         */

        /*10-The institutional client submits allocations details to both its broker and custodian.*/
        UUID allocationDetailBlockId = UUID.fromString("00000000-0000-0000-0000-adb000000000");
        UUID allocationDetail1Id = UUID.fromString("00000000-0000-0000-0000-ad1000000000");
        UUID allocationDetail2Id = UUID.fromString("00000000-0000-0000-0000-ad2000000000");
        UUID allocationDetail3Id = UUID.fromString("00000000-0000-0000-0000-ad3000000000");
        UUID allocationDetail4Id = UUID.fromString("00000000-0000-0000-0000-ad4000000000");
        UUID allocationDetail5Id = UUID.fromString("00000000-0000-0000-0000-ad5000000000");
        UUID allocationDetail6Id = UUID.fromString("00000000-0000-0000-0000-ad6000000000");
        UUID allocationDetail7Id = UUID.fromString("00000000-0000-0000-0000-ad7000000000");
        UUID allocationDetail8Id = UUID.fromString("00000000-0000-0000-0000-ad8000000000");
        UUID allocationDetail9Id = UUID.fromString("00000000-0000-0000-0000-ad9000000000");
        UUID allocationDetail10Id = UUID.fromString("00000000-0000-0000-0000-ad0100000000");

        SubmitAllocationDetailsOutputClass brokerSubmitAllocationDetailsOutputClass =
                broker.submitAllocationDetails_UI(new ArrayList<SubmitAllocationDetailsInputClass>() {{
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail1Id, allocationDetailBlockId,
                            allocationDetailClient1TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail2Id, allocationDetailBlockId,
                            allocationDetailClient2TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail3Id, allocationDetailBlockId,
                            allocationDetailClient3TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail4Id, allocationDetailBlockId,
                            allocationDetailClient4TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail5Id, allocationDetailBlockId,
                            allocationDetailClient5TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail6Id, allocationDetailBlockId,
                            allocationDetailClient6TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail7Id, allocationDetailBlockId,
                            allocationDetailClient7TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail8Id, allocationDetailBlockId,
                            allocationDetailClient8TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail9Id, allocationDetailBlockId,
                            allocationDetailClient9TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodianId, allocationDetail10Id, allocationDetailBlockId,
                            allocationDetailClient10TradingCode, institutionalClient1RegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                }});

        emSeco.custodianUnit.core.modules.custodian.models.
                SubmitAllocationDetailsOutputClass custodianSubmitAllocationDetailsOutputClass =
                custodian.submitAllocationDetails_UI(new ArrayList<emSeco.custodianUnit.core.modules.custodian.models.
                        SubmitAllocationDetailsInputClass>() {{
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail1Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient1ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient1DematAccountNumber, allocationDetailClient1TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail2Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient2ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient2DematAccountNumber, allocationDetailClient2TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail3Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient3ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient3DematAccountNumber, allocationDetailClient3TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail4Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient4ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient4DematAccountNumber, allocationDetailClient4TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail5Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient5ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient5DematAccountNumber, allocationDetailClient5TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail6Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient6ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient6DematAccountNumber, allocationDetailClient6TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail7Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient7ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient7DematAccountNumber, allocationDetailClient7TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail8Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient8ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient8DematAccountNumber, allocationDetailClient8TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail9Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient9ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient9DematAccountNumber, allocationDetailClient9TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(brokerId, allocationDetail10Id, allocationDetailBlockId,
                            clearingBankId, allocationDetailClient10ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient10DematAccountNumber, allocationDetailClient10TradingCode, institutionalClient1RegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                }});

        /*11-The broker of the institutional client generates some contracts based on the allocation details and
        send them to the custodian of the institutional client.

            contract side    |  quantity	    contract side                     |  quantity
            ---------------------------------------------------------------------------------
            retail seller 1  /  100	            allocation detail retail buyer 1  |  100
            retail seller 1  /  100	            allocation detail retail buyer 2  |  100
            retail seller 2  /  100	            allocation detail retail buyer 3  |  100
            retail seller 2  /  100	            allocation detail retail buyer 4  |  100
            retail seller 3  /  100	            allocation detail retail buyer 5  |  100
            retail seller 3  /  100	            allocation detail retail buyer 6  |  100
            retail seller 4  /  100	            allocation detail retail buyer 7  |  100
            retail seller 4  /  100	            allocation detail retail buyer 8  |  100
            retail seller 5  /  100	            allocation detail retail buyer 9  |  100
            retail seller 5  /  100	            allocation detail retail buyer 10 |  100
         */
        BooleanResultMessages generateContractsResultMessages = broker.generateContracts_REC();

        /*12-The custodian affirms the correct contracts and sends the analysis result to the broker.*/
        BooleanResultMessages affirmContractsResultMessages = custodian.affirmContracts_REC();

        /*13-The broker converts the affirmed contracts to trades and send them to the custodian.*/
        BooleanResultMessages submitTradesResultMessages = broker.submitTrades_REC();

        /*14-Since, from this point onward, the custodian is totally responsible for settling trades, the custodian
        submits the trades to the clearing corporation for settlement.*/
        List<BooleanResultMessages> submitInstitutionalTradesResultMessages =
                custodian.submitInstitutionalTrades_REC();

        /*15-The clearing corporation settles the received trades. After settlement, the clearing corporation sends the
        settlement result to both the buy-side custodian and the exchange.*/
        SettleTradesOutputClass settleTradesOutputClass = clearingCorp.settleTrades_REC();

        /*16-The exchange sends the settlement result to the sell-side broker.*/
        BooleanResultMessages sendSettlementResultsResultMessages = exchange.sendSettlementResults_REC();

        /*17-The monetary and equity-related obligations of the broker and custodian are discharged against their clients.
        After doing so, the buyers have their equities, and sellers have their money. Both sides' balances are as follows:

            side                               |  Bank account balance 	side                               | demat account balance
            -----------------------------------------------------       ----------------------------------------------------------
            retail seller 1                    |  2000	                retail seller 1                    |  0
            retail seller 1                    |  2000	                retail seller 1                    |  0
            retail seller 2                    |  2000	                retail seller 2                    |  0
            retail seller 2                    |  2000	                retail seller 2                    |  0
            retail seller 3                    |  2000	                retail seller 3                    |  0
            retail seller 3                    |  2000	                retail seller 3                    |  0
            retail seller 4                    |  2000	                retail seller 4                    |  0
            retail seller 4                    |  2000	                retail seller 4                    |  0
            retail seller 5                    |  2000	                retail seller 5                    |  0
            retail seller 5                    |  2000	                retail seller 5                    |  0
            allocation detail retail buyer 1   |  0	                    allocation detail retail buyer 1   |  100
            allocation detail retail buyer 2   |  0	                    allocation detail retail buyer 2   |  100
            allocation detail retail buyer 3   |  0	                    allocation detail retail buyer 3   |  100
            allocation detail retail buyer 4   |  0	                    allocation detail retail buyer 4   |  100
            allocation detail retail buyer 5   |  0	                    allocation detail retail buyer 5   |  100
            allocation detail retail buyer 6   |  0	                    allocation detail retail buyer 6   |  100
            allocation detail retail buyer 7   |  0	                    allocation detail retail buyer 7   |  100
            allocation detail retail buyer 8   |  0	                    allocation detail retail buyer 8   |  100
            allocation detail retail buyer 9   |  0	                    allocation detail retail buyer 9   |  100
            allocation detail retail buyer 10  |  0	                    allocation detail retail buyer 10  |  100
         */

        List<BooleanResultMessage> brokerDischargeObligationResultMessages =
                broker.dischargeObligationsAgainstClients_REC();
        List<BooleanResultMessage> custodianDischargeObligationResultMessages =
                custodian.dischargeObligationsAgainstClients_REC();
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //---------------------------------------------Assertions------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        assertEquals(4000,retailClient1ClearingBankAccount.getBalance());
        double retailClient1DematAccountQuantity = retailClient1DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,retailClient1DematAccountQuantity);

        assertEquals(4000,retailClient2ClearingBankAccount.getBalance());
        double retailClient2DematAccountQuantity = retailClient2DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,retailClient2DematAccountQuantity);

        assertEquals(4000,retailClient3ClearingBankAccount.getBalance());
        double retailClient3DematAccountQuantity = retailClient3DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,retailClient3DematAccountQuantity);

        assertEquals(4000,retailClient4ClearingBankAccount.getBalance());
        double retailClient4DematAccountQuantity = retailClient4DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,retailClient4DematAccountQuantity);

        assertEquals(4000,retailClient5ClearingBankAccount.getBalance());
        double retailClient5DematAccountQuantity = retailClient5DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,retailClient5DematAccountQuantity);


        assertEquals(0,institutionalClient1ClearingBankAccount.getBalance());
        double institutionalClient1DematAccountQuantity =
                institutionalClient1DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,institutionalClient1DematAccountQuantity);

        assertEquals(0,allocationDetailClient1ClearingBankAccount.getBalance());
        double allocationDetailClient1DematAccountQuantity =
                allocationDetailClient1DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient1DematAccountQuantity);

        assertEquals(0,allocationDetailClient2ClearingBankAccount.getBalance());
        double allocationDetailClient2DematAccountQuantity =
                allocationDetailClient2DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient2DematAccountQuantity);

        assertEquals(0,allocationDetailClient3ClearingBankAccount.getBalance());
        double allocationDetailClient3DematAccountQuantity =
                allocationDetailClient3DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient3DematAccountQuantity);

        assertEquals(0,allocationDetailClient4ClearingBankAccount.getBalance());
        double allocationDetailClient4DematAccountQuantity =
                allocationDetailClient4DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient4DematAccountQuantity);

        assertEquals(0,allocationDetailClient5ClearingBankAccount.getBalance());
        double allocationDetailClient5DematAccountQuantity =
                allocationDetailClient5DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient5DematAccountQuantity);

        assertEquals(0,allocationDetailClient6ClearingBankAccount.getBalance());
        double allocationDetailClient6DematAccountQuantity =
                allocationDetailClient6DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient6DematAccountQuantity);

        assertEquals(0,allocationDetailClient7ClearingBankAccount.getBalance());
        double allocationDetailClient7DematAccountQuantity =
                allocationDetailClient7DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient7DematAccountQuantity);

        assertEquals(0,allocationDetailClient8ClearingBankAccount.getBalance());
        double allocationDetailClient8DematAccountQuantity =
                allocationDetailClient8DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient8DematAccountQuantity);

        assertEquals(0,allocationDetailClient9ClearingBankAccount.getBalance());
        double allocationDetailClient9DematAccountQuantity =
                allocationDetailClient9DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient9DematAccountQuantity);

        assertEquals(0,allocationDetailClient10ClearingBankAccount.getBalance());
        double allocationDetailClient10DematAccountQuantity =
                allocationDetailClient10DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100,allocationDetailClient10DematAccountQuantity);


        assertEquals(0,brokerClearingBankAccount.getBalance());
        double brokerDematAccountQuantity =
                brokerDematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,brokerDematAccountQuantity);

        assertEquals(0,custodianClearingBankAccount.getBalance());
        double custodianDematAccountQuantity =
                custodianDematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0,custodianDematAccountQuantity);

        assertEquals(20000,clearingCorpClearingBankAccount.getBalance());
        double clearingCorpDematAccountQuantity =
                clearingCorpDematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(1000,clearingCorpDematAccountQuantity);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        //} catch (Throwable ex) {
        //    System.err.println("Uncaught exception - " + ex.getMessage());
        //    ex.printStackTrace(System.err);
        //}
    }
}

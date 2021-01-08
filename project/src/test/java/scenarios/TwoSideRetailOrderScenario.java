package scenarios;

import com.google.inject.*;
import org.junit.jupiter.api.Test;
import emSeco.brokerUnit.core.entities.equityInformation.EquityInformation;
import emSeco.brokerUnit.core.entities.brokerBankAccount.BrokerBankAccount;
import emSeco.brokerUnit.core.entities.brokerDematAccount.BrokerDematAccount;
import emSeco.brokerUnit.core.entities.shared.*;
import emSeco.brokerUnit.core.entities.order.OrderType;
import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.brokerUnit.core.modules.broker.models.InitiateRetailOrderOutputClass;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IBrokerBankAccountRepository;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IBrokerDematAccountRepository;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IEquityInformationRepository;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTradesOutputClass;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingbankUnit.core.entities.bankAccount.BankAccount;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.IClearingBankUnitRepositories;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.repositories.IBankAccountRepository;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
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

public class TwoSideRetailOrderScenario {

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
        /*1-Imagine that ten retail client want to trade in an equity market. ّFour of them are sellers, and six of
        them are buyers. Each buyer wants to buy 2000 equities with a price of 20 dollars for each equity. Each seller
        wants to sell 3000 equities with a price of 20 dollars for each equity. They make their orders with the
        following sequence:

        seller1 -> seller2-> buyer1 -> buyer2 -> buyer3 -> seller3 -> seller4-> buyer4 -> buyer5 -> buyer6

        The initial balances of the retail clients' clearing bank and demat accounts are as follows:

            side     |  bank account balance                side     |  demat account balance
            --------------------------------                ---------------------------------
            Seller1  |  0                                   Seller1  |  3000
            Seller2  |  0                                   Seller2  |  3000
            buyer1   |  4000                                buyer1   |  0
            buyer2   |  4000                                buyer2   |  0
            buyer3   |  4000                                buyer3   |  0
            Seller3  |  0                                   Seller3  |  3000
            Seller4  |  0                                   Seller4  |  3000
            buyer4   |  4000                                buyer4   |  0
            buyer5   |  4000                                buyer5   |  0
            buyer6   |  4000                                buyer6   |  0

        To make a trade, each retail client needs a clearing bank account number, a demat account number, and a trading
        code, which is used as an account number in case the client wants to use the broker's internal accounts as a
        method of Money or equity transfer. The accounts' information of these people are as follows:
        */

        //seller retail client 1
        UUID sellerRetailClient1ClearingBankAccountNumber = UUID.fromString("cb000000-c100-0000-0000-000000000000");
        UUID sellerRetailClient1DematAccountNumber = UUID.fromString("d0000000-c100-0000-0000-000000000000");
        UUID sellerRetailClient1TradingCode = UUID.fromString("e0000000-c1bd-0000-0000-000000000000");
        BankAccount sellerRetailClient1ClearingBankAccount =
                new BankAccount(sellerRetailClient1ClearingBankAccountNumber, 0);
        DematAccount sellerRetailClient1DematAccount =
                new DematAccount(sellerRetailClient1DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 3000));
                        }});
        BrokerBankAccount sellerRetailClient1BrokerBankAccount =
                new BrokerBankAccount(sellerRetailClient1TradingCode, 0);
        BrokerDematAccount sellerRetailClient1BrokerDematAccount =
                new BrokerDematAccount(sellerRetailClient1TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 3000
                            ));
                        }});

        //seller retail client 2
        UUID sellerRetailClient2ClearingBankAccountNumber = UUID.fromString("cb000000-c200-0000-0000-000000000000");
        UUID sellerRetailClient2DematAccountNumber = UUID.fromString("d0000000-c200-0000-0000-000000000000");
        UUID sellerRetailClient2TradingCode = UUID.fromString("e0000000-c2bd-0000-0000-000000000000");
        BankAccount sellerRetailClient2ClearingBankAccount =
                new BankAccount(sellerRetailClient2ClearingBankAccountNumber, 0);
        DematAccount sellerRetailClient2DematAccount =
                new DematAccount(sellerRetailClient2DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 3000));
                        }});
        BrokerBankAccount sellerRetailClient2BrokerBankAccount =
                new BrokerBankAccount(sellerRetailClient2TradingCode, 0);
        BrokerDematAccount sellerRetailClient2BrokerDematAccount =
                new BrokerDematAccount(sellerRetailClient2TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 3000
                            ));
                        }});

        //buyer retail client 1
        UUID buyerRetailClient1ClearingBankAccountNumber = UUID.fromString("cb000000-c300-0000-0000-000000000000");
        UUID buyerRetailClient1DematAccountNumber = UUID.fromString("d0000000-c300-0000-0000-000000000000");
        UUID buyerRetailClient1TradingCode = UUID.fromString("e0000000-c3bd-0000-0000-000000000000");
        BankAccount buyerRetailClient1ClearingBankAccount =
                new BankAccount(buyerRetailClient1ClearingBankAccountNumber, 40000);
        DematAccount buyerRetailClient1DematAccount = new DematAccount(buyerRetailClient1DematAccountNumber,
                new ArrayList<InstrumentQuantityPair>() {{
                    add(new InstrumentQuantityPair("MSFT", 0));
                }});
        BrokerBankAccount buyerRetailClient1BrokerBankAccount =
                new BrokerBankAccount(buyerRetailClient1TradingCode, 40000);
        BrokerDematAccount buyerRetailClient1BrokerDematAccount =
                new BrokerDematAccount(buyerRetailClient1TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 0
                            ));
                        }});

        //buyer retail client 2
        UUID buyerRetailClient2ClearingBankAccountNumber = UUID.fromString("cb000000-c400-0000-0000-000000000000");
        UUID buyerRetailClient2DematAccountNumber = UUID.fromString("d0000000-c400-0000-0000-000000000000");
        UUID buyerRetailClient2TradingCode = UUID.fromString("e0000000-c4bd-0000-0000-000000000000");
        BankAccount buyerRetailClient2ClearingBankAccount =
                new BankAccount(buyerRetailClient2ClearingBankAccountNumber, 40000);
        DematAccount buyerRetailClient2DematAccount =
                new DematAccount(buyerRetailClient2DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});
        BrokerBankAccount buyerRetailClient2BrokerBankAccount =
                new BrokerBankAccount(buyerRetailClient2TradingCode, 40000);
        BrokerDematAccount buyerRetailClient2BrokerDematAccount =
                new BrokerDematAccount(buyerRetailClient2TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 0
                            ));
                        }});

        //buyer retail client 3
        UUID buyerRetailClient3ClearingBankAccountNumber = UUID.fromString("cb000000-c500-0000-0000-000000000000");
        UUID buyerRetailClient3DematAccountNumber = UUID.fromString("d0000000-c500-0000-0000-000000000000");
        UUID buyerRetailClient3TradingCode = UUID.fromString("e0000000-c5bd-0000-0000-000000000000");
        BankAccount buyerRetailClient3ClearingBankAccount =
                new BankAccount(buyerRetailClient3ClearingBankAccountNumber, 40000);
        DematAccount buyerRetailClient3DematAccount = new DematAccount(buyerRetailClient3DematAccountNumber,
                new ArrayList<InstrumentQuantityPair>() {{
                    add(new InstrumentQuantityPair("MSFT", 0));
                }});
        BrokerBankAccount buyerRetailClient3BrokerBankAccount =
                new BrokerBankAccount(buyerRetailClient3TradingCode, 40000);
        BrokerDematAccount buyerRetailClient3BrokerDematAccount =
                new BrokerDematAccount(buyerRetailClient3TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 0
                            ));
                        }});

        //seller retail client 3
        UUID sellerRetailClient3ClearingBankAccountNumber = UUID.fromString("cb000000-c600-0000-0000-000000000000");
        UUID sellerRetailClient3DematAccountNumber = UUID.fromString("d0000000-c600-0000-0000-000000000000");
        UUID sellerRetailClient3TradingCode = UUID.fromString("e0000000-c6bd-0000-0000-000000000000");
        BankAccount sellerRetailClient3ClearingBankAccount =
                new BankAccount(sellerRetailClient3ClearingBankAccountNumber, 0);
        DematAccount sellerRetailClient3DematAccount =
                new DematAccount(sellerRetailClient3DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 3000));
                        }});
        BrokerBankAccount sellerRetailClient3BrokerBankAccount =
                new BrokerBankAccount(sellerRetailClient3TradingCode, 0);
        BrokerDematAccount sellerRetailClient3BrokerDematAccount =
                new BrokerDematAccount(sellerRetailClient3TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 3000
                            ));
                        }});

        //seller retail client 4
        UUID sellerRetailClient4ClearingBankAccountNumber = UUID.fromString("cb000000-c700-0000-0000-000000000000");
        UUID sellerRetailClient4DematAccountNumber = UUID.fromString("d0000000-c700-0000-0000-000000000000");
        UUID sellerRetailClient4TradingCode = UUID.fromString("e0000000-c7bd-0000-0000-000000000000");
        BankAccount sellerRetailClient4ClearingBankAccount =
                new BankAccount(sellerRetailClient4ClearingBankAccountNumber, 0);
        DematAccount sellerRetailClient4DematAccount =
                new DematAccount(sellerRetailClient4DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 3000));
                        }});
        BrokerBankAccount sellerRetailClient4BrokerBankAccount =
                new BrokerBankAccount(sellerRetailClient4TradingCode, 0);
        BrokerDematAccount sellerRetailClient4BrokerDematAccount =
                new BrokerDematAccount(sellerRetailClient4TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 3000
                            ));
                        }});

        //buyer retail client 4
        UUID buyerRetailClient4ClearingBankAccountNumber = UUID.fromString("cb000000-c800-0000-0000-000000000000");
        UUID buyerRetailClient4DematAccountNumber = UUID.fromString("d0000000-c800-0000-0000-000000000000");
        UUID buyerRetailClient4TradingCode = UUID.fromString("e0000000-c8bd-0000-0000-000000000000");
        BankAccount buyerRetailClient4ClearingBankAccount =
                new BankAccount(buyerRetailClient4ClearingBankAccountNumber, 40000);
        DematAccount buyerRetailClient4DematAccount =
                new DematAccount(buyerRetailClient4DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});
        BrokerBankAccount buyerRetailClient4BrokerBankAccount =
                new BrokerBankAccount(buyerRetailClient4TradingCode, 40000);
        BrokerDematAccount buyerRetailClient4BrokerDematAccount =
                new BrokerDematAccount(buyerRetailClient4TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 0
                            ));
                        }});

        //buyer retail client 5
        UUID buyerRetailClient5ClearingBankAccountNumber = UUID.fromString("cb000000-c900-0000-0000-000000000000");
        UUID buyerRetailClient5DematAccountNumber = UUID.fromString("d0000000-c900-0000-0000-000000000000");
        UUID buyerRetailClient5TradingCode = UUID.fromString("e0000000-c9bd-0000-0000-000000000000");
        BankAccount buyerRetailClient5ClearingBankAccount =
                new BankAccount(buyerRetailClient5ClearingBankAccountNumber, 40000);
        DematAccount buyerRetailClient5DematAccount =
                new DematAccount(buyerRetailClient5DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});
        BrokerBankAccount buyerRetailClient5BrokerBankAccount =
                new BrokerBankAccount(buyerRetailClient5TradingCode, 40000);
        BrokerDematAccount buyerRetailClient5BrokerDematAccount =
                new BrokerDematAccount(buyerRetailClient5TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 0
                            ));
                        }});

        //buyer retail client 6
        UUID buyerRetailClient6ClearingBankAccountNumber = UUID.fromString("cb000000-c010-0000-0000-000000000000");
        UUID buyerRetailClient6DematAccountNumber = UUID.fromString("d0000000-c010-0000-0000-000000000000");
        UUID buyerRetailClient6TradingCode = UUID.fromString("e0000000-c010-bd00-0000-000000000000");
        BankAccount buyerRetailClient6ClearingBankAccount =
                new BankAccount(buyerRetailClient6ClearingBankAccountNumber, 40000);
        DematAccount buyerRetailClient6DematAccount =
                new DematAccount(buyerRetailClient6DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});
        BrokerBankAccount buyerRetailClient6BrokerBankAccount =
                new BrokerBankAccount(buyerRetailClient6TradingCode, 40000);
        BrokerDematAccount buyerRetailClient6BrokerDematAccount =
                new BrokerDematAccount(buyerRetailClient6TradingCode,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair(
                                    "MSFT", 0
                            ));
                        }});

        /*2-Other market participants, such as brokers and the clearing corporation, should have accounts to make a
        retail trade possible. The accounts information of these people are as follows:*/

        //broker
        UUID brokerClearingBankAccountNumber = UUID.fromString("cbb00000-0000-0000-0000-000000000000");
        UUID brokerDematAccountNumber = UUID.fromString("db000000-0000-0000-0000-000000000000");
        UUID brokerInternalBankAccountNumber = UUID.fromString("bb000000-0000-0000-0000-000000000000");
        UUID brokerInternalDematAccountNumber = UUID.fromString("bd000000-0000-0000-0000-000000000000");
        BankAccount brokerClearingBankAccount =
                new BankAccount(brokerClearingBankAccountNumber, 0);
        DematAccount brokerDematAccount =
                new DematAccount(brokerDematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //clearing corporation
        UUID clearingCorpClearingBankAccountNumber = UUID.fromString("cbcc0000-0000-0000-0000-000000000000");
        UUID clearingCorpDematAccountNumber = UUID.fromString("dcc00000-0000-0000-0000-000000000000");
        BankAccount clearingCorpClearingBankAccount = new
                BankAccount(clearingCorpClearingBankAccountNumber, 240000);
        DematAccount clearingCorpDematAccount =
                new DematAccount(clearingCorpDematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 12000));
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
        bankAccountRepository.add(clearingCorpClearingBankAccount);

        bankAccountRepository.add(sellerRetailClient1ClearingBankAccount);
        bankAccountRepository.add(sellerRetailClient2ClearingBankAccount);
        bankAccountRepository.add(sellerRetailClient3ClearingBankAccount);
        bankAccountRepository.add(sellerRetailClient4ClearingBankAccount);

        bankAccountRepository.add(buyerRetailClient1ClearingBankAccount);
        bankAccountRepository.add(buyerRetailClient2ClearingBankAccount);
        bankAccountRepository.add(buyerRetailClient3ClearingBankAccount);
        bankAccountRepository.add(buyerRetailClient4ClearingBankAccount);
        bankAccountRepository.add(buyerRetailClient5ClearingBankAccount);
        bankAccountRepository.add(buyerRetailClient6ClearingBankAccount);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //---------------------------------------Depository Initialization---------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID depositoryId = UUID.fromString("d0000000-0000-0000-0000-000000000000");
        IDepository depository = injector.getInstance(IDepository.class);
        depository.setDepositoryInfo(depositoryId);

        IDepositoryUnitRepositories depositoryUnitRepositories =
                injector.getInstance(IDepositoryUnitRepositories.class);

        IDematAccountRepository dematAccountRepository =
                depositoryUnitRepositories.getDematAccountRepository();

        dematAccountRepository.add(brokerDematAccount);
        dematAccountRepository.add(clearingCorpDematAccount);

        dematAccountRepository.add(sellerRetailClient1DematAccount);
        dematAccountRepository.add(sellerRetailClient2DematAccount);
        dematAccountRepository.add(sellerRetailClient3DematAccount);
        dematAccountRepository.add(sellerRetailClient4DematAccount);

        dematAccountRepository.add(buyerRetailClient1DematAccount);
        dematAccountRepository.add(buyerRetailClient2DematAccount);
        dematAccountRepository.add(buyerRetailClient3DematAccount);
        dematAccountRepository.add(buyerRetailClient4DematAccount);
        dematAccountRepository.add(buyerRetailClient5DematAccount);
        dematAccountRepository.add(buyerRetailClient6DematAccount);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //---------------------------------------Broker Initialization-------------------------------------------//
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

        IBrokerBankAccountRepository brokerBankAccountRepository =
                brokerUnitRepositories.getBrokerBankAccountRepository();

        IBrokerDematAccountRepository brokerDematAccountRepository =
                brokerUnitRepositories.getBrokerDematAccountRepository();

        IEquityInformationRepository equityInformationRepository =
                brokerUnitRepositories.getEquityInformationRepository();

        brokerBankAccountRepository.add(sellerRetailClient1BrokerBankAccount);
        brokerBankAccountRepository.add(sellerRetailClient2BrokerBankAccount);
        brokerBankAccountRepository.add(sellerRetailClient3BrokerBankAccount);
        brokerBankAccountRepository.add(sellerRetailClient4BrokerBankAccount);

        brokerDematAccountRepository.add(sellerRetailClient1BrokerDematAccount);
        brokerDematAccountRepository.add(sellerRetailClient2BrokerDematAccount);
        brokerDematAccountRepository.add(sellerRetailClient3BrokerDematAccount);
        brokerDematAccountRepository.add(sellerRetailClient4BrokerDematAccount);

        brokerBankAccountRepository.add(buyerRetailClient1BrokerBankAccount);
        brokerBankAccountRepository.add(buyerRetailClient2BrokerBankAccount);
        brokerBankAccountRepository.add(buyerRetailClient3BrokerBankAccount);
        brokerBankAccountRepository.add(buyerRetailClient4BrokerBankAccount);
        brokerBankAccountRepository.add(buyerRetailClient5BrokerBankAccount);
        brokerBankAccountRepository.add(buyerRetailClient6BrokerBankAccount);

        brokerDematAccountRepository.add(buyerRetailClient1BrokerDematAccount);
        brokerDematAccountRepository.add(buyerRetailClient2BrokerDematAccount);
        brokerDematAccountRepository.add(buyerRetailClient3BrokerDematAccount);
        brokerDematAccountRepository.add(buyerRetailClient4BrokerDematAccount);
        brokerDematAccountRepository.add(buyerRetailClient5BrokerDematAccount);
        brokerDematAccountRepository.add(buyerRetailClient6BrokerDematAccount);

        equityInformationRepository.add(new EquityInformation("MSFT", false));
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //----------------------------------------Exchange Initialization----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID exchangeId = UUID.fromString("e0000000-0000-0000-0000-000000000000");
        IExchange exchange = injector.getInstance(IExchange.class);
        exchange.setExchangeInfo(exchangeId);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //-------------------------------------ClearingCorp Initialization---------------------------------------//
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

        //-------------------------------Connectivity mechanisms initialization----------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        IBrokerServiceRegistry brokerServiceRegistry =
                injector.getInstance(IBrokerServiceRegistry.class);

        brokerServiceRegistry.initializeRegistry(
                new ArrayList<ICustodian>() {{
                    add(null);
                }},
                new ArrayList<IExchange>() {{
                    add(exchange);
                }},
                new ArrayList<IClearingBank>() {{
                    add(clearingBank);
                }},
                depository);

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
                    add(null);
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

        //------------------------------------------Simulation start---------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        /*4-Orders are created by the seller and buyer retail clients and then submitted to a broker. If each order
        passes all checks, it is routed from the broker to an exchange.*/
        InitiateRetailOrderOutputClass initiateSellRetailOrderOutputClass1 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, sellerRetailClient1ClearingBankAccountNumber, depositoryId,
                        sellerRetailClient1DematAccountNumber, sellerRetailClient1TradingCode, SideName.sell,
                        new Term(20, 3000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateSellRetailOrderOutputClass2 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, sellerRetailClient2ClearingBankAccountNumber, depositoryId,
                        sellerRetailClient2DematAccountNumber, sellerRetailClient2TradingCode, SideName.sell,
                        new Term(20, 3000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateBuyRetailOrderOutputClass1 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, buyerRetailClient1ClearingBankAccountNumber, depositoryId,
                        buyerRetailClient1DematAccountNumber, buyerRetailClient1TradingCode, SideName.buy,
                        new Term(20, 2000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateBuyRetailOrderOutputClass2 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, buyerRetailClient2ClearingBankAccountNumber, depositoryId,
                        buyerRetailClient2DematAccountNumber, buyerRetailClient2TradingCode, SideName.buy,
                        new Term(20, 2000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateBuyRetailOrderOutputClass3 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, buyerRetailClient3ClearingBankAccountNumber, depositoryId,
                        buyerRetailClient3DematAccountNumber, buyerRetailClient3TradingCode, SideName.buy,
                        new Term(20, 2000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateSellRetailOrderOutputClass3 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, sellerRetailClient3ClearingBankAccountNumber, depositoryId,
                        sellerRetailClient3DematAccountNumber, sellerRetailClient3TradingCode, SideName.sell,
                        new Term(20, 3000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateSellRetailOrderOutputClass4 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, sellerRetailClient4ClearingBankAccountNumber, depositoryId,
                        sellerRetailClient4DematAccountNumber, sellerRetailClient4TradingCode, SideName.sell,
                        new Term(20, 3000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateBuyRetailOrderOutputClass4 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, buyerRetailClient4ClearingBankAccountNumber, depositoryId,
                        buyerRetailClient4DematAccountNumber, buyerRetailClient4TradingCode, SideName.buy,
                        new Term(20, 2000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateRetailOrderOutputClass5 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, buyerRetailClient5ClearingBankAccountNumber, depositoryId,
                        buyerRetailClient5DematAccountNumber, buyerRetailClient5TradingCode, SideName.buy,
                        new Term(20, 2000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        InitiateRetailOrderOutputClass initiateBuyRetailOrderOutputClass6 =
                broker.initiateRetailOrder_UI(
                        exchangeId, clearingBankId, buyerRetailClient6ClearingBankAccountNumber, depositoryId,
                        buyerRetailClient6DematAccountNumber, buyerRetailClient6TradingCode, SideName.buy,
                        new Term(20, 2000, "MSFT"), OrderType.LIM,
                        MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount);

        /*5-After processing orders in the exchange, eight trades, which are pairs of buy and sell orders, have been
        created. The trades with their quantities are as follows:

            side    |  quantity   |  side   |  quantity
            --------------------------------------------
            seller1 |  2000		 |   buyer1  |  2000
            seller1 |  1000		 |   buyer2  |  1000
            seller2 |  1000		 |   buyer2  |  1000
            seller2 |  2000		 |   buyer3  |  2000
            seller3 |  2000		 |   buyer4  |  2000
            seller3 |  1000		 |   buyer5  |  1000
            seller4 |  1000		 |   buyer5  |  1000
            seller4 |  2000		 |   buyer6  |  2000
         */
        List<BooleanResultMessages> listOfOrderProcessingResultMessages = exchange.processOrders_REC();

        /*6-Trades are settled in a clearing corporation*/
        SettleTradesOutputClass settleTradesOutputClass = clearingCorp.settleTrades_REC();

        /*7-The results of the trade settlement stage are sent from the exchange to the broker */
        BooleanResultMessages sendSettlementResultsResultMessages = exchange.sendSettlementResults_REC();

        /*8-The monetary and equity-related obligations of the broker are discharged against its retail clients. After
        doing so, the buyers have their equities, and sellers have their money. Both sides' balances are as follows:

            side     |  Bank account balance                side     |  demat account balance
            --------------------------------                ---------------------------------
            Seller1  |  60000                               Seller1  |  0
            Seller2  |  60000                               Seller2  |  0
            buyer1   |  0                                   buyer1   |  2000
            buyer2   |  0                                   buyer2   |  2000
            buyer3   |  0                                   buyer3   |  2000
            Seller3  |  60000                               Seller3  |  0
            Seller4  |  60000                               Seller4  |  0
            buyer4   |  0                                   buyer4   |  2000
            buyer5   |  0                                   buyer5   |  2000
            buyer6   |  0                                   buyer6   |  2000
         */
        List<BooleanResultMessage> dischargeObligationsResultMessages =
                broker.dischargeObligationsAgainstClients_REC();
        //---------------------------------------------Assertions------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        assertEquals(sellerRetailClient1ClearingBankAccount.getBalance(), 60000);
        double sellerRetailClient1DematAccountQuantity = sellerRetailClient1DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(sellerRetailClient1DematAccountQuantity, 0);

        assertEquals(sellerRetailClient2ClearingBankAccount.getBalance(), 60000);
        double sellerRetailClient2DematAccountQuantity = sellerRetailClient2DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(sellerRetailClient2DematAccountQuantity, 0);

        assertEquals(sellerRetailClient3ClearingBankAccount.getBalance(), 60000);
        double sellerRetailClient3DematAccountQuantity = sellerRetailClient3DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(sellerRetailClient3DematAccountQuantity, 0);

        assertEquals(sellerRetailClient4ClearingBankAccount.getBalance(), 60000);
        double sellerRetailClient4DematAccountQuantity = sellerRetailClient4DematAccount.getInstrumentQuantityPairs()
                .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(sellerRetailClient4DematAccountQuantity, 0);


        assertEquals(buyerRetailClient1ClearingBankAccount.getBalance(), 0);
        double buyerRetailClient1BrokerDematAccountQuantity =
                buyerRetailClient1DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(buyerRetailClient1BrokerDematAccountQuantity, 2000);

        assertEquals(buyerRetailClient2ClearingBankAccount.getBalance(), 0);
        double buyerRetailClient2BrokerDematAccountQuantity =
                buyerRetailClient2DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(buyerRetailClient2BrokerDematAccountQuantity, 2000);

        assertEquals(buyerRetailClient3ClearingBankAccount.getBalance(), 0);
        double buyerRetailClient3BrokerDematAccountQuantity =
                buyerRetailClient3DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(buyerRetailClient3BrokerDematAccountQuantity, 2000);

        assertEquals(buyerRetailClient4ClearingBankAccount.getBalance(), 0);
        double buyerRetailClient4BrokerDematAccountQuantity =
                buyerRetailClient4DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(buyerRetailClient4BrokerDematAccountQuantity, 2000);

        assertEquals(buyerRetailClient5ClearingBankAccount.getBalance(), 0);
        double buyerRetailClient5BrokerDematAccountQuantity =
                buyerRetailClient5DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(buyerRetailClient5BrokerDematAccountQuantity, 2000);

        assertEquals(buyerRetailClient6ClearingBankAccount.getBalance(), 0);
        double buyerRetailClient6BrokerDematAccountQuantity =
                buyerRetailClient6DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(buyerRetailClient6BrokerDematAccountQuantity, 2000);


        assertEquals(brokerClearingBankAccount.getBalance(), 0);
        double brokerDematAccountQuantity =
                brokerDematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(brokerDematAccountQuantity, 0);

        assertEquals(clearingCorpClearingBankAccount.getBalance(), 240000);
        double clearingCorpDematAccountQuantity =
                clearingCorpDematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(clearingCorpDematAccountQuantity, 12000);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        //} catch (Throwable ex) {
        //    System.err.println("Uncaught exception - " + ex.getMessage());
        //    ex.printStackTrace(System.err);
        //}
    }
}
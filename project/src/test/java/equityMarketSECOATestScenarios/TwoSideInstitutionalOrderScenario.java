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
import emSeco.brokerUnit.core.modules.broker.models.SubmitAllocationDetailsInputClass;
import emSeco.brokerUnit.core.modules.broker.models.SubmitAllocationDetailsOutputClass;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IEquityInformationRepository;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTradesOutputClass;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingbankUnit.core.entities.bankAccount.BankAccount;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.IClearingBankUnitRepositories;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.repositories.IBankAccountRepository;
import emSeco.custodianUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.custodianUnit.core.entities.shared.EquityTransferMethod;
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

public class TwoSideInstitutionalOrderScenario {

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

        Injector injector2 = Guice.createInjector(
                new SharedServicesConfigurations(),
                new BrokerUnitConfigurations(),
                new CustodianUnitConfigurations()
        );
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //-----------------------------------------Creating Accounts---------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        /*1-Imagine that two institutional clients want to trade in an equity market. One institutional client is the
        seller, and the other is a buyer. The institutional buyer wants to buy 1500 equities with a price of 20 dollars
        for each equity. The institutional seller wants to sell 1500 equities with a price of 20 dollars for each equity.
        they make their orders with the following sequence:

        institutional seller -> institutional buyer

        The initial balances of institutional clients' clearing bank and demat accounts are as follows:

            side                  |  bank account balance 	              side                  |  demat account balance
            ----------------------------------------------                -----------------------------------------------
            institutional buyer   |  30000	                              institutional buyer   |  0
            institutional seller  |  0	                                  institutional seller  |  1500
         */

        //buyer institutional Client
        UUID buyerInstitutionalClientClearingBankAccountNumber = UUID.fromString("cb000000-0000-c100-0000-000000000000");
        UUID buyerInstitutionalClientDematAccountNumber = UUID.fromString("d0000000-0000-c100-0000-000000000000");
        UUID buyerInstitutionalClientRegisteredCode = UUID.fromString("e0000000-0000-c1bd-0000-000000000000");
        BankAccount buyerInstitutionalClientClearingBankAccount =
                new BankAccount(buyerInstitutionalClientClearingBankAccountNumber, 30000);
        DematAccount buyerInstitutionalClientDematAccount =
                new DematAccount(buyerInstitutionalClientDematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller institutional Client
        UUID sellerInstitutionalClientClearingBankAccountNumber = UUID.fromString("cb000000-0000-c200-0000-000000000000");
        UUID sellerInstitutionalClientDematAccountNumber = UUID.fromString("d0000000-0000-c200-0000-000000000000");
        UUID sellerInstitutionalClientRegisteredCode = UUID.fromString("e0000000-0000-c2bd-0000-000000000000");
        BankAccount sellerInstitutionalClientClearingBankAccount =
                new BankAccount(sellerInstitutionalClientClearingBankAccountNumber, 0);
        DematAccount sellerInstitutionalClient2ematAccount =
                new DematAccount(sellerInstitutionalClientDematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 1500));
                        }});

        /*2-The accounts' information of the fifteen clients of the buyer institutional client and six clients of the
        seller institutional client, which we are going to talk about in later stages, are as follows:*/

        //buyer institutional Client - allocation detail client 1
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

        //buyer institutional Client - allocation detail client 2
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

        //buyer institutional Client - allocation detail client 3
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

        //buyer institutional Client - allocation detail client 4
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

        //buyer institutional Client - allocation detail client 5
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

        //buyer institutional Client - allocation detail client 6
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

        //buyer institutional Client - allocation detail client 7
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

        //buyer institutional Client - allocation detail client 8
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

        //buyer institutional Client - allocation detail client 9
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

        //buyer institutional Client - allocation detail client 10
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

        //buyer institutional Client - allocation detail client 11
        UUID allocationDetailClient11ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c110-000000000000");
        UUID allocationDetailClient11DematAccountNumber = UUID.fromString("d0000000-0000-0000-c110-000000000000");
        UUID allocationDetailClient11TradingCode = UUID.fromString("e0000000-0000-0000-c011-bd0000000000");
        BankAccount allocationDetailClient11ClearingBankAccount =
                new BankAccount(allocationDetailClient11ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient11DematAccount =
                new DematAccount(allocationDetailClient11DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client - allocation detail client 12
        UUID allocationDetailClient12ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c120-000000000000");
        UUID allocationDetailClient12DematAccountNumber = UUID.fromString("d0000000-0000-0000-c120-000000000000");
        UUID allocationDetailClient12TradingCode = UUID.fromString("e0000000-0000-0000-c012-bd0000000000");
        BankAccount allocationDetailClient12ClearingBankAccount =
                new BankAccount(allocationDetailClient12ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient12DematAccount =
                new DematAccount(allocationDetailClient12DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client - allocation detail client 13
        UUID allocationDetailClient13ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c130-000000000000");
        UUID allocationDetailClient13DematAccountNumber = UUID.fromString("d0000000-0000-0000-c130-000000000000");
        UUID allocationDetailClient13TradingCode = UUID.fromString("e0000000-0000-0000-c013-bd0000000000");
        BankAccount allocationDetailClient13ClearingBankAccount =
                new BankAccount(allocationDetailClient13ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient13DematAccount =
                new DematAccount(allocationDetailClient13DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client - allocation detail client 14
        UUID allocationDetailClient14ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c140-000000000000");
        UUID allocationDetailClient14DematAccountNumber = UUID.fromString("d0000000-0000-0000-c140-000000000000");
        UUID allocationDetailClient14TradingCode = UUID.fromString("e0000000-0000-0000-c014-bd0000000000");
        BankAccount allocationDetailClient14ClearingBankAccount =
                new BankAccount(allocationDetailClient14ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient14DematAccount =
                new DematAccount(allocationDetailClient14DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //buyer institutional Client - allocation detail client 15
        UUID allocationDetailClient15ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c150-000000000000");
        UUID allocationDetailClient15DematAccountNumber = UUID.fromString("d0000000-0000-0000-c150-000000000000");
        UUID allocationDetailClient15TradingCode = UUID.fromString("e0000000-0000-0000-c015-bd0000000000");
        BankAccount allocationDetailClient15ClearingBankAccount =
                new BankAccount(allocationDetailClient15ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient15DematAccount =
                new DematAccount(allocationDetailClient15DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller institutional Client - allocation detail client 16
        UUID allocationDetailClient16ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c160-000000000000");
        UUID allocationDetailClient16DematAccountNumber = UUID.fromString("d0000000-0000-0000-c160-000000000000");
        UUID allocationDetailClient16TradingCode = UUID.fromString("e0000000-0000-0000-c016-bd0000000000");
        BankAccount allocationDetailClient16ClearingBankAccount =
                new BankAccount(allocationDetailClient16ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient16DematAccount =
                new DematAccount(allocationDetailClient16DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller institutional Client - allocation detail client 17
        UUID allocationDetailClient17ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c170-000000000000");
        UUID allocationDetailClient17DematAccountNumber = UUID.fromString("d0000000-0000-0000-c170-000000000000");
        UUID allocationDetailClient17TradingCode = UUID.fromString("e0000000-0000-0000-c017-bd0000000000");
        BankAccount allocationDetailClient17ClearingBankAccount =
                new BankAccount(allocationDetailClient17ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient17DematAccount =
                new DematAccount(allocationDetailClient17DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller institutional Client - allocation detail client 18
        UUID allocationDetailClient18ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c180-000000000000");
        UUID allocationDetailClient18DematAccountNumber = UUID.fromString("d0000000-0000-0000-c180-000000000000");
        UUID allocationDetailClient18TradingCode = UUID.fromString("e0000000-0000-0000-c018-bd0000000000");
        BankAccount allocationDetailClient18ClearingBankAccount =
                new BankAccount(allocationDetailClient18ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient18DematAccount =
                new DematAccount(allocationDetailClient18DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller institutional Client - allocation detail client 19
        UUID allocationDetailClient19ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c190-000000000000");
        UUID allocationDetailClient19DematAccountNumber = UUID.fromString("d0000000-0000-0000-c190-000000000000");
        UUID allocationDetailClient19TradingCode = UUID.fromString("e0000000-0000-0000-c019-bd0000000000");
        BankAccount allocationDetailClient19ClearingBankAccount =
                new BankAccount(allocationDetailClient19ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient19DematAccount =
                new DematAccount(allocationDetailClient19DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller institutional Client - allocation detail client 20
        UUID allocationDetailClient20ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c020-000000000000");
        UUID allocationDetailClient20DematAccountNumber = UUID.fromString("d0000000-0000-0000-c020-000000000000");
        UUID allocationDetailClient20TradingCode = UUID.fromString("e0000000-0000-0000-c020-bd0000000000");
        BankAccount allocationDetailClient20ClearingBankAccount =
                new BankAccount(allocationDetailClient20ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient20DematAccount =
                new DematAccount(allocationDetailClient20DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //seller institutional Client - allocation detail client 21
        UUID allocationDetailClient21ClearingBankAccountNumber = UUID.fromString("cb000000-0000-0000-c021-000000000000");
        UUID allocationDetailClient21DematAccountNumber = UUID.fromString("d0000000-0000-0000-c021-000000000000");
        UUID allocationDetailClient21TradingCode = UUID.fromString("e0000000-0000-0000-c021-bd0000000000");
        BankAccount allocationDetailClient21ClearingBankAccount =
                new BankAccount(allocationDetailClient21ClearingBankAccountNumber, 0);
        DematAccount allocationDetailClient21DematAccount =
                new DematAccount(allocationDetailClient21DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //broker 1
        UUID broker1ClearingBankAccountNumber = UUID.fromString("cbb10000-0000-0000-0000-000000000000");
        UUID broker1DematAccountNumber = UUID.fromString("db100000-0000-0000-0000-000000000000");
        UUID broker1InternalBankAccountNumber = UUID.fromString("bb100000-0000-0000-0000-000000000000");
        UUID broker1InternalDematAccountNumber = UUID.fromString("bd100000-0000-0000-0000-000000000000");
        BankAccount broker1ClearingBankAccount =
                new BankAccount(broker1ClearingBankAccountNumber, 0);
        DematAccount broker1DematAccount =
                new DematAccount(broker1DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //broker 2
        UUID broker2ClearingBankAccountNumber = UUID.fromString("cbb10000-0000-0000-0000-000000000000");
        UUID broker2DematAccountNumber = UUID.fromString("db100000-0000-0000-0000-000000000000");
        UUID broker2InternalBankAccountNumber = UUID.fromString("bb100000-0000-0000-0000-000000000000");
        UUID broker2InternalDematAccountNumber = UUID.fromString("bd100000-0000-0000-0000-000000000000");
        BankAccount broker2ClearingBankAccount =
                new BankAccount(broker2ClearingBankAccountNumber, 0);
        DematAccount broker2DematAccount =
                new DematAccount(broker2DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //custodian 1
        UUID custodian1ClearingBankAccountNumber = UUID.fromString("cbc10000-0000-0000-0000-000000000000");
        UUID custodian1DematAccountNumber = UUID.fromString("dc100000-0000-0000-0000-000000000000");
        UUID custodian1InternalBankAccountNumber = UUID.fromString("cb100000-0000-0000-0000-000000000000");
        UUID custodian1InternalDematAccountNumber = UUID.fromString("cd100000-0000-0000-0000-000000000000");
        BankAccount custodian1ClearingBankAccount =
                new BankAccount(custodian1ClearingBankAccountNumber, 0);
        DematAccount custodian1DematAccount =
                new DematAccount(custodian1DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //custodian 2
        UUID custodian2ClearingBankAccountNumber = UUID.fromString("cbc20000-0000-0000-0000-000000000000");
        UUID custodian2DematAccountNumber = UUID.fromString("dc200000-0000-0000-0000-000000000000");
        UUID custodian2InternalBankAccountNumber = UUID.fromString("cb200000-0000-0000-0000-000000000000");
        UUID custodian2InternalDematAccountNumber = UUID.fromString("cd200000-0000-0000-0000-000000000000");
        BankAccount custodian2ClearingBankAccount =
                new BankAccount(custodian2ClearingBankAccountNumber, 0);
        DematAccount custodian2DematAccount =
                new DematAccount(custodian2DematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 0));
                        }});

        //clearing corporation
        UUID clearingCorpClearingBankAccountNumber = UUID.fromString("cbcc0000-0000-0000-0000-000000000000");
        UUID clearingCorpDematAccountNumber = UUID.fromString("dcc00000-0000-0000-0000-000000000000");
        BankAccount clearingCorpClearingBankAccount =
                new BankAccount(clearingCorpClearingBankAccountNumber, 30000);
        DematAccount clearingCorpDematAccount =
                new DematAccount(clearingCorpDematAccountNumber,
                        new ArrayList<InstrumentQuantityPair>() {{
                            add(new InstrumentQuantityPair("MSFT", 1500));
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

        bankAccountRepository.add(broker1ClearingBankAccount);
        bankAccountRepository.add(broker2ClearingBankAccount);
        bankAccountRepository.add(custodian1ClearingBankAccount);
        bankAccountRepository.add(custodian2ClearingBankAccount);
        bankAccountRepository.add(clearingCorpClearingBankAccount);

        bankAccountRepository.add(buyerInstitutionalClientClearingBankAccount);
        bankAccountRepository.add(sellerInstitutionalClientClearingBankAccount);

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
        bankAccountRepository.add(allocationDetailClient11ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient12ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient13ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient14ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient15ClearingBankAccount);

        bankAccountRepository.add(allocationDetailClient16ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient17ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient18ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient19ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient20ClearingBankAccount);
        bankAccountRepository.add(allocationDetailClient21ClearingBankAccount);
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

        dematAccountRepository.add(broker1DematAccount);
        dematAccountRepository.add(broker2DematAccount);
        dematAccountRepository.add(custodian1DematAccount);
        dematAccountRepository.add(custodian2DematAccount);
        dematAccountRepository.add(clearingCorpDematAccount);

        dematAccountRepository.add(buyerInstitutionalClientDematAccount);
        dematAccountRepository.add(sellerInstitutionalClient2ematAccount);

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
        dematAccountRepository.add(allocationDetailClient11DematAccount);
        dematAccountRepository.add(allocationDetailClient12DematAccount);
        dematAccountRepository.add(allocationDetailClient13DematAccount);
        dematAccountRepository.add(allocationDetailClient14DematAccount);
        dematAccountRepository.add(allocationDetailClient15DematAccount);

        dematAccountRepository.add(allocationDetailClient16DematAccount);
        dematAccountRepository.add(allocationDetailClient17DematAccount);
        dematAccountRepository.add(allocationDetailClient18DematAccount);
        dematAccountRepository.add(allocationDetailClient19DematAccount);
        dematAccountRepository.add(allocationDetailClient20DematAccount);
        dematAccountRepository.add(allocationDetailClient21DematAccount);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //-----------------------------------------Broker1 Initialization----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID broker1Id = UUID.fromString("b1000000-0000-0000-0000-000000000000");
        IBroker broker1 = injector.getInstance(IBroker.class);
        broker1.setBrokerInfo
                (
                        broker1Id,
                        new AccountsInformation(
                                clearingBankId,
                                broker1ClearingBankAccountNumber,
                                depositoryId,
                                broker1DematAccountNumber
                        ),
                        new InternalAccountsInformation(
                                broker1InternalBankAccountNumber,
                                broker1InternalDematAccountNumber
                        ),
                        0,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair
                                    ("MSFT", 0));
                        }}
                );

        IBrokerUnitRepositories broker1UnitRepositories =
                injector.getInstance(IBrokerUnitRepositories.class);

        IEquityInformationRepository equityInformationRepository1 =
                broker1UnitRepositories.getEquityInformationRepository();

        equityInformationRepository1.add(new EquityInformation("MSFT", false));
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //-----------------------------------------Broker2 Initialization----------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID broker2Id = UUID.fromString("b2000000-0000-0000-0000-000000000000");
        IBroker broker2 = injector2.getInstance(IBroker.class);
        broker2.setBrokerInfo
                (
                        broker2Id,
                        new AccountsInformation(
                                clearingBankId,
                                broker2ClearingBankAccountNumber,
                                depositoryId,
                                broker2DematAccountNumber
                        ),
                        new InternalAccountsInformation(
                                broker2InternalBankAccountNumber,
                                broker2InternalDematAccountNumber
                        ),
                        0,
                        new ArrayList<emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair>() {{
                            add(new emSeco.brokerUnit.core.entities.brokerDematAccount.InstrumentQuantityPair
                                    ("MSFT", 0));
                        }}
                );

        IBrokerUnitRepositories broker2UnitRepositories =
                injector2.getInstance(IBrokerUnitRepositories.class);

        IEquityInformationRepository equityInformationRepository2 =
                broker2UnitRepositories.getEquityInformationRepository();

        equityInformationRepository2.add(new EquityInformation("MSFT", false));
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //---------------------------------------Custodian1 Initialization---------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID custodian1Id = UUID.fromString("c1000000-0000-0000-0000-000000000000");
        ICustodian custodian1 = injector.getInstance(ICustodian.class);
        custodian1.setCustodianInfo(
                custodian1Id,
                new emSeco.custodianUnit.core.entities.shared.AccountsInformation(
                        clearingBankId,
                        custodian1ClearingBankAccountNumber,
                        depositoryId,
                        custodian1DematAccountNumber
                ),
                new emSeco.custodianUnit.core.entities.shared.InternalAccountsInformation(
                        custodian1InternalBankAccountNumber,
                        custodian1InternalDematAccountNumber
                ),
                0,
                new ArrayList<emSeco.custodianUnit.core.entities.custodianDematAccount.InstrumentQuantityPair>() {{
                    add(new emSeco.custodianUnit.core.entities.custodianDematAccount.InstrumentQuantityPair
                            ("MSFT", 0));
                }}
        );
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //---------------------------------------Custodian2 Initialization---------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        UUID custodian2Id = UUID.fromString("c2000000-0000-0000-0000-000000000000");
        ICustodian custodian2 = injector2.getInstance(ICustodian.class);
        custodian2.setCustodianInfo(
                custodian2Id,
                new emSeco.custodianUnit.core.entities.shared.AccountsInformation(
                        clearingBankId,
                        custodian2ClearingBankAccountNumber,
                        depositoryId,
                        custodian2DematAccountNumber
                ),
                new emSeco.custodianUnit.core.entities.shared.InternalAccountsInformation(
                        custodian2InternalBankAccountNumber,
                        custodian2InternalDematAccountNumber
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
        clearingCorp.setClearingCorpInfo(new emSeco.clearingCropUnit.core.entities.shared.AccountsInformation(
                clearingBankId,
                clearingCorpClearingBankAccountNumber,
                depositoryId,
                clearingCorpDematAccountNumber
        ));
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //----------------------------------Connectivity mechanisms initialization-------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        IBrokerServiceRegistry broker1ServiceRegistry =
                injector.getInstance(IBrokerServiceRegistry.class);
        broker1ServiceRegistry.initializeRegistry(
                new ArrayList<ICustodian>() {{
                    add(custodian1);
                }},
                new ArrayList<IExchange>() {{
                    add(exchange);
                }},
                new ArrayList<IClearingBank>() {{
                    add(clearingBank);
                }},
                depository);

        IBrokerServiceRegistry broker2ServiceRegistry =
                injector2.getInstance(IBrokerServiceRegistry.class);
        broker2ServiceRegistry.initializeRegistry(
                new ArrayList<ICustodian>() {{
                    add(custodian2);
                }},
                new ArrayList<IExchange>() {{
                    add(exchange);
                }},
                new ArrayList<IClearingBank>() {{
                    add(clearingBank);
                }},
                depository);

        ICustodianServiceRegistry custodian1ServiceRegistry =
                injector.getInstance(ICustodianServiceRegistry.class);
        custodian1ServiceRegistry.initializeRegistry(
                new ArrayList<IBroker>() {{
                    add(broker1);
                }},
                clearingCorp,
                new ArrayList<IClearingBank>() {{
                    add(clearingBank);
                }},
                depository
        );

        ICustodianServiceRegistry custodian2ServiceRegistry =
                injector2.getInstance(ICustodianServiceRegistry.class);
        custodian2ServiceRegistry.initializeRegistry(
                new ArrayList<IBroker>() {{
                    add(broker2);
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
                    add(broker1);
                    add(broker2);
                }}, clearingCorp
        );

        IClearingCorpServiceRegistry clearingCorpServiceRegistry =
                injector.getInstance(IClearingCorpServiceRegistry.class);
        clearingCorpServiceRegistry.initializeRegistry(
                new ArrayList<ICustodian>() {{
                    add(custodian1);
                    add(custodian2);
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
        /*4-Orders are created by the seller and buyer institutional clients and then submitted to a broker. If each
        order passes all checks, it is routed from the broker to an exchange.*/
        InitiateInstitutionalOrderOutputClass initiateInstitutionalOrderOutputClass =
                broker1.initiateInstitutionalOrder_UI(
                        custodian1Id, exchangeId, buyerInstitutionalClientRegisteredCode, SideName.buy,
                        new Term(20, 1500, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);

        InitiateInstitutionalOrderOutputClass initiateInstitutionalOrderOutputClass2 =
                broker2.initiateInstitutionalOrder_UI(
                        custodian2Id, exchangeId, sellerInstitutionalClientRegisteredCode, SideName.sell,
                        new Term(20, 1500, "MSFT"), OrderType.LIM,
                        emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod.clearingBankAccount,
                        emSeco.brokerUnit.core.entities.shared.EquityTransferMethod.depositoryDematAccount);

        /*5-The buyer institutional client deposits the required money for the trades settlement stage with a custodian*/
        BooleanResultMessage depositMoneyResultMessage =
                custodian1.depositMoney_UI(clearingBankId, buyerInstitutionalClientClearingBankAccountNumber,
                        buyerInstitutionalClientRegisteredCode,
                        30000, MoneyTransferMethod.clearingBankAccount);

        /*6-The seller institutional client deposits the required equities for the trades settlement stage with a custodian*/
        BooleanResultMessage depositEquitiesResultMessage =
                custodian2.depositEquities_UI(depositoryId, sellerInstitutionalClientDematAccountNumber,
                        sellerInstitutionalClientRegisteredCode, "MSFT", 1500,
                        EquityTransferMethod.depositoryDematAccount);

        /*7-After processing orders in the exchange, one trade, which is a pair of buy and sell orders, has been created.
        The trade with its quantities is as follows::

            side                 | quantity             side                  | quantity
            --------------------------------            --------------------------------
            institutional buyer  | 1500	                institutional seller  | 1500
        */
        List<BooleanResultMessages> listOfOrderProcessingResultMessages = exchange.processOrders_REC();

        /*8-Next, trades are sent from the exchange to the broker of the buyer and seller sides.*/
        BooleanResultMessages sendTradesResultMessages = exchange.sendTrades_REC();

        /*9-At this stage, the buyer institutional client can see the result of conducting the trade.*/
        List<NoticeOfExecution> noticeOfExecutions =
                broker1.getSettlementResults_UI(initiateInstitutionalOrderOutputClass.getOrderId());

        /*10-At this stage, the seller institutional client can see the result of conducting the trade.*/
        List<NoticeOfExecution> noticeOfExecutions2 =
                broker2.getSettlementResults_UI(initiateInstitutionalOrderOutputClass2.getOrderId());

        /*11-After conducting the trade, the buyer institutional client decides to allocate its trade to fifteen clients.
        Since they have paid the buyer institutional client the costs of buying equities beforehand, they do not need to
        pay any extra money. The initial balances of the fifteen retail buyers' clearing bank and demat accounts are as
        follows:

            side                              |  bank account balance 	  side                              |  demat account balance
            ---------------------------------------------------------     ----------------------------------------------------------
            allocation detail retail buyer 1  |  0	                      allocation detail retail buyer 1  |  0
            allocation detail retail buyer 2  |  0	                      allocation detail retail buyer 2  |  0
            allocation detail retail buyer 3  |  0	                      allocation detail retail buyer 3  |  0
            allocation detail retail buyer 4  |  0	                      allocation detail retail buyer 4  |  0
            allocation detail retail buyer 5  |  0	                      allocation detail retail buyer 5  |  0
            allocation detail retail buyer 6  |  0	                      allocation detail retail buyer 6  |  0
            allocation detail retail buyer 7  |  0	                      allocation detail retail buyer 7  |  0
            allocation detail retail buyer 8  |  0	                      allocation detail retail buyer 8  |  0
            allocation detail retail buyer 9  |  0	                      allocation detail retail buyer 9  |  0
            allocation detail retail buyer 10 |  0	                      allocation detail retail buyer 10 |  0
            allocation detail retail buyer 11 |  0	                      allocation detail retail buyer 11 |  0
            allocation detail retail buyer 12 |  0	                      allocation detail retail buyer 12 |  0
            allocation detail retail buyer 13 |  0	                      allocation detail retail buyer 13 |  0
            allocation detail retail buyer 14 |  0	                      allocation detail retail buyer 14 |  0
            allocation detail retail buyer 15 |  0	                      allocation detail retail buyer 15 |  0
        */

        /*12-The buyer institutional client submits allocations details to both its broker and custodian.*/
        UUID allocationDetailBlockId1 = UUID.fromString("00000000-0000-0000-0000-adb100000000");
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
        UUID allocationDetail11Id = UUID.fromString("00000000-0000-0000-0000-ad0110000000");
        UUID allocationDetail12Id = UUID.fromString("00000000-0000-0000-0000-ad0120000000");
        UUID allocationDetail13Id = UUID.fromString("00000000-0000-0000-0000-ad0130000000");
        UUID allocationDetail14Id = UUID.fromString("00000000-0000-0000-0000-ad0140000000");
        UUID allocationDetail15Id = UUID.fromString("00000000-0000-0000-0000-ad0150000000");

        SubmitAllocationDetailsOutputClass broker1SubmitAllocationDetailsOutputClass =
                broker1.submitAllocationDetails_UI(new ArrayList<SubmitAllocationDetailsInputClass>() {{
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail1Id, allocationDetailBlockId1,
                            allocationDetailClient1TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail2Id, allocationDetailBlockId1,
                            allocationDetailClient2TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail3Id, allocationDetailBlockId1,
                            allocationDetailClient3TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail4Id, allocationDetailBlockId1,
                            allocationDetailClient4TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail5Id, allocationDetailBlockId1,
                            allocationDetailClient5TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail6Id, allocationDetailBlockId1,
                            allocationDetailClient6TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail7Id, allocationDetailBlockId1,
                            allocationDetailClient7TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail8Id, allocationDetailBlockId1,
                            allocationDetailClient8TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail9Id, allocationDetailBlockId1,
                            allocationDetailClient9TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail10Id, allocationDetailBlockId1,
                            allocationDetailClient10TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail11Id, allocationDetailBlockId1,
                            allocationDetailClient11TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail12Id, allocationDetailBlockId1,
                            allocationDetailClient12TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail13Id, allocationDetailBlockId1,
                            allocationDetailClient13TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail14Id, allocationDetailBlockId1,
                            allocationDetailClient14TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                    add(new SubmitAllocationDetailsInputClass(custodian1Id, allocationDetail15Id, allocationDetailBlockId1,
                            allocationDetailClient15TradingCode, buyerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 100));
                }});

        emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsOutputClass
                custodian1SubmitAllocationDetailsOutputClass =
                custodian1.submitAllocationDetails_UI(new ArrayList<emSeco.custodianUnit.core.modules.custodian.models.
                        SubmitAllocationDetailsInputClass>() {{
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail1Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient1ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient1DematAccountNumber, allocationDetailClient1TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail2Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient2ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient2DematAccountNumber, allocationDetailClient2TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail3Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient3ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient3DematAccountNumber, allocationDetailClient3TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail4Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient4ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient4DematAccountNumber, allocationDetailClient4TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail5Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient5ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient5DematAccountNumber, allocationDetailClient5TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail6Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient6ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient6DematAccountNumber, allocationDetailClient6TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail7Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient7ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient7DematAccountNumber, allocationDetailClient7TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail8Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient8ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient8DematAccountNumber, allocationDetailClient8TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail9Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient9ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient9DematAccountNumber, allocationDetailClient9TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail10Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient10ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient10DematAccountNumber, allocationDetailClient10TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail11Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient11ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient11DematAccountNumber, allocationDetailClient11TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail12Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient12ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient12DematAccountNumber, allocationDetailClient12TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail13Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient13ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient13DematAccountNumber, allocationDetailClient13TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail14Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient14ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient14DematAccountNumber, allocationDetailClient14TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker1Id, allocationDetail15Id, allocationDetailBlockId1,
                            clearingBankId, allocationDetailClient15ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient15DematAccountNumber, allocationDetailClient15TradingCode, buyerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass.getOrderId(), "MSFT", 20, 100,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                }});
        /*13-After conducting the trade, the seller institutional client decides to allocate its trade to six clients.
        Since they have transferred the ownership of equities to the seller institutional client beforehand, they do
        not need to do the same in the next settlement stages. The initial balances of the six retail buyers' clearing
        bank and demat accounts are as follows:

            side                               |  bank account balance 	  side                               |  demat account balance
            ----------------------------------------------------------    -----------------------------------------------------------
            allocation detail retail seller 1  |  0	                      allocation detail retail seller 1  |  0
            allocation detail retail seller 2  |  0	                      allocation detail retail seller 2  |  0
            allocation detail retail seller 3  |  0	                      allocation detail retail seller 3  |  0
            allocation detail retail seller 4  |  0	                      allocation detail retail seller 4  |  0
            allocation detail retail seller 5  |  0	                      allocation detail retail seller 5  |  0
            allocation detail retail seller 6  |  0	                      allocation detail retail seller 6  |  0
        */

        /*14-The seller institutional client submits allocations details to both its broker and custodian.*/
        UUID allocationDetailBlockId2 = UUID.fromString("00000000-0000-0000-0000-adb200000000");
        UUID allocationDetail16Id = UUID.fromString("00000000-0000-0000-0000-ad0160000000");
        UUID allocationDetail17Id = UUID.fromString("00000000-0000-0000-0000-ad0170000000");
        UUID allocationDetail18Id = UUID.fromString("00000000-0000-0000-0000-ad0180000000");
        UUID allocationDetail19Id = UUID.fromString("00000000-0000-0000-0000-ad0190000000");
        UUID allocationDetail20Id = UUID.fromString("00000000-0000-0000-0000-ad0200000000");
        UUID allocationDetail21Id = UUID.fromString("00000000-0000-0000-0000-ad0210000000");

        SubmitAllocationDetailsOutputClass broker2SubmitAllocationDetailsOutputClass =
                broker2.submitAllocationDetails_UI(new ArrayList<SubmitAllocationDetailsInputClass>() {{
                    add(new SubmitAllocationDetailsInputClass(custodian2Id, allocationDetail16Id, allocationDetailBlockId2,
                            allocationDetailClient16TradingCode, sellerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass2.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 250));
                    add(new SubmitAllocationDetailsInputClass(custodian2Id, allocationDetail17Id, allocationDetailBlockId2,
                            allocationDetailClient17TradingCode, sellerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass2.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 250));
                    add(new SubmitAllocationDetailsInputClass(custodian2Id, allocationDetail18Id, allocationDetailBlockId2,
                            allocationDetailClient18TradingCode, sellerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass2.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 250));
                    add(new SubmitAllocationDetailsInputClass(custodian2Id, allocationDetail19Id, allocationDetailBlockId2,
                            allocationDetailClient19TradingCode, sellerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass2.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 250));
                    add(new SubmitAllocationDetailsInputClass(custodian2Id, allocationDetail20Id, allocationDetailBlockId2,
                            allocationDetailClient20TradingCode, sellerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass2.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 250));
                    add(new SubmitAllocationDetailsInputClass(custodian2Id, allocationDetail21Id, allocationDetailBlockId2,
                            allocationDetailClient21TradingCode, sellerInstitutionalClientRegisteredCode, initiateInstitutionalOrderOutputClass2.getOrderId(),
                            InitiatorType.retail, "MSFT", 20, 250));
                }});

        emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsOutputClass
                custodian2SubmitAllocationDetailsOutputClass =
                custodian2.submitAllocationDetails_UI(new ArrayList<emSeco.custodianUnit.core.modules.custodian.models.
                        SubmitAllocationDetailsInputClass>() {{
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker2Id, allocationDetail16Id, allocationDetailBlockId2,
                            clearingBankId, allocationDetailClient16ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient16DematAccountNumber, allocationDetailClient16TradingCode, sellerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass2.getOrderId(), "MSFT", 20, 250,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker2Id, allocationDetail17Id, allocationDetailBlockId2,
                            clearingBankId, allocationDetailClient17ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient17DematAccountNumber, allocationDetailClient17TradingCode, sellerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass2.getOrderId(), "MSFT", 20, 250,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker2Id, allocationDetail18Id, allocationDetailBlockId2,
                            clearingBankId, allocationDetailClient18ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient18DematAccountNumber, allocationDetailClient18TradingCode, sellerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass2.getOrderId(), "MSFT", 20, 250,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker2Id, allocationDetail19Id, allocationDetailBlockId2,
                            clearingBankId, allocationDetailClient19ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient19DematAccountNumber, allocationDetailClient19TradingCode, sellerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass2.getOrderId(), "MSFT", 20, 250,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker2Id, allocationDetail20Id, allocationDetailBlockId2,
                            clearingBankId, allocationDetailClient20ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient20DematAccountNumber, allocationDetailClient20TradingCode, sellerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass2.getOrderId(), "MSFT", 20, 250,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                    add(new emSeco.custodianUnit.core.modules.custodian.models.
                            SubmitAllocationDetailsInputClass(broker2Id, allocationDetail21Id, allocationDetailBlockId2,
                            clearingBankId, allocationDetailClient21ClearingBankAccountNumber, depositoryId,
                            allocationDetailClient21DematAccountNumber, allocationDetailClient21TradingCode, sellerInstitutionalClientRegisteredCode,
                            initiateInstitutionalOrderOutputClass2.getOrderId(), "MSFT", 20, 250,
                            MoneyTransferMethod.clearingBankAccount, EquityTransferMethod.depositoryDematAccount));
                }});

        /*15-The broker of the buyer institutional client generates some contracts based on the allocation details and
        send them to the custodian of the buyer institutional client.

        side                              |  quantity              side   | quantity
        ---------------------------------------------              -----------------------------
        allocation detail retail buyer 1  |  100	               institutional seller   | 100
        allocation detail retail buyer 2  |  100	               institutional seller   | 100
        allocation detail retail buyer 3  |  100	               institutional seller   | 100
        allocation detail retail buyer 4  |  100	               institutional seller   | 100
        allocation detail retail buyer 5  |  100	               institutional seller   | 100
        allocation detail retail buyer 6  |  100	               institutional seller   | 100
        allocation detail retail buyer 7  |  100	               institutional seller   | 100
        allocation detail retail buyer 8  |  100	               institutional seller   | 100
        allocation detail retail buyer 9  |  100	               institutional seller   | 100
        allocation detail retail buyer 10 |  100	               institutional seller   | 100
        allocation detail retail buyer 11 |  100	               institutional seller   | 100
        allocation detail retail buyer 12 |  100	               institutional seller   | 100
        allocation detail retail buyer 13 |  100	               institutional seller   | 100
        allocation detail retail buyer 14 |  100	               institutional seller   | 100
        allocation detail retail buyer 15 |  100	               institutional seller   | 100
         */
        BooleanResultMessages broker1GenerateContractsResultMessages = broker1.generateContracts_REC();

        /*16-The broker of the seller institutional client generates some contracts based on the allocation details and
        send them to the custodian of the seller institutional client.

        side                 |  quantity              side                           |  quantity
        ---------------------------------              -----------------------------------------
        institutional buyer  |  250	               allocation detail retail seller 1 |  250
        institutional buyer  |  250	               allocation detail retail seller 2 |  250
        institutional buyer  |  250	               allocation detail retail seller 3 |  250
        institutional buyer  |  250	               allocation detail retail seller 4 |  250
        institutional buyer  |  250	               allocation detail retail seller 5 |  250
        institutional buyer  |  250	               allocation detail retail seller 6 |  250
         */
        BooleanResultMessages broker2GenerateContractsResultMessages = broker2.generateContracts_REC();

        /*17-The buy-side custodian affirms the correct contracts and sends the analysis result to the buy-side broker.*/
        BooleanResultMessages custodian1AffirmContractsResultMessages = custodian1.affirmContracts_REC();

        /*18-The sell-side custodian affirms the correct contracts and sends the analysis result to the sell-side broker.*/
        BooleanResultMessages custodian2AffirmContractsResultMessages = custodian2.affirmContracts_REC();

        /*19-The buy-side broker converts the affirmed contracts to trades and send them to the buy-side custodian.*/
        BooleanResultMessages broker1SubmitTradesResultMessages = broker1.submitTrades_REC();

        /*20-The sell-side broker converts the affirmed contracts to trades and send them to the sell-side custodian.*/
        BooleanResultMessages broker2SubmitTradesResultMessages = broker2.submitTrades_REC();

        /*21-Since, from this point onward, the buy-side custodian is partially responsible for settling trades, the
        buy-side custodian submits the trades to the clearing corporation for settlement.*/
        List<BooleanResultMessages> custodian1SubmitInstitutionalTradesResultMessages =
                custodian1.submitInstitutionalTrades_REC();

        /*22-Since, from this point onward, the sell-side custodian is partially responsible for settling trades, the
        sell-side custodian submits the trades to the clearing corporation for settlement.*/
        List<BooleanResultMessages> custodian2SubmitInstitutionalTradesResultMessages =
                custodian2.submitInstitutionalTrades_REC();

        /*23-The clearing corporation matches the received trades from both buyer and seller institutional clients.
        After successfully matching trades, the clearing corporation settles the matched trades and sends the settlement
        result to both the buy-side and the sell-side custodians.

        side                              |  quantity          side                               |  quantity
        -------------------------------------------            --------------------------------------------
        allocation detail retail buyer 1  |  100	           allocation detail retail seller 1  |  100
        allocation detail retail buyer 2  |  100	           allocation detail retail seller 1  |  100
        allocation detail retail buyer 3  |  50	               allocation detail retail seller 1  |  50
        allocation detail retail buyer 3  |  50	               allocation detail retail seller 2  |  50
        allocation detail retail buyer 4  |  100	           allocation detail retail seller 2  |  100
        allocation detail retail buyer 5  |  100	           allocation detail retail seller 2  |  100
        allocation detail retail buyer 6  |  100	           allocation detail retail seller 3  |  100
        allocation detail retail buyer 7  |  100	           allocation detail retail seller 3  |  100
        allocation detail retail buyer 8  |  50	               allocation detail retail seller 3  |  50
        allocation detail retail buyer 8  |  50	               allocation detail retail seller 4  |  50
        allocation detail retail buyer 9  |  100	           allocation detail retail seller 4  |  100
        allocation detail retail buyer 10 |  100	           allocation detail retail seller 4  |  100
        allocation detail retail buyer 11 |  100	           allocation detail retail seller 5  |  100
        allocation detail retail buyer 12 |  100	           allocation detail retail seller 5  |  100
        allocation detail retail buyer 13 |  50	               allocation detail retail seller 5  |  50
        allocation detail retail buyer 13 |  50	               allocation detail retail seller 6  |  50
        allocation detail retail buyer 14 |  100	           allocation detail retail seller 6  |  100
        allocation detail retail buyer 15 |  100	           allocation detail retail seller 6  |  100
         */
        SettleTradesOutputClass settleTradesOutputClass = clearingCorp.settleTrades_REC();


        /*24-The monetary and equity-related obligations of the buy-side and sell-side custodians are discharged against
        their clients. After doing so, the buyers have their equities, and sellers have their money. Both sides' balances
        are as follows:

            side                              |  bank account balance          side   | demat account balance
            ---------------------------------------------------------          ----------------------------------------
            allocation detail retail buyer 1  |  0	                           allocation detail retail buyer 1  |  100
            allocation detail retail buyer 2  |  0	                           allocation detail retail buyer 2  |  100
            allocation detail retail buyer 3  |  0	                           allocation detail retail buyer 3  |  100
            allocation detail retail buyer 3  |  0	                           allocation detail retail buyer 3  |  100
            allocation detail retail buyer 4  |  0	                           allocation detail retail buyer 4  |  100
            allocation detail retail buyer 5  |  0	                           allocation detail retail buyer 5  |  100
            allocation detail retail buyer 6  |  0	                           allocation detail retail buyer 6  |  100
            allocation detail retail buyer 7  |  0	                           allocation detail retail buyer 7  |  100
            allocation detail retail buyer 8  |  0	                           allocation detail retail buyer 8  |  100
            allocation detail retail buyer 8  |  0	                           allocation detail retail buyer 8  |  100
            allocation detail retail buyer 9  |  0	                           allocation detail retail buyer 9  |  100
            allocation detail retail buyer 10 |  0	                           allocation detail retail buyer 10 |  100
            allocation detail retail buyer 11 |  0	                           allocation detail retail buyer 11 |  100
            allocation detail retail buyer 12 |  0	                           allocation detail retail buyer 12 |  100
            allocation detail retail buyer 13 |  0	                           allocation detail retail buyer 13 |  100
            allocation detail retail buyer 14 |  0	                           allocation detail retail buyer 14 |  100
            allocation detail retail buyer 15 |  0	                           allocation detail retail buyer 15 |  100
            allocation detail retail seller 1 |  2000	                       allocation detail retail seller 1 |  0
            allocation detail retail seller 1 |  2000	                       allocation detail retail seller 1 |  0
            allocation detail retail seller 1 |  1000	                       allocation detail retail seller 1 |  0
            allocation detail retail seller 2 |  1000	                       allocation detail retail seller 2 |  0
            allocation detail retail seller 2 |  2000	                       allocation detail retail seller 2 |  0
            allocation detail retail seller 2 |  2000	                       allocation detail retail seller 2 |  0
            allocation detail retail seller 3 |  2000	                       allocation detail retail seller 3 |  0
            allocation detail retail seller 3 |  2000	                       allocation detail retail seller 3 |  0
            allocation detail retail seller 3 |  1000	                       allocation detail retail seller 3 |  0
            allocation detail retail seller 4 |  1000	                       allocation detail retail seller 4 |  0
            allocation detail retail seller 4 |  2000	                       allocation detail retail seller 4 |  0
            allocation detail retail seller 4 |  2000	                       allocation detail retail seller 4 |  0
            allocation detail retail seller 5 |  2000	                       allocation detail retail seller 5 |  0
            allocation detail retail seller 5 |  2000	                       allocation detail retail seller 5 |  0
            allocation detail retail seller 5 |  1000	                       allocation detail retail seller 5 |  0
            allocation detail retail seller 6 |  1000	                       allocation detail retail seller 6 |  0
            allocation detail retail seller 6 |  2000	                       allocation detail retail seller 6 |  0
            allocation detail retail seller 6 |  2000	                       allocation detail retail seller 6 |  0
        */

        List<BooleanResultMessage> custodian1DischargeObligationResultMessages =
                custodian1.dischargeObligationsAgainstClients_REC();
        List<BooleanResultMessage> custodian2DischargeObligationResultMessages =
                custodian2.dischargeObligationsAgainstClients_REC();
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//

        //---------------------------------------------Assertions------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        assertEquals(0, buyerInstitutionalClientClearingBankAccount.getBalance());
        double institutionalClient1DematAccountQuantity =
                buyerInstitutionalClientDematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, institutionalClient1DematAccountQuantity);

        assertEquals(0, allocationDetailClient1ClearingBankAccount.getBalance());
        double allocationDetailClient1DematAccountQuantity =
                allocationDetailClient1DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient1DematAccountQuantity);

        assertEquals(0, allocationDetailClient2ClearingBankAccount.getBalance());
        double allocationDetailClient2DematAccountQuantity =
                allocationDetailClient2DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient2DematAccountQuantity);

        assertEquals(0, allocationDetailClient3ClearingBankAccount.getBalance());
        double allocationDetailClient3DematAccountQuantity =
                allocationDetailClient3DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient3DematAccountQuantity);

        assertEquals(0, allocationDetailClient4ClearingBankAccount.getBalance());
        double allocationDetailClient4DematAccountQuantity =
                allocationDetailClient4DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient4DematAccountQuantity);

        assertEquals(0, allocationDetailClient5ClearingBankAccount.getBalance());
        double allocationDetailClient5DematAccountQuantity =
                allocationDetailClient5DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient5DematAccountQuantity);

        assertEquals(0, allocationDetailClient6ClearingBankAccount.getBalance());
        double allocationDetailClient6DematAccountQuantity =
                allocationDetailClient6DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient6DematAccountQuantity);

        assertEquals(0, allocationDetailClient7ClearingBankAccount.getBalance());
        double allocationDetailClient7DematAccountQuantity =
                allocationDetailClient7DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient7DematAccountQuantity);

        assertEquals(0, allocationDetailClient8ClearingBankAccount.getBalance());
        double allocationDetailClient8DematAccountQuantity =
                allocationDetailClient8DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient8DematAccountQuantity);

        assertEquals(0, allocationDetailClient9ClearingBankAccount.getBalance());
        double allocationDetailClient9DematAccountQuantity =
                allocationDetailClient9DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient9DematAccountQuantity);

        assertEquals(0, allocationDetailClient10ClearingBankAccount.getBalance());
        double allocationDetailClient10DematAccountQuantity =
                allocationDetailClient10DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient10DematAccountQuantity);

        assertEquals(0, allocationDetailClient11ClearingBankAccount.getBalance());
        double allocationDetailClient11DematAccountQuantity =
                allocationDetailClient11DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient11DematAccountQuantity);

        assertEquals(0, allocationDetailClient12ClearingBankAccount.getBalance());
        double allocationDetailClient12DematAccountQuantity =
                allocationDetailClient12DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient12DematAccountQuantity);

        assertEquals(0, allocationDetailClient13ClearingBankAccount.getBalance());
        double allocationDetailClient13DematAccountQuantity =
                allocationDetailClient13DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient13DematAccountQuantity);

        assertEquals(0, allocationDetailClient14ClearingBankAccount.getBalance());
        double allocationDetailClient14DematAccountQuantity =
                allocationDetailClient14DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient14DematAccountQuantity);

        assertEquals(0, allocationDetailClient15ClearingBankAccount.getBalance());
        double allocationDetailClient15DematAccountQuantity =
                allocationDetailClient15DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(100, allocationDetailClient15DematAccountQuantity);


        assertEquals(0, sellerInstitutionalClientClearingBankAccount.getBalance());
        double institutionalClient2DematAccountQuantity =
                sellerInstitutionalClient2ematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, institutionalClient2DematAccountQuantity);

        assertEquals(5000, allocationDetailClient16ClearingBankAccount.getBalance());
        double allocationDetailClient16DematAccountQuantity =
                allocationDetailClient16DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, allocationDetailClient16DematAccountQuantity);

        assertEquals(5000, allocationDetailClient17ClearingBankAccount.getBalance());
        double allocationDetailClient17DematAccountQuantity =
                allocationDetailClient17DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, allocationDetailClient17DematAccountQuantity);

        assertEquals(5000, allocationDetailClient18ClearingBankAccount.getBalance());
        double allocationDetailClient18DematAccountQuantity =
                allocationDetailClient18DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, allocationDetailClient18DematAccountQuantity);

        assertEquals(5000, allocationDetailClient19ClearingBankAccount.getBalance());
        double allocationDetailClient19DematAccountQuantity =
                allocationDetailClient19DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, allocationDetailClient19DematAccountQuantity);

        assertEquals(5000, allocationDetailClient20ClearingBankAccount.getBalance());
        double allocationDetailClient20DematAccountQuantity =
                allocationDetailClient20DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, allocationDetailClient20DematAccountQuantity);

        assertEquals(5000, allocationDetailClient21ClearingBankAccount.getBalance());
        double allocationDetailClient21DematAccountQuantity =
                allocationDetailClient21DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, allocationDetailClient21DematAccountQuantity);


        assertEquals(0, broker1ClearingBankAccount.getBalance());
        double broker1DematAccountQuantity =
                broker1DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, broker1DematAccountQuantity);

        assertEquals(0, broker2ClearingBankAccount.getBalance());
        double broker2DematAccountQuantity =
                broker2DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, broker2DematAccountQuantity);

        assertEquals(0, custodian1ClearingBankAccount.getBalance());
        double custodian1DematAccountQuantity =
                custodian1DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, custodian1DematAccountQuantity);

        assertEquals(0, custodian2ClearingBankAccount.getBalance());
        double custodian2DematAccountQuantity =
                custodian2DematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(0, custodian2DematAccountQuantity);

        assertEquals(30000, clearingCorpClearingBankAccount.getBalance());
        double clearingCorpDematAccountQuantity =
                clearingCorpDematAccount.getInstrumentQuantityPairs()
                        .stream().filter(instrumentQuantityPair -> instrumentQuantityPair.
                        getInstrumentName().equals("MSFT")).findFirst().orElse(null).getQuantity();
        assertEquals(1500, clearingCorpDematAccountQuantity);
        //-------------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------------//
        //} catch (Throwable ex) {
        //    System.err.println("Uncaught exception - " + ex.getMessage());
        //    ex.printStackTrace(System.err);
        //}
    }
}

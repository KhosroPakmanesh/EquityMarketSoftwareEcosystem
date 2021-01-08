package emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.clearingCorpUnitInfo.ClearingCorpUnitInfo;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.interfaces.IMoneyAndEquityTransferUnit;
import emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.models.NovateMoneyAndSharesOutputClass;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.IClearingCorpUnitRepositories;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.IClearingCorUnitpApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;
import emSeco.shared.exceptions.DataPersistenceMalfunctionException;
import emSeco.shared.services.dateTimeService.interfaces.IDateTimeService;

import java.util.ArrayList;
import java.util.List;

public class MoneyAndEquityTransferUnit implements IMoneyAndEquityTransferUnit {
    private final IClearingCorUnitpApiGateways clearingCorpApiGateways;
    private final IClearingCorpUnitRepositories clearingCorpUnitRepositories;
    private final IDateTimeService dateTimeService;

    @Inject
    public MoneyAndEquityTransferUnit(IClearingCorUnitpApiGateways clearingCorpApiGateways,
                                      IClearingCorpUnitRepositories clearingCorpUnitRepositories,
                                      IDateTimeService dateTimeService) {
        this.clearingCorpApiGateways = clearingCorpApiGateways;
        this.clearingCorpUnitRepositories = clearingCorpUnitRepositories;
        this.dateTimeService = dateTimeService;
    }

    public NovateMoneyAndSharesOutputClass NovateMoneyAndShares(List<Trade> trades) {
        ClearingCorpUnitInfo clearingCorpUnitInfo =
                clearingCorpUnitRepositories.getClearingCorpUnitInfoRepository().get();

        if (clearingCorpUnitInfo == null) {
            throw new DataPersistenceMalfunctionException
                    ("Clearing corporation's data persistence mechanism has not stored the data!");
        }

        List<SettlementResult> settlementResults = new ArrayList<>();
        BooleanResultMessage transferResultMessage;
        for (Trade trade : trades) {
            transferResultMessage = clearingCorpApiGateways.getClearingCorpToClearingBankUnitApiGateway().
                    debit(trade.getBuySide().getAccountsInformation().getClearingBankId(),
                            trade.getBuySide().getAccountsInformation().getClearingBankAccountNumber(),
                            trade.getBuySide().getTerm().getTotalPrice());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            transferResultMessage = clearingCorpApiGateways.getClearingCorpToClearingBankUnitApiGateway().
                    credit(clearingCorpUnitInfo.getAccountsInformation().getClearingBankId(),
                            clearingCorpUnitInfo.getAccountsInformation().getClearingBankAccountNumber(),
                            trade.getBuySide().getTerm().getTotalPrice());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            transferResultMessage = clearingCorpApiGateways.getClearingCorpToClearingBankUnitApiGateway().
                    debit(clearingCorpUnitInfo.getAccountsInformation().getClearingBankId(),
                            clearingCorpUnitInfo.getAccountsInformation().getClearingBankAccountNumber(),
                            trade.getBuySide().getTerm().getTotalPrice());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            transferResultMessage = clearingCorpApiGateways.getClearingCorpToClearingBankUnitApiGateway().
                    credit(trade.getSellSide().getAccountsInformation().getClearingBankId(),
                            trade.getSellSide().getAccountsInformation().getClearingBankAccountNumber(),
                            trade.getSellSide().getTerm().getTotalPrice());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            transferResultMessage = clearingCorpApiGateways.getClearingCorpToDepositoryUnitApiGateway().
                    debit(trade.getSellSide().getAccountsInformation().getDepositoryId(),
                            trade.getSellSide().getAccountsInformation().getDematAccountNumber(),
                            trade.getSellSide().getTerm().getInstrumentName(),
                            trade.getSellSide().getTerm().getQuantity());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            transferResultMessage = clearingCorpApiGateways.getClearingCorpToDepositoryUnitApiGateway().
                    credit(clearingCorpUnitInfo.getAccountsInformation().getDepositoryId(),
                            clearingCorpUnitInfo.getAccountsInformation().getDematAccountNumber(),
                            trade.getSellSide().getTerm().getInstrumentName(),
                            trade.getSellSide().getTerm().getQuantity());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            transferResultMessage = clearingCorpApiGateways.getClearingCorpToDepositoryUnitApiGateway().
                    debit(clearingCorpUnitInfo.getAccountsInformation().getDepositoryId(),
                            clearingCorpUnitInfo.getAccountsInformation().getDematAccountNumber(),
                            trade.getBuySide().getTerm().getInstrumentName(),
                            trade.getBuySide().getTerm().getQuantity());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            transferResultMessage = clearingCorpApiGateways.getClearingCorpToDepositoryUnitApiGateway().
                    credit(trade.getBuySide().getAccountsInformation().getDepositoryId(),
                            trade.getBuySide().getAccountsInformation().getDematAccountNumber(),
                            trade.getBuySide().getTerm().getInstrumentName(),
                            trade.getBuySide().getTerm().getQuantity());
            if (!transferResultMessage.getOperationResult()) {
                return new NovateMoneyAndSharesOutputClass
                        (transferResultMessage, new ArrayList<>());
            }

            settlementResults.add(new SettlementResult(trade, dateTimeService.getDateTime()));
        }

        return new NovateMoneyAndSharesOutputClass
                (new BooleanResultMessage(true, OperationMessage.emptyOperationMessage()), settlementResults);
    }
}

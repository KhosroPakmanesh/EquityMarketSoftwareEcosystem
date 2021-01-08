package emSeco.clearingCropUnit.core.modules.clearingCorp.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.clearingCorpUnitInfo.ClearingCorpUnitInfo;
import emSeco.clearingCropUnit.core.entities.shared.AccountsInformation;
import emSeco.clearingCropUnit.core.entities.trade.*;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.clearingCropUnit.core.modules.tradeFactory.interfaces.ITradeFactory;
import emSeco.clearingCropUnit.core.modules.tradeFactory.models.ConstructTradeOutputClass;
import emSeco.clearingCropUnit.core.modules.tradeSettler.interfaces.ITradeSettler;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTradesOutputClass;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.IClearingCorpUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.*;

import static emSeco.shared.architecturalConstructs.BooleanResultMessages.aggregateListOfResultMessage;

public class ClearingCorp implements IClearingCorp {
    private final IClearingCorpUnitRepositories clearingCorpUnitRepositories;
    private final ITradeFactory tradeFactory;
    private final ITradeSettler tradeSettler;

    @Inject
    public ClearingCorp(IClearingCorpUnitRepositories clearingCorpUnitRepositories,
                        ITradeFactory tradeFactory,
                        ITradeSettler tradeSettler) {
        this.clearingCorpUnitRepositories = clearingCorpUnitRepositories;
        this.tradeFactory = tradeFactory;
        this.tradeSettler = tradeSettler;
    }

    @Override
    public void setClearingCorpInfo(AccountsInformation accountsInformation) {
        clearingCorpUnitRepositories.getClearingCorpUnitInfoRepository().
                add(new ClearingCorpUnitInfo(accountsInformation));
    }

    @Override
    public List<BooleanResultMessages> submitRetailTrades_API(List<Trade> trades) {
        List<BooleanResultMessages> invalidSettlementResultMessages = new ArrayList<>();
        List<Trade> validTrades = new ArrayList<>();

        for (Trade trade : trades) {
            ConstructTradeOutputClass constructSettlementResultMessages =
                    tradeFactory.constructTwoSideRetailTrade(trade);

            if (constructSettlementResultMessages.getTrade() != null) {
                validTrades.add(constructSettlementResultMessages.getTrade());
            } else {
                invalidSettlementResultMessages.add(aggregateListOfResultMessage(
                        constructSettlementResultMessages.getConstructSettlementResultMessages()));
            }

        }

        clearingCorpUnitRepositories.getTradeRepository().add(validTrades);
        return invalidSettlementResultMessages;
    }

    @Override
    public List<BooleanResultMessages> submitInstitutionalTrades_API(List<Trade> trades) {
        List<BooleanResultMessages> invalidSettlementResultMessages = new ArrayList<>();
        List<Trade> validTrades = new ArrayList<>();

        for (Trade trade : trades) {
            ConstructTradeOutputClass constructSettlementResultMessages =
                    tradeFactory.constructInstitutionalTrade(trade);

            if (constructSettlementResultMessages.getTrade() != null) {
                validTrades.add(constructSettlementResultMessages.getTrade());
            } else {
                invalidSettlementResultMessages.add(aggregateListOfResultMessage(
                        constructSettlementResultMessages.getConstructSettlementResultMessages()));
            }
        }

        clearingCorpUnitRepositories.getTradeRepository().add(validTrades);
        return invalidSettlementResultMessages;
    }

    @Override
    public SettleTradesOutputClass settleTrades_REC() {
        List<Trade> trades = clearingCorpUnitRepositories.getTradeRepository().get();
        return tradeSettler.settleTrades(trades);
    }
}

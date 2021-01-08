package emSeco.depositoryUnit.core.entities.dematAccount;

import java.util.List;
import java.util.UUID;

public class DematAccount {
    private final UUID brokerBankAccountNumber;
    private final List<InstrumentQuantityPair> instrumentQuantityPairs;

    public UUID getDematAccountNumber() {
        return brokerBankAccountNumber;
    }

    public DematAccount(UUID brokerBankAccountNumber,
                        List<InstrumentQuantityPair> instrumentQuantityPairs) {
        this.brokerBankAccountNumber = brokerBankAccountNumber;
        this.instrumentQuantityPairs=instrumentQuantityPairs;
    }

    public void credit(String instrumentName,int creditAmount)
    {
        InstrumentQuantityPair instrumentQuantityPair=this.instrumentQuantityPairs.stream().
                filter(iqp -> iqp.getInstrumentName()
                        .equals(instrumentName)).findAny().orElse(null);

        if (instrumentQuantityPair == null){
            this.instrumentQuantityPairs.add(new InstrumentQuantityPair(instrumentName,creditAmount));
        }
        else {
            instrumentQuantityPair.setQuantity(instrumentQuantityPair.getQuantity()+creditAmount);
        }
    }

    public void debit(String instrumentName,int creditAmount)
    {
        InstrumentQuantityPair instrumentQuantityPair=this.instrumentQuantityPairs.stream().
                filter(iqp -> iqp.getInstrumentName()
                        .equals(instrumentName)).findAny().orElse(null);

        if (instrumentQuantityPair != null) {
            instrumentQuantityPair.setQuantity(instrumentQuantityPair.getQuantity() - creditAmount);
        }
    }

    public Boolean hasEnoughBalance(String instrumentName, int balance)
    {
        InstrumentQuantityPair instrumentQuantityPair= this.instrumentQuantityPairs.stream().
                filter(iqp -> iqp.getInstrumentName()
                        .equals(instrumentName)).findAny().orElse(null);

        if (instrumentQuantityPair != null) {
            return instrumentQuantityPair.getQuantity()>= balance;
        }

        return false;
    }

    public List<InstrumentQuantityPair> getInstrumentQuantityPairs() {
        return instrumentQuantityPairs;
    }
}

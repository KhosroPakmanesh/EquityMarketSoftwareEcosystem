package emSeco.custodianUnit.core.entities.custodianDematAccount;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CustodianDematAccount {
    private final UUID custodianBankAccountNumber;
    private final List<InstrumentQuantityPair> instrumentQuantityPairs;

    public UUID getCustodianBankAccountNumber() {
        return custodianBankAccountNumber;
    }

    public CustodianDematAccount(UUID custodianBankAccountNumber,
                                 List<InstrumentQuantityPair> instrumentQuantityPairs) {
        this.custodianBankAccountNumber = custodianBankAccountNumber;
        this.instrumentQuantityPairs=instrumentQuantityPairs;
    }

    public void credit(String instrumentName, int creditAmount)
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

    public void debit(String instrumentName, int creditAmount)
    {
        InstrumentQuantityPair instrumentQuantityPair=this.instrumentQuantityPairs.stream().
                filter(iqp -> iqp.getInstrumentName()
                        .equals(instrumentName)).findAny().orElse(null);

        if (instrumentQuantityPair != null) {
            instrumentQuantityPair.setQuantity(instrumentQuantityPair.getQuantity() - creditAmount);
        }
    }

    public Boolean HasEnoughBalance(String instrumentName,int balance)
    {
        InstrumentQuantityPair instrumentQuantityPair=this.instrumentQuantityPairs.stream().
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

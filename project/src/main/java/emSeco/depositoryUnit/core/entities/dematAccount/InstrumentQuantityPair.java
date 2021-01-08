package emSeco.depositoryUnit.core.entities.dematAccount;

public class InstrumentQuantityPair {
    private final String instrumentName;
    private int quantity;

    public String getInstrumentName() {
        return instrumentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public InstrumentQuantityPair(String instrumentName, int quantity) {
        this.instrumentName = instrumentName;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }
}

package emSeco.clearingCropUnit.core.entities.shared;

public class Term {
    private final double price;
    private int quantity;
    private String instrumentName;

    public Term(double price, int quantity, String instrumentName) {
        this.price = price;
        this.quantity = quantity;
        this.instrumentName = instrumentName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public double getTotalPrice() {
        return price*quantity;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName=instrumentName;
    }

    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }
}

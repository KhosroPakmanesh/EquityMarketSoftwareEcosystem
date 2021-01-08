package emSeco.brokerUnit.core.entities.equityInformation;

public class EquityInformation {
    private final String instrumentName;
    final boolean isSinEquity;

    public EquityInformation(String instrumentName, boolean isSinEquity) {
        this.instrumentName = instrumentName;
        this.isSinEquity = isSinEquity;
    }

    public String getInstrumentName() {
        return instrumentName;
    }
    public boolean isSinEquity() {
        return isSinEquity;
    }
}

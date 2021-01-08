package emSeco.clearingCropUnit.core.modules.tradeSettler.models;

import java.util.ArrayList;
import java.util.List;

public class SettleTradesOutputClass {
    private final List<SettleTwoSideInstitutionalTradesOutputClass> settleTwoSideInstitutionalTradesOutputClasses;
    private final List<SettleOneSideInstitutionalTradesOutputClass> settleOneSideInstitutionalTradesOutputClasses;
    private final List<SettleTwoSideRetailTradesOutputClass> settleTwoSideRetailTradesOutputClasses;

    public SettleTradesOutputClass() {
        this.settleTwoSideInstitutionalTradesOutputClasses = new ArrayList<>();
        this.settleOneSideInstitutionalTradesOutputClasses = new ArrayList<>();
        this.settleTwoSideRetailTradesOutputClasses = new ArrayList<>();
    }

    public List<SettleTwoSideInstitutionalTradesOutputClass> getSettleTwoSideInstitutionalTradesOutputClasses() {
        return settleTwoSideInstitutionalTradesOutputClasses;
    }

    public List<SettleOneSideInstitutionalTradesOutputClass> getSettleOneSideInstitutionalTradesOutputClasses() {
        return settleOneSideInstitutionalTradesOutputClasses;
    }

    public List<SettleTwoSideRetailTradesOutputClass> getSettleTwoSideRetailTradesOutputClasses() {
        return settleTwoSideRetailTradesOutputClasses;
    }
}

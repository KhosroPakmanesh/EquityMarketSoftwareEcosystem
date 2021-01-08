package emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces;

import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;

import java.util.ArrayList;
import java.util.List;

public interface ICustodianServiceRegistry {
    void initializeRegistry(
            ArrayList<IBroker> iBrokers,
            IClearingCorp clearingCorp,
            ArrayList<IClearingBank> iClearingBanks,
            IDepository depository);

    List<IBroker> getBrokers();

    IClearingCorp getClearingCorp();

    List<IClearingBank> getClearingBanks();

    IDepository getDepository();
}

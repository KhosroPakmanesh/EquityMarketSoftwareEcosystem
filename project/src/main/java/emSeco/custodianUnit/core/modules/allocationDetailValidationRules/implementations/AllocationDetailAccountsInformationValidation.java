package emSeco.custodianUnit.core.modules.allocationDetailValidationRules.implementations;

//#if CustodianAllocationDetailAccountsInformationValidation
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;

public class AllocationDetailAccountsInformationValidation implements IAllocationDetailValidationRule {
    @Override
    public BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails) {
        for (int counter = 0, allocationDetailsSize = allocationDetails.size();
             counter < allocationDetailsSize; counter++) {
            AllocationDetail allocationDetail = allocationDetails.get(counter);

            if (allocationDetail.getAccountsInformation().getClearingBankId() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The clearingBankId field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getAccountsInformation().getClearingBankAccountNumber() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The clearing bank account number field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getAccountsInformation().getDepositoryId() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The depositoryId field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getAccountsInformation().getDematAccountNumber() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The demat account number field for the allocation detail number "
                                        + counter + " is empty!"));
            }
        }

        return new BooleanResultMessage
                (true,OperationMessage.emptyOperationMessage());
    }
}
//#endif

package emSeco.custodianUnit.core.entities.custodianBankAccount;

import java.util.UUID;

public class CustodianBankAccount {
    private final UUID custodianBankAccountNumber;
    private double balance;

    public UUID getCustodianBankAccountNumber() {
        return custodianBankAccountNumber;
    }

    public CustodianBankAccount(UUID custodianBankAccountNumber, int balance) {
        this.custodianBankAccountNumber = custodianBankAccountNumber;
        this.balance=balance;
    }

    public void credit(double creditAmount)
    {
        this.balance=this.balance+creditAmount;
    }

    public void debit(double debitAmount)
    {
        this.balance=this.balance-debitAmount;
    }

    public Boolean HasEnoughBalance(double balance)
    {
        return this.balance>= balance;
    }
}

package emSeco.clearingbankUnit.core.entities.bankAccount;

import java.util.UUID;

public class BankAccount {
    private final UUID brokerBankAccountNumber;
    private double balance;

    public UUID getBrokerBankAccountNumber() {
        return brokerBankAccountNumber;
    }

    public BankAccount(UUID brokerBankAccountNumber,double balance) {
        this.brokerBankAccountNumber = brokerBankAccountNumber;
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }
}

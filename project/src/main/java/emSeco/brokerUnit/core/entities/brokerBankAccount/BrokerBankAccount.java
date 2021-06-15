package emSeco.brokerUnit.core.entities.brokerBankAccount;

import java.util.UUID;

public class BrokerBankAccount {
    private final UUID brokerBankAccountNumber;
    private double balance;

    public UUID getBrokerBankAccountNumber() {
        return brokerBankAccountNumber;
    }

    public BrokerBankAccount(UUID brokerBankAccountNumber, int balance) {
        this.brokerBankAccountNumber = brokerBankAccountNumber;
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

    public double getBalance() {
        return balance;
    }
}

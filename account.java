/*
 * @author    Greg Prawdzik
 * @since    5/9/2019
 * Program: Account.java
 * Descr:   This is a JavaFX Banking System that holds a record of users names,
 *          account numbers, balance, and type of account.  Users can withdraw
 *          or deposit money and create new checking and savings accounts.
 */
package pkgfinal;

import java.util.Objects;

/**
 *
 * @author Gregory Prawdzik
 */
public class Account {
    private String firstName;
    private String lastName;
    private String acctNum;
    private String acctType;
    private String balance;

    public Account() {
        this.balance = "0.00";
    }

    /**
     * 
     * @param firstName
     * @param lastName
     * @param acctNum
     * @param acctType
     * @param balance 
     */
    public Account(String firstName, String lastName, String acctNum, String acctType, String balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.acctNum = acctNum;
        this.acctType = acctType;
        this.balance = balance;
    }
    /**
     * 
     * @param firstName
     * @param lastName
     * @param acctNum
     * @param acctType 
     */
    public Account(String firstName, String lastName, String acctNum, String acctType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.acctNum = acctNum;
        this.acctType = acctType;
        this.balance = "0.00";
    }

    
    /**
     * 
     * @return firstName String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @return lastName String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @return acctNum String
     */
    public String getAcctNum() {
        return acctNum;
    }

    /**
     * 
     * @return account type String
     */
    public String getAcctType() {
        if (null == acctType)return "Error";else switch (acctType) {
            case "0":
                return "Checking";
            case "1":
                return "Savings";
            default:
                return "Error";
        }
            
    }

    /**
     * 
     * @return balance String
     */
    public String getBalance() {
        return balance;
    }



    /**
     * 
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @param lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @param acctNum 
     */
    public void setAcctNum(String acctNum) {
        this.acctNum = acctNum;
    }

    /**
     * 
     * @param acctType 
     */
    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }
    
    /**
     * 
     * @param deposit
     * @throws NegativeAmount 
     */
    public void deposit(double deposit) throws NegativeAmount{
        String stringBalance = this.balance;
        double dblBalance = Double.parseDouble(stringBalance);
        if (deposit >= 0){
            double result = dblBalance + deposit;
            String stringResult = String.format ("%.2f", result);
         //   String stringResult = "" + result;
            this.balance = stringResult; 
        }
        else 
            throw new NegativeAmount("Enter a positive number!!!");
    }

    /**
     * 
     * @param withdrawl
     * @throws NegativeAmount
     * @throws InsufficientFunds 
     */
    public void withdrawl(double withdrawl)throws NegativeAmount, InsufficientFunds{
        
        String stringBalance = this.balance;
        double dblBalance = Double.parseDouble(stringBalance);
        if (withdrawl >= 0){
            double result = dblBalance - withdrawl;
            if (result>= 0){
                
                String stringResult = String.format ("%.2f", result);
             //   String stringResult = "" + result;
                
                this.balance = stringResult;
                
            }
            else {
                throw new InsufficientFunds("You do not have enough money.");
            }

        }
        else 
            throw new NegativeAmount("Enter a positive number!!!");

    }
    
    /**
     * 
     * @return hash int
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.acctNum);
        return hash;
    }

    /**
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.acctNum, other.acctNum)) {
            return false;
        }
        if (!Objects.equals(this.acctType, other.acctType)) {
            return false;
        }
        if (!Objects.equals(this.balance, other.balance)) {
            return false;
        }
        return true;
    }

   /**
    * 
    * @return Account record String
    */
    @Override
    public String toString() {
        return "Account{" + "firstName=" + firstName + ", lastName=" + lastName + ", acctNum=" + acctNum + ", acctType=" + acctType + ", balance=" + balance + '}';
    }
    
    
    
}

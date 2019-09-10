/*
 * @author    Greg Prawdzik
 * @since    5/9/2019
 * Program: InsufficientFunds.java
 * Descr:   This is a JavaFX Banking System that holds a record of users names,
 *          account numbers, balance, and type of account.  Users can withdraw
 *          or deposit money and create new checking and savings accounts.
 */
package pkgfinal;

/**
 *
 * @author Gregory Prawdzik
 */
public class InsufficientFunds extends Exception {

    /**
     * 
     * @param message 
     */
  public InsufficientFunds(String message) {
		super(message);
	}
}

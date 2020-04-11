package com.seneca.accounts;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 
 * @author nesabertanico
 * @since Feb 23, 2020
 * @version 1.0
 *
 */

public class Chequing extends Account{

	private BigDecimal m_serviceCharge;
	private int m_maxTransaction;
	private BigDecimal[] m_transactions;
	
	/**
	 * Default constructor
	 * @param none
	 * 
	 */
	public Chequing() {
		super();
		m_serviceCharge = new BigDecimal(0.25);
		m_maxTransaction = 3;
	}
	
	/**
	 * Custom Constructor
	 * @param n
	 * @param ac
	 * @param bal
	 * @param charge
	 * @param maxTrans
	 */
	public Chequing(String n, String ac, double bal, double charge, int maxTrans) {
		super(n, ac, bal);
		if (n != null || ac != null || bal > 0 || charge > 0 || maxTrans > 0)
		{
		m_serviceCharge = new BigDecimal(charge);
		m_maxTransaction = maxTrans;
		m_transactions = new BigDecimal[0];
		}
		else {
			m_maxTransaction = 0;
		}
	}
	
	/**
	 * calculate the total charges by multiplying the total number of transactions into the service charge
	 * @return double charges = result of the multiplicataion
	 */
	public double getTotalCharges() {
		if(m_transactions.length != 0) {
			return m_serviceCharge.multiply(new BigDecimal(m_transactions.length)).doubleValue();
		}
		return 0;
	}
	/**
	 * getter for m_maxTransactions, needed this because m_maxTransaction is private
	 * @return int maxtransaction 
	 */
	public int getMaxTransaction() { return m_maxTransaction;}
	
	@Override
	/**
	 * calls the getBalance() from super and subtract the total charges from it.
	 * @return balance - charges
	 */
	public double getBalance() {
		double check = super.getBalance() - this.getTotalCharges();
		if(check >0)
		return check;
		else
			return 0.0;
	}
	
	
	@Override
	/**
	 * equals method that calls the equals() from the super account class
	 * then compare the m_serviceCharge, m_maxTransaction and m_transactions
	 * @param other = an object
	 * @return boolean check = true if objects are the same, false if not
	 */
	public boolean equals(Object other) {
		boolean check = false;
		if(other instanceof Chequing && other != null) {
			Chequing temp_other = (Chequing) other;
			if(super.equals(temp_other) && m_serviceCharge.equals(temp_other.m_serviceCharge)
					&& m_maxTransaction == temp_other.m_maxTransaction
					&& m_transactions.length == temp_other.m_transactions.length) {
				for(int i = 0; i < m_transactions.length; i++) {
					if (m_transactions[i].equals(temp_other.m_transactions[i])) {
						check = true;
						if(check == false) {
							return check;
						}
					}
				}
			}
		}
		return check;
	}
	
	@Override
	/**
	 * toString method that return the information in the format from instruction
	 */
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		StringBuffer strbuf = new StringBuffer();
		
		strbuf.append(super.toString());
		strbuf.append(String.format("%-24s: ", "Account Type")).append("CHQ\n");
		strbuf.append(String.format("%-24s: ", "Service Charge")).append(nf.format(m_serviceCharge)).append("\n");
		strbuf.append(String.format("%-24s: ", "Total Charges")).append(nf.format(this.getTotalCharges())).append("\n");
		strbuf.append(String.format("%-24s: ", "List of Transactions"));
		
		String temp_transactions[] = new String[m_transactions.length];
		for(int i = 0; i < m_transactions.length; i++) {
			temp_transactions[i] = String.format("%.2f", m_transactions[i]);
		}
		strbuf.append(String.join(", ", temp_transactions)).append("\n");
		
		strbuf.append(String.format("%-24s: ", "Final Balance")).append(String.format("%.2f", this.getBalance())).append("\n");
		
		return strbuf.toString();
	}
	
	/**
	 * generate a number base on the predefine hash number
	 * @return hash
	 */
	public int hashCode(){
        int hash = 15;
        int supAcc = super.hashCode();
        hash = hash * supAcc + m_serviceCharge.hashCode();
        hash = hash * supAcc + m_maxTransaction;
        for(int i = 0; i < m_transactions.length; i++) {
        	hash = hash * supAcc + m_transactions[i].hashCode();
        }
        return  hash;

	}

	@Override
	/**
	 * overrided from its super class Account to withdraw an amount of money from the chequing account
	 * @param amount = a double, amount to be substracted from balance
	 * @return boolean check = true if successfully substracted amount from balance.
	 */
	public boolean withdraw(double amount) {
	      if (m_transactions.length < m_maxTransaction && amount >0){
	            boolean result = super.withdraw(amount);
	            if (result){
	                BigDecimal[] temp = new BigDecimal[m_transactions.length + 1];
	                for (int i = 0; i < m_transactions.length; ++i){
	                    temp[i] = m_transactions[i];
	                }
	                temp[m_transactions.length] = new BigDecimal(amount).negate();
	                m_transactions = new BigDecimal[temp.length];
	                for (int i = 0; i < temp.length; ++i){
	                	m_transactions[i] = temp[i];
	                }
	            }
	            return result;
	        }else {
	        	System.out.println("Reached maximum number of transaction for this account.");
	        	return false;
	        }
}
	
	@Override
	/**
	 * overrided from its super class Account to deposit an amount of money from the chequing account
	 * returns nothing, it just adds amt into the balance
	 * @param amt
	 *
	 */
	public void deposit(double amt) {
		if(m_transactions.length < m_maxTransaction&& amt >0) {
			super.deposit(amt);
			BigDecimal[] temp_bd = new BigDecimal[m_transactions.length + 1];
			
			for(int i = 0; i < m_transactions.length; i++) {
				temp_bd[i] = m_transactions[i];
			}
			
			temp_bd[m_transactions.length] = new BigDecimal(amt);
			m_transactions = new BigDecimal[temp_bd.length];
			
			for(int i = 0; i < temp_bd.length; i++) {
				m_transactions[i] = temp_bd[i];
			}			
		}else {
			System.out.println("Reached maximum number of transaction for this account.");

		}
	}
	
}

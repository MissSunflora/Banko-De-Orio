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
public class Account {
	String m_fullName;
	String m_accountNumber;
	BigDecimal m_balance;
	
	/**
	 * zero-argument constructor
	 * 
	 * @param none
	 */
	public Account(){this("", "", 0);}
	
	/**
	 * a constructor that takes three arguments
	 * 
	 * @param fn;
	 * @param an;
	 * @param b;
	 */
	public Account(String fn, String an, double b){
		if (fn != null || an != null || b > 0){
			BigDecimal bgD = new BigDecimal(b);
			this.m_fullName = fn;
			this.m_accountNumber = an;
			this.m_balance = bgD;}
		else
			{
			this.m_fullName = null;
			this.m_accountNumber = null;
		}
	}

	/**
	 * getter for fullname
	 * 
	 * @return FullName
	 */
	public String getFullName() { 
		if (m_fullName.equals(null))
			return "";
		else
		return this.m_fullName;
		}
	
	/**
	 * getter for the first name
	 * 
	 * @return AccountBalance
	 */
	public String getFirstName() { 
		int i = m_fullName.lastIndexOf(' ');
		String fn = m_fullName.substring(0, i);
		
		return fn;
	}
	
	/**
	 * getter for the last name
	 * 
	 * @return AccountBalance
	 */
	public String getLastName() { 
		int i = m_fullName.lastIndexOf(' ');
		String ln = m_fullName.substring(i + 1);
		
		return ln;
	}
	
	/**
	 * getter for accountnumber
	 * 
	 * @return AccountNumber
	 */
	public String getAccountNumber() { return this.m_accountNumber; }
	
	
	/**
	 * getter for the accountbalance
	 * 
	 * @return AccountBalance
	 */
	public double getBalance() { return this.m_balance.doubleValue(); }
	
	
	/**
	 * To string method used to display information about the object in a specific way
	 */
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		StringBuffer strbuf = new StringBuffer();
		
		strbuf.append(String.format("%-20s: ", "Name")).append(getLastName()).append(", ").append(getFirstName()).append("\n");
		strbuf.append(String.format("%-20s: ", "Number")).append(m_accountNumber).append("\n");
		strbuf.append(String.format("%-20s: ", "Current Balance")).append(nf.format(m_balance)).append("\n");
		return strbuf.toString();
	}
		
	/**
	 * Equals method to check if current account and the one passed in are the same
	 * @param other = an object
	 * @return boolean = true if objects are same, false if different 
	 */
	public boolean equals(Object other) {
		boolean check = false;
		if(other instanceof Chequing && other != null) {
			Account temp_other = (Account) other;
			if(this.m_fullName.equals(temp_other.m_fullName) 
					&& this.m_accountNumber.equals(temp_other.m_accountNumber)
					&& this.m_balance.equals(temp_other.m_balance)) {
				check = true;
			}
		}
		return check;		
	}

		
	/**
	 *generate a number base on the predefine hash number
	 * hashCode()
	 * @return hash
	 */
	public int hashCode(){
		int hash = 15;
		hash = hash * 31 + m_fullName.hashCode();
		hash = hash * 31 + m_accountNumber.hashCode();
		hash = hash * 31 + m_balance.hashCode();

		return  hash;

    }
		
	/**
	 * this method substracts amount from balance in the Account
	 * @param amt = amount to be withdrawn
	 * @return boolean = true if withdraw is successful, false if not
	 */
	public boolean withdraw(double amt) {
		boolean check = false;
	
		if(amt > 0) {
			 BigDecimal bd_amt = ( m_balance.subtract(BigDecimal.valueOf(amt)));
			if(bd_amt.compareTo(BigDecimal.ZERO) > 0) {
				m_balance = bd_amt;
				check = true;
			}
					
		}
		return check;
	}
		
		/**
		 * this method adds amt into balance of the Account
		 * @param amt
		 * @return nothing
		 */
		public void deposit(double amt) {	
			if(amt > 0) {
				this.m_balance = this.m_balance.add(new BigDecimal(amt));
			}
		}


}

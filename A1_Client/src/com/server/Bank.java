package com.server;

import java.util.ArrayList;

import com.seneca.accounts.Account;

/**
 * 
 * @author nesabertanico
 * @since Feb 23, 2020
 * @version 1.0
 *
 */
public class Bank {
	private String m_bankName;
	private ArrayList<Account> m_bankAccounts;
	
	/**
	 * 	Code the zero-argument, constructor and a constructor that takes one argument
	 *  zero-argument constructor initializes a Bank object with "Seneca@York" 
	 *  and creates an empty ArrayList
	 *  @param none
	 */
	public Bank() { 
		this("Seneca@York");
		//this.m_bankName = "Seneca@York";	
		//this.m_bankAccounts = new ArrayList<>();
	}	   
	
	/**
	 * 	one-argument constructor initializes a Bank object with the actual parameter received
	 *  and creates an empty ArrayList 
	 *  
	 *  @param bn
	 */
	public Bank(String bn) {
		this.m_bankName = bn;
		this.m_bankAccounts = new ArrayList<>(0);
	} 
	
	/**
	 * getter for the m_bankName
	 * @return value of bankName
	 */
	public String getBankName() { return this.m_bankName; }
	
	/**
	 * Code the addAccount() method that adds an account to the bank.
	 *  
	 *  @param newAccount = an account
	 *  @return boolean, true 
	 */
	public boolean addAccount( Account newAccount ) {
		if(newAccount.getAccountNumber() == null
				|| newAccount.getBalance() < 0
				|| newAccount.getFirstName() == null)
		{return false;}
		
		
		for(int i=0; i<m_bankAccounts.size();i++){
			if (m_bankAccounts.get(i).getAccountNumber().equals(newAccount.getAccountNumber())){ 
				return false;
			}
		}
		m_bankAccounts.add(newAccount);
		return true;
	}
	
	/**
	 * this method will check if param accountNumber is equals to one of the bankAccounts' account number
	 * if its the same, that account will be removed from the Account.
	 * @param accountNumber
	 * @return Account, if Account is not empty method will return the Account without the removed account, 
	 *  if its empty, it will return a null Account.
	 */
	public Account removeAccount(String accountNumber){
		if(accountNumber.length() > 0){
			for(int i = 0; i < m_bankAccounts.size(); i++) {
				if(m_bankAccounts.get(i).getAccountNumber().equals(accountNumber)){
					Account temp_sameAN;
					temp_sameAN = m_bankAccounts.get(i);
					m_bankAccounts.remove(i);
					return temp_sameAN;
				}
			}
		}
		return null;
	}
	
	/**
	 * this method will loop through the accounts and check if there is an account has the same balance
	 * @param balance = to be compared with the account's balance
	 * @return Account[] = it will return an empty Account[] if no balance is same,
	 * if its not empty, it will return the arraylist of accounts containing similar balance
	 */
	public Account[] searchByBalance(double balance){
		if(balance >= 0){
			ArrayList<Account> temp_sameBal = new ArrayList<Account>(0);
			for(Account a : m_bankAccounts){
				if(a.getBalance() == balance) {
					temp_sameBal.add(a);
				}
			}
			
			Account[] temp_sameArray = new Account[temp_sameBal.size()];
			temp_sameArray = temp_sameBal.toArray(temp_sameArray);
			return temp_sameArray;
		}
		Account[] empty = {};
        return empty;
	}
	
	/**
	 * this method will loop through the accounts and check if there is an account has the same accountName
	 * @return Account[] = it will return an empty Account[] if no accountName is same,
	 * if its not empty, it will return the arraylist of accounts containing similar accountName
	 */
	public Account[] searchByAccountName( String accountName ) {
        ArrayList<Account> match = new ArrayList<>();

        for(Account a: m_bankAccounts){
            if(a.getFullName().matches(accountName)){
                match.add(a);
            }
        }

        Account[] matchArray = new Account[match.size()];
        return match.toArray(matchArray);		
	}
	
	/**
	 * return a new array with the accounts inside the bankAccounts array list
	 * @return Account[] temp_allAcc
	 */
	public Account[] getAllAccounts() {
        Account[] temp_allAcc= new Account[m_bankAccounts.size()];
        temp_allAcc = m_bankAccounts.toArray(temp_allAcc);
        return temp_allAcc;
	}
	
	/**
	 * this method will loop through the accounts and check if there is an account has the same accountNumber
	 * @return Account = it will return an empty Account[] if no accountNumber is same,
	 * if its not empty, it will return the arraylist of accounts containing similar accountNumber
	 */
    public Account searchByAccountNumber (String accountNumber){

        for(Account a: m_bankAccounts){
            if(a.getAccountNumber().equals(accountNumber)){
                return a;
            }
        }

        return null;
    }
	
	@Override
	/**
	 * Code the toString() method. Read the sample output below for details.
	 * Two Bank objects are equal if they have the same bank names and identical ArrayLists of 
	 * Account objects. (In other words, both ArrayLists have the same sequences of Account objects.)
	 * 
	 */
	public String toString() {
		StringBuffer strbuf = new StringBuffer();
        strbuf.append("*** Welcome to the bank of ").append(getBankName()).append(" ***\n");
        strbuf.append("It has ").append(m_bankAccounts.size()).append(" accounts.\n");
        for (int i = 0; i < m_bankAccounts.size(); ++i){
        	strbuf.append(String.format("%d.\n", i+1)).append(m_bankAccounts.get(i)).append("\n");
        }
        return strbuf.toString();
		
	}

	@Override
	/**
	 * *generate a number base on the predefine hash number
	 * hashCode()
	 */
	public int hashCode(){
        int hash = 15;
        //for(int i = 0; i < this.m_bankAccounts.size(); i++) {
        	hash = hash * m_bankAccounts.hashCode();
        //}
        return  hash;

	}

	@Override
	/**
	 * Equals method to check if current Object and the one passed in are the same
	 * this method is an overrided method, to check if bankNames and bank size are the same
	 * @param other = an object
	 * @return boolean = true if objects are same, false if different 
	 */
	public boolean equals(Object other){
		boolean check = false;
		if(other instanceof Bank && other != null) {
			Bank temp_other = (Bank) other;
			if(temp_other.m_bankName.equals(this.m_bankName) && temp_other.m_bankAccounts.size() == this.m_bankAccounts.size()) {
				for(int i = 0; i < this.m_bankAccounts.size(); i++) {
					if(temp_other.m_bankAccounts.get(i).equals(this.m_bankAccounts.get(i))) {
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
	

	 

}


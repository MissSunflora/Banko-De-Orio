package com.seneca.accounts;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 
 * @author Jianpeng Zhang
 * @since Feb 23, 2020
 * @version 1.0
 *
 */
public class GIC extends Account implements Taxable{
    private int m_periodOfInvestment;
    private BigDecimal m_interestRate;
    private BigDecimal m_tax = new BigDecimal(0);

    /**
     * is provided with a full name, an account number, the starting balance 
     * (i.e. the principal amount of investment), 
     * the period of investment (in years) and annual interest rate (in percentage).
     * 
     * @param fn
     * @param an
     * @param b
     * @param poi
     * @param ir
     */
    public GIC(String fn, String an, double b, int poi, double ir)
    {
        super(fn, an, b);
        m_periodOfInvestment = poi;
        m_interestRate = BigDecimal.valueOf(ir);;
    }

    /**
     *  The default constructor uses the default constructor of the Account class. 
     *  It initializes the period of investment to 1 year and annual interest rate to 1.25%. 
     */
    public GIC() {this("","",0.00, 1, 0.0125);}


    /**
     * getter method that return the Period of investment
     * @return m_periodOfInvestment
     */
    public double getPeriodOfInvestment() {
    	if(m_periodOfInvestment > 0)
    		return m_periodOfInvestment;
    	else
        return 0.0;
    }
    
    /**
     * equals method that call the equals in account class, then compare the m_periodOfInvestment and m_interestRate
     * @param other
     * @return check
     */
	public boolean equals(Object other) {
		boolean check = false;
		if(other instanceof GIC && other != null) {
			GIC temp_other = (GIC) other;
			if(super.equals(temp_other) 
					&& this.m_periodOfInvestment == temp_other.m_periodOfInvestment
					&& this.m_interestRate == temp_other.m_interestRate) {
				check = true;
			}
		}
		return check;		
	}
	
	/**
     * generate a number base on the predefine hash number
     */
     public int hashCode(){
    	 return super.hashCode();
     }
    
     /**
      * toString method that return the information in the format from instruction
      */
     public String toString() {
 		StringBuffer strbuf = new StringBuffer();
 		
 		strbuf.append(super.toString());
 		strbuf.append(String.format("%-30s: ", "Account Type")).append("GIC").append("\n");
 		strbuf.append(String.format("%-30s: ", "Annual Interest Rate")).append(String.format("%.2f%%", m_interestRate.multiply(new BigDecimal(100)))).append("\n");
 		strbuf.append(String.format("%-30s: ", "Period Of Investment")).append(String.format("%d " , m_periodOfInvestment)).append((m_periodOfInvestment <= 1) ? "year\n" : "years\n");
 		strbuf.append(String.format("%-30s: ", "Interest Income At Maturity")).append(String.format("$%.2f\n", getPeriodOfInvestment()));
 		strbuf.append(String.format("%-30s: ", "Balance at Maturity")).append(String.format("$%.2f\n", getBalance()));
        return strbuf.toString();
 	}
    
    
    @Override
    /**
     * do nothing 
     * @param amount
     */
    public void deposit(double amount) {

    }

    @Override
    /**
     * returns false
     * @param amount
     * @return
     */
    public boolean withdraw(double amount){
        return false;
    }

    @Override
    /**calculate the balance after tax
     * maturity
	 * @return calculated balance
     */
    public double getBalance(){
    	double check = ((m_interestRate.add(new BigDecimal(1))).pow(m_periodOfInvestment)).multiply(new BigDecimal(super.getBalance())).doubleValue();
    	
    	if(check > 0)
    		return check;
    	else return 0;   
    }


	@Override
	/**calculate the tax part
	 * maturity
	 */
	public void calculateTax() {
		BigDecimal amount = taxRate.multiply(BigDecimal.valueOf(this.getBalance()));
		m_tax = amount;
	}

	@Override
	/**return the tax amount in double
	 * maturity
	 * @return m_tax
	 */
	public double getTaxAmount() {
		calculateTax();
        return m_tax.doubleValue();
	}

	
	@Override
	/**
	 * a method that return the information in the format from instruction
	 */
    public String createTaxStatement() {
        StringBuffer strbuf = new StringBuffer();
        NumberFormat nf = NumberFormat.getCurrencyInstance();
         
         strbuf.append(String.format("%-20s: ", "Account Number")).append(getAccountNumber()).append("\n");
         strbuf.append(String.format("%-20s: ", "Interest income")).append(nf.format(this.getBalance())).append("\n");
         strbuf.append(String.format("%-20s: ", "Amount of tax")).append(nf.format(getTaxAmount())).append("\n");
         return strbuf.toString();
    }
}
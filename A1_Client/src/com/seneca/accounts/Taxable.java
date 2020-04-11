package com.seneca.accounts;

import java.math.BigDecimal;
/**
 * 
 * @author Jianpeng Zhang
 * @since Feb 23, 2020
 * @version 1.0
 *
 */
public interface Taxable{
	
		public static final BigDecimal taxRate = new BigDecimal(0.15);
		
	    public void calculateTax();
	    public double getTaxAmount();
	    public String createTaxStatement();


}
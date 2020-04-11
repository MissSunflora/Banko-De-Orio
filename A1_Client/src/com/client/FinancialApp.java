package com.client;

//import java.math.BigDecimal;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Scanner;

import com.seneca.accounts.Account;
import com.seneca.accounts.Chequing;
import com.seneca.accounts.GIC;
import com.seneca.accounts.Taxable;
import com.server.Bank;

import java.net.*;
import java.io.*;
import java.util.*;


/**
*
* @author nesabertanico
* @since Feb 23, 2020
* @version 1.0
*
*/

public class FinancialApp {

	
	
	
  /**
   * This application class has the main method that calls other static methods.
   * The main method displays a menu and allows a user to do business with a bank
   * @param args
   */
  public static void main(String[] args) {
	  
	  Socket clientSocket;		// TCP/IP socket

		try {

	             /* step 1: connect to the server
	                        IP address/server name: "localhost"
	                        port number:            8000
	              */

		     clientSocket = new Socket( InetAddress.getByName( "localhost" ),
	                                        5678 );

	                                      // InetAddress.getByName( "127.0.0.1" )

		     System.out.println( "Connected to " +
			                 clientSocket.getInetAddress().getHostName() );

		         /* step 2: connect input and output streams to the socket
	              */

		     DataOutputStream dosToServer = new DataOutputStream(
	                                                clientSocket.getOutputStream() );

		     DataInputStream  disFromServer= new DataInputStream(
	                                                 clientSocket.getInputStream() );

		     //System.out.println( "I/O streams connected to the socket" );


		     /* step 3: exchange data with the server
	              */
	         /* BufferedReader keyboard = new BufferedReader(
	                                           new InputStreamReader( System.in ) ); */

		     Scanner scnr = new Scanner(System.in);
             Bank tempBank = new Bank();
             
	        //for (int i=1; i < 5; i++) {

					try {
	                     // keyboard input
			             //System.out.print( "What is the radius of the circle? " );

	                     //double radius = Double.parseDouble( keyboard.nextLine() );
	                     
///////////////////////////////////////////////////////////////
	                     
	                     loadBank(tempBank);
	                     int customerOption = 0;
	                   
	                     while (customerOption != 7) {
	                      	
	                     	displayMenu(tempBank.getBankName());
	                     	String errorFixer;
	                     	
	                         customerOption = menuChoice();



	                         if (customerOption == 1) {
	                         	Scanner in = new Scanner(System.in);
	                         	System.out.print("Please enter the account type (CHQ/GIC)> ");
	                             
	                             String input = in.nextLine();
	                             Account newAccount = null;
	                             Account GnewAccount = null;

	                             if(input.equals("CHQ") || input.equals("chq")){
	                             	System.out.println("Please enter account information at one line\n (e.g. John M. Doe;A1234;1000.00;1.5;2) > ");
	                                 
	                                 String inputC = in.nextLine();
	                                 String[] inputCHQ = inputC.split(";");
	                                 boolean correct = true;

	                                 for(int x = 0; x<inputCHQ.length;x++){
	                                     if( inputCHQ[x].equals(null)){
	                                         correct=false;
	                                     }
	                                 }	                                
	                                 if(inputCHQ.length==5 && correct){                   //string, string, double, double int
	                                 	newAccount = new Chequing(inputCHQ[0].trim(),inputCHQ[1].trim(),Double.valueOf(inputCHQ[2].trim()),Double.valueOf(inputCHQ[3].trim()),Integer.parseInt(inputCHQ[4].trim()));
	                                 	if(tempBank.addAccount(newAccount)) {
	                                     	System.out.println("\n ### SUCCESS: Chequing account opened: ###");
	               							displayAccount(newAccount);
	                                     }else {
	                                     	System.out.println(" *** ERROR: Failed opening chequing account. ***");
	                                     }
	                                      
	                                 }else {
	                                     System.out.println(" *** ERROR: Wrong format. *** ");                    	
	                                 }


	                             }else if(input.equals("GIC") || input.equals("gic")){
	                                 System.out.println("Please enter account information at one line\n" +
	                                         "(e.g. Nesa Bertanico;S666;6000;2;.0150) > ");
	                                 String inputG = in.nextLine();
	                                 String[] inputGIC = inputG.split(";");
	                                 boolean correctG = true;

	                                 

	                                 for(int y = 0; y<inputGIC.length;y++){
	                                     if( inputGIC[y].equals(null)){
	                                         correctG=false;
	                                     }
	                                 }
	                                 System.out.println("inputG: " + inputG + correctG);
	                                 if(inputGIC.length==5 && correctG){                   //String fn, String an, double b, int poi, double ir
	                                 	GnewAccount =  new GIC(inputGIC[0].trim(),inputGIC[1].trim(),Double.valueOf(inputGIC[2].trim()),Integer.parseInt(inputGIC[3].trim()),Double.valueOf(inputGIC[4].trim()));
	                                     if(tempBank.addAccount(GnewAccount)) {
	                                     	System.out.println("\n+ GIC account opened:");
	               							displayAccount(GnewAccount);
	                                     }else {
	                                     	System.out.println(" *** ERROR: Failed opening GIC account ***");
	                                     }
	                                     
	                                 }else {
	                                     System.out.println(" *** ERROR: Wrong format *** ");                    	
	                                 }
	                                 
	                             }else{
	                                 System.out.println(" *** ERROR: Unable to open account *** ");
	                             }

	                         } else if (customerOption == 2) {
	                         	Scanner in2 = new Scanner(System.in);
	                             System.out.print("Please enter Account Number to close > ");
	                             String inputClose = in2.nextLine();
	                             
	                             if(tempBank.searchByAccountNumber(inputClose.trim())!=null ){	
	               					System.out.println("\n+ Account Deleted:");
	               					displayAccount(tempBank.removeAccount(inputClose.trim()));
	               					
	               				}else{
	               					System.out.println(" *** ERROR: unable to close account ***\n");
	               					}
	                             
	               			} else if (customerOption == 3) {
	               				Scanner in3 = new Scanner(System.in);
	                             System.out.print("Please enter Account Number to start deposit > ");
	                             String inputDep = in3.nextLine();
	                             
	                             Account accDep = tempBank.searchByAccountNumber(inputDep);
	                             //if accountnumber is a match
	                             if( accDep != null){
	                             	Scanner in31 = new Scanner(System.in);
	                             	System.out.print("Please enter amount to deposit > ");
	                             	double amt = in31.nextDouble();	
	                             	double check = accDep.getBalance();
	                             	accDep.deposit(amt);
	                             	double check2 = accDep.getBalance();
	                             	if(check != check2) {
	                             		System.out.println(" ### SUCCESS: deposited $" + amt + " to " + inputDep +". ###\n");	
	                             	}else {
	                 					System.out.println(" *** ERROR: failed to deposit. ***\n");
	                 				}
	                             	
	               				}else{
	               					System.out.println(" *** ERROR: unable to find account "+ inputDep +" ***\n");
	               					}
	                         } else if (customerOption == 4) {
	                         	Scanner in4 = new Scanner(System.in);
	                             System.out.print("Please enter Account Number to start withdraw > ");
	                             String inputWithd = in4.nextLine();
	                             
	                             Account accWithd = tempBank.searchByAccountNumber(inputWithd.trim());
	                             //if accountnumber is a match
	                             if( accWithd != null){
	                             	Scanner in41 = new Scanner(System.in);
	                             	System.out.print("Please enter amount to withdraw > ");
	                             	double amtW = in41.nextDouble();	
	                             	
	                             	//accWithd.withdraw(amtW);
	                             	
	                             	if(accWithd.withdraw(amtW)) {
	                             		System.out.println(" ### SUCCESS: widthdrawn $" + amtW + " from " + inputWithd +". ***\n");	
	                             	}else {
	                             		
	                 					System.out.println(" *** ERROR: failed to widthdraw. ***\n");
	                 				}
	                             	
	               				}else{
	               					System.out.println(" *** ERROR: unable to find account ***\n");
	               					}
	                         } else if (customerOption == 5) {
	                         	
	                         	Scanner in5 = new Scanner(System.in);
	                             System.out.println("a) display all accounts with the same account name");
	                             System.out.println("b) display all accounts with the same final balance");
	                             System.out.println("c) display all accounts opened at the bank.");
	                             System.out.print("Select display option from above (eg. a): > ");
	                             String inputDisplay = in5.nextLine();
	                             
	                             if(inputDisplay.equals("a") || inputDisplay.equals("A")) {
	                             	Scanner in51 = new Scanner(System.in);
	                                 
	                             	System.out.print("Please enter Account Name to display > ");
	                                 String a_name = in51.nextLine();

	                                 Account[] accountSameName = tempBank.searchByAccountName(a_name);

	                                 if(accountSameName.length > 0 ) {//searchByAccountName
	                                 	System.out.println(" ### SUCCESS: Displaying all accounts with the account name [ " + a_name + " ] ###");
	                                 	listAccounts(accountSameName);
	                                 }else {
	                                 	System.out.print(" *** ERROR: account name >> " + a_name + " << does not exist ***");
	                                 }                    
	                                 
	                             }else if(inputDisplay.equals("b") || inputDisplay.equals("B")) {
	                             	Scanner in52 = new Scanner(System.in);
	                                 System.out.print("Please enter Final Balance to display > ");
	                                 double a_balance = in52.nextDouble();
	                                 
	                                 Account[] accountSameBal = tempBank.searchByBalance(a_balance);

	                                 if(accountSameBal.length > 0 ) {
	                                 	System.out.println(" ### SUCCESS: Displaying all accounts with the account balance [ " + a_balance + " ]  ###");
	                                 	listAccounts(accountSameBal);
	                                 }else {
	                                 	System.out.print(" *** ERROR: account balance >> " + a_balance + " << does not exist ***");
	                                 }
	                                 
	                             }else if(inputDisplay.equals("c") || inputDisplay.equals("C")) {
	                             	System.out.println(" ### SUCCESS: Displaying all acounts opened in the bank: ###");
	                             	System.out.println(tempBank);
	                                  
	                             }else {
	               					System.out.println(" *** ERROR: wrong input ***\n");
	                             }
	                             
	                         } else if (customerOption == 6) {
	                             System.out.println(" ### SUCCESS: Displaying Tax Statement of all accounts: ###");
	                             
	                             displayTax(tempBank.getAllAccounts());

	                         } else if (customerOption == 7) {
	                             System.out.println("Exiting the bank app. Thank you.");
	                		     /*dosToServer.close();
	                		     disFromServer.close();
	                		     clientSocket.close();*/
	                		     break;
	                         } else {
	               				System.out.println(" *** Redirected to main menu *** \n");
	                         }
	                         
	                         dosToServer.writeInt( customerOption );
		                     dosToServer.flush();

	                     //}
	                 

	               	
	                     
///////////////////////////////////////////////////////////////
			             // send data to the server
	                     dosToServer.writeInt( customerOption );
	                     dosToServer.flush();		//  data sent immediately!
	                     
	                     // receive data from the server
	                     int serverRes = disFromServer.readInt();

			             // display the result to the screen
	                     System.out.println( "\t### response from serverRes: " + serverRes );

	                     // DecimalFormat
					}
					catch( EOFException eof ) { // loss of connection

				    System.out.println( "The server has terminated connection!" ); }

					catch(IOException e ) { //System.out.println( "I/O errors in data exchange" );
	                                        //e.printStackTrace(); 								
						}
		     }
	              System.out.println( "Client: data exchange completed" );

		     /* step 4: close the connection to the server
	              */
		     System.out.println( "Client: closing the connection..." );

		     dosToServer.close();
		     disFromServer.close();
		     clientSocket.close();
		}
	    catch( IOException ioe ) { 
	    	//System.out.println( "I/O errors in socket connection" ); ioe.printStackTrace(); 
	                               }

	    System.out.println( "... the client is going to stop running..." );

	   } // end main

	  
      
  public static void loadBank( Bank bank ){
/*
      bank.addAccount(new Chequing("Mary Ryan","D1234",500.0,.75,2));
      bank.addAccount(new Chequing("Nesa Bertanico","104497185",820.50,0.25,4));
      bank.addAccount(new Chequing("Peter Liu","A1213",200.0,0.10,5));
      bank.addAccount(new GIC("Jianpeng Zhang","S666",6000,2,.0150));
      bank.addAccount(new Chequing("John Doe", "S666", 666, 0.75, 2));
      bank.addAccount(new Chequing("Mary Ryan", "H777", 820.50, .50, 3));
      bank.addAccount(new GIC("John Doe", "JD68", 6000, 2, .0150));
      bank.addAccount(new GIC("Mary Ryan", "MR15", 15000, 4, .0250));*/
  }
	

  public static void displayMenu(String bankName) {
      System.out.println("\n\n================================================");
      System.out.println("      Welcome to >>" + bankName + "<< Bank!");
      System.out.println("================================================");
      System.out.println("=    1. Open an account                        =");
      System.out.println("=    2. Close an account                       =");
      System.out.println("=    3. Deposit money                          =");
      System.out.println("=    4. Withdraw money                         =");
      System.out.println("=    5. Display accounts                       =");
      System.out.println("=    6. Display a tax statement                =");
      System.out.println("=    7. Exit                                   =");
      System.out.println("================================================");
      }


  public static int menuChoice() {
      Scanner mc = new Scanner(System.in);
      System.out.print("Please enter your choice > ");
      String tempChoice = mc.next();
      int tempIntChoice = 0;
      try{
          tempIntChoice = Integer.parseInt(tempChoice);
      }catch (NumberFormatException ex){

      }
      return tempIntChoice;
  }
  
	/**
	 * Calls the toString method of the inputted account
	 * 
	 * @param account account to be displayed
	 */
	public static void displayAccount( Account account ){
		System.out.println(account);
	}
	
  /**
   * Displays all the GIC accounts belonging to a particular person(identified by their name)
   * @param accounts Takes in an array of accounts. Any taxable accounts are printed out
   */

	public static void displayTax(Account[] accounts){
      ArrayList<String> printedAccount = new ArrayList<String>();
      boolean printed = false;
      // String[] printAccount2 = {};
      for(int i = 0; i < accounts.length; i++){

          if(accounts[i] instanceof Taxable){

              StringBuffer strbuf = new StringBuffer();
              for(int q = 0; q<printedAccount.size();q++){
                  if(accounts[i].getFullName().equals(printedAccount.get(q)) ){
                      printed=true;
                  }
              }
              if(!printed){
                  strbuf.append("Name: " + accounts[i].getFullName() + "\n");
                  strbuf.append("Tax Rate: 15%"+ "\n");
                  printedAccount.add(accounts[i].getFullName());
                  //strbuf.append(((Taxable) accounts[i]).createTaxStatement());
                  int index=0;
                  for(int j = i; j<accounts.length;j++){

                      if (accounts[i].getFullName().equals(accounts[j].getFullName()) ){
                          index++;
                          strbuf.append("["+ index +"]"+"\n");
                          strbuf.append(((Taxable) accounts[j]).createTaxStatement());

                      }
                  }
                  // System.out.println(((Taxable) a).createTaxStatement());//todo:Formatting for this is all wrong:( function doesn't print int the same way
                  //specs for this menu choice !compatible with those for GIC
                  System.out.println(strbuf);
              }

          }
      }
  }
	/**
	 * Loops through an array of accounts calling each index's respective toString method no matter the type of account
	 * @param listOfAccounts list of accounts to be displayed
	 */
	public static void listAccounts( Account[] listOfAccounts){
		
		for (int i=0; i<listOfAccounts.length; i++){    
			System.out.println(listOfAccounts[i] + "\n");
		}
	}
  
}

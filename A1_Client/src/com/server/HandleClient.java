package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class HandleClient extends Thread {


	      private Socket connection;
	      private int cN;

	      public HandleClient(Socket sock, int clientNumber) { connection = sock;  this.cN = clientNumber; }

	      public void run() {
	         try {
		        	/* connect input and output streams to the socket
	              */

		        	DataOutputStream dosToClient = new DataOutputStream(
	                                                   connection.getOutputStream() );

		        	DataInputStream  disFromClient = new DataInputStream(
	                                                     connection.getInputStream() );

		        	//System.out.println( "I/O streams connected to the socket" );

		       		/* exchange data with ONE client
	              */
	            	try {
	              	 while (true) {  // keep on getting data from the client
	              	 	// read data from ONE client
	                  	int customerOption = disFromClient.readInt();
				        

	                  	System.out.print( "Client (#"+ cN+")"+" picked option ( ");
	                  			
	                  	if(customerOption == 1) {
	                  		System.out.println( customerOption +" ) : opened a new account" );
	                  	}else if(customerOption == 2) {
	                  		System.out.println( customerOption +" ) : closed an existing account" );
	                  	}else if(customerOption == 3) {
	                  		System.out.println( customerOption +" ) : deposit money" );
	                  	}else if(customerOption == 4) {
	                  		System.out.println( customerOption +" ) : withdraw money" );
	                  	}else if(customerOption == 5) {
	                  		System.out.println( customerOption +" ) : display all accounts" );
	                  	}else if(customerOption == 6) {
	                  		System.out.println( customerOption +" ) : display a tax statement" );
	                  	}else if(customerOption == 7) {
	                  		System.out.println( customerOption +" ) : exit" );
	                  		dosToClient.close();
	    		     		disFromClient.close();
	    		     		connection.close();
	    		     		break;
	                  	}else {
	                  		System.out.println( "Just typed in garbage" );
	                  	}

			     		 	// send the data to THE client
	                  	dosToClient.writeInt( customerOption );
	                  	dosToClient.flush();

			     		 	// display the result to the screen
	                  	//System.out.println( "\t*** send area to the client: " + customerOption );

		          		 } // end while
	              	 
		     	   	}
	           	catch( EOFException eof ) {
	           		System.out.println( "*** the CLIENT has terminated connection ***" );
	                     } finally {
	                    	 try {
	                    		 dosToClient.close();
	                    	 }catch(Exception e){
	                    		 System.out.println("Error: not closing properly");
	                    	 }
	                    	 System.out.println("Connection with client #" + cN + " closed.");
	                     }

		     		/* close the connection to the client
	           	 */
		     		dosToClient.close();
		     		disFromClient.close();
	          	connection.close();
	     	}
				catch(IOException e ) { 
					//e.printStackTrace();   
					}

	     } // end run

	  } // end HandleClientThread


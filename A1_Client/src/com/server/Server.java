package com.server;


//a simple client/server application
//console-based

//a "mutlithreaded" SERVER program that uses a stream socket connection
//use of DataOutputStream and DataInputStream classes

import java.net.*;
import java.io.*;

public class Server{

public static void main(String[] args) {

	ServerSocket serverSocket;		// TCP server socket used for listening

 System.out.println( "*** a multithreaded server is running ***" );

	try {
          /* step 1: create a server socket
                     port number:     8000
           */

			serverSocket = new ServerSocket( 5678);

          /* setp 2: a loop that listens for connections and
                                 creates THREADS with sockets
           */

         int count = 1;

	     	while (true) {

	        	//System.out.println( "listening for a connection..." );

         	Socket socketConnection
                       = serverSocket.accept();  // listen and wait
                                                 // socketConnection: a TCP socket
						    						// that is connected
                                                 // to the client

         	System.out.println( "**************************************************************\n"
         			+ "*\t start a thread for client #" + count );
	        	System.out.println( "**************************************************************\n"
	        			+ "*\t host name: " + socketConnection.getInetAddress().getHostName() +
                                 "\t IP address: " + socketConnection.getInetAddress().getHostAddress()
                                 + "\n**************************************************************\n");

             // System.out.println( "at port: " + socketConnection.getPort() );
	        	//System.out.print("Client (#"+ count+")");
         	Thread t = new HandleClient( socketConnection, count);
         	t.start();
/*	        	HandleClient t1 = new HandleClient(socketConnection),
	        			t2 = new HandleClient(socketConnection);
*/
         	count++;
	     	}
  }
	 catch(IOException e ) { e.printStackTrace();     }

  System.out.println( "*** the server is going to stop running ***" );

} // end main
/*
public int ccount(int x) {
	return count = x;
}*/
}


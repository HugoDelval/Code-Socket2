/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {
	
	private Socket clientSocket;
	private EchoServerMultiThreaded parent;
	// Flux d'entrée du point de vue Client
	BufferedReader socIn = null;
	// Flux de sortie du point de vue Client
	PrintStream socOut = null;

	ClientThread(Socket s, EchoServerMultiThreaded p) {
		try {
			this.clientSocket = s;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			socOut = new PrintStream(clientSocket.getOutputStream());
			parent = p;
		} catch (IOException e) {
			System.out.println("Erreur construction client du cote serveur : "+e);
		}

	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
		try {
    		while (true) {
				String commande = socIn.readLine();
				// Renvoie de la meme chose
				if(parent != null && !commande.isEmpty())
					parent.envoyerInfo(commande);
    		}
    	}catch (Exception e) {
        	System.err.println("Error in ClientThread:" + e);
        }
	}

	public void envoyerInfo(String commande)
	{
		if(!commande.isEmpty()){
			socOut.println(commande);
		}
	}

	public void deconnecter(){
		System.out.println("Deconnection ClientThread");
		try {
			socOut.close();
			socIn.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("erreur de fermeture de client cote serveur : " + e);
		}
	}
}

  

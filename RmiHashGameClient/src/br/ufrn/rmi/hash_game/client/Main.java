package br.ufrn.rmi.hash_game.client;

import br.ufrn.rmi.hash_game.commons.HashGameClientInterface;
import br.ufrn.rmi.hash_game.commons.HashGameServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

		String rmiPort = "1099";
		String rmiPath = "HashGameServer";

		if(args != null && args.length > 0) {
			if(args[0] != null && !args[0].equalsIgnoreCase("")){
				rmiPort = args[0];
			}
			if(args[1] != null && !args[1].equalsIgnoreCase("")){
				rmiPath = args[1];
			}
		}

		String serverName = "rmi://127.0.0.1:" + rmiPort + "/" + rmiPath;
		HashGameServerInterface server;

		try {
			server = (HashGameServerInterface)
					Naming.lookup(serverName);
		} catch (Exception e) {
			System.out.println("\nERROR: Must initialize Server first.");
			System.out.println(e.getMessage());
			return;
		}

		HashGameClientInterface client = new HashGameClient(server);
		client.startGame();
	}
}

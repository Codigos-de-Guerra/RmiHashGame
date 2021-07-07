package br.ufrn.rmi.hash_game.server.core;

import br.ufrn.rmi.hash_game.commons.HashGameServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {

	public static void main(String[] args) throws RemoteException, MalformedURLException {

		System.setProperty("java.rmi.server.hostname","127.0.0.1");

		HashGameServerInterface server = new HashGameServer();

		LocateRegistry.createRegistry(1099);

		Naming.rebind("rmi://127.0.0.1:1099/HashGameServer", server);

		LocateRegistry.createRegistry(2000);

		Naming.rebind("rmi://127.0.0.1:2000/HashGameServer2", server);

		System.out.println("Server Started.");

	}

}

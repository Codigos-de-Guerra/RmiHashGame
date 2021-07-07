package br.ufrn.rmi.hash_game.commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HashGameServerInterface extends Remote{

	public void registerPlayer(HashGameClientInterface playerName) throws RemoteException;

	public void killGame() throws RemoteException;

}
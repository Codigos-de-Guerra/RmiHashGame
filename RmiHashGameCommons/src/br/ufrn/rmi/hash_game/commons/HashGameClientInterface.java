package br.ufrn.rmi.hash_game.commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HashGameClientInterface extends Remote {
    public void startGame() throws RemoteException;

    public void endGame() throws RemoteException;

    public void setPlayerId(int playerId) throws RemoteException;
    public int getPlayerId() throws RemoteException;

    public void print(String str) throws RemoteException;
    public String scan() throws RemoteException;

}

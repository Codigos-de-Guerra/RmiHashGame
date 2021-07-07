package br.ufrn.rmi.hash_game.client;

import br.ufrn.rmi.hash_game.commons.HashGameClientInterface;
import br.ufrn.rmi.hash_game.commons.HashGameServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class HashGameClient extends UnicastRemoteObject implements HashGameClientInterface {
    HashGameServerInterface server;
    int playerId;

    protected HashGameClient(HashGameServerInterface server) throws RemoteException {
//        super();
        this.server = server;
    }

    @Override
    public void setPlayerId(int playerId) throws RemoteException {
        this.playerId = playerId;
    }

    @Override
    public int getPlayerId() throws RemoteException {
        return playerId;
    }

    @Override
    public void print(String str) throws RemoteException {
        System.out.println(str);
    }

    @Override
    public String scan() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void startGame() throws RemoteException {
        try {
            server.registerPlayer(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endGame() throws RemoteException {
        System.exit(0);
    }
}

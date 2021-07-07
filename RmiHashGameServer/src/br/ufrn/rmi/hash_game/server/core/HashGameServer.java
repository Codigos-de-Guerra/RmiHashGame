package br.ufrn.rmi.hash_game.server.core;

import br.ufrn.rmi.hash_game.server.exceptions.GameStateException;
import br.ufrn.rmi.hash_game.commons.HashGameClientInterface;
import br.ufrn.rmi.hash_game.commons.HashGameServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class HashGameServer extends UnicastRemoteObject implements HashGameServerInterface {
    private volatile ArrayList<HashGameClientInterface> players;

    private MainLoop mainLoop;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final AtomicBoolean isStopped = new AtomicBoolean(true);
    public final HashGame game = new HashGame();

    protected HashGameServer() throws RemoteException {
        super();
        players = new ArrayList<>();
        mainLoop = new MainLoop(isRunning, isStopped, game);
        mainLoop.start();
    }

    @Override
    public void killGame() throws RemoteException {
        mainLoop.isRunning.set(false);
        mainLoop.isStopped.set(true);
    }

    @Override
    public void registerPlayer(HashGameClientInterface player) throws RemoteException {

        if (players.size() == 2) {
            // reject new player!
            player.print("Sorry, players capacity is full already.");
            System.out.println("Another played tried to enter at full capacity");
            player.endGame();
        } else {
            players.add(player);
            player.setPlayerId(players.size());

            final String msg = "Player " + player.getPlayerId() + " registered.";
            System.out.println(msg);
            player.print(msg);
        }

    }

    private HashGameClientInterface getPlayer(int id) throws ArrayIndexOutOfBoundsException {
        if (id <= 0 || id > players.size()) {
            throw new ArrayIndexOutOfBoundsException("Player " + id + " is not present");
        }
        return players.get(id - 1);
    }

    private HashGameClientInterface PLAYER1() {
        return getPlayer(1);
    }

    private HashGameClientInterface PLAYER2() {
        return getPlayer(2);
    }

    private class MainLoop extends Thread {
        private final AtomicBoolean isRunning;
        private final AtomicBoolean isStopped;
        private final AtomicBoolean gameStarted = new AtomicBoolean(false);
        private volatile HashGame game;

        MainLoop(AtomicBoolean isRunning, AtomicBoolean isStopped, HashGame game) {
            this.isRunning = isRunning;
            this.isStopped = isStopped;

            this.game = game;

        }

        void sleepFor(long seconds) {
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void playLoop(HashGameClientInterface currentPlayer, HashGameClientInterface nextPlayer) throws RemoteException {
            nextPlayer.print("Wait for the other player's turn...");

            // parsing play
            String play = null;
            String result = "";
            while (result != null) {
                if (result != "") {
                    System.out.println("\n" + currentPlayer.getPlayerId() + " ERROR");
                    System.out.println(result + "\n");
                    currentPlayer.print(result + "\n");
                }

                currentPlayer.print("Enter your play:");
                System.out.println("Waiting for player " + currentPlayer.getPlayerId() + " to play.");
                String text = currentPlayer.scan();

                play = currentPlayer.getPlayerId() + ": " + text;

                String[] coords = text.split(" ");
                try {
                    result = game.auxMarkBoard(coords, currentPlayer.getPlayerId());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    currentPlayer.print(e.getMessage());
                    for (HashGameClientInterface client : players) {
                        client.endGame();
                    }
                    killGame();
                    return;
                }
            }

            printForAll(play);
            printForAll(game.hashView());
        }

        void printForAll(String str) throws RemoteException {
            PLAYER1().print(str);
            PLAYER2().print(str);
            System.out.println(str);
        }

        boolean canProceedWithGameFlow() throws RemoteException {
            switch (game.checkGameState()) {
                case draw:
                    printForAll("It\'s a draw!");
                    return false;
                case player1Win:
                    printForAll("Player " + PLAYER1().getPlayerId() + " with mark " + game.player1Mark + " has won! Congratulations!");
                    return false;
                case player2Win:
                    printForAll("Player " + PLAYER2().getPlayerId() + " with mark " + game.player2Mark + " has won! Congratulations!");
                    return false;
                case stillGoing:
                    // keep going
                    return true;
                default:
                    throw new GameStateException();
            }
        }

        HashGameClientInterface currentPlayer = null;
        HashGameClientInterface nextPlayer = null;
        HashGameClientInterface aux = null;


        public void run() {
            isRunning.set(true);

            int timesMainLoopRunned=0;
            while (isRunning.get()) {
                isStopped.set(false);
                try {
                    if (players.size() == 1 && timesMainLoopRunned == 0) {
                        System.out.println("Waiting for player 2");
                        PLAYER1().print("Waiting for the other player...");
                        timesMainLoopRunned++;
                    } else if (players.size() == 2) {
                        if (gameStarted.get()) {
                            aux = currentPlayer;
                            currentPlayer = nextPlayer;
                            nextPlayer = aux;
                        } else {
                            currentPlayer = PLAYER1();
                            nextPlayer = PLAYER2();
                            gameStarted.set(true);
                        }

                        // JOGO
                        playLoop(currentPlayer, nextPlayer);
                        isRunning.set(canProceedWithGameFlow());
                        gameStarted.set(isRunning.get());
                    }

                    sleepFor(1);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    isRunning.set(false);
                }
                isStopped.set(true);
            }

            try {
                for (HashGameClientInterface client : players) {
                    try {
                        client.endGame();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }

            System.exit(0);

        }
    }
}

package br.ufrn.rmi.hash_game.server.exceptions;

public class GameStateException extends RuntimeException {
    public GameStateException(){super("invalid game state");}
    GameStateException(String s) {super(s);}
}

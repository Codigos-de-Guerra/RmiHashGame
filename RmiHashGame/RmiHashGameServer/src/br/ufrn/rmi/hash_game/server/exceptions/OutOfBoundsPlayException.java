package br.ufrn.rmi.hash_game.server.exceptions;

public class OutOfBoundsPlayException extends RuntimeException {
    OutOfBoundsPlayException() {}

    public OutOfBoundsPlayException(String s) {super(s);}

    OutOfBoundsPlayException(short index, String s) {super("Can place yout mark at the " + String.valueOf(index) + " " + s );}
}

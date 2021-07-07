package br.ufrn.rmi.hash_game.server.tests;

import br.ufrn.rmi.hash_game.server.core.HashGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HashGameTest {


    private HashGame hashGame;

    @BeforeEach
    public void setUp() throws Exception {
        hashGame = new HashGame();
    }

    @Test
    @DisplayName("Should be a blank board")
    public void testInitialState() {
        assertEquals(hashGame.hashView(),
                " | | \n" +
                "-----\n" +
                " | | \n" +
                "-----\n" +
                " | | \n");
    }

    @Test
    @DisplayName("Should find Line 0")
    public void testLine0() {
        hashGame.markBoard(0, 0, hashGame.player1Mark);
        hashGame.markBoard(0, 1, hashGame.player1Mark);
        hashGame.markBoard(0, 2, hashGame.player1Mark);

        final Character mark = hashGame.hasLine();

        assertEquals(hashGame.player1Mark, hashGame.hasLine(),
                "Should Detect Lines");
        assertNotEquals(hashGame.player2Mark, mark, "Should not detect player 1");
        assertEquals(hashGame.hashView(), mark + "|" + mark + "|" + mark + "\n" +
                "-----\n" +
                " | | \n" +
                "-----\n" +
                " | | \n");

    }

    @Test
    @DisplayName("Should NOT find Line")
    public void testNoLine0() {
        hashGame.markBoard(0, 0, hashGame.player1Mark);
        hashGame.markBoard(0, 1, hashGame.player2Mark);
//        hashGame.markBoard(0, 2, hashGame.player1Mark);

        final Character mark = hashGame.hasLine();

        assertEquals(' ', hashGame.hasLine(),
                "Should NOT Detect Lines");
        assertEquals(hashGame.hashView(), hashGame.player1Mark + "|" + hashGame.player2Mark + "|" + mark + "\n" +
                "-----\n" +
                " | | \n" +
                "-----\n" +
                " | | \n");

    }

    @Test
    @DisplayName("Should NOT find Line")
    public void testNoLine1() {
        hashGame.markBoard(1, 0, hashGame.player1Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
        hashGame.markBoard(1, 2, hashGame.player1Mark);

        final Character mark = hashGame.hasLine();

        assertEquals(' ', hashGame.hasLine(),
                "Should NOT Detect Lines");
        assertEquals(hashGame.hashView(), " | | \n" +
                "-----\n" +
                hashGame.player1Mark + "|" + hashGame.player2Mark + "|" + hashGame.player1Mark + "\n" +
                "-----\n" +
                " | | \n");

    }

    @Test
    @DisplayName("Should find Line 1")
    public void testLine1() {
        hashGame.markBoard(1, 0, hashGame.player2Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
        hashGame.markBoard(1, 2, hashGame.player2Mark);


        final Character mark = hashGame.hasLine();
        assertEquals(hashGame.player2Mark, mark,
                "Should Detect Line player 2");
        assertNotEquals(hashGame.player1Mark, mark, "Should not detect player 1");
        assertEquals(hashGame.hashView(),
                " | | \n" +
                        "-----\n" +
                        mark + "|" + mark + "|" + mark + "\n" +
                        "-----\n" +
                        " | | \n");
    }

    @Test
    @DisplayName("Should find Line 2")
    public void testLine2() {
        hashGame.markBoard(2, 0, hashGame.player1Mark);
        hashGame.markBoard(2, 1, hashGame.player1Mark);
        hashGame.markBoard(2, 2, hashGame.player1Mark);

        final Character mark = hashGame.hasLine();

        assertEquals(hashGame.player1Mark, mark,
                "Should Detect Lines");
        assertNotEquals(hashGame.player2Mark, mark, "Should not detect player 2");
        assertEquals(hashGame.hashView(),
                " | | \n" +
                        "-----\n" +
                        " | | \n" +
                        "-----\n" +
                        mark + "|" + mark + "|" + mark + "\n");
    }

    @Test
    @DisplayName("Should find Column 0")
    public void testColumn0() {
        hashGame.markBoard(0, 0, hashGame.player1Mark);
        hashGame.markBoard(1, 0, hashGame.player1Mark);
        hashGame.markBoard(2, 0, hashGame.player1Mark);

        final Character mark = hashGame.hasColumn();

        assertEquals(hashGame.player1Mark, mark,
                "Should Detect Column");
        assertNotEquals(hashGame.player2Mark, mark, "Should not detect player 2");
    }

    @Test
    @DisplayName("Should find Column 1")
    public void testColumn1() {
        hashGame.markBoard(0, 1, hashGame.player2Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
        hashGame.markBoard(2, 1, hashGame.player2Mark);

        final Character mark = hashGame.hasColumn();

        assertEquals(hashGame.player2Mark, mark,
                "Should Detect Column");
        assertNotEquals(hashGame.player1Mark, mark, "Should not detect player 2");
    }

    @Test
    @DisplayName("Should find MAIN DIAGONAL")
    public void testMainDiagonalTrue() {
        hashGame.markBoard(0, 0, hashGame.player2Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
        hashGame.markBoard(2, 2, hashGame.player2Mark);

        final Character mark = hashGame.hasDiagonal();

        System.out.println(hashGame.hashView());

        assertEquals(hashGame.player2Mark, mark,
                "Should Detect Main DIAGONAL");
        assertNotEquals(hashGame.player1Mark, mark, "Should not detect player 2");
    }

    @Test
    @DisplayName("Should NOT find MAIN DIAGONAL 1")
    public void testMainDiagonalFalse() {
        hashGame.markBoard(0, 1, hashGame.player2Mark);
        hashGame.markBoard(1, 1, hashGame.player1Mark);
        hashGame.markBoard(2, 1, hashGame.player2Mark);

        final Character mark = hashGame.hasColumn();

        assertEquals(' ', mark,
                "Should not detect Column");
    }

    @Test
    @DisplayName("Should NOT find MAIN DIAGONAL 2")
    public void testMainDiagonalFalse2() {
        hashGame.markBoard(1, 1, hashGame.player2Mark);

        final Character mark = hashGame.hasColumn();

        assertEquals(' ', mark,
                "Should not detect Column");
    }

    @Test
    @DisplayName("Should find SECONDARY DIAGONAL")
    public void testSecondaryDiagonalTrue() {
        hashGame.markBoard(0, 2, hashGame.player1Mark);
        hashGame.markBoard(1, 1, hashGame.player1Mark);
        hashGame.markBoard(2, 0, hashGame.player1Mark);

        final Character mark = hashGame.hasDiagonal();

        System.out.println(hashGame.hashView());

        assertEquals(hashGame.player1Mark, mark,
                "Should Detect Diagonal win");
    }

    @Test
    @DisplayName("Should NOT find SECONDARY DIAGONAL")
    public void testSecondaryDiagonalFalse() {
        hashGame.markBoard(0, 2, hashGame.player1Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
//        hashGame.markBoard(2, 0, hashGame.player2Mark);

        final Character mark = hashGame.hasDiagonal();

        System.out.println(hashGame.hashView());

        assertEquals(' ', mark,
                "Should NOT Detect Diagonal win");
    }

    @Test
    @DisplayName("Should return game state stillGoing")
    public void testGameStillGoingState() {
        hashGame.markBoard(0, 2, hashGame.player1Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
//        hashGame.markBoard(2, 0, hashGame.player2Mark);

        System.out.println(hashGame.hashView());

        assertEquals(hashGame.checkGameState(), HashGame.HashGameStateEnum.stillGoing,
                "Game is still going on");
    }

    @Test
    @DisplayName("Should return game state draw")
    public void testGameDrawState() {
        hashGame.markBoard(0, 0, hashGame.player2Mark);
        hashGame.markBoard(0, 1, hashGame.player1Mark);
        hashGame.markBoard(0, 2, hashGame.player2Mark);

        hashGame.markBoard(1, 0, hashGame.player2Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
        hashGame.markBoard(1, 2, hashGame.player1Mark);

        hashGame.markBoard(2, 0, hashGame.player1Mark);
        hashGame.markBoard(2, 1, hashGame.player2Mark);
        hashGame.markBoard(2, 2, hashGame.player1Mark);
//        hashGame.markBoard(2, 0, hashGame.player2Mark);

        System.out.println(hashGame.hashView());

        assertEquals(hashGame.checkGameState(), HashGame.HashGameStateEnum.draw,
                "Game has ended in a draw");
    }

    @Test
    @DisplayName("Should return game state player1Win")
    public void testGamePlayer1WinState() {
        hashGame.markBoard(0, 0, hashGame.player2Mark);
        hashGame.markBoard(0, 1, hashGame.player1Mark);
        hashGame.markBoard(0, 2, hashGame.player1Mark);

        hashGame.markBoard(1, 0, hashGame.player2Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
        hashGame.markBoard(1, 2, hashGame.player1Mark);

        hashGame.markBoard(2, 0, hashGame.player1Mark);
        hashGame.markBoard(2, 1, hashGame.player2Mark);
        hashGame.markBoard(2, 2, hashGame.player1Mark);
//        hashGame.markBoard(2, 0, hashGame.player2Mark);

        System.out.println(hashGame.hashView());

        assertEquals(hashGame.checkGameState(), HashGame.HashGameStateEnum.player1Win,
                "Game has ended with player 1 win");
    }

    @Test
    @DisplayName("Should return game state player2Win")
    public void testGamePlayer2WinState() {
        hashGame.markBoard(0, 0, hashGame.player2Mark);
        hashGame.markBoard(0, 1, hashGame.player1Mark);
        hashGame.markBoard(0, 2, hashGame.player1Mark);

//        hashGame.markBoard(1, 0, hashGame.player2Mark);
        hashGame.markBoard(1, 1, hashGame.player2Mark);
//        hashGame.markBoard(1, 2, hashGame.player1Mark);

        hashGame.markBoard(2, 0, hashGame.player1Mark);
        hashGame.markBoard(2, 1, hashGame.player2Mark);
        hashGame.markBoard(2, 2, hashGame.player2Mark);
//        hashGame.markBoard(2, 0, hashGame.player2Mark);

        System.out.println(hashGame.hashView());

        assertEquals(hashGame.checkGameState(), HashGame.HashGameStateEnum.player2Win,
                "Game has ended with player 2 win");
    }



//        @RepeatedTest(5)
//        @DisplayName("Ensure correct handling of zero")
//        public void testMultiplyWithZero() {
//            assertEquals(0, calculator.multiply(0,5), "Multiple with zero should be zero");
//            assertEquals(0, calculator.multiply(5,0), "Multiple with zero should be zero");
//        }
}

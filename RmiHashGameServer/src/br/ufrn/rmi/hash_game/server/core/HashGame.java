package br.ufrn.rmi.hash_game.server.core;

import br.ufrn.rmi.hash_game.server.exceptions.OutOfBoundsPlayException;

public class HashGame {
    public enum HashGameStateEnum {
        player1Win,
        player2Win,
        draw,
        stillGoing,
    }

    public volatile Character[][] hashMap;
    final int LINES = 3;
    final int COLUMNS = 3;
    public final Character player1Mark;
    public final Character player2Mark;

    public HashGame() {
        // populate hashMap
        hashMap = new Character[LINES][COLUMNS];
        resetHashMap();

        // set player marks
        this.player1Mark = 'X';
        this.player2Mark = 'O';

        // assert that player's marks are not equal
        assert (this.player1Mark.compareTo(this.player2Mark) != 0);
        // assert playerMark are not an empty space ' '
        assert (this.player1Mark.compareTo(' ') != 0);
        assert (this.player2Mark.compareTo(' ') != 0);
    }

    // fill hashMap with blank characters
    void resetHashMap() {
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                hashMap[i][j] = ' ';
            }
        }
    }

    public String auxMarkBoard(String[] coords, int playerId) {
        int line = 0;
        int column = 0;
        try {
            if(coords.length != 2) {
                throw new Exception("\nMust type only 2 integers in range[0,2] to represent position in hashGame\nTry again");
            }
            line = Integer.parseInt(coords[0]);
            column = Integer.parseInt(coords[1]);

            return markBoard(line, column, playerId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String markBoard(int line, int column, int playerId) {
        Character mark = player1Mark;
        if (playerId == 2) {
            mark = player2Mark;
        }
        try {
            if (line >= LINES) {
                throw new OutOfBoundsPlayException("Line can't be greater or equal than " + String.valueOf(LINES));
            }
            if (column >= COLUMNS) {
                throw new OutOfBoundsPlayException("Column can't be greater or equal than " + String.valueOf(COLUMNS));
            }
            if (hashMap[line][column].compareTo(' ') != 0) {
                throw new OutOfBoundsPlayException("This position has already been filled.");
            }

            hashMap[line][column] = mark;
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public Character hasColumn() {
        Character mark = ' ';

        for (int j = 0; j < COLUMNS; j++) {

            if (hashMap[0][j].compareTo(' ') == 0) {
                continue;
            }

            mark = hashMap[0][j];

            for (int i = 1; i < LINES; i++) {
                if (hashMap[i][j].compareTo(mark) != 0) {
                    mark = ' ';
                    break;
                }
            }

            if (mark.compareTo(' ') != 0) {
                return mark;
            }
        }
        return mark;
    }

    public Character hasLine() {
        Character mark = ' ';

        for (int i = 0; i < LINES; i++) {

            if (hashMap[i][0].compareTo(' ') == 0) {
                continue;
            }

            mark = hashMap[i][0];

            for (int j = 1; j < COLUMNS; j++) {
                if (hashMap[i][j].compareTo(mark) != 0) {
                    mark = ' ';
                    break;
                }
            }

            if (mark.compareTo(' ') != 0) {
                return mark;
            }
        }
        return mark;
    }

    public Character hasDiagonal() {
        Character mark = player2Mark;
        Character result = ' ';

        // Can get a valid diagonal if LINES != COLUMN
        if (LINES != COLUMNS) {
            return result;
        }

        // reading main diagonal
        for (int n = 0; n < LINES; n++) {
            if (hashMap[n][n].compareTo(' ') == 0) {
                result = ' ';
                break;
            }

            if (n == 0) {
                mark = hashMap[n][n];
                result = hashMap[n][n];
            } else {
                if (mark.compareTo(hashMap[n][n]) != 0) {
                    result = ' ';
                    break;
                }
            }
        }

        // if could the main diagonal has a valid combination, return
        if (result.compareTo(mark) == 0) {
            return result;
        }

        // reading secondary diagonal
        for (int n = 0; n < LINES; n++) {
            int i = n;
            int j = LINES - (n + 1);

            if (hashMap[i][j].compareTo(' ') == 0) {
                result = ' ';
                break;
            }

            if (n == 0) {
                mark = hashMap[i][j];
                result = hashMap[i][j];
            } else {
                if (mark.compareTo(hashMap[i][j]) != 0) {
                    result = ' ';
                    break;
                }
            }
        }
        return result;
    }

    public boolean boardIsComplete() {
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (hashMap[i][j].compareTo(' ') == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check for 3 winning conditions
    public Character winnerMark() {
        Character winnerMark = hasLine();
        if (winnerMark.compareTo(' ') == 0) {
            winnerMark = hasColumn();
            if (winnerMark.compareTo(' ') == 0) {
                winnerMark = hasDiagonal();
            }
        }
        return winnerMark;
    }

    public HashGameStateEnum checkGameState() {
        Character winnerMark = winnerMark();
        if (winnerMark.compareTo(player1Mark) == 0) {
            return HashGameStateEnum.player1Win;
        } else if (winnerMark.compareTo(player2Mark) == 0) {
            return HashGameStateEnum.player2Win;
        } else if (boardIsComplete()) {
            return HashGameStateEnum.draw;
        }

        return HashGameStateEnum.stillGoing;
    }

    public String hashView() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                stringBuilder.append(hashMap[i][j]);

                // if not on the last 2 positions
                if (j < COLUMNS - 1) {
                    // append the column divisor
                    stringBuilder.append("|");
                }
            }
            stringBuilder.append('\n');


            // if not on the last 2 positions
            if (i < LINES - 1) {
                // append the line divisor "------"
                for (int t = 0; t < (2 * COLUMNS - 1); t++) {
                    stringBuilder.append('-');
                }
                stringBuilder.append('\n');
            }
        }

        return stringBuilder.toString();
    }
}

package game;

import java.util.Arrays;

public class Field {

    private static String[] field = new String[9];

    private static void fieldInitialize() {
        Arrays.fill(field, " ");
    }

    public void gameStart() {
        fieldInitialize();
    }

    public static boolean hasMove() {
        for (String str : field) {
            if (str.equals(" ")) {
                return true;
            }
        }
        return false;
    }

    public static void setMove(int move, String mark) {
        field[move] = mark;
    }

    public static boolean cellIsEmpty(int move) {
        return (field[move]).equals(" ");
    }

    public static boolean isWin() {

        return (!field[0].equals(" ") && field[0].equals(field[1]) && field[1].equals(field[2])) ||
                (!field[3].equals(" ") && field[3].equals(field[4]) && field[4].equals(field[5])) ||
                (!field[6].equals(" ") && field[6].equals(field[7]) && field[7].equals(field[8])) ||
                (!field[0].equals(" ") && field[0].equals(field[3]) && field[3].equals(field[6])) ||
                (!field[1].equals(" ") && field[1].equals(field[4]) && field[4].equals(field[7])) ||
                (!field[2].equals(" ") && field[2].equals(field[5]) && field[5].equals(field[8])) ||
                (!field[0].equals(" ") && field[0].equals(field[4]) && field[4].equals(field[8])) ||
                (!field[2].equals(" ") && field[2].equals(field[4]) && field[4].equals(field[6]));

    }

    public static boolean isDraw() {
        return (!hasMove() && !isWin());
    }

    public static boolean gameFinished() {

        return isDraw() || isWin();
    }

}



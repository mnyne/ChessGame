import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PGNParser parser = new PGNParser("C:\\Users\\Michael\\IdeaProjects\\ChessGame\\Kasparov.pgn");
        ArrayList<String> moves = parser.getMovesFromGame(0);
        for (int i = 0; i < moves.size(); i++) {
            String[] splitMoves = parser.splitMoveIntoSegments(moves.get(i));
            for (int j = 0; j < splitMoves.length; j++) {
                System.out.println(splitMoves[j]);
            }
        }
    }
}
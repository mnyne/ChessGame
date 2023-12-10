import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PGNParser {

    public PGNParser(String filename) {
        File PGNFile;
        try {
            PGNFile = new File(filename);
            parsePGN(PGNFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parsePGN(File PGNFile) {
        ArrayList<HashMap<String, String>> gamesInPGN = new ArrayList<>();
        HashMap<String, String> game = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PGNFile))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("[")) {
                    String[] parts = splitLineIntoKeyAndAttributes(line);
                    String key = parts[0];
                    String attributes = parts[1];
                    game.put(key, attributes);
                } else if (line.isEmpty()) {
                    line = reader.readLine();

                    if (line.startsWith("1.")) {
                        StringBuilder moves = new StringBuilder();
                        moves.append(line);

                        while ((line = reader.readLine()) != null && !line.isEmpty()) {
                            moves.append(line).append(" ");
                        }

                        game.put("Moves", moves.toString());
                        gamesInPGN.add(game);
                        game = new HashMap<>();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < gamesInPGN.size(); i++) {
            System.out.println(gamesInPGN.get(i));
        }
        System.out.println("Nr. of games: " + gamesInPGN.size());
    }


    private String[] splitLineIntoKeyAndAttributes(String line) {
        String[] parts = line.split(" ", 2);
        String key = parts[0].substring(1, parts[0].length());
        String attributes = parts[1].substring(1, parts[1].length() - 2);
        return new String[]{key, attributes};
    }
}
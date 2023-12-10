package Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PGNParser {
    //todo: get into hashmaps here!

   //[Event "Troll Masters"]
    //[Site "Gausdal NOR"]
    //[Date "2001.01.05"]
    //[Round "1"]
    //[White "Edvardsen,R"]
    //[Black "Carlsen,Magnus"]
    //[Result "1/2-1/2"]
    //[WhiteElo "2055"]
    //[BlackElo ""]
    //[ECO "D12"]
    //
    //1.d4 Nf6 2.Nf3 d5 3.e3 Bf5 4.c4 c6 5.Nc3 e6 6.Bd3 Bxd3 7.Qxd3 Nbd7 8.b3 Bd6
    //9.O-O O-O 10.Bb2 Qe7 11.Rad1 Rad8 12.Rfe1 dxc4 13.bxc4 e5 14.dxe5 Nxe5 15.Nxe5 Bxe5
    //16.Qe2 Rxd1 17.Rxd1 Rd8 18.Rxd8+ Qxd8 19.Qd1 Qxd1+ 20.Nxd1 Bxb2 21.Nxb2 b5
    //22.f3 Kf8 23.Kf2 Ke7  1/2-1/2



    public PGNParser() {
        File PGNFile;
        try {
            PGNFile = new File("test.pgn");
            parsePGN(PGNFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parsePGN(File PGNFile) {
        //new arraylist storing all entries
        //while file is not empty
        //hashmap at arrayindex
        //for each line
        //if line starts with [
        //splitLineIntoKeyAndAttributes()
        //if line is "" && next line starts with 1.
        //key "moveset" value moves
        //else
        //arrayIndex +=

        ArrayList<HashMap<String, String>> gamesInPGN = new ArrayList<>();


    }
}
package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;


/**
 * Created by bcoll on 5/3/2017.
 */
public class Board implements GameBoard{

    private IO boardIO;
    private int numHouses;
    private List<PlayerPrinter> players;


    public Board(IO io) {
        boardIO = io;
    }

    public void setPlayers(List<PlayerPrinter> players){
        this.players = players;
    }

    //Print current state of board, prompt for input
    public void printBoard(){

        if(numHouses == 0){
            numHouses = players.get(0).getHousePrinters().size();
        }

        PlayerPrinter p1 = players.get(0);
        PlayerPrinter p2 = players.get(1);

        String separatorStart = "+----+";
        String separatorPerHouse ="";
        String separatorEnd = "----+";

        String middleEnds = "|    |";

        String p2Houses = "| P2 |";
        String p1Houses = "|" +p2.getStorePrinter().toString() +  "|";

        for (int i=0; i < numHouses ; i++ ){
            p2Houses += (p2.getHousePrinters().get(numHouses-1-i).toString() + "|");
            p1Houses += (p1.getHousePrinters().get(i).toString() + "|");
            separatorPerHouse += "-------+";
        }
        p2Houses += (p1.getStorePrinter().toString() + "|");
        p1Houses += " P1 |";
        String middleMiddle = separatorPerHouse.substring(0,separatorPerHouse.length()-1);

        String separatorString = separatorStart + separatorPerHouse + separatorEnd;
        String middleString = middleEnds + middleMiddle + middleEnds;

        //Print the board
        boardIO.println(separatorString);
        boardIO.println(p2Houses);
        boardIO.println(middleString);
        boardIO.println(p1Houses);
        boardIO.println(separatorString);
    }
}

/*
 * Copyright 2014 Francesco Corvino <fcorvino86@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fcorvino.it.fitet.input;

import static fcorvino.it.fitet.input.SimpleLoader.printRanking;
import static fcorvino.it.fitet.input.SimpleLoader.printResults;
import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.model.UniqueNamePlayer;
import fcorvino.it.fitet.roundutil.RoundRanking;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InputFromTextFile 
 * 
 * classe per caricare i dati direttamente da file testuali nel formato:
 * 
 * nome giocatore A \t nome giocatore B \t risultato \t set1;set2;set3;...
 * 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class InputFromTextFile {
    private static String testString = 
            "A\tB\t3-0\t11-8, 11-5, 11-9\n" +
            "C\tD\t3-1\t11-7, 5-11, 11-9, 11-6\n" +
            "A\tC\t3-0\t11-5, 11-7, 11-5\n" +
            "B\tD\t2-3\t7-11, 9-11, 11-9, 11-5, 9-11\n" +
            "D\tA\t1-3\t11-8, 7-11, 14-16, 8-11\n" +
            "B\tC\t3-2\t5-11, 12-10, 12-10, 8-11, 11-7";
    
    public SimpleRound getRound(String roundString){
        SimpleRound r = new SimpleRound();
        String[] matchs = roundString.split("\n");
        for(int i =0;i<matchs.length; i++){
            String parts[] = matchs[i].split("\t");
            SimplePlayer[] pl = addPlayers(parts, r);
            String sets[] = parts[3].split(",");
            SimpleMatch m = completeMatch(sets, pl);
            r.addMatch(m);
            //for(int j=0;j<parts.length;j++) System.out.print("String " + j + "[" + parts[j] + "]");
            //System.out.println("");
        }
        return r;
    }
    
    public static void main(String args[]){
        InputFromTextFile input = new InputFromTextFile();
        //input.pathInput = "../examples/out-ex-gir7a";
        //input.pathInput = "out-ex-gir7a";
        System.out.println(UniqueNamePlayer.getPlayer("Corvino Francesco"));
        SimpleRound r = input.getRound(testString);
        System.out.println("Termino: " + r.getNumMatch() + " players: " + r.getNumPlayers());
        SimpleLoader.printResults(r);
        RoundRanking ranking = new RoundRanking(r);
        ranking.generateRanking();
        SimpleLoader.printRanking(ranking);
    }

    private SimplePlayer[] addPlayers(String[] parts, SimpleRound r) {
        SimplePlayer pl1 = UniqueNamePlayer.getPlayer(parts[0]), 
                pl2 = UniqueNamePlayer.getPlayer(parts[1]);
        if(!r.containPlayer(pl1)) r.addPlayer(pl1);
        if(!r.containPlayer(pl2)) r.addPlayer(pl2);
        return new SimplePlayer[]{ pl1, pl2};
    }

    private SimpleMatch completeMatch(String[] sets, SimplePlayer[] pl) throws NumberFormatException {
        SimpleMatch m = new SimpleMatch(sets.length);
        m.setFirstPlayer(pl[0]);
        m.setSecondPlayer(pl[1]);
        for( int j =0;j<sets.length;j++){
            String point[] = sets[j].split("-");
            int p1 = Integer.valueOf(point[0].trim());
            int p2 = Integer.valueOf(point[1].trim());
            m.getSets().get(j).setPntPlayer1(p1);
            m.getSets().get(j).setPntPlayer2(p2);
        }
        return m;
    }

}

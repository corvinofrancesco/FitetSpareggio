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

package fcorvino.it.fitet.output;

import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.model.SimpleSet;
import fcorvino.it.fitet.roundutil.RoundRanking;
import fcorvino.it.fitet.roundutil.SimpleRank;

/**
 * SimplePrinter 
 * Stampa su console java
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class SimplePrinter {

    
    public void printRanking(RoundRanking ranking){
        System.out.println("---- Classifica -------");
        for(SimpleRank r : ranking.getRanks()){
            r.calculateDifferences();
            System.out.println(" * " + r.getPoint() + " - " + r.getPlayer() + " | gruppo di " 
                    + r.getGroup().size() + " | differenze: " + r.getDifferenceSet() + " - " + r.getDifferencePoint() );
        }        
    }     
    
    public void printResults(SimpleRound round){
        for(int i=0; i<round.getNumMatch(); i++){
            SimpleMatch m = round.getMatch(i);
            int result[] = m.getResult();
            System.out.print(m.getFirstPlayer() + " - " + m.getSecondPlayer() + "-->" + result[0] +  "-" + result[1] + " {");
            for(SimpleSet s : m.getSets()) System.out.print(s.getPntPlayer1() + "-" + s.getPntPlayer2() + " ");
            System.out.println("}");
        }        
    }    
    
    public void printTable(OutputMatrix matrix){

        int size = matrix.size();
        
        System.out.print("--- Players \\ (id):\t");
        for(int i =0 ; i< size; i++){
            System.out.print("(" + i + ")\t");
        }
        System.out.println("");
        
        for(int i =0 ; i< size; i++){
            String name = matrix.getPlayer(i).toString();
            if(name.length()>15){
                name = name.substring(0, 15);
            }
            System.out.print(name + " (" + i + ") : \t");
            for (int j =0 ; j<size; j++){
                OutputMatrixElement e = matrix.get(i, j);
                System.out.print(e.getResult() + "\t|");
            }
            System.out.println("");
        }
    }
}

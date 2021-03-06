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
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * OutputMatrix 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class OutputMatrix {
    private ArrayList<OutputMatrixElement> matrix;
    private HashMap<String,ArrayList<String>> totalRows;

    private ArrayList<SimplePlayer> players;

    public OutputMatrix() {
        matrix = new ArrayList<OutputMatrixElement>();
        players = new ArrayList<SimplePlayer>();
        totalRows = new HashMap<String, ArrayList<String>>();
    }    
    
    public OutputMatrixElement get(int indexPlayer1, int indexPlayer2){
        OutputMatrixElement el = null;
        try {
        if(indexPlayer1 == indexPlayer2) return new OutputMatrixElement(players.get(indexPlayer1));
        SimplePlayer p1 = players.get(indexPlayer1), p2 = players.get(indexPlayer2);
        for(OutputMatrixElement e : matrix){
            if(e.containsPlayer(p1) && e.containsPlayer(p2)){
                    if(e.getP1().equals(p1)) return e;
                    return e.invert();
                }
        }
        } catch (Exception e){
            return new OutputMatrixElement(
                    new SimplePlayer("Giocatore " + indexPlayer1, ""),
                    new SimplePlayer("Giocatore " + indexPlayer2, ""),
                    "errore");
        }
        return el;
    }
    
    public String get(int index, String nameProp){
        try {
            return totalRows.get(nameProp).get(index);
        } catch (Exception e){}
        return "";
    }

    public boolean add(OutputMatrixElement e) {
        return matrix.add(e);
    }

    public boolean add(SimplePlayer e) {
        return players.add(e);
    }

    public void setGroupPlayers(ArrayList<SimplePlayer> group) {
        this.players = group;
    }
    
    public SimplePlayer getPlayer(int index){
        return players.get(index);
    }

    public int size() {
        return players.size();
    }
    
    public static OutputMatrix create(SimpleRound r){
        OutputMatrix m = new OutputMatrix();
        for(int i =0; i< r.getNumPlayers();i++) m.add(r.getPlayer(i));
        for(int i=0; i<r.getNumMatches();i++) {
            SimpleMatch match = r.getMatch(i);
            int result[] = match.getResult();
            m.add(
                new OutputMatrixElement(
                match.getFirstPlayer(), 
                match.getSecondPlayer(),
                result[0] + "-" + result[1]
                ));
        }
        return m;
    }
    
    public static OutputMatrix createForGroup(SimpleRound r, ArrayList<SimplePlayer> group){
        OutputMatrix m = new OutputMatrix();
        m.setGroupPlayers(group);
        for(int i=0; i<r.getNumMatches();i++) {
            SimpleMatch match = r.getMatch(i);
            boolean flagPlayers = false;
            for(SimplePlayer p : group) if ( match.containPlayer(p)) flagPlayers = true;;
            if(!flagPlayers) continue;
            int result[] = match.getResult();
            m.add(
                new OutputMatrixElement(
                match.getFirstPlayer(), 
                match.getSecondPlayer(),
                result[0] + "-" + result[1]
                ));
        }
        return m;
    }

}

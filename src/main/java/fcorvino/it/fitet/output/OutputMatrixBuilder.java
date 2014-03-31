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

/**
 * OutputMatrixBuilder 
 * classe che consente di costruire un tabellone a partire da un @{link SimpleRound}
 * 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class OutputMatrixBuilder {
    private SimpleRound round;
    private ArrayList<SimplePlayer> group;
    private TableMethod tableMethod = null;
    
    private OutputMatrixBuilder() {
        round = new SimpleRound();
        group = new ArrayList<SimplePlayer>();          
    }
 
    public OutputMatrixBuilder setTableMethod(TableMethod method){
        this.tableMethod = method;
        return this;
    }
    
    /**
     * Metodo principale invoca la creazione della tabella
     * @return @{link OutputMatrix} secondo le regole specificate
     */
    public OutputMatrix build(){
        OutputMatrix m = new OutputMatrix();
        // inizializzare i players
        m.setGroupPlayers(group);
        // seleziona i match
        for(int i=0; i<round.getNumMatches();i++) {
            SimpleMatch match = round.getMatch(i);
            if(!tableMethod.validMatch(match)) continue;
            String result = tableMethod.calculateResult(match);
            m.add(
                new OutputMatrixElement(
                match.getFirstPlayer(), 
                match.getSecondPlayer(),
                   result
                ));
        }
        return m;
    }
    
    public static OutputMatrixBuilder tableForMatch(SimpleRound round){
        OutputMatrixBuilder b = new OutputMatrixBuilder();
        b.round = round;
        b.group = new ArrayList<SimplePlayer>();
        for(int i=0;i<round.getNumPlayers();i++) b.group.add(round.getPlayer(i));
        return b;
    }
    
    public static OutputMatrixBuilder tableForGroup(SimpleRound round, ArrayList<SimplePlayer> group){
        OutputMatrixBuilder b = new OutputMatrixBuilder();
        b.round = round;
        b.group = group;        
        return b;        
    }


    public interface TableMethod {
        public String calculateResult(SimpleMatch m );
        public boolean validMatch(SimpleMatch m);
    }
    
    public static class DefaultTableMethod implements TableMethod {
        private boolean resultInDiffPoint = false;
        
        public void makeForSet(){
            resultInDiffPoint = false;
        }
        
        public void makeForPoint(){
            resultInDiffPoint = true;
        }
        
        public String calculateResult(SimpleMatch match) {
            int result[] = match.getResult();
            if(resultInDiffPoint) result = match.getResultPointsInSet();
            return result[0] + "-" + result[1];
        }
    
        public boolean validMatch(SimpleMatch match) {
            return true;
        }
        
    }

    /**
     * Seleziona solo i match disputati tra i giocatori presenti nel gruppo 
     */
    public static class GroupTableMethod extends DefaultTableMethod {
        private ArrayList<SimplePlayer> group = null;

        public GroupTableMethod(ArrayList<SimplePlayer> group) {
            this.group = group;
        }
    
        public boolean validMatch(SimpleMatch match) {
            for(SimplePlayer p1 : group) if ( match.getFirstPlayer().equals(p1)) {
                ArrayList<SimplePlayer> group2 = new ArrayList<SimplePlayer>(group);
                group2.remove(p1);
                for(SimplePlayer p2 : group2) if ( match.getSecondPlayer().equals(p2)) 
                    return true;                
            }
            return false;
        }
        
    }
    
}

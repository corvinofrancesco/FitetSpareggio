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

package fcorvino.it.fitet.model;

import java.util.ArrayList;

/**
 *
 * @author Francesco Corvino
 */
public class SimpleMatch {
    private SimplePlayer firstPlayer;
    private SimplePlayer secondPlayer;
    private ArrayList<SimpleSet> sets;

    private static void initMatch(SimpleMatch m, int numSet){
        m.sets = new ArrayList<SimpleSet>();
        for(int i = 0; i<numSet; i++){
            m.sets.add(new SimpleSet());
        }                
    }
    
    public SimpleMatch(int numSet){
        initMatch(this, numSet);
    }
    
    public SimpleMatch() {
        initMatch(this, 3);
    }
    
    public SimplePlayer getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(SimplePlayer firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public SimplePlayer getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(SimplePlayer secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public ArrayList<SimpleSet> getSets() {
        return sets;
    }

    public void setSets(ArrayList<SimpleSet> sets) {
        this.sets = sets;
    }    
    
    public int getPointPlayer1(){
        int result[] = getResult();
        switch(result[0]){
            case 3: return 2;
            case 2: return 1;
            default:
                return 0;
        }
    }
    
    public int getPointPlayer(SimplePlayer p){
        int result[] = getResult();
        int index_pl = p.equals(firstPlayer)?0:1;
        switch(result[index_pl]){
            case 3: return 2;
            case 2: return 1;
            default:
                return 0;
        }        
    }
    
    /**
     * 
     * @return conteggio totale dei punti nei vari set
     */
    public int[] getResultPointsInSet(){
        int result[] = {0,0};
        for(int i=0;i<sets.size();i++){
            if(!sets.get(i).isValid()) continue;
            result[0] += sets.get(i).getPntPlayer1();
            result[1] += sets.get(i).getPntPlayer2();
        }
        return result;                
    }
    
    /**
     * Restituisce il punteggio in termini di numero di set
     * vinti dal primo giocatore e quelli vinti dal secondo giocatore
     * @return int[0] set vinti dal primo giocatore, int[1] set vinti dal secondo giocatore
     */
    public int[] getResult(){
        int result[] = {0,0};
        for(int i=0;i<sets.size();i++){
            if(!sets.get(i).isValid()) continue;
            int plIndex = (int) (0.5 + 0.5 * sets.get(i).getWinner());
            result[plIndex] += 1;
        }
        return result;        
    }

    public boolean containPlayer(SimplePlayer p) {
        if(p.equals(this.firstPlayer)) return true;
        if(p.equals(this.secondPlayer)) return true;
        return false;
    }
    
    public SetDifference getDifferenceFor(SimplePlayer p){
        SetDifference diff = new SetDifference(this, p);
        return diff;
    }

    /**
     * Metodo equals valido solo per incontri nello stesso girone
     * controlla solo se i due giocatori sono gli stessi
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SimpleMatch){
            SimpleMatch s = (SimpleMatch) obj;
            if(s.containPlayer(firstPlayer) && s.containPlayer(secondPlayer)) return true;
        }
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.firstPlayer != null ? this.firstPlayer.hashCode() : 0);
        hash = 17 * hash + (this.secondPlayer != null ? this.secondPlayer.hashCode() : 0);
        return hash;
    }    
    
}

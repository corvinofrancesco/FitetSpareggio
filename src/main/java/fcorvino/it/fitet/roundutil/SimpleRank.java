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

package fcorvino.it.fitet.roundutil;

import fcorvino.it.fitet.model.SetDifference;
import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * SimpleRank
 * Descrive un posizionamento nella classifica.
 * 
 * Definisce il giocatore a cui è associato, l'insieme di incontri disputati dal
 *  giocatore e le proprietà che descrivono la posizione.
 * 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class SimpleRank {
    public static final String DIFFERENCESET = "difference-set";
    public static final String DIFFERENCEPOINT = "difference-point";
    public static final String DIFFERENCES = "differences";
    public static final String GROUP = "group";
    
    private Integer position = 0;
    private SimplePlayer player;
    private ArrayList<SimpleMatch> matches;
    private HashMap<String, Object> properties;
    
    /**
     * Inizializza la classe recuperando le informazioni necessarie.
     * Dal girone prende i match che interessano l'utente e salva il giocatore
     * interessanto dalla posizione.
     * 
     * @param r girone.
     * @param p giocatore associato alla posizione nella classifica.
     */
    public SimpleRank(SimpleRound r, SimplePlayer p) {
        this.player = p;
        this.matches = new ArrayList<SimpleMatch>();
        this.properties = new HashMap<String, Object>();
        for(int i =0; i<r.getNumMatches(); i++){
            if(r.getMatch(i).containPlayer(p)){
                matches.add(r.getMatch(i));
            }
        }
    }
    
    /**
     * Calcola i punti ottenuti.
     * Effettua una sommatoria del metodo {@link SimpleMatch#getPointPlayer(fcorvino.it.fitet.model.SimplePlayer) }
     * di tutte le partite disputate dal giocatore inserite nella proprietà <b>matches</b>.
     * 
     * @return totale punti ottenuti.
     */
    public int getPoint(){
        int point =0;
        for(SimpleMatch m : matches){
            point += m.getPointPlayer(player);
        }
        return point;
    }

    public SimplePlayer getPlayer() {
        return this.player;
    }
    
    public ArrayList<SimplePlayer> getGroup(){
        return (ArrayList<SimplePlayer>) properties.get(GROUP);
    }
    
    public void setGroup(ArrayList<SimplePlayer> group){
        properties.put(GROUP, group);
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
         
    public int getDifferenceSet(){
        return (Integer) properties.get(DIFFERENCESET);
    }

    public int getDifferencePoint(){
        return (Integer) properties.get(DIFFERENCEPOINT);
    }

    public Integer getPosition() {
        return position;
    }
        
    public HashMap<String, Object> getProperties() {
        return properties;
    }
        
    public void calculateDifferences(){
        ArrayList<SimplePlayer> group = (ArrayList<SimplePlayer>) properties.get(GROUP);
        ArrayList<SetDifference> diffs = new ArrayList<SetDifference>();
        int totWinnerSet = 0, totWinnerPoint = 0;
        for(SimpleMatch m : matches){
            SetDifference dif = m.getDifferenceFor(player);
            if(!group.contains(dif.getAdvPlayer())) continue;
            diffs.add(dif);
            totWinnerSet += dif.getDifferenceSetValue();
            totWinnerPoint += dif.getDifferencePointValue();
        }
        properties.put(DIFFERENCES, diffs);
        properties.put(DIFFERENCESET, totWinnerSet);
        properties.put(DIFFERENCEPOINT, totWinnerPoint);
    }
    
}

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

import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * RoundRanking
 * Specifica il posizionamento dei giocatori nella classifica fornendo alcuni metodi
 * per definire le regole da applicare.
 * 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class RoundRanking {
    private SimpleRound round;

    private ArrayList<SimpleRank> ranks = null;
    private ArrayList<ArrayList<SimplePlayer>> groups = null;
    
    
    public RoundRanking(SimpleRound round) {
        this.round = round;
    }
    
    /**
     * Inizializza la classifica. 
     * Fase 1: Vengono aggiunti tutti i giocatori nella lista della classifica,
     * la lista precedente viene eliminata, <b>non viene fatto nessun ordinamento</b>.
     * 
     */
    public void initRanks(){
        ranks = new ArrayList<SimpleRank>();
        for(int i = 0; i < round.getNumPlayers(); i++){
            SimpleRank r = new SimpleRank(round, round.getPlayer(i));
            ranks.add(r);
        }
    }
    
    /**
     * Effettua l'ordinamento della classifica.
     * Fase 2-4: consente di ordirare la classifica secondo delle regole
     * specificate nel comparatore.
     * Si veda le classi:
     * <ul>
     * <li>{@link RankingByDifferences}: compara dopo che sono state calcolate le differenze</li>
     * <li>{@link RankingByPoint}: comparatore basilare che effettua la somma dei punti nel girone</li>
     * </ul>
     */
    public void reorderRanks(Comparator<SimpleRank> c){
        Collections.sort(ranks, c);        
    }
    
    public void updatePositions(){
        int i =1;
        for(SimpleRank r : ranks){
            r.setPosition(i);
            i++;
        }
    }

    /**
     * Crea i raggruppamenti di giocatori.
     * Fase 3: crea i raggruppamenti dei giocatori, deve esser stato effettuato 
     * già l'ordinamento, altrimenti i gruppi non vengono creati correttamente.
     * 
     * Ogni gruppo viene creato confrontando i punteggi dei vari giocatori con il
     * metodo {@link SimpleRank#getPoint() }.
     * 
     * @return numero di gruppi creati
     */
    public int createGrouping(){
        if(ranks==null) initRanks();
        int currPoint = ranks.get(0).getPoint();
        ArrayList<SimplePlayer> currGroup = new ArrayList<SimplePlayer>();
        groups = new ArrayList<ArrayList<SimplePlayer>>();
        for(SimpleRank r : ranks){
            if(currPoint!= r.getPoint()){
                groups.add(currGroup);
                currGroup = new ArrayList<SimplePlayer>();
                currPoint = r.getPoint();
            }
            currGroup.add(r.getPlayer());
        }
        // inserisce l'ultimo gruppo creato
        groups.add(currGroup);
        for(ArrayList<SimplePlayer> group : groups){
            for(SimplePlayer p : group){
                this.getPlayerRank(p).setGroup(group);
            }
        }
        return groups.size();
    }
       
    /**
     * Genera il ranking.
     * Per generarlo esegue le fasi di default.
     */
    public void generateRanking(){
        initRanks();
        Collections.sort(ranks, new RankingByPoint());
        // raggruppa i giocatori a pari punti 
        createGrouping();
        // calcola le differenze parziali
        for(SimpleRank r : ranks){
            r.calculateDifferences();
        }
        // effettua l'ordinamento sulle differenze
        Collections.sort(ranks, new RankingByDifferences());         
        updatePositions();
    }
    
    /**
     * Restituisce il corrispondente {@link SimpleRank}.
     * Per un giocatore {@link SimplePlayer} ricerca il rank dove è posizionato.
     * 
     * @param p oggetto identificativo del giocatore.
     * @return {@link SimpleRank} oggetto che specifica il posizionamento nella classifica.
     */
    private SimpleRank getPlayerRank(SimplePlayer p) {
        for(SimpleRank r : ranks){
            if(r.getPlayer().equals(p)) return r;
        }
        return null;
    }
    
    public ArrayList<SimpleRank> getRanks() {
        return ranks;
    }

    public ArrayList<ArrayList<SimplePlayer>> getGroups() {
        return groups;
    }    
    
}

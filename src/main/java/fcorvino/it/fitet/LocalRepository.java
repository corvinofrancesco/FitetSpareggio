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

package fcorvino.it.fitet;

import fcorvino.it.fitet.dto.MatchDTO;
import fcorvino.it.fitet.dto.PlayerDTO;
import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.util.Common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * LocalRepository 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class LocalRepository {
    private ArrayList<PlayerDTO> players;
    private SimpleRound round;
    private HashMap<String, Object> properties;
    
    public LocalRepository() {
        players = new ArrayList<PlayerDTO>();
        round = new SimpleRound();
        properties = new HashMap<String, Object>();
    }
    
    public ArrayList<PlayerDTO> getPlayers() {
        return players;
    }

    public SimpleRound getRound() {
        return round;
    }    

    public Object get(String key) {
        return properties.get(key);
    }

    public Object put(String key, Object value) {
        return properties.put(key, value);
    }

    public Object remove(String key) {
        return properties.remove(key);
    }
    
    
    
    /**
     * Ricerca un giocatore nel repositori
     * 
     * @param searchCode può rappresentare l'id o il cognome del giocatore
     * @return 
     */
    public PlayerDTO findPlayer(String searchCode){
//        Collections.sort(players, new Comparator<PlayerDTO>(){
//            public int compare(PlayerDTO o1, PlayerDTO o2) {                
//                return o1.getId().compareTo(o2.getId());
//            }
//        });
        for(PlayerDTO p : players){
            if(searchCode.compareTo(p.getId().toString()) == 0) return p;
        }
        for(PlayerDTO p : players){
            if(searchCode.compareTo(p.getSurname()) == 0) return p;
        }
        return null;
    } 
    
    public PlayerDTO searchAndAddPlayer(String serachCode){
        PlayerDTO p1 = findPlayer(serachCode);
        int numPlayer = players.size();
        if(p1==null) {
            p1 = new PlayerDTO(numPlayer, "" , serachCode);
            players.add(p1);
        }
        return p1;
    }
    
    public MatchDTO findMatch(PlayerDTO p1, PlayerDTO p2){
        int size_match = round.getMatches().size();
        for(int i =0;i<size_match;i++){
            SimpleMatch m = round.getMatch(i);
            if(m.containPlayer(p1) && m.containPlayer(p2)){
                MatchDTO mdto = new MatchDTO(m);
                return mdto;
            }                            
        }
        return null;
    }

    public MatchDTO editMatch(PlayerDTO p1, PlayerDTO p2, String[] results){
        MatchDTO m = findMatch(p1, p2);
        try {
            Common.convertStringsToSets(results, m);
        } catch(Exception e){
            return null;            
        }
        return m;
    }

    public MatchDTO addMatch(PlayerDTO p1, PlayerDTO p2, String[] results) {
        MatchDTO m = new MatchDTO(p1, p2,new ArrayList<String>(Arrays.asList(results)));
        m.setId(round.getMatches().size());
        if(!round.containPlayer(p1)) round.addPlayer(p1);
        if(!round.containPlayer(p2)) round.addPlayer(p2);
        round.getMatches().add(m);
        return m;
    }
}

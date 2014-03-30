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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * LocalRepository 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class LocalRepository {
    private ArrayList<PlayerDTO> players;
    private SimpleRound round;

    public LocalRepository() {
        players = new ArrayList<PlayerDTO>();
        round = new SimpleRound();
    }
    
    public ArrayList<PlayerDTO> getPlayers() {
        return players;
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
    
    public MatchDTO findMatch(String player1, String player2){
        PlayerDTO p1 = findPlayer(player1);
        PlayerDTO p2 = findPlayer(player2);
        int size_match = round.getNumMatch();
        for(int i =0;i<size_match;i++){
            SimpleMatch m = round.getMatch(i);
            if(m.getFirstPlayer().equals(p1) || m.getSecondPlayer().equals(p1))
                if(m.getFirstPlayer().equals(p2) || m.getSecondPlayer().equals(p2)) {
                    MatchDTO mdto = new MatchDTO(m);
                    return mdto;
                }                            
        }
        return null;
    }

    public MatchDTO editMatch(PlayerDTO p1, PlayerDTO p2, String[] results) {
        
        return null;
    }
}

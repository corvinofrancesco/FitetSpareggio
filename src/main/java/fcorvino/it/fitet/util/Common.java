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

package fcorvino.it.fitet.util;

import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.model.SimpleSet;
import fcorvino.it.fitet.model.UniqueNamePlayer;

/**
 * Common 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class Common {
    public static String KEY_OUTPUT_FILE = "output-file";
    public static String KEY_TITLE_ROUND = "round-title";
    public static String KEY_TABLE_NUMBER = "num-table";
    
    public static String SEPARATOR_RESULT = "-";
    
    public static Integer[] convertResultStrToInt(String result) throws Exception {
        String sep[] = result.split(SEPARATOR_RESULT);
        if(sep.length!=2) throw new Exception("No valid result: [" + result + "]");
        Integer pnt[] = {
            Integer.valueOf(sep[0]),
            Integer.valueOf(sep[1])
        }; 
        return pnt;
    }

    public static SimpleMatch completeMatch(String[] sets, SimplePlayer[] pl) throws NumberFormatException, Exception {
        SimpleMatch m = new SimpleMatch(sets.length);
        m.setFirstPlayer(pl[0]);
        m.setSecondPlayer(pl[1]);
        convertStringsToSets(sets, m);
        return m;
    }    
    
    public static int convertStringsToSets(String results[], SimpleMatch m) throws Exception{
        int i = 0, countValid = 0;
        if(results.length>m.getSets().size()){ // aggiorna la dimensione
            int diff = results.length - m.getSets().size();
            for(int j=0; j<diff; j++ ) m.getSets().add(new SimpleSet());
        }
        for(String r : results){ // scrive i risultati
            Integer pnt[] = convertResultStrToInt(r);
            SimpleSet set = m.getSets().get(i);
            set.setPntPlayer1(pnt[0]);
            set.setPntPlayer2(pnt[1]);
            i++;
            if(set.isValid()) countValid++;
        }        
        if(results.length<m.getSets().size()){ // cancella precedenti risultati
            int diff = m.getSets().size() - results.length;
            for(int j=0; j<diff; j++ ) {
                SimpleSet set = m.getSets().get(results.length + j);
                set.setPntPlayer1(0);
                set.setPntPlayer2(0);
            }            
        }
        return countValid;
    }
    
    /**
     * Aggiunge due giocatori a un girone
     * Attraverso due stringhe identificative di un giocatore, crea le istanze 
     * attraverso la classe {@link UniqueNamePlayer} e controlla se nel girone siano
     * stati già inseriti. Se sono già presenti non li inserisce.
     * 
     * @param parts stringhe rappresentati i due giocatori (dimensione pari a 2)
     * @param r girone dove verranno inseriti i giocatori
     * @return array dei giocatori creati.
     */
    public static SimplePlayer[] addPlayers(String[] parts, SimpleRound r) {
        SimplePlayer pl1 = UniqueNamePlayer.getPlayer(parts[0]), 
                pl2 = UniqueNamePlayer.getPlayer(parts[1]);
        if(!r.containPlayer(pl1)) r.addPlayer(pl1);
        if(!r.containPlayer(pl2)) r.addPlayer(pl2);
        return new SimplePlayer[]{ pl1, pl2};
    }
    
}

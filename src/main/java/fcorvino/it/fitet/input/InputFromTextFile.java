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

import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.util.Common;

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
    
    public SimpleRound getRound(String roundString){
        SimpleRound r = new SimpleRound();
        String[] matches = roundString.split("\n");
        for(int i =0;i<matches.length; i++){
            String parts[] = matches[i].split("\t");
            SimplePlayer[] pl = Common.addPlayers(parts, r);
            String sets[] = parts[3].split(",");
            try {
                SimpleMatch m = Common.completeMatch(sets, pl);
                r.addMatch(m);
            } catch (Exception ex) { }
        }
        return r;
    }
    

}

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

import asg.cliche.Command;
import asg.cliche.Param;
import fcorvino.it.fitet.input.SimpleLoader;
import fcorvino.it.fitet.model.SimplePlayer;

/**
 * SimulationCommands 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class SimulationCommands {
    LocalRepository repository = null;

    public SimulationCommands(LocalRepository r) {
        repository = r;
    }
    
    @Command(name = "simulation", description ="Inserisce dei dati simulati con i giocatori registrati, attenzione sovrascrive i dati inserti.")
    public String simulation(){
        if(repository.getRound().getNumPlayers()<2)
             return "Non ci sono sufficenti giocatori per simulare un girone.";
        for(SimplePlayer p : repository.getPlayers()) repository.getRound().addPlayer(p);
        SimpleLoader loader = new SimpleLoader();
        loader.populateMatches(repository.getRound());
        return "Girone simulato con i giocatori registrati.";
    }
    
    @Command(name = "simulation", description ="Inserisce dei dati simulati, attenzione sovrascrive i dati inserti.")
    public String simulation(
            @Param(name = "numero giocatori", description = "Numero di giocatori da inserire") Integer num_players
            ){
        SimpleLoader loader = new SimpleLoader();
        if(num_players > 1 ) {
            loader.delta_num_players = 1;
            loader.min_num_players = num_players;
            loader.populatePlayers(repository.getRound());
        } else return "Non ci sono sufficenti giocatori per simulare un girone.";
        loader.populateMatches(repository.getRound());
        return "";
    }

}

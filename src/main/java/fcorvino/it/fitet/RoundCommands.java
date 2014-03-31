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
import fcorvino.it.fitet.dto.RoundDTO;
import fcorvino.it.fitet.input.SimpleLoader;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.output.OutputMatrix;
import fcorvino.it.fitet.output.VelocityPrinter;
import fcorvino.it.fitet.roundutil.RoundRanking;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.velocity.Template;

/**
 *
 * @author Francesco
 */
public class RoundCommands {
    LocalRepository repository = null;

    public RoundCommands(LocalRepository r) {
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

        return "";
    }
    
    /**
     * 
     * @param fileName
     * @param titleRound
     * @param numberTable
     * @return 
     */
    @Command(name = "description",description = "Imposta la descrizione del girone")
    public String description(
            @Param(name = "output-file", description = "Nome del file da creare") String fileName,
            @Param(name = "round", description = "Titolo del girone") String titleRound,
            @Param(name = "num-table", description = "Numero del tavolo del girone") Integer numberTable
            ){
        repository.put("output-file", fileName);
        repository.put("round-title", titleRound);
        repository.put("num-table", numberTable);
        return "Su " + fileName + ".html verranno stampati i risultati";
    }
    
    /**
     * Comando per stampare su file HTML il girone in memoria
     * @return Stringa di stato.
     */
    @Command(name = "tabellaHTML",description = "Crea file HTML del girone")
    public String tabellaHTML() {
        String out = "";

        SimpleRound round = repository.getRound();
        RoundRanking ranking = new RoundRanking(round);
        ranking.generateRanking();
        
        RoundDTO roundDto = new RoundDTO(round, OutputMatrix.create(round));
        String table = (repository.get("num-table")==null)?null:repository.get("num-table").toString();
        String title = (repository.get("round-title")==null)?null:repository.get("round-title").toString();
        String file = repository.get("output-file")==null?"index":repository.get("output-file").toString();
        roundDto.setName(title);
        roundDto.setTable(table);
        
        VelocityPrinter p = VelocityPrinter.getPrinter();
        p.getContext().put("round", roundDto);
        p.getContext().put("ranking", ranking);
        Template t = p.loadTemplate("base-round-template.vm");
        if(t==null) return "Template non valido";
        System.out.println("Avvio la stampa");            
        try {
            FileWriter fw = new FileWriter(file + ".html");
            fw.write(p.printToString(t));
            fw.close();
        } catch (IOException e){
            System.out.println("Eccezione nell'esecuzione: " + e.getMessage());
        }
        out += "Processo terminato";
        return out;
    }
    
    public void matchList(){
        
    }
}

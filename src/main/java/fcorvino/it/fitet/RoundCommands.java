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
import fcorvino.it.fitet.dto.MatchDTO;
import fcorvino.it.fitet.dto.PlayerDTO;
import fcorvino.it.fitet.dto.RoundDTO;
import fcorvino.it.fitet.input.ShellProxy;
import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.output.OutputMatrix;
import fcorvino.it.fitet.output.VelocityPrinter;
import fcorvino.it.fitet.roundutil.RoundRanking;
import fcorvino.it.fitet.roundutil.SimpleRank;
import fcorvino.it.fitet.util.Common;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    /**
     * 
     * @param fileName
     * @param titleRound
     * @param numberTable
     * @return 
     */
    @Command(name = "description",description = "Imposta la descrizione del girone", abbrev = "d")
    public String description(
            @Param(name = "output-file", description = "Nome del file da creare") String fileName,
            @Param(name = "round", description = "Titolo del girone") String titleRound,
            @Param(name = "num-table", description = "Numero del tavolo del girone") Integer numberTable
            ){
        repository.put(Common.KEY_OUTPUT_FILE, fileName);
        repository.put(Common.KEY_TITLE_ROUND, titleRound);
        repository.put(Common.KEY_TABLE_NUMBER, numberTable);
        return "Su " + fileName + ".html verranno stampati i risultati";
    }
    
    /**
     * Comando per stampare su file HTML il girone in memoria
     * @return Stringa di stato.
     */
    @Command(name = "tabellaHTML",description = "Crea file HTML del girone", abbrev = "t")
    public String tabellaHTML() {
        String out = "";

        SimpleRound round = repository.getRound();
        RoundRanking ranking = new RoundRanking(round);
        ranking.generateRanking();
        
        RoundDTO roundDto = new RoundDTO(round, OutputMatrix.create(round));
        String table = (repository.get("num-table")==null)?null:repository.get(Common.KEY_TABLE_NUMBER).toString();
        String title = (repository.get("round-title")==null)?null:repository.get(Common.KEY_TITLE_ROUND).toString();
        String file = repository.get("output-file")==null?"index":repository.get(Common.KEY_OUTPUT_FILE).toString();
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
    
    @Command(name = "matchesList",description = "Elenco dei match nel girone", abbrev = "ml")
    public String matchesList(){
        if(repository.getRound().getMatches().size()==0) 
            return "Nessun incontro registrato!";
        VelocityPrinter v = VelocityPrinter.getPrinter();
        Template t = v.loadTemplate("match-list.vm");
        ArrayList<MatchDTO> matches = new ArrayList<MatchDTO>();
        for(SimpleMatch m : repository.getRound().getMatches()){
            matches.add(new MatchDTO(m));
        }
        v.getContext().put("round", repository.getRound());
        v.getContext().put("matches", matches);
        //v.getContext().put("matches", repository.getRound().getMatches());
        return v.printToString(t);        
    }
    
    @Command(name = "tableMatches",description = "Elenco dei match nel girone", abbrev = "tm")
    public String tableMatches(){
        VelocityPrinter v = VelocityPrinter.getPrinter();
        Template t = v.loadTemplate("console/matrix-output.vm");
        v.getContext().put("matrix", OutputMatrix.create(repository.getRound()));
        return v.printToString(t);        
    }

    @Command(name = "ranking",description = "Classifica del girone", abbrev = "c")
    public String tableRanking(){
        VelocityPrinter v = VelocityPrinter.getPrinter();
        Template t = v.loadTemplate("console/ranking-output.vm");
        RoundRanking ranking = new RoundRanking(repository.getRound());
        ranking.generateRanking();
        for(SimpleRank r : ranking.getRanks()){
            r.calculateDifferences();
        }
        v.getContext().put("ranking", ranking);
        return v.printToString(t);        
    }

    
    @Command(name = "match", description="Aggiunge un incontro al girone", abbrev = "m")
    public String match(
            @Param(name="player1", description="Codice del primo giocatore") String player1, 
            @Param(name="player2", description="Codice del secondo giocatore") String player2, 
            @Param(name="risultati", description="Elenco dei risultati, esempio: 1-11 3-11 12-14 ") String... results) {
        
            PlayerDTO p1 = repository.searchAndAddPlayer(player1);
            PlayerDTO p2 = repository.searchAndAddPlayer(player2);
            MatchDTO m = repository.findMatch(p1, p2);
            if(m!=null) m = repository.editMatch(p1,p2, results);                
            else m = repository.addMatch(p1,p2, results);
            int[] result = m.getResult();
            return ShellProxy.out("match.adding",p1.getSurname(),p2.getSurname(),result[0] ,result[1]);        
    }    
}

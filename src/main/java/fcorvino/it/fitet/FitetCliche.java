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
import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import fcorvino.it.fitet.dto.MatchDTO;
import fcorvino.it.fitet.dto.PlayerDTO;
import fcorvino.it.fitet.output.VelocityPrinter;
import java.io.IOException;
import org.apache.velocity.Template;

/**
 *
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class FitetCliche implements ShellDependent {
    // The shell which runs us. Needed to create subshells.
    private Shell shell;
    private LocalRepository repository;

    public FitetCliche() {
        repository = new LocalRepository();
    }

    public void cliSetShell(Shell shell) {
        this.shell = shell;
    }

    @Command(description="Varargs example")
    public Integer add(
            @Param(name="numbers", description="some numbers to add")
            Integer... numbers) {

        int result = 0;
        for (int i : numbers) {
            result += i;
        }
        return result;
    }

    @Command(description="Aggiunge un incontro al girone")
    public String match(
            @Param(name="player1", description="Codice del primo giocatore") String player1, 
            @Param(name="player2", description="Codice del secondo giocatore") String player2, 
            @Param(name="risultati", description="Elenco dei risultati, esempio: 1-11 3-11 12-14 ") String... results) {
        
            PlayerDTO p1 = repository.searchAndAddPlayer(player1);
            PlayerDTO p2 = repository.searchAndAddPlayer(player2);
            MatchDTO m = null;
            try {
                m = repository.editMatch(p1,p2, results);
            } catch(Exception e){
                return e.getMessage();
            }
            int[] result = m.getResult();
            return "Match " + 
                    p1.getSurname() + " - " + p2.getSurname() + ": " + 
                    result[0] + "-" + result[1]+ " inserito.";        
    }
    
    @Command(description="Registra un giocatore nel repository")
    public String player(
            @Param(name="nome e cognome", description="Nome e cognome del giocatore, l'ultima parola inserita è il cognome")
            String... names){
        int numWord = names.length;
        int code = repository.getPlayers().size();
        String name = "", surname = "";
        if(numWord == 0) return "";
        if(numWord == 1) surname = names[0];
        else {
            name = names[0];
            for(int i =1;i<numWord-1;i++) name = name + " " +names[i];
            surname = names[numWord-1];
        }
        PlayerDTO player = new PlayerDTO(code, name, surname);
        repository.getPlayers().add(player);
        return "Aggiunto giocatore " + code + ": nome {" + name + "} cognome {" + surname + "}";
    }
    
    @Command(description="Elenco dei giocatori nel repository")
    public String players(){
        VelocityPrinter v = VelocityPrinter.getPrinter();
        Template t = v.loadTemplate("player-list.vm");
        v.getContext().put("players", repository.getPlayers());
        return v.printToString(t);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {        
        ShellFactory.createConsoleShell(
                "Fitet Console", 
                "Benvenuti nella console FITeT \n" +
                "Scrivere ?l per avere una lista dei comandi disponibili.", 
                new FitetCliche())
                .commandLoop();  
    }
}

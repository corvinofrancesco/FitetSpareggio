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
import com.thoughtworks.xstream.XStream;
import fcorvino.it.fitet.dto.PlayerDTO;
import fcorvino.it.fitet.output.VelocityPrinter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
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
    
    @Command(name = "round", description = "Avvia la modifica del girone")
    public String round() throws IOException{
        System.out.println("Avviata modifica del girone, premere ?l per la lista dei comandi.");
        ShellFactory.createSubshell(
                "round", shell, "Modifica del girone.", 
                new RoundCommands(repository)).commandLoop();        
        return "Menu home.";        
    }

    @Command(name = "simulation", description = "Accede ai comandi per creare delle simulazioni.")
    public String simulation() throws IOException{
        System.out.println("Avviata modifica del girone, premere ?l per la lista dei comandi.");
        ShellFactory.createSubshell(
                "sim", shell, "Comandi di simulazione risultati.", 
                new SimulationCommands(repository)).commandLoop();        
        return "Menu home.";        
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
    
    @Command(name="info", description="Restituisce le informazioni generali sullo stato del programma.")
    public String info(){
        VelocityPrinter v = VelocityPrinter.getPrinter();
        Template t = v.loadTemplate("info.vm");
        v.getContext().put("players", repository.getPlayers());
        v.getContext().put("round", repository.getRound());
        v.getContext().put("repository", repository);
        return v.printToString(t);        
    }
    
    @Command(name="save", description="Salva i dati in memoria.", abbrev = "s")
    public String save(String fileName){
        XStream xstream = new XStream();
        try {
            String serialObj = xstream.toXML(this.repository);            
            FileWriter fw = new FileWriter(fileName);
            fw.write(serialObj);
            fw.close();        
        } catch (IOException ex) {
            return "Problemi durante il salvataggio";
        }
        return "scrittura con successo";
    }
    
    @Command(name="load", description="Carica dei dati precedentemente salvati in memoria.", abbrev = "l")
    public String load(String fileName){
        XStream xstream = new XStream();
        LocalRepository rep = null;
        try {
            String xml = new Scanner( new File(fileName), "UTF-8" ).useDelimiter("\\A").next();
            rep = (LocalRepository)xstream.fromXML(xml);        
        } catch (FileNotFoundException ex) { }
        if(rep!=null) {
            this.repository = rep;
            return "Caricamento completato!";
        }
        return "Problemi durante il caricamento";
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

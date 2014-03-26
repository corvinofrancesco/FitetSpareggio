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

import fcorvino.it.fitet.input.SimpleLoader;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.output.OutputMatrix;
import fcorvino.it.fitet.roundutil.RoundRanking;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author Francesco
 */
public class Console {
    
    public static Template loadTemplate(String path){
        Template template = null;        
        try {
           template = Velocity.getTemplate(path);
        } catch( ResourceNotFoundException rnfe ) {
           // couldn't find the template
        } catch( ParseErrorException pee ){
          // syntax error: problem parsing the template
        } catch( MethodInvocationException mie ) {
          // something invoked in the template
          // threw an exception
        } catch( Exception e ) {}
        return template;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", ClasspathResourceLoader.class.getName());
        // inizializza velocity
        try {
            Velocity.init(prop);                     
        } catch(Exception e){}        
        VelocityContext context = new VelocityContext();
        
        
        SimpleLoader loader = new SimpleLoader();        
        SimpleRound round = new SimpleRound();
        loader.populatePlayers(round);
        loader.populateMatchs(round);
        RoundRanking ranking = new RoundRanking(round);
        ranking.generateRanking();
        OutputMatrix m = OutputMatrix.create(round);
        
        RoundDTO roundDto = new RoundDTO(round, m);
        roundDto.setName("test" + new Date());
        roundDto.setTable("1");
        
        context.put("round", roundDto);
        context.put("ranking", ranking);
        
        Template t = loadTemplate("base-round-template.vm");
        if(t!=null){
            System.out.println("Avvio la stampa");
            StringWriter sw = new StringWriter();
            try {
                t.merge( context, sw );
                FileWriter fw = new FileWriter("index.html");
                fw.write(sw.toString());
                fw.close();
            } catch (IOException e){
                System.out.println("Eccezione nell'esecuzione: " + e.getMessage());
            }
        }    
        System.out.println("Processo terminato");
    }
}

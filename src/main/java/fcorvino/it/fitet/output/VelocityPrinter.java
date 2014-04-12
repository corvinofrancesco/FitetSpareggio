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

package fcorvino.it.fitet.output;

import fcorvino.it.fitet.input.ShellProxy;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * VelocityPrinter 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class VelocityPrinter {
    private static VelocityPrinter instance = new VelocityPrinter();
    
    private VelocityContext context = null;
    
    private VelocityPrinter(){
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", ClasspathResourceLoader.class.getName());
        // inizializza velocity
        try {
            Velocity.init(prop);                     
        } catch(Exception e){}        
        context = new VelocityContext();
        context.put("message", ShellProxy.getInstance());
    }

        
    public Template loadTemplate(String path){
        Template template = null;        
        try {
           template = Velocity.getTemplate(path);
        } catch( Exception e ) {}
        return template;
    }

    public VelocityContext getContext() {
        return context;
    }
    
    public void printToFile(Template t,String file){
        if(t==null) return;
        StringWriter sw = new StringWriter();
        try {
            t.merge( context, sw );
            FileWriter fw = new FileWriter(file);
            fw.write(sw.toString());
            fw.close();
        } catch (IOException e){
            
        }
    }
    
    public String printToString(Template t){
        if(t==null) return "";
        StringWriter sw = new StringWriter();
        try {
            t.merge( context, sw );
        } catch (Exception ex) {
            return "";
        }
        return sw.toString();
    }
    
    public static VelocityPrinter getPrinter(){
        return instance;
    }

}

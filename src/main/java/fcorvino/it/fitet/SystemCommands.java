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
import fcorvino.it.fitet.input.ShellProxy;
import java.io.File;

/**
 * SystemCommands 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class SystemCommands {
    LocalRepository repository = null;

    public SystemCommands(LocalRepository r) {
        repository = r;
    }
    
    @Command(name = "workingDir", description ="Imposta la cartella di lavoro")
    public String workingDir(String path){
        return ShellProxy.out("working.directory.set", path);        
    }

    @Command(name = "templateDir", description ="Sposta la cartella dei template")
    public String templateDir(String path){
        repository.put("templateDir", path);                
        File file = new File(path);
        boolean success = file.mkdirs();
        if (!success && !file.exists()) {            
            repository.remove("templateDir");
            return ShellProxy.out("template.directory.notfound", path);
        }        
        return ShellProxy.out("template.directory.set", path);
    }
    
    @Command(name = "copyDefault", description ="Copia il template di default nella cartella")
    public String copy(){
        
        return ShellProxy.out("template.default");
    }
    
    @Command(name = "stampa", description ="")
    public String stampa(String key){
        return ShellProxy.out(key);
    }
        
}

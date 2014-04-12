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

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ShellProxy 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class ShellProxy {

    private static ShellProxy instance = new ShellProxy();
    private String language = "it";
    private String country ="IT";
    private ResourceBundle messages;
    
    private ShellProxy() {
        Locale currentLocale;
        currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("Language", currentLocale);        
    }

    public void changeLanguage(String language, String country){
        this.language = language;
        this.country = country;
        Locale currentLocale;
        currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("Language", currentLocale);                
    }
    
    public ResourceBundle getMessages() {
        return messages;
    }
            
    public static String out(String key, Object... params){
        String format = instance.getMessages().getString(key);
        return String.format(format, params);
    }

    public static String out(String msg){
        return instance.getMessages().getString(msg);        
    }

    public static void sout(String key, Object... params){
        System.out.println(out(key, params));        
    }
    
    public static void sout(String msg){
        System.out.println(out(msg));
    }
    
    public static ShellProxy getInstance(){
        return instance;
    }
}

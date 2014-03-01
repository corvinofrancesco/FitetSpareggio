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

package fcorvino.it.fitet.model;

/**
 * UniqueNamePlayer 
 * 
 * Definisce il medodo che confronta due giocatori dal nome e cognome
 * 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class UniqueNamePlayer extends SimplePlayer {

    public UniqueNamePlayer(String name, String surname) {
        super(name, surname);
    }

    public static UniqueNamePlayer getPlayer(String completeName){
        if(completeName.contains(" ")){
            String namePartes[] = completeName.split(" ");
            String name = completeName.substring(namePartes[0].length() + 1);
            return new UniqueNamePlayer(name, namePartes[0]);
        }
        return new UniqueNamePlayer("", completeName);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SimplePlayer){
            SimplePlayer p2 = (SimplePlayer) obj;
            if(getName().equals(p2.getName())) 
                return getSurname().equals(p2.getSurname());
        }
        return false;    
    }

    
}

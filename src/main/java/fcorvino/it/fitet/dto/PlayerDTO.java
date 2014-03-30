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

package fcorvino.it.fitet.dto;

import fcorvino.it.fitet.model.SimplePlayer;

/**
 * PlayerDTO 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class PlayerDTO extends SimplePlayer {

    private Integer id;
    
    public PlayerDTO(Integer id, String name, String surname) {
        super(name, surname);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PlayerDTO ){
            PlayerDTO conv = (PlayerDTO) obj;
            return this.id.equals(conv.id);
        } 
        if(obj instanceof SimplePlayer ){
            SimplePlayer conv = (SimplePlayer) obj;
            if(this.getName().equals(conv.getName()))
                return this.getSurname().equals(conv.getSurname());
        }
        return false;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    

    
}

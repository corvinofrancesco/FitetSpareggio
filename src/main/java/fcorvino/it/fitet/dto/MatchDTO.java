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

import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimpleSet;
import java.util.ArrayList;

/**
 * MatchDTO 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class MatchDTO extends SimpleMatch {
    private Integer id;
    
    public MatchDTO(PlayerDTO player1, PlayerDTO player2, ArrayList<String> results) {
        super(results.size());
        this.setFirstPlayer(player1);
        this.setSecondPlayer(player2);
        int i=0;
        for(String result : results){
            try {
                String[] r = result.split("-");
                getSets().get(i).setPntPlayer1(Integer.valueOf(r[0]));
                getSets().get(i).setPntPlayer2(Integer.valueOf(r[1]));
            } catch(Exception e){}
            i++;
        }
    }

    public MatchDTO(SimpleMatch m) {
        this.setFirstPlayer(m.getFirstPlayer());
        this.setSecondPlayer(m.getSecondPlayer());
        this.setSets(m.getSets());
    }
    
    public ArrayList<String> getResults(){
        ArrayList<String> results = new ArrayList<String>();
        for(SimpleSet s : this.getSets()){
            results.add(s.getPntPlayer1() + "-" + s.getPntPlayer2());
        }
        return results;
    }
    
    public String getResultsPartial(){
        String results = "";
        for(SimpleSet s : this.getSets()){
            if(s.isValid()) results +=  " " + s.getPntPlayer1() + "-" + s.getPntPlayer2();
        }
        return results;        
    }
    
    public String getResultsFinal(){
        String results = getResult()[0] + "-" + getResult()[1];
        return results;        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MatchDTO){
            return this.id.equals(((MatchDTO)obj).id);
        }
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}

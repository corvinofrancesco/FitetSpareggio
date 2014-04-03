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

import java.util.ArrayList;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.output.OutputMatrix;

/**
 * RoundDTO 
 * classe che si occupa di trasformare una classe {@link fcorvino.it.fitet.model.SimpleRound } in 
 * un formato più accessibile ai template grafici.
 * 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class RoundDTO {
    private String name = "";
    private String table = "";
    private SimpleRound round = null;
    private OutputMatrix matrix = null;

    public RoundDTO(SimpleRound round, OutputMatrix matrix){
        this.round = round;
        this.matrix = matrix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }        

    public Integer getNumplayers(){
        return round.getNumPlayers();            
    }

    public ArrayList<String> getPlayers(){
        int size = round.getNumPlayers();
        ArrayList<String> players = new ArrayList<String>(); 
        for(int i=0; i<size;i++){
            players.add(round.getPlayer(i).toString());
        }
        return players;
    }

    public ArrayList<RankDTO> getRanks(){
        int size = round.getNumPlayers();
        ArrayList<RankDTO> ranks = new ArrayList<RankDTO>();
        for(int i=0; i<size;i++){
            RankDTO rank = new RankDTO(round,matrix,i);
            ranks.add(rank);
        }            
        return ranks;
    }
    
}

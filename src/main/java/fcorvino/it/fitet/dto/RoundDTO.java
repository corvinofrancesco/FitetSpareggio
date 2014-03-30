/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcorvino.it.fitet.dto;

import fcorvino.it.fitet.dto.RankDTO;
import java.util.ArrayList;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.output.OutputMatrix;

/**
 *
 * @author Francesco
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

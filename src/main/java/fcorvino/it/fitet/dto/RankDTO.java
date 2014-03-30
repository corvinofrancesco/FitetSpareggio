/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcorvino.it.fitet.dto;

import fcorvino.it.fitet.model.SimplePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.output.OutputMatrix;

/**
 *
 * @author Francesco
 */
public class RankDTO {
    private ArrayList<String> result;
    private HashMap<String, Object > properties;
    private SimplePlayer player;
    
    public RankDTO(SimpleRound r, OutputMatrix m, int player){
        int sizePlayers = r.getNumPlayers();
        int sizeProps = 0;
        result = new ArrayList<String>();
        properties = new HashMap<String, Object>();
        this.player = r.getPlayer(player);        
        
        for(int i =0;i<sizePlayers;i++){
           result.add(m.get(player, i).getResult());                
        }
        for(int i=0;i<sizeProps;i++){
            properties.put(null, i);
        }
    }

    public ArrayList<String> getResults(){
        return result;
    }

    public Object get(String key){
        return properties.get(key);
    }

    public SimplePlayer getPlayer() {
        return player;
    }
        
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcorvino.it.fite.roundutil;

import fcorvino.it.fitet.model.SetDifference;
import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Francesco
 */
public class SimpleRank {
    private SimplePlayer player;
    private ArrayList<SimpleMatch> matchs;
    private HashMap<String, Object> properties;
    
    public SimpleRank(SimpleRound r, SimplePlayer p) {
        this.player = p;
        this.matchs = new ArrayList<SimpleMatch>();
        this.properties = new HashMap<String, Object>();
        for(int i =0; i<r.getNumMatch(); i++){
            if(r.getMatch(i).containPlayer(p)){
                matchs.add(r.getMatch(i));
            }
        }
    }
    
    public int getPoint(){
        int point =0;
        for(SimpleMatch m : matchs){
            point += m.getPointPlayer(player);
        }
        return point;
    }

    public SimplePlayer getPlayer() {
        return this.player;
    }
    
    public ArrayList<SimplePlayer> getGroup(){
        return (ArrayList<SimplePlayer>) properties.get("group");
    }
    
    public void setGroup(ArrayList<SimplePlayer> group){
        properties.put("group", group);
    }
        
    public int getDifferenceSet(){
        return (Integer) properties.get("difference-set");
    }

    public int getDifferencePoint(){
        return (Integer) properties.get("difference-point");
    }
    
    public void calculateDifferences(){
        ArrayList<SimplePlayer> group = (ArrayList<SimplePlayer>) properties.get("group");
        ArrayList<SetDifference> diffs = new ArrayList<SetDifference>();
        int totWinnerSet = 0, totWinnerPoint = 0;
        for(SimpleMatch m : matchs){
            SetDifference dif = m.getDifferenceFor(player);
            if(!group.contains(dif.getAdvPlayer())) continue;
            diffs.add(dif);
            totWinnerSet += dif.getDifferenceSetValue();
            totWinnerPoint += dif.getDifferencePointValue();
        }
        properties.put("differences", diffs);
        properties.put("difference-set", totWinnerSet);
        properties.put("difference-point", totWinnerPoint);
    }
    
}

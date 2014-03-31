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

package fcorvino.it.fitet.roundutil;

import fcorvino.it.fitet.model.SetDifference;
import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Francesco Corvino
 */
public class SimpleRank {
    private SimplePlayer player;
    private ArrayList<SimpleMatch> matches;
    private HashMap<String, Object> properties;
    
    public SimpleRank(SimpleRound r, SimplePlayer p) {
        this.player = p;
        this.matches = new ArrayList<SimpleMatch>();
        this.properties = new HashMap<String, Object>();
        for(int i =0; i<r.getNumMatches(); i++){
            if(r.getMatch(i).containPlayer(p)){
                matches.add(r.getMatch(i));
            }
        }
    }
    
    public int getPoint(){
        int point =0;
        for(SimpleMatch m : matches){
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
        for(SimpleMatch m : matches){
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

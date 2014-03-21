package fcorvino.it.fitet.roundutil;

import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Francesco Corvino
 */
public class RoundRanking {
    private SimpleRound round;

    private ArrayList<SimpleRank> ranks = null;
    private ArrayList<ArrayList<SimplePlayer>> groups = null;
    
    public RoundRanking(SimpleRound round) {
        this.round = round;
    }
    
    public void initRanks(){
        ranks = new ArrayList<SimpleRank>();
        for(int i = 0; i < round.getNumPlayers(); i++){
            SimpleRank r = new SimpleRank(round, round.getPlayer(i));
            ranks.add(r);
        }
    }

    public int createGrouping(){
        if(ranks==null) initRanks();
        int currPoint = ranks.get(0).getPoint();
        ArrayList<SimplePlayer> currGroup = new ArrayList<SimplePlayer>();
        groups = new ArrayList<ArrayList<SimplePlayer>>();
        for(SimpleRank r : ranks){
            if(currPoint!= r.getPoint()){
                groups.add(currGroup);
                currGroup = new ArrayList<SimplePlayer>();
                currPoint = r.getPoint();
            }
            currGroup.add(r.getPlayer());
        }
        // inserisce l'ultimo gruppo creato
        groups.add(currGroup);
        for(ArrayList<SimplePlayer> group : groups){
            for(SimplePlayer p : group){
                this.getPlayerRank(p).setGroup(group);
            }
        }
        return groups.size();
    }
    
    public ArrayList<SimpleRank> getRanks() {
        return ranks;
    }

    private SimpleRank getPlayerRank(SimplePlayer p) {
        for(SimpleRank r : ranks){
            if(r.getPlayer().equals(p)) return r;
        }
        return null;
    }
        
    public void generateRanking(){
        initRanks();
        Collections.sort(ranks, new RankingByPoint());
        createGrouping();
        for(SimpleRank r : ranks){
            r.calculateDifferences();
        }
        Collections.sort(ranks, new RankingByDifferences());        
    }

    public ArrayList<ArrayList<SimplePlayer>> getGroups() {
        return groups;
    }    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcorvino.it.fitet.model;

import java.util.ArrayList;

/**
 *
 * @author Francesco
 */
public class SimpleRound {
    private ArrayList<SimplePlayer> players;
    private ArrayList<SimpleMatch> matchs;

    public SimpleRound() {
        players = new ArrayList<SimplePlayer>();
        matchs = new ArrayList<SimpleMatch>();
    }
    
    public void addPlayer(SimplePlayer simplePlayer) {
        players.add(simplePlayer);
    }
    
    public void addMatch(SimpleMatch match){
        matchs.add(match);
    }

    public int getNumMatch() {
        int numPlayers = players.size();
        return (int) (numPlayers * (numPlayers - 1) * 0.5);
    }

    public int getNumPlayers() {
        return players.size();
    }

    public SimplePlayer getPlayer(int curPl) {
        return players.get(curPl);
    }

    public SimpleMatch getMatch(int i) {
        return matchs.get(i);
    }

    public boolean containPlayer(SimplePlayer pl) {
        return players.contains(pl);
    }
}

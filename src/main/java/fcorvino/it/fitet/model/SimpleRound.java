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

import java.util.ArrayList;

/**
 *
 * @author Francesco Corvino
 */
public class SimpleRound {
    private ArrayList<SimplePlayer> players;
    private ArrayList<SimpleMatch> matches;

    public SimpleRound() {
        players = new ArrayList<SimplePlayer>();
        matches = new ArrayList<SimpleMatch>();
    }
    
    public void addPlayer(SimplePlayer simplePlayer) {
        players.add(simplePlayer);
    }
    
    public void addMatch(SimpleMatch match){
        matches.add(match);
    }

    public int getNumMatches() {
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
        return matches.get(i);
    }

    public boolean containPlayer(SimplePlayer pl) {
        return players.contains(pl);
    }

    public ArrayList<SimpleMatch> getMatches() {
        return matches;
    }
}

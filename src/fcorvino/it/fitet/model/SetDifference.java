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

/**
 * SetDifference 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class SetDifference {
    private SimplePlayer advPlayer;
    private SimpleMatch match;
    private int[] playerIndex = {0,1};
    private int[] differenceSet;
    private int[] differencePoint;

    public SetDifference(SimpleMatch m, SimplePlayer p) {
        if(m.getFirstPlayer().equals(p)){
            this.advPlayer = m.getSecondPlayer();
        } else {
            this.advPlayer = m.getFirstPlayer();            
            playerIndex = new int[] {1,0};            
        }
        this.differenceSet = m.getResult();
        this.differencePoint = m.getResultPointsInSet();
    }
    
    public SimplePlayer getAdvPlayer() {
        return advPlayer;
    }

    public void setAdvPlayer(SimplePlayer advPlayer) {
        this.advPlayer = advPlayer;
    }

    public SimpleMatch getMatch() {
        return match;
    }

    public void setMatch(SimpleMatch match) {
        this.match = match;
    }

    public int[] getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int[] playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int[] getDifferenceSet() {
        return differenceSet;
    }

    public void setDifferenceSet(int[] differenceSet) {
        this.differenceSet = differenceSet;
    }

    public int[] getDifferencePoint() {
        return differencePoint;
    }

    public void setDifferencePoint(int[] differencePoint) {
        this.differencePoint = differencePoint;
    }
    
    public void invertPlayers(){
        int tmp = playerIndex[0];
        playerIndex[0] = playerIndex[1];
        playerIndex[1] = tmp;
    }
    
    public int getDifferenceSetValue(){
        return differenceSet[playerIndex[0]] - differenceSet[playerIndex[1]];
    }
    
    public int getDifferencePointValue(){
        return differencePoint[playerIndex[0]] - differencePoint[playerIndex[1]];                 
    }
}

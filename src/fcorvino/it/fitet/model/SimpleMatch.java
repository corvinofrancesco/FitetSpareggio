package fcorvino.it.fitet.model;

import java.util.ArrayList;

/**
 *
 * @author Francesco
 */
public class SimpleMatch {
    private SimplePlayer firstPlayer;
    private SimplePlayer secondPlayer;
    private ArrayList<SimpleSet> sets;

    private static void initMatch(SimpleMatch m, int numSet){
        m.sets = new ArrayList<SimpleSet>();
        for(int i = 0; i<numSet; i++){
            m.sets.add(new SimpleSet());
        }                
    }
    
    public SimpleMatch(int numSet){
        initMatch(this, numSet);
    }
    
    public SimpleMatch() {
        initMatch(this, 3);
    }
    
    public SimplePlayer getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(SimplePlayer firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public SimplePlayer getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(SimplePlayer secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public ArrayList<SimpleSet> getSets() {
        return sets;
    }

    public void setSets(ArrayList<SimpleSet> sets) {
        this.sets = sets;
    }    
    
    public int getPointPlayer1(){
        int result[] = getResult();
        switch(result[0]){
            case 3: return 2;
            case 2: return 1;
            default:
                return 0;
        }
    }
    
    public int getPointPlayer(SimplePlayer p){
        int result[] = getResult();
        int index_pl = p.equals(firstPlayer)?0:1;
        switch(result[index_pl]){
            case 3: return 2;
            case 2: return 1;
            default:
                return 0;
        }        
    }
    
    /**
     * 
     * @return conteggio totale dei punti nei vari set
     */
    public int[] getResultPointsInSet(){
        int result[] = {0,0};
        for(int i=0;i<sets.size();i++){
            if(!sets.get(i).isValid()) continue;
            result[0] += sets.get(i).getPntPlayer1();
            result[1] += sets.get(i).getPntPlayer2();
        }
        return result;                
    }
    
    /**
     * Restituisce il punteggio in termini di numero di set
     * vinti dal primo giocatore e quelli vinti dal secondo giocatore
     * @return int[0] set vinti dal primo giocatore, int[1] set vinti dal secondo giocatore
     */
    public int[] getResult(){
        int result[] = {0,0};
        for(int i=0;i<sets.size();i++){
            if(!sets.get(i).isValid()) continue;
            int plIndex = (int) (0.5 + 0.5 * sets.get(i).getWinner());
            result[plIndex] += 1;
        }
        return result;        
    }

    public boolean containPlayer(SimplePlayer p) {
        if(p.equals(this.firstPlayer)) return true;
        if(p.equals(this.secondPlayer)) return true;
        return false;
    }
    
    public SetDifference getDifferenceFor(SimplePlayer p){
        SetDifference diff = new SetDifference(this, p);
        return diff;
    }
}

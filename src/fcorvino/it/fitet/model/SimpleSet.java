/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcorvino.it.fitet.model;

/**
 *
 * @author Francesco
 */
public class SimpleSet {
    private int pntPlayer1 = 0;
    private int pntPlayer2 = 0;

    public int getPntPlayer1() {
        return pntPlayer1;
    }

    public void setPntPlayer1(int pntPlayer1) {
        this.pntPlayer1 = pntPlayer1;
    }

    public int getPntPlayer2() {
        return pntPlayer2;
    }

    public void setPntPlayer2(int pntPlayer2) {
        this.pntPlayer2 = pntPlayer2;
    }
    
    public boolean isValid(){
        if(Math.abs(pntPlayer1-pntPlayer2)<2) return false;
        if((pntPlayer1<11) && (pntPlayer2<11)) return false;
        return true;
    }
    
    /**
     * Restituisce un intero che rappresenta il vincitore
     * 
     * @return Se sta vincendo il giocatore 1 restituisce -1, altrimenti 1
     */
    public int getWinner(){
        if(pntPlayer1>pntPlayer2) return -1;
        return 1;
    }
}

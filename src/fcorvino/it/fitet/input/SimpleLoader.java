package fcorvino.it.fitet.input;

import fcorvino.it.fite.roundutil.RoundRanking;
import fcorvino.it.fite.roundutil.SimpleRank;
import fcorvino.it.fitet.model.SimpleMatch;
import fcorvino.it.fitet.model.SimplePlayer;
import fcorvino.it.fitet.model.SimpleRound;
import fcorvino.it.fitet.model.SimpleSet;

/**
 *
 * @author Francesco Corvino
 */
public class SimpleLoader {
    public int min_num_players = 4;
    public int delta_num_players = 1;
    
    public static String[] names = {
        "abele", "adamo", "antonio", "bruno", "carmelo", "carlo", 
        "damiano", "daniele", "dario", "domenico", 
        "enrico", "ernesto", "francesco", "filippo", "fabrizio",
        "giuliano", "giulio", "giuseppe", "luca", 
        "mario", "marco", "massimo", "mattia",  
        "sergio", "paolo", "salvatore", "sandro", "stefano", "tommaso"
    };
    
    public static String[] surnames = {
        "antonucci", "bascia", "corvino", "congedo", "longo", "carrisi",
        "peccarisi", "montalbano", "cassano", "giacovelli", "pantaleo", "fortunato",
        "curto", "turco", "buccolieri", "cassatella", "de giorgi", "volpe",
        "renzi", "de gasperi", "brunetta"
    };
    
    public void populatePlayers(SimpleRound round) {
        int numPlayer =  (int) Math.round(Math.random() * delta_num_players) + min_num_players;
        for (int i =0; i< numPlayer;i++){
            int rndName = (int) Math.round(Math.random() * (names.length -1));
            int rndSurname =  (int) Math.round(Math.random() * (surnames.length -1));
            round.addPlayer(new SimplePlayer(names[rndName], surnames[rndSurname]));
        }
    }            

    public void populateMatchs(SimpleRound round) {
        int curPl = 0, avvPl = 1;
        for(int i=0; i<round.getNumMatch(); i++){
            SimpleMatch match = new SimpleMatch(5);
            setPoint(match); 
            if(avvPl==round.getNumPlayers()){
                curPl++; avvPl = curPl + 1;                        
            }
            match.setFirstPlayer(round.getPlayer(curPl));
            match.setSecondPlayer(round.getPlayer(avvPl));
            round.addMatch(match);
            avvPl++;
        }
    }
    
    public void setPoint(SimpleMatch match) {
        int pnt1 =0, pnt2 =0;
        for(int i = 0; i < 5; i++){
            int rnd1 = (int) Math.round(Math.random() * 11);
            int pnt = 11;
            if(rnd1 >= 10){ // gara terminata ai vantaggi
                rnd1 += (int) Math.round(Math.random() * 5);
                pnt = rnd1 + 2;
            }
            int win = (int) Math.round(Math.random() * 1);
            if(win>0) {
                match.getSets().get(i).setPntPlayer1(rnd1);
                match.getSets().get(i).setPntPlayer2(pnt);
                pnt2++;
            } else {
                match.getSets().get(i).setPntPlayer1(pnt);
                match.getSets().get(i).setPntPlayer2(rnd1);                
                pnt1++;
            }
            if(Math.max(pnt1, pnt2)>2) return;
        }
    }

    public static void printResults(SimpleRound round){
        for(int i=0; i<round.getNumMatch(); i++){
            SimpleMatch m = round.getMatch(i);
            int result[] = m.getResult();
            System.out.print(m.getFirstPlayer() + " - " + m.getSecondPlayer() + "-->" + result[0] +  "-" + result[1] + " {");
            for(SimpleSet s : m.getSets()) System.out.print(s.getPntPlayer1() + "-" + s.getPntPlayer2() + " ");
            System.out.println("}");
        }        
    }
    
    public static void printRanking(RoundRanking ranking){
        System.out.println("---- Classifica -------");
        for(SimpleRank r : ranking.getRanks()){
            r.calculateDifferences();
            System.out.println(" * " + r.getPoint() + " - " + r.getPlayer() + " | gruppo di " 
                    + r.getGroup().size() + " | differenze: " + r.getDifferenceSet() + " - " + r.getDifferencePoint() );
        }        
    } 
    
    public static void main(String args[]){
        SimpleLoader loader = new SimpleLoader();
        
        SimpleRound round = new SimpleRound();
        loader.populatePlayers(round);
        loader.populateMatchs(round);
        printResults(round);
        RoundRanking ranking = new RoundRanking(round);
        ranking.generateRanking();
        printRanking(ranking);
    }    
}

package fcorvino.it.fite.roundutil;

import java.util.Comparator;

/**
 *
 * @author Francesco Corvino
 */
public class RankingByPoint implements Comparator<SimpleRank> {

    @Override
    public int compare(SimpleRank r1, SimpleRank r2) {
        if(r1.getPoint()>r2.getPoint()) return -1;
        else if(r1.getPoint()<r2.getPoint()) return 1;
        return 0;
    }
    
}

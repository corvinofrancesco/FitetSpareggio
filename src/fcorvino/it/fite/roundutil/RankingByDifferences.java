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

package fcorvino.it.fite.roundutil;

import java.util.Comparator;

/**
 * RankingByDifferences 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class RankingByDifferences implements Comparator<SimpleRank> {

    @Override
    public int compare(SimpleRank r1, SimpleRank r2) {
        if(r1.getPoint()>r2.getPoint()) return -1;
        else if(r1.getPoint()<r2.getPoint()) return 1;
        // controllo differenza set
        if(r1.getDifferenceSet()> r2.getDifferenceSet()) return -1;
        else if(r1.getDifferenceSet()<r2.getDifferenceSet()) return 1;
        // controllo differenza punti
        if(r1.getDifferencePoint()> r2.getDifferencePoint()) return -1;
        else if(r1.getDifferencePoint()<r2.getDifferencePoint()) return 1;
        return 0;
    }

}

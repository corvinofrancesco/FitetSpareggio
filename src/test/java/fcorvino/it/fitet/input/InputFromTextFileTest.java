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
package fcorvino.it.fitet.input;

import fcorvino.it.fitet.CommonData;
import fcorvino.it.fitet.model.SimpleRound;
import junit.framework.TestCase;

/**
 *
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class InputFromTextFileTest extends TestCase {
    
    public InputFromTextFileTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getRound method, of class InputFromTextFile.
     */
    public void testGetRound() {
        System.out.println("getRound");
        InputFromTextFile instance = new InputFromTextFile();
        SimpleRound r = instance.getRound(CommonData.testRoundString);
        
        assertEquals("Expected 6 match in round",r.getNumMatches(), 6);
        assertEquals("Expected 4 players", r.getNumPlayers(), 4);
        
    }
}

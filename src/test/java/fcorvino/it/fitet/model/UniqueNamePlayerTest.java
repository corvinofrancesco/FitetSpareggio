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

import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class UniqueNamePlayerTest extends TestCase {
    
    public UniqueNamePlayerTest(String testName) {
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
     * Test of getPlayer method, of class UniqueNamePlayer.
     */
    public void testGetPlayer() {
        System.out.println("getPlayer");
        String completeName = "PlayerName PlayerSurname";
        String expResult = "PlayerSurname PlayerName";
        UniqueNamePlayer result = UniqueNamePlayer.getPlayer(completeName);
        assertEquals(expResult, result.toString());
    }

    /**
     * Test of equals method, of class UniqueNamePlayer.
     */
    public void testEquals() {
        System.out.println("equals");
        Object obj = new UniqueNamePlayer("Name", "Surname");
        UniqueNamePlayer instance = new UniqueNamePlayer("Name", "Surname");;
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
                
        obj = new UniqueNamePlayer("Name", "Surname2");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);
    }
}

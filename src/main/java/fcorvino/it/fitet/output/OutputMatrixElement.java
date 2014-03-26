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

package fcorvino.it.fitet.output;

import fcorvino.it.fitet.model.SimplePlayer;

/**
 * OutputMatrixElement 
 * @author Francesco Corvino <fcorvino86@gmail.com>
 */
public class OutputMatrixElement {
    private SimplePlayer p1;
    private SimplePlayer p2;
    
    private String result;

    public OutputMatrixElement() {
    }

    public OutputMatrixElement(SimplePlayer p1, SimplePlayer p2, String result) {
        this.p1 = p1;
        this.p2 = p2;
        this.result = result;
    }

    public OutputMatrixElement(SimplePlayer p) {
        this.p1 = p;
        this.p2 = p;
        this.result = "X";
    }
    
    public SimplePlayer getP1() {
        return p1;
    }

    public void setP1(SimplePlayer p1) {
        this.p1 = p1;
    }

    public SimplePlayer getP2() {
        return p2;
    }

    public void setP2(SimplePlayer p2) {
        this.p2 = p2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public boolean containsPlayer(SimplePlayer p){
        if(p1.equals(p)) return true;
        if(p2.equals(p)) return true;
        return false;
    }
    
    public OutputMatrixElement invert(){
        String invResult = new StringBuilder(result).reverse().toString();
        OutputMatrixElement el = new OutputMatrixElement(p2, p1, invResult);
        return el;
    }
}

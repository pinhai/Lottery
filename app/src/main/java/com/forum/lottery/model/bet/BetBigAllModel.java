package com.forum.lottery.model.bet;

import com.forum.lottery.model.bet.lhc.BetLHCAllModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/9.
 */

public class BetBigAllModel {

    List<BetBigBigModel> result;
    List<BetBigBigModel2> result2;
    List<BetBigBigModel41> result3;
    List<BetLHCAllModel> resultLHC;

    public List<BetLHCAllModel> getResultLHC(){
        return resultLHC;
    }

    public void setResultLHC(List<BetLHCAllModel> resultLHC){
        this.resultLHC = resultLHC;
    }

    public List<BetBigBigModel41> getResult3() {
        return result3;
    }

    public void setResult3(List<BetBigBigModel41> result3) {
        this.result3 = result3;
    }

    public List<BetBigBigModel> getResult() {
        return result;
    }

    public void setResult(List<BetBigBigModel> result) {
        this.result = result;
    }

    public List<BetBigBigModel2> getResult2() {
        return result2;
    }

    public void setResult2(List<BetBigBigModel2> result2) {
        this.result2 = result2;
    }
}

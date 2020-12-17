package com.witnip.bmi;

import com.witnip.bmi.Model.WeightTrackerModel;

import java.util.Comparator;

public class CompareWeight implements Comparator<WeightTrackerModel> {



    @Override
    public int compare(WeightTrackerModel o1, WeightTrackerModel o2) {
        if(o2.getWeight()>o1.getWeight()){
            return 1;
        }else
        {
            return -1;
        }

    }
}

package com.lightcyclesoftware.fragmentexample.webservices;

import com.lightcyclesoftware.fragmentexample.com.lightcyclesoftware.fragmentexample.core.entities.Culture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ewilliams on 2/23/15.
 */
public class FeedzillaCulturesResponse {
    public Culture[] culture;

    public List<Culture> toCultures(){
        List<Culture> cultures  = new ArrayList<Culture>(Arrays.asList(culture));
        return cultures;
    }

}

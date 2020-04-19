package com.altimetrik.pppocr.utils;

import org.springframework.stereotype.Component;

@Component
public class StringCleaner {

    public String clearChar(String val){
        if(val==null){
            return val;
        }

        val = val.replaceAll("[\\n\\r ]", "");
        return val;
    }
}

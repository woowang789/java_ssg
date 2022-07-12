package com.woowang.ssg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Command {
    private String path;
    private Map<String,String> query = new HashMap<>();

    public Command(String url) {
        String[] split = url.split("\\?",2);
        this.path = split[0];
        if(split.length==2)
            getQueries(split[1]);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    private void getQueries(String queryStr){
        Arrays.stream(queryStr.split("&")).forEach(token->{
            String[] split = token.split("=");
            if(split.length==2) query.put(split[0],split[1]);
        });
    }


}

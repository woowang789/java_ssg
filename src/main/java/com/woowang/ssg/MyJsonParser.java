package com.woowang.ssg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyJsonParser {
    private ObjectMapper mapper = new ObjectMapper();

//   {"id": 1,"content": "니 자신을 알라","author": "몰루"}
//    TODO : content 에 , 포함되면 망함
    public Map<Integer,WiseSay> toWiseSayList(String str){
        try {
            Map<Integer,WiseSay> map = new HashMap<>();
            WiseSay[] wiseSays = mapper.readValue(str,WiseSay[].class);
            System.out.println(Arrays.toString(wiseSays));
            Arrays.stream(wiseSays).forEach(item->{
                map.put(item.getId(),item);
            });
            return map;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJsonArray(Map<Integer,WiseSay> map){
        try {
            List<WiseSay> wiseSays = map.values().stream().toList();
            return mapper.writeValueAsString(wiseSays);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

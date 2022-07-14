package com.woowang.ssg.Parser;

import com.woowang.ssg.WiseSay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyJsonParser implements JsonParser{
//    [{"id":1,"content":"나는 포기했을 때의 기분을 ...","author":"네일라 레이"},{...},....]
    @Override
    public Map<Integer, WiseSay> toWiseSayList(String str) {
        Map<Integer,WiseSay> sayMap = new HashMap<>();
        tokenization(str.substring(1, str.length() - 1)).stream().map(this::strToWiseSay)
                .forEach(i->{
                    sayMap.put(i.getId(),i);
                });

        return sayMap;
    }
//[{"id":1,"content":"나는 포기했을 때의 기분을 ...","author":"네일라 레이"},{...}]
    @Override
    public String toJsonArray(Map<Integer, WiseSay> map) {
        StringBuilder sb=  new StringBuilder("[");
        for(Map.Entry<Integer,WiseSay> entry : map.entrySet()){
            WiseSay say = entry.getValue();
            sb.append("{\"id\":"+say.getId()+",\"content\":\""+say.getContent()+"\",\"author\":\""+say.getAuthor()+"\"},");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
//   output :  "id":1,"content":"나는 포기했을 때의 기분을 ...","author":"네일라 레이" , "id":2,"content":"나는 포기했을 때의 기분을 ...","author":"네일라 레이"
    private List<String> tokenization(String str){
        List<String> tokens = new ArrayList<>();
        int idx = 0;
        while(idx < str.length()){
            int tmp = idx+1;
            while(tmp-1 >=0 && tmp < str.length() && (str.charAt(tmp-1)!='}'
                    || str.charAt(tmp)!=',')){
                tmp++;
            }
            tokens.add(str.substring(idx+1,tmp));
            idx = tmp+1;
        }
        return tokens;
    }
    // "id":1,"content":"나는 포기했을 때의 기분을 ...","author":"네일라 레이" -> WiseSay
    private WiseSay strToWiseSay(String str){
        Map<String,String> map = new HashMap<>();
        int idx = 0;
        while(idx < str.length()){
            int tmp = idx;
            while(tmp +1 < str.length() && (str.charAt(tmp)!=',' || str.charAt(tmp+1)!='"')) tmp++;
            String[] token = str.substring(idx,tmp).split(":",2);
            map.put(processQuote(token[0]),processQuote(token[1]));
            idx = tmp+1;
        }
        if(!(map.containsKey("id") && map.containsKey("content") && map.containsKey("author")))
            throw new RuntimeException("파싱 중 에러");
        return new WiseSay(Integer.parseInt(map.get("id")), map.get("content"),map.get("author"));
    }
    // "id" -> id, "content" -> content
    private String processQuote(String str){
        if(str.startsWith("\"")) return str.substring(1,str.length()-1);
        return str;
    }
}

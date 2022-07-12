package com.woowang.ssg;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WiseSayRepository {
    private final MyJsonParser parser;
    private final String path;
    private Map<Integer,WiseSay> wiseSayMap = new HashMap<>();
    private int idx; //현재 번호

    public WiseSayRepository(String path,MyJsonParser parser){
        this.parser = parser;
        this.path = path;
        idx = load(this.path);
    }

    private int load(String path){
        int max = 1;
        File f = new File(path);
        if(!f.exists()){
            System.out.println("data.json 파일이 존재하지 않습니다.");
            return 1;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line=br.readLine()) != null) sb.append(line);
            wiseSayMap = parser.toWiseSayList(sb.toString());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        for(int t : wiseSayMap.keySet()) max = Math.max(max,t);
        return max;
    }

    public int save(String content,String author){
        WiseSay say = new WiseSay(++idx,content,author);
        this.wiseSayMap.put(idx,say);
        syncData();
        return idx;
    }

    public WiseSay getOne(int id){
        return wiseSayMap.getOrDefault(id,null);
    }
    public List<WiseSay> getAll(){
        return wiseSayMap.entrySet().stream().sorted((o1, o2) -> o2.getKey()-o1.getKey())
                .map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public void remove(int id){
        wiseSayMap.remove(id);
        syncData();
    }
    public void edit(int id,String content){
        WiseSay wiseSay = getOne(id);
        wiseSay.setContent(content);
        syncData();
    }

    private void syncData(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.path))){
            bw.write(parser.toJsonArray(this.wiseSayMap));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

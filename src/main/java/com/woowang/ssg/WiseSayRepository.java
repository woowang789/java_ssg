package com.woowang.ssg;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WiseSayRepository {
    private final String path;
    private Map<Integer,WiseSay> wiseSayMap = new HashMap<>();
    private int idx; //현재 번호

    public WiseSayRepository(String path){
        this.path = path;
        idx = load(this.path);
    }

    private int load(String path){
        int max = 1;
        File[] datas = new File(path).listFiles();
        if(datas == null || datas.length == 0) return 1; //데이터가 없어서 인덱스 1 반환
        for(File data : datas){
            max = Math.max(Integer.parseInt(data.getName().split("\\.")[0]),max);

            try(BufferedReader br = new BufferedReader(new FileReader(data));){
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line=br.readLine()) != null) sb.append(line);
                WiseSay wiseSay = MyJsonParser.toWiseSay(sb.toString());
                wiseSayMap.put(wiseSay.getId(), wiseSay);

            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return max;
    }

    public int save(String content,String author){
        WiseSay say = new WiseSay(++idx,content,author);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.path+idx+".json"))){
            bw.write(MyJsonParser.toJson(say));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.wiseSayMap.put(idx,say);
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
        File f = new File(this.path+id+".json");
        boolean delete = f.delete();
        if(!delete) throw new RuntimeException("파일 실패 삭제");
        wiseSayMap.remove(id);
    }
    public void edit(int id,String content){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.path+id+".json"))){
            WiseSay wiseSay = getOne(id);
            wiseSay.setContent(content);
            bw.write(MyJsonParser.toJson(wiseSay));
        }catch (IOException e){
            throw new RuntimeException("파일 수정 실패");
        }
    }


}

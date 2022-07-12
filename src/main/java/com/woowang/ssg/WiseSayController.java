package com.woowang.ssg;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WiseSayController {
    private final WiseSayRepository repository;
    private final BufferedReader br;

    public WiseSayController(WiseSayRepository repository, BufferedReader br) {
        this.repository = repository;
        this.br = br;
    }

    public void saveWiseSay(){
        try {
            System.out.print("명언 : ");
            String content = br.readLine();
            System.out.print("작가 : ");
            String author = br.readLine();
            int idx = repository.save(content, author);
            System.out.println(idx+"번 명언이 등록되었습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getWiseSayList(){
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-----------------");
        List<WiseSay> list = repository.getAll();
        for(WiseSay say : list){
            System.out.println(say.getId()+" / "+say.getAuthor()+" / "+say.getContent());
        }
    }
    public void removeWiseSay(Map<String,String> query){
        if(!query.containsKey("id")){
            System.out.println("쿼리가 잘못 되었습니다.");
            return;
        }
        int id = Integer.parseInt(query.get("id"));
        if(repository.getOne(id) == null){
            System.out.println("해당 명언이 존재하지 않습니다.");
            return;
        }
        repository.remove(id);
        System.out.println(id+"번 명언이 삭제되었습니다.");
    }

    public void editWiseSay(Map<String,String> query){
        if(!query.containsKey("id")){
            System.out.println("쿼리가 잘못 되었습니다.");
            return;
        }
        int id = Integer.parseInt(query.get("id"));
        WiseSay one = repository.getOne(id);
        if(one == null){
            System.out.println("해당 명언이 존재하지 않습니다.");
            return;
        }try {
            System.out.println("기존의 명언 ) "+one.getContent());
            System.out.print("수정할 명언 ) ");
            String newContent = br.readLine();
            repository.edit(id,newContent);
        } catch (IOException e) {
            throw new RuntimeException("수정 실패");
        }
    }
}

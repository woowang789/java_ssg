package com.woowang.ssg;

import org.apache.commons.lang3.StringUtils;

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

    public void saveCmd(){
        try {
            //검증
            System.out.print("명언 : ");
            String content = br.readLine();
            if(!isValidInput(content)) return ;
            System.out.print("작가 : ");
            String author = br.readLine();
            if(!isValidInput(author)) return ;

            int idx = repository.save(content, author);
            System.out.println(idx+"번 명언이 등록되었습니다.");
        } catch (IOException e) {
            throw new RuntimeException("입력을 읽는 도중 에러 발생");
        }
    }


    public void listCmd(){
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-----------------");
        List<WiseSay> list = repository.getAll();
        for(WiseSay say : list)
            System.out.println(say.getId()+" / "+say.getAuthor()+" / "+say.getContent());
    }
    public void removeCmd(Map<String,String> query){
        // 검증
        if(!isValidQuery(query)) return;
        int id = Integer.parseInt(query.get("id"));
        if(findWiseSayById(id)== null) return;

        repository.remove(id);
        System.out.println(id+"번 명언이 삭제되었습니다.");
    }

    public void editCmd(Map<String,String> query){
        // 검중
        if(!isValidQuery(query)) return;
        int id = Integer.parseInt(query.get("id"));
        WiseSay one = findWiseSayById(id);
        if(one == null) return;

        try {
            System.out.println("기존의 명언 ) "+one.getContent());
            System.out.print("수정할 명언 ) ");
            String newContent = br.readLine();

            if(!isValidInput(newContent)) return;

            repository.edit(id,newContent);
            System.out.println(id+"번 명언을 수정했스니다.");
        } catch (IOException e) {
            throw new RuntimeException("수정 도중 에러 발생");
        }
    }

    private boolean isValidInput(String content) {
        if(StringUtils.isEmpty(content)){
            System.out.println("올바르지 않은 입력입니다.");
            return false;
        }
        return true;
    }
    private boolean isValidQuery(Map<String,String> map){
        boolean ret = true;
        if(!map.containsKey("id")) ret = false;
        String val = map.get("id");
        int idx = 0;
        while(idx < val.length()) {
            if(!Character.isDigit(val.charAt(idx))){
                ret = false;
                break;
            }
            idx++;
        }
        if(!ret) System.out.println("올바르지 않은 쿼리입니다.");
        return ret;
    }
    private WiseSay findWiseSayById(int id){
        WiseSay one = repository.getOne(id);
        if(one == null){
            System.out.println("해당 명언이 존재하지 않습니다.");
            return null;
        }
        return one;
    }
}

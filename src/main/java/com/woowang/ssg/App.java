package com.woowang.ssg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class App {
    private final WiseSayController controller;
    private final BufferedReader br;

    public App(WiseSayController controller,BufferedReader br) {
        this.controller = controller;
        this.br =br;
    }

    void run() throws IOException {
        System.out.println("=== 명언 SSG ===");
        outer:
        while(true){
            System.out.print("명령 ) ");
            Command cmd = new Command(br.readLine());

            switch (cmd.getPath()){
                case "등록":
                    controller.saveWiseSay();
                    break;
                case "목록":
                    controller.getWiseSayList();
                    break;
                case "삭제":
                    controller.removeWiseSay(cmd.getQuery());
                    break;
                case "수정":
                    controller.editWiseSay(cmd.getQuery());
                    break;
                case "종료":
                    break outer;

            }
        }
    }

}

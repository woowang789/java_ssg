package com.woowang.ssg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String DATA_DIR_PATH = "./data.json" ;

        MyJsonParser parser = new MyJsonParser();

        WiseSayRepository repository= new WiseSayRepository(DATA_DIR_PATH,parser);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        WiseSayController controller = new WiseSayController(repository,br);

        new App(controller,br).run();
        br.close();
    }
}

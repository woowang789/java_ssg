package com.woowang.ssg;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String DATA_DIR_PATH = "./src/main/java/com/woowang/ssg/data.json" ;
        MyJsonParser parser = new MyJsonParser();
        WiseSayRepository repository= new WiseSayRepository(DATA_DIR_PATH,parser);
        new App(repository).run();

    }
}

package com.woowang.ssg;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String DATA_DIR_PATH = "./src/main/java/com/woowang/ssg/data/" ;
        new App(new WiseSayRepository(DATA_DIR_PATH)).run();
    }
}

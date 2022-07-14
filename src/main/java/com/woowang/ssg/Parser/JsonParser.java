package com.woowang.ssg.Parser;

import com.woowang.ssg.WiseSay;

import java.util.Map;

public interface JsonParser {
    Map<Integer, WiseSay> toWiseSayList(String str);
    String toJsonArray(Map<Integer,WiseSay> map);
}

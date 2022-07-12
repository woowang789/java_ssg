package com.woowang.ssg;

public class MyJsonParser {

//   {"id": 1,"content": "니 자신을 알라","author": "몰루"}
//    TODO : content 에 , 포함되면 망함
    public static WiseSay toWiseSay(String str){
        String[] split = str.substring(1, str.length() - 1).split(",");
        int id = 0;
        String content = "";
        String author = "";
        for(String s : split){
            String[] token = s.split(":");
            String key = detachQuot(token[0].trim());
            String value = detachQuot(token[1].trim());
            if(key.startsWith("id")) id = Integer.parseInt(value);
            if(key.startsWith("content")) content = value;
            if(key.startsWith("author")) author = value;
        }
        return new WiseSay(id,content,author);
    }
    public static String toJson(WiseSay wise){
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":").append(wise.getId()+",");
        sb.append("\"content\":").append("\""+wise.getContent()+"\",");
        sb.append("\"author\":").append("\""+wise.getAuthor()+"\"");
        sb.append("}");
        return sb.toString();
    }
    private static String detachQuot(String str){
        if(!str.startsWith("\"")) return str;
        return str.substring(1,str.length()-1);
    }
}

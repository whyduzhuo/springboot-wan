package com.duzhuo.wansystem.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallableAndFuture {
    public static void main(String[] args) {
        String s = "万乐根 40\n" +
                "陶大平 20\n" +
                "万国金 20\n" +
                "万美连 20\n" +
                "万玉兵 20\n" +
                "熊银根 20\n" +
                "端葵    20\n" +
                "万发根 20 \n" +
                "万红军 20\n" +
                "任珊   20\n" +
                "万国强 20\n" +
                "万红红 20\n" +
                "陶勇文 100\n" +
                "万华华 20\n" +
                "万凤仁20\n" +
                "万斌 20\n" +
                "万里红 20\n" +
                "万小平20\n" +
                "陶火仁 20\n" +
                "万春福 20\n" +
                "万玉明 20\n" +
                "车远华 20\n" +
                "陶毛崽50\n" +
                "万春泉20\n" +
                "万义勇20\n" +
                "万玉平 20\n" +
                "万玉华 20\n" +
                "陶贵兰 50\n" +
                "万北荣 20\n" +
                "万凯    20\n" +
                "万里龙 100\n" +
                "占凤英 20\n" +
                "万正辉 20\n" +
                "陶勇根 20\n" +
                "万青文 20\n" +
                "万永金 20\n" +
                "万青根 20\n" +
                "万爱民 20\n" +
                "万金林 20\n" +
                "帅腊根 20\n" +
                "万明华 20\n" +
                "陶勇明 20\n" +
                "陶勇国 20\n" +
                "陶勇红 20\n" +
                "万国华 20\n" +
                "熊永平   20\n" +
                "万春冬 20\n" +
                "万小兵 20\n" +
                "陶勇强 20\n" +
                "万金海 20\n" +
                "虎子   20\n" +
                "陶勇平 20\n" +
                "万里强 20\n" +
                "万国红 20\n" +
                "万六子 20\n" +
                "万清国 20";
        List<String> stringList = getMatchers("[0-9]{2,3}",s);
        System.err.println(stringList);
        BigDecimal count = BigDecimal.ZERO;
        for (String str:stringList) {
            count = count.add(new BigDecimal(str));
        }

        System.err.println(count);
    }

    public static List<String> getMatchers(String regex, String source){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
}
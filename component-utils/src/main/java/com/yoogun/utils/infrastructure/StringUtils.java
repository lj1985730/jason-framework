package com.yoogun.utils.infrastructure;

import difflib.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  自定义字符串帮助类
 */
public class StringUtils {

    public static float getSimilarityRatio(String src, String target) {
        List<Delta<String>> deltas = StringUtils.getDeltas(src, target);
        if(deltas == null || deltas.size() == 0) {
            return 0;
        }
        if(deltas.size() == 1 && deltas.get(0) instanceof ChangeDelta) {
            return 0;
        }
        for(Delta<String> delta : deltas) {
            delta.getRevised().getLines();
        }
        return 0;
    }

    /**
     * 获取两字符串的相似度
     * @param src 比较源
     * @param target 比较对象
     */
    public static List<Delta<String>> getDeltas(String src, String target) {
        if(org.apache.commons.lang3.StringUtils.isBlank(src) ||
                org.apache.commons.lang3.StringUtils.isBlank(target) ) {
            return null;
        }

        List<String>  srcList = StringUtils.splitString(src);   // 拆解字符串成List
        List<String>  targetList = StringUtils.splitString(target);

        Patch<String> patch = DiffUtils.diff(srcList, targetList);
        return patch.getDeltas();
    }

    /**
     * 拆解字符串成List
     * @param src 拆解源
     * @return 拆解结果
     */
    private static List<String> splitString(String src) {
        if(org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return Collections.emptyList();
        }
        char[] srcChars = src.toCharArray();
        List<String> resultList = new ArrayList<>();
        for (char srcChar : srcChars) {
            resultList.add(String.valueOf(srcChar));
        }
        return resultList;
    }
}

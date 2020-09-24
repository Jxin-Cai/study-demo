package com.jxin.study.demo.language.script.relolver;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jxin.study.demo.language.script.ast.INode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 简单语义解释器
 * @author Jxin
 * @version 1.0
 * @since 2020/7/16 11:06
 */
public class SimpleRelolver implements IRelolver {
    private HashMap<String, Integer> intMap = Maps.newHashMap();
    private HashMap<String, String> strMap = Maps.newHashMap();
    private HashSet<String> allMap = Sets.newHashSet();
    @Override
    public List<String> decipher(INode node) {
        switch (node.type()){
            case ROOT :
                return node.childrens()
                           .stream()
                           .map(this::decipher)
                           .reduce(new ArrayList<>(), (all, item) -> {all.addAll(item);return all;});
            case INT_VALUE :
                break;
            case FLOAT_VALUE :
                break;
            case STR_VALUE :
                break;
            case ID :
                break;
            case LEVEL_ONE_STMT :
                break;
            case LEVEL_TWO_STMT :
                break;
            case ASSIGNMENT_STMT :
                break;

        }
        return null;
    }


}

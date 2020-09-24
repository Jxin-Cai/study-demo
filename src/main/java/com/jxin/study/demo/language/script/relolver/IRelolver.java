package com.jxin.study.demo.language.script.relolver;

import com.jxin.study.demo.language.script.ast.INode;

import java.util.List;

/**
 * 语义解释器
 * @author Jxin
 * @version 1.0
 * @since 2020/7/16 11:02
 */
public interface IRelolver {

    /**
     * 语义解释
     * @param  node 抽象语法树
     * @return 解释语义的执行结果
     */
    List<String> decipher(INode node);
}

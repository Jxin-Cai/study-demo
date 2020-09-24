package com.jxin.study.demo.language.script.parser;

import com.jxin.study.demo.language.script.ast.INode;
import com.jxin.study.demo.language.script.ast.ITokenReader;

/**
 * 语法解析器
 * @author Jxin
 * @version 1.0
 * @since 2020/6/24 18:51
 */
public interface IParser {
    static IParser instance(){
        return SimpleParser.instance();
    }

    /**
     * 解析脚本
     * @param  tokenReader token流 读取器
     * @return AST的节点树(链表)
     */
    INode parse(ITokenReader tokenReader);
}

package com.jxin.study.demo.language.script.lexer;

import com.jxin.study.demo.language.script.ast.ITokenReader;

/**
 * 词法分析器
 * @author Jxin
 * @version 1.0
 * @since 2020/6/22 10:48
 */
public interface ILexer {
    static ILexer instance(){
        return SimpleLexer.instance();
    }
    /**
     * 解析字符串 输出Token流读取器
     * @param  text 代码文本
     * @return token流 读取器
     */
    ITokenReader tokenize(String text);
}

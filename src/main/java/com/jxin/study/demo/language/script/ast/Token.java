package com.jxin.study.demo.language.script.ast;

/**
 *
 * token
 * @author Jxin
 * @version 1.0
 * @since 2020/6/21 11:31 下午
 */
public interface Token {
    /**
     * 获取Token的类型
     * @return Token的类型
     */
    TokenTypeEnum type();

    /**
     * 获取Token的文本值
     * @return Token的文本值
     */
    String textVal();
}

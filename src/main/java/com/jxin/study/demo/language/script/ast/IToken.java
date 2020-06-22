package com.jxin.study.demo.language.script.ast;

/**
 *
 * token
 * @author Jxin
 * @version 1.0
 * @since 2020/6/21 11:31 下午
 */
public interface IToken {
    /**
     * 创建token
     * @param  type    Token 类型枚举
     * @param  textVal 文本值对象
     * @return token
     */
     static IToken of(TokenTypeEnum type, String textVal){
        return SimpleToken.of(type, textVal);
    }
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

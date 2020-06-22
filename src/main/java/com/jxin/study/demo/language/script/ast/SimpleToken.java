package com.jxin.study.demo.language.script.ast;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * token 简单实现
 * @author Jxin
 * @version 1.0
 * @since 2020/6/22 10:01
 */
@NoArgsConstructor
@AllArgsConstructor
public class SimpleToken implements IToken {
    /**Token类型*/
    private TokenTypeEnum type;

    /**文本值*/
    private String textVal;
    /**
     * 创建token
     * @param  type    Token 类型枚举
     * @param  textVal 文本值对象
     * @return token
     */
    static IToken of(TokenTypeEnum type, String textVal){
        return new SimpleToken(type, textVal);
    }
    @Override
    public TokenTypeEnum type() {
        return type;
    }

    @Override
    public String textVal() {
        return textVal;
    }
}

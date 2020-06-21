package com.jxin.study.demo.language.script.ast;

/**
 *
 * Token 类型枚举
 * @author Jxin
 * @version 1.0
 * @since 2020/6/21 11:32 下午
 */
public enum TokenTypeEnum {
    /** 加 */
    ADD,
    /** 减 */
    SUB,
    /** 乘 */
    MUL,
    /**除*/
    DIV,

    /**大于等于*/
    GE,
    /**大于*/
    GT,
    /**恒等于*/
    EQ,
    /**小于等于*/
    LE,
    /**小于*/
    LT,

    /**分号*/
    SEMI_COLON,
    /**左括号*/
    LEFT_PAREN,
    /**右括号*/
    RIGHT_PAREN,


    /**等于号*/
    ASSIGNMENT,

    /**逻辑运算符*/
    If,
    Else,

    /**int 关键字*/
    INT_KEY,
    /**标识符*/
    ID,
    /**整型字面量*/
    INT_VALUE,
    /**字符串字面量*/
    STR_VALUE
}

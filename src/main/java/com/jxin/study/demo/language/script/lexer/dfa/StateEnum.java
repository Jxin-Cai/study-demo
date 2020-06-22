package com.jxin.study.demo.language.script.lexer.dfa;

/**
 * 确定的有限自动机 状态枚举类
 * @author Jxin
 * @version 1.0
 * @since 2020/6/22 11:27
 */
public enum StateEnum {
    /**初始状态*/
    INIT,
    /**字符*/
    ID,
    /**字符串类型*/
    STR,
    /**int整型*/
    INT,
    /**浮点型*/
    FLOAT,

    /**等于号*/
    ASSIGNMENT,
    /** 加 */
    ADD,
    /** 减 */
    SUB,
    /** 乘 */
    MUL,
    /**除*/
    DIV,

    /**大于*/
    GT,
    /**小于等于*/
    LE,
    /**小于*/
    LT,
}

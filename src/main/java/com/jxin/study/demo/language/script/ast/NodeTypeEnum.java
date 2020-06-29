package com.jxin.study.demo.language.script.ast;

/**
 *
 * AST节点类型枚举类
 * @author Jxin
 * @version 1.0
 * @since 2020/6/21 11:21 下午
 */
public enum NodeTypeEnum {
    /**程序入口，根节点*/
    ROOT,

    /**整型变量声明*/
    INT_STMT,
    /**表达式语句，即表达式后面跟个分号*/
    EXPRESSION_STMT,
    /**赋值语句*/
    ASSIGNMENT_STMT,

    /**一级表达式（+, -）*/
    LEVEL_ONE_STMT,
    /**二级表达式（*, /）*/
    LEVEL_TWO_STMT,


    /**标识符*/
    ID,
    /**整型字面量*/
    INT_VALUE,
    /**浮点数字面量*/
    FLOAT_VALUE,
    /**字符串字面量*/
    STR_VALUE
}

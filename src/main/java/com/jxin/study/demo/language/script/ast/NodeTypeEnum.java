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

    /**乘法表达式*/
    MUL,
    /**加法表达式*/
    ADD,

    /**标识符*/
    ID,
    /**整型字面量*/
    INT_VALUE
}

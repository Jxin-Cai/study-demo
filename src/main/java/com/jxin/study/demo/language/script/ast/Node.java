package com.jxin.study.demo.language.script.ast;

/**
 *
 * AST的节点。
 * 属性包括AST的类型、文本值、下级子节点和父节点
 * @author Jxin
 * @version 1.0
 * @since 2020/6/21 11:16 下午
 */
public interface Node {

    /**
     * AST类型
     * @return AST节点类型
     */
    NodeTypeEnum type();

    /**
     * 获取文本值
     *
     * @return 文本值
     */
    String textVal();
    /**
     * 获取子节点数组
     * @return 子节点arr
     */
    Node[] childrens();
    /**
     * 父节点
     * @return 获取父节点
     */
    Node parent();


}

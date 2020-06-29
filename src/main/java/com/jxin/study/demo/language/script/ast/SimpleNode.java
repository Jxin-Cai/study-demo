package com.jxin.study.demo.language.script.ast;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * AST的节点
 * @author Jxin
 * @version 1.0
 * @since 2020/6/22 10:05
 */
@AllArgsConstructor
@NoArgsConstructor
public class SimpleNode implements INode {
    /**节点类型*/
    private NodeTypeEnum type = null;
    /**文本值*/
    private String textVal = null;
    /**父节点*/
    private INode parent;
    /**子节点数组*/
    private List<INode> childrenList;
    static INode of(NodeTypeEnum type, String textVal){
        return new SimpleNode(type, textVal, null, Lists.newArrayList());
    }
    static INode of(NodeTypeEnum type, String textVal, INode parent, List<INode> childrenList){
        return new SimpleNode(type, textVal, parent, childrenList);
    }
    @Override
    public NodeTypeEnum type() {
        return type;
    }

    @Override
    public String textVal() {
        return textVal;
    }

    @Override
    public List<INode> childrens() {
        return Collections.unmodifiableList(childrenList);
    }

    @Override
    public INode parent() {
        return parent;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(INode childNode) {
        childrenList.add(childNode);
    }
}

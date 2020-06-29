package com.jxin.study.demo.language.script.parser;

import com.jxin.study.demo.language.script.ast.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单语法解析器
 * @author Jxin
 * @version 1.0
 * @since 2020/6/24 18:53
 */
@NoArgsConstructor
@Slf4j
public class SimPleParser implements IParser {
    static SimPleParser instance(){
        return new SimPleParser();
    }
    @Override
    public INode parse(ITokenReader tokenReader) {
        final INode root = INode.of(NodeTypeEnum.ROOT, "root");
        while (tokenReader.peek() != null){
            final INode child;
            switch (tokenReader.peek().type()){
                case INT_KEY:
                    child = intDeclare(tokenReader);
                    break;
                case ID:
                    child = assignmentStatement(tokenReader);
                    break;
                default:
                    child = null;
            }

            if(child == null){
                throw new RuntimeException("语法错误: 未知表达式");
            }
            root.addChild(child);
        }
        return root;
    }
    /**
     * 整型变量声明
     * int a;
     * int b = 2+3;
     * @return AST的节点
     * @throws RuntimeException 语法不合法
     */
    private INode intDeclare(ITokenReader tokenReader) {
        // 消耗掉int声明
        tokenReader.next();
        if(tokenReader.peek().type() != TokenTypeEnum.ID){
            throw new RuntimeException("语法错误: int整型声明右边不是标识符");
        }

        final INode result = INode.of(NodeTypeEnum.ID, tokenReader.next().textVal());
        // 声明后如果直接接赋值操作
        if (tokenReader.peek() != null && tokenReader.peek().type() == TokenTypeEnum.ASSIGNMENT) {
            // 取出等号
            tokenReader.next();
            final INode child = operationStatement(tokenReader);
            if (child == null) {
                throw new RuntimeException("invalide variable initialization, expecting an expression");
            }
            result.addChild(child);
        }
        // 消耗分号
        consumeSemicolon(tokenReader);
        return result;
    }
    /**
     * 赋值语句
     *  age = 1 + 1;
     * @param  tokenReader token流 读取器
     * @return 一个语法块
     * @throws RuntimeException 语法不合法
     */
    private INode assignmentStatement(ITokenReader tokenReader) {
        // 字节流往下走
        final IToken next = tokenReader.next();
        final INode result = INode.of(NodeTypeEnum.ASSIGNMENT_STMT, next.textVal());
        if (tokenReader.peek() == null || tokenReader.peek().type() != TokenTypeEnum.ASSIGNMENT) {
            // 回溯，吐出之前消化掉的标识符
            tokenReader.unread();
            return null;
        }
        // 消耗掉等号
        tokenReader.next();
        INode child = operationStatement(tokenReader);
        if (child == null) {
            throw new RuntimeException("语法错误: 等号右面没有一个合法的表达式");
        }
        // 添加子节点
        result.addChild(child);
        // 消耗分号
        consumeSemicolon(tokenReader);
        return result;
    }
    /**
     * 运算表达式
     * @param  tokenReader token流 读取器
     * @return AST的节点
     * @throws RuntimeException 语法不合法
     */
    private INode operationStatement(ITokenReader tokenReader) {
        return levelOneExpression(tokenReader);
    }
    /**
     * 运算赋值语句
     *  age -= 1;
     * @param  tokenReader token流 读取器
     * @return 一个语法块
     * @throws RuntimeException 语法不合法
     */
    private INode operationAssignmentStatement(ITokenReader tokenReader) {
        // 字节流往下走
        final IToken id = tokenReader.next();
        final INode result = INode.of(NodeTypeEnum.ASSIGNMENT_STMT, id.textVal());
        final INode child;
        switch (tokenReader.peek().type()){
            case ADD_EQ:
                child = INode.of(NodeTypeEnum.LEVEL_ONE_STMT, "+");
                child.addChild(INode.of(NodeTypeEnum.ID, id.textVal()));
                break;
            case SUB_EQ:
                child = INode.of(NodeTypeEnum.LEVEL_ONE_STMT, "-");
                child.addChild(INode.of(NodeTypeEnum.ID, id.textVal()));
                break;
            case MUL_EQ:
                child = INode.of(NodeTypeEnum.LEVEL_TWO_STMT, "*");
                child.addChild(INode.of(NodeTypeEnum.ID, id.textVal()));
                break;
            case DIV_EQ:
                child = INode.of(NodeTypeEnum.LEVEL_TWO_STMT, "/");
                child.addChild(INode.of(NodeTypeEnum.ID, id.textVal()));
                break;
            default:
                child = null;
        }

        if (child == null){
            return null;
        }
        INode child2 = operationStatement(tokenReader);
        if (child2 == null) {
            throw new RuntimeException("语法错误: 等号右面没有一个合法的表达式");
        }
        child.addChild(child2);

        result.addChild(child);
        // 消耗分号
        consumeSemicolon(tokenReader);

        return result;
    }


    /**
     * 一级表达式
     *  +, -
     * @param  tokenReader token流 读取器
     * @return AST的节点
     * @throws RuntimeException 语法不合法
     */
    private INode levelOneExpression(ITokenReader tokenReader) {
        INode child1 = levelTwoExpression(tokenReader);
        if(tokenReader.peek() != null ||
           (tokenReader.peek().type() != TokenTypeEnum.ADD && tokenReader.peek().type() != TokenTypeEnum.SUB)){
            return child1;
        }

        // 消耗掉 乘 除表达式
        final IToken token = tokenReader.next();
        final INode child2 = levelTwoExpression(tokenReader);
        if(child2 == null) {
            throw new RuntimeException("语法错误: +, -表达式缺少右半部分");
        }
        final INode result = INode.of(NodeTypeEnum.LEVEL_ONE_STMT, token.textVal());
        result.addChild(child1);
        result.addChild(child2);

        return result;
    }
    /**
     * 二级表达式
     * *, /
     * @param  tokenReader token流 读取器
     * @return AST的节点
     * @throws RuntimeException 语法不合法
     */
    private INode levelTwoExpression(ITokenReader tokenReader) {
        INode child1 = levelThirdExpression(tokenReader);
        if(tokenReader.peek() != null ||
           (tokenReader.peek().type() != TokenTypeEnum.MUL && tokenReader.peek().type() != TokenTypeEnum.DIV)){
            return child1;
        }

        // 消耗掉 乘 除表达式
        final IToken token = tokenReader.next();
        final INode child2 = levelThirdExpression(tokenReader);
        if(child2 == null) {
            throw new RuntimeException("语法错误: *, /表达式缺少右半部分");
        }
        final INode result = INode.of(NodeTypeEnum.LEVEL_TWO_STMT, token.textVal());
        result.addChild(child1);
        result.addChild(child2);

        return result;
    }
    /**
     * 三级表达式
     * 带()
     * @param  tokenReader token流 读取器
     * @return AST的节点
     * @throws RuntimeException 语法不合法
     */
    private INode levelThirdExpression(ITokenReader tokenReader) {
        if(tokenReader.peek() == null){
            return null;
        }
        final TokenTypeEnum type = tokenReader.peek().type();
        if (type == TokenTypeEnum.INT_VALUE) {
            return INode.of(NodeTypeEnum.INT_VALUE, tokenReader.next().textVal());
        }
        if(type == TokenTypeEnum.FLOAT_VALUE){
            return INode.of(NodeTypeEnum.FLOAT_VALUE, tokenReader.next().textVal());
        }
        if(type == TokenTypeEnum.STR_VALUE){
            return INode.of(NodeTypeEnum.STR_VALUE, tokenReader.next().textVal());
        }
        if(type == TokenTypeEnum.ID){
            return INode.of(NodeTypeEnum.ID, tokenReader.next().textVal());
        }

        if (type == TokenTypeEnum.LEFT_PAREN) {
            // 消耗掉左挎号
            tokenReader.next();
            final INode node = levelOneExpression(tokenReader);
            if(node == null){
                throw new RuntimeException("语法错误: 左挎号右边缺少表达式节点");
            }
            if (tokenReader.peek() == null || tokenReader.peek().type() != TokenTypeEnum.RIGHT_PAREN) {
                throw new RuntimeException("语法错误: 左挎号右边缺少右挎号");
            }
            // 消耗掉分号
            tokenReader.next();
            return node;
        }
        throw new RuntimeException("语法错误: 非允许的三级表达式类型");
    }

    /**
     * 消耗分号
     * @param  tokenReader token流 读取器
     * @throws RuntimeException 语法不合法
     */
    private void consumeSemicolon(ITokenReader tokenReader) {
        if (tokenReader.peek() == null || tokenReader.peek().type() != TokenTypeEnum.SEMI_COLON) {
            throw new RuntimeException("语法错误: 缺少分号");
        }
        // 消耗掉分号
        tokenReader.next();
    }
}

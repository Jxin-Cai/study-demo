package com.jxin.study.demo.language.script.lexer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jxin.study.demo.language.script.ast.IToken;
import com.jxin.study.demo.language.script.ast.ITokenReader;
import com.jxin.study.demo.language.script.ast.TokenTypeEnum;
import com.jxin.study.demo.language.script.lexer.dfa.StateEnum;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 简单 词法分析器
 * @author Jxin
 * @version 1.0
 * @since 2020/6/22 11:13
 */
@NoArgsConstructor
@Slf4j
public class SimpleLexer implements ILexer{
    private static final Map<String, TokenTypeEnum> KEY_MAP;
    static {
        KEY_MAP = Maps.newHashMap();
        KEY_MAP.put("int", TokenTypeEnum.INT_KEY);
        KEY_MAP.put("str", TokenTypeEnum.STR_KEY);
        KEY_MAP.put("if", TokenTypeEnum.IF);
        KEY_MAP.put("else", TokenTypeEnum.ELSE);
    }
    /**临时保存token的文本*/
    private StringBuilder tokenText;
    /**保存解析出来的Token 的容器*/
    private List<IToken> tokenList;

    static SimpleLexer instance(){
        return new SimpleLexer();
    }
    @Override
    public ITokenReader tokenize(String text) {
        init();
        final CharArrayReader reader = new CharArrayReader(text.toCharArray());
        try {
            int ich;
            StateEnum dfaState = StateEnum.INIT;
            while ((ich = reader.read()) != -1) {
               final char ch = (char)ich;
                switch (dfaState) {
                    case INIT:
                        // 重新确定后续状态
                        dfaState = initReedState(ch);
                        break;
                    case ID:
                        dfaState = idReedState(ch);
                        break;
                    case INT:
                        dfaState = intReedState(ch);
                        break;
                    case FLOAT:
                        dfaState = floatReedState(ch);
                        break;
                    case ADD:
                        dfaState = addReedState(ch);
                        break;
                    case SUB:
                        dfaState = subReedState(ch);
                        break;
                    case MUL:
                        dfaState = mulReedState(ch);
                        break;
                    case DIV:
                        dfaState = divReedState(ch);
                        break;
                    case GT:
                        dfaState = gtReedState(ch);
                        break;
                    case ASSIGNMENT:
                        dfaState = assignmentReedState(ch);
                        break;
                    case LT:
                        dfaState = ltReedState(ch);
                        break;
                    case STR:
                        dfaState = strReedState(ch);
                        break;
                    default:
                }
            }
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }

        return ITokenReader.of(tokenList);
    }


    /**
     * 初始状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum initReedState(char ch){
        if(isBlank(ch)){
            return StateEnum.INIT;
        }
        if(isId(ch)){
            tokenText.append(ch);
            return StateEnum.ID;
        }
        if(isNum(ch)){
            tokenText.append(ch);
            return StateEnum.INT;
        }
        if(ch == '`'){
            tokenText.append(ch);
            return StateEnum.STR;
        }
        if(ch == '+'){
            tokenText.append(ch);
            return StateEnum.ADD;
        }
        if(ch == '-'){
            tokenText.append(ch);
            return StateEnum.SUB;
        }
        if(ch == '*'){
            tokenText.append(ch);
            return StateEnum.MUL;
        }
        if(ch == '/'){
            tokenText.append(ch);
            return StateEnum.DIV;
        }
        if(ch == '>'){
            tokenText.append(ch);
            return StateEnum.GT;
        }
        if(ch == '<'){
            tokenText.append(ch);
            return StateEnum.LT;
        }
        if(ch == '='){
            tokenText.append(ch);
            return StateEnum.ASSIGNMENT;
        }
        if (ch == ';') {
            tokenText.append(ch);
            addCurrentToken(TokenTypeEnum.SEMI_COLON);
        }
        if (ch == '(') {
            tokenText.append(ch);
            addCurrentToken(TokenTypeEnum.LEFT_PAREN);
        }
        if (ch == ')') {
            tokenText.append(ch);
            addCurrentToken(TokenTypeEnum.RIGHT_PAREN);
        }
        if (ch == '{') {
            tokenText.append(ch);
            addCurrentToken(TokenTypeEnum.DELIM_START);
        }
        if (ch == '}') {
            tokenText.append(ch);
            addCurrentToken(TokenTypeEnum.DELIM_END);
        }
        // skip all unknown patterns
        return StateEnum.INIT;
    }
    /**
     * 字符状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum idReedState(char ch){
        if(isBlank(ch)){
            final TokenTypeEnum tokenTypeEnum = KEY_MAP.get(tokenText.toString());
            if(tokenTypeEnum == null){
                addCurrentToken(TokenTypeEnum.ID);
            }else {
                addCurrentToken(tokenTypeEnum);
            }
            return StateEnum.INIT;
        }
        if(isId(ch)){
            tokenText.append(ch);
            return StateEnum.ID;
        }
        if(isNum(ch)){
            tokenText.append(ch);
            return StateEnum.ID;
        }
        if(ch == '+'){
            addCurrentToken(TokenTypeEnum.ID);
            return initReedState(ch);
        }
        if(ch == '='){
            addCurrentToken(TokenTypeEnum.ID);
            return initReedState(ch);
        }
        if (ch == ';') {
            addCurrentToken(TokenTypeEnum.ID);
            return initReedState(ch);
        }
        throw new IllegalArgumentException("非法的字符格式");
    }
    /**
     * int整型状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum intReedState(char ch) {
        if(isBlank(ch)){
            addCurrentToken(TokenTypeEnum.INT_VALUE);
            return StateEnum.INIT;
        }
        if(ch == '.'){
            tokenText.append(ch);
            return StateEnum.FLOAT;
        }
        if(isNum(ch)){
            tokenText.append(ch);
            return StateEnum.INT;
        }
        if(isOperator(ch)){
            addCurrentToken(TokenTypeEnum.INT_VALUE);
            return initReedState(ch);
        }
        if(ch == ';'){
            addCurrentToken(TokenTypeEnum.INT_VALUE);
            return initReedState(ch);
        }
        if(ch == ')'){
            addCurrentToken(TokenTypeEnum.INT_VALUE);
            return initReedState(ch);
        }
        throw new IllegalArgumentException("非法的int整型格式");
    }
    /**
     * 浮点型状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum floatReedState(char ch) {
        if(isBlank(ch)){
            addCurrentToken(TokenTypeEnum.FLOAT_VALUE);
            return StateEnum.INIT;
        }
        if(isNum(ch)){
            tokenText.append(ch);
            return StateEnum.FLOAT;
        }
        if(isOperator(ch)){
            addCurrentToken(TokenTypeEnum.INT_VALUE);
            return initReedState(ch);
        }
        if(ch == ';'){
            addCurrentToken(TokenTypeEnum.INT_VALUE);
            return initReedState(ch);
        }
        if(ch == ')'){
            addCurrentToken(TokenTypeEnum.INT_VALUE);
            return initReedState(ch);
        }
        throw new IllegalArgumentException("非法的浮点型格式");
    }
    /**
     * 字符串状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum strReedState(char ch) {
        if(ch == '`'){
            addCurrentToken(TokenTypeEnum.STR_VALUE);
            return StateEnum.INIT;
        }
        tokenText.append(ch);
        return StateEnum.STR;

    }

    /**
     * 加法状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum addReedState(char ch) {
        return operatorReedState(ch, TokenTypeEnum.ADD_EQ, TokenTypeEnum.ADD);
    }
    /**
     * 减法状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum subReedState(char ch) {
        return operatorReedState(ch, TokenTypeEnum.SUB_EQ, TokenTypeEnum.SUB);
    }
    /**
     * 乘法状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum mulReedState(char ch) {
        return operatorReedState(ch, TokenTypeEnum.MUL_EQ, TokenTypeEnum.MUL);
    }
    /**
     * 除法状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum divReedState(char ch) {
        return operatorReedState(ch, TokenTypeEnum.DIV_EQ, TokenTypeEnum.DIV);
    }
    /**
     * 大于状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum gtReedState(char ch) {
        return operatorReedState(ch, TokenTypeEnum.GE, TokenTypeEnum.GT);
    }
    /**
     * 等于状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum assignmentReedState(char ch) {
        return operatorReedState(ch, TokenTypeEnum.EQ, TokenTypeEnum.ASSIGNMENT);
    }
    /**
     * 小于状态的值读取
     * @param  ch 字符
     * @return 有限自动机 状态枚举类
     */
    private StateEnum ltReedState(char ch) {
        return operatorReedState(ch, TokenTypeEnum.LE, TokenTypeEnum.LT);
    }

    /**
     * 运算符状态的值读取
     * @param  ch         字符
     * @param  operatorEq token的某一种带等于的运算符类型
     * @param  operator   token的某一种运算符类型
     * @return 有限自动机 状态枚举类
     */
    private StateEnum operatorReedState(char ch, TokenTypeEnum operatorEq, TokenTypeEnum operator) {
        if (ch == '=') {
            tokenText.append(ch);
            addCurrentToken(operatorEq);
            return StateEnum.INIT;
        }
        addCurrentToken(operator);
        return initReedState(ch);
    }



    /**
     * 是否是空白字符
     * @param  ch 字符
     * @return 若是返回true
     */
    private boolean isBlank(int ch) {
        return ch == ' ' || ch == '\t' || ch == '\n';
    }
    /**
     * 是否是字母或下划线
     * @param  ch 字符
     * @return 若是返回true
     */
    private boolean isId(int ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == '_';
    }

    /**
     * 是否是数字
     * @param  ch 字符
     * @return 若是返回true
     */
    private boolean isNum(int ch) {
        return ch >= '0' && ch <= '9';
    }
    /**
     * 是否是运算符
     * @param  ch 字符
     * @return 若是返回true
     */
    private boolean isOperator(int ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
    /**
     * 添加当前token
     */
    private void addCurrentToken(TokenTypeEnum tokenType){
        final IToken currentToken = IToken.of(tokenType, tokenText.toString());
        tokenList.add(currentToken);
        tokenText = new StringBuilder();
    }

    /**
     * 初始化 词法分析器
     */
    private void init(){
        tokenText = new StringBuilder();
        tokenList = Lists.newArrayList();
    }
}

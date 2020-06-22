package com.jxin.study.demo.language.script.ast;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * token流 读取器 简单实现
 * @author Jxin
 * @version 1.0
 * @since 2020/6/22 9:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SimpleTokenReader implements ITokenReader {
    /**
     * token数组
     */
    private IToken[] tokens = null;
    /**
     * 当前偏移量
     */
    private int offset;
    private static final String DUMP_TMP = "{}\t\t{}";
    public static SimpleTokenReader of(List<IToken> tokenList){
        return new SimpleTokenReader(tokenList.toArray(new IToken[0]), 0);
    }

    @Override
    public IToken read() {
        if (offset < tokens.length) {
            return tokens[offset++];
        }
        return null;
    }

    @Override
    public IToken peek() {
        if (offset < tokens.length) {
            return tokens[offset];
        }
        return null;
    }

    @Override
    public void unread() {
        if (offset > 0) {
            offset--;
        }
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        if (offset >=0 && offset < tokens.length){
            this.offset = offset;
        }
    }

    @Override
    public void dump() {
        Arrays.stream(tokens).forEach(token -> log.info(DUMP_TMP, token.textVal(), token.type()));
    }
}

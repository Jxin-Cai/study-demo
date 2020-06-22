package com.jxin.study.demo.language.script.ast;

import java.util.List;

/**
 *
 * token流 读取器
 * @author Jxin
 * @version 1.0
 * @since 2020/6/21 11:40 下午
 */
public interface ITokenReader {
    static ITokenReader of(List<IToken> tokens){
        return SimpleTokenReader.of(tokens);
    }
    /**
     * 返回Token流中下一个Token，并从流中取出。 如果流已经为空，返回null;
     * @return token
     * @author Jxin
     */
    IToken read();

    /**
     * 返回Token流中下一个Token，但不从流中取出。 如果流已经为空，返回null;
     * @return token
     * @author Jxin
     */
    IToken peek();

    /**
     * Token流回退一步。回退到read前的状态
     * @author Jxin
     */
    void unread();

    /**
     * 获取Token流当前的 偏移量。
     * @return 偏移量
     * @author Jxin
     */
    int getOffset();

    /**
     * 设置Token流 当前的偏移量
     * @param offset 偏移量
     * @author Jxin
     */
    void setOffset(int offset);
    /**
     * 打印Token流的所有token
     */
    void dump();
}

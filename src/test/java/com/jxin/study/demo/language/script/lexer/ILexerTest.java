package com.jxin.study.demo.language.script.lexer;

import com.google.common.collect.Lists;
import com.jxin.study.demo.language.script.ast.ITokenReader;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Jxin
 * @version 1.0
 * @since 2020/6/22 16:31
 */
public class ILexerTest {

    @Test
    public void tokenize() {
        final ILexer instance = ILexer.instance();
        final String text = "int a = 1 + 3 * 4;if (a <= 4){a= 0;}";
        final ITokenReader tokenize = instance.tokenize(text);
        tokenize.dump();
    }
}
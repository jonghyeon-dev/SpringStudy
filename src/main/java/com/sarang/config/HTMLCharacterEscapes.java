package com.sarang.config;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

public class HTMLCharacterEscapes extends CharacterEscapes{
     private final int[] asciiEscapes;

    public HTMLCharacterEscapes() {

        // 1. XSS 방지 처리할 특수 문자 지정
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        // 윈도우 이모지 이슈 처리
        SerializedString serializedString;
        char charAt = (char) ch;
        if (Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {
            StringBuilder sb = new StringBuilder();
            sb.append("\\u");
            sb.append(String.format("%04x", ch));
            serializedString = new SerializedString(sb.toString());
        } else {
            serializedString = new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString(charAt)));
        }
        return serializedString;
    }
}

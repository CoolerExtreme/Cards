package com.cooler.cool.Util;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static com.cooler.cool.Util.References.*;

public class TextRenderer
{
    private static HashMap<String, HashMap<Character, charObj>> fonts = new HashMap<String, HashMap<Character, charObj>>();
    private static ArrayList<TextObj> Texts = new ArrayList<TextObj>();
    public static float offX = 0,offY = 0;
    private static short i = 0;
    private static GOText textPlaceholder = new GOText();

    public static void addFont(Textures texObj, String fontName, String charSet)
    {
        textPlaceholder.currFont = new HashMap<Character, charObj>();
        for(char c: charSet.toCharArray())
        {
            textPlaceholder.setChar(c);
            texObj.addToTextureAtlas(RES + "textures/Fonts/" + fontName + "/Char" + c + ".png", textPlaceholder, true);
        }
        fonts.put(fontName, textPlaceholder.currFont);
    }

    public static int addTextToBuffer(FloatBuffer posBuf, FloatBuffer texBuf, ShortBuffer indexBuf, short index, int drawElements)
    {
        i = index;
        for(TextObj currText: Texts)
        {
            HashMap<Character, charObj> currFont = fonts.get(currText.font);
            offX = currText.x;
            offY = currText.y;
            for(char c:currText.text.toCharArray())
            {
                i = currFont.get(c).addToBuffer(posBuf, texBuf, indexBuf, currText.size, i);
                drawElements += 5;
            }
        }
        Texts.clear();
        return drawElements;
    }

    public static void addText(TextObj text)
    {
        Texts.add(text);
    }
}

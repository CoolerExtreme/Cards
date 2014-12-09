package com.cooler.cool.Util.Text;

import com.cooler.cool.Main;
import com.cooler.cool.Util.Textures;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import static com.cooler.cool.Util.References.*;

public class TextRenderer
{
    private static HashMap<String, HashMap<Character, charObj>> fonts = new HashMap<String, HashMap<Character, charObj>>();
    private static ArrayList<TextObj> Texts = new ArrayList<TextObj>();
    public static float offX = 0, offY = 0;
    private static short i = 0;
    private static GOText textPlaceholder = new GOText();

    public static void addFontFromFFT(String fontFile)
    {
        try
        {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(RES + "fonts/" + fontFile));
            font = font.deriveFont(80f);

            BufferedImage charImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D renderer = charImg.createGraphics();
            renderer.setFont(font);
            FontMetrics fontMetrics = renderer.getFontMetrics();
            renderer.dispose();

            String path = RES + "/textures/Fonts/" + fontFile.subSequence(0, fontFile.lastIndexOf('/'));

            File dir = new File(path);
            if (!dir.exists())
                Files.createDirectory(dir.toPath());
            else
                return;

            for (char c = 0; c < 256; c++)
                if (fontMetrics.charWidth(c) > 0)
                {
                    int stringWidth = fontMetrics.charWidth(c);
                    int stringHeight = fontMetrics.getAscent();
                    int stringDescent = fontMetrics.getDescent();

                    BufferedImage actualChar = new BufferedImage(stringWidth, stringHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D actualRenderer = actualChar.createGraphics();

                    actualRenderer.setFont(font);
                    actualRenderer.setPaint(Color.black);
                    actualRenderer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    actualRenderer.drawString(String.valueOf(c), 0, stringHeight - stringDescent);
                    actualRenderer.dispose();

                    ImageIO.write(actualChar, "png", new File(path + "/$" + (short)c + "$.png"));
                }

        } catch (FontFormatException | IOException e)
        {
            e.printStackTrace();
            Main.ErrorClose();
        }
    }

    public static void addFont(Textures texObj, String fontName, String charSet)
    {
        textPlaceholder.currFont = new HashMap<Character, charObj>();
        for (char c : charSet.toCharArray())
        {
            textPlaceholder.setChar(c);
            String charFile = RES + "textures/Fonts/" + fontName + "/$" + (short)c + "$.png";
            texObj.addToTextureAtlas(charFile, textPlaceholder, true);
        }
        fonts.put(fontName, textPlaceholder.currFont);
    }

    public static int addTextToBuffer(FloatBuffer posBuf, FloatBuffer texBuf, ShortBuffer indexBuf, short index, int drawElements)
    {
        i = index;
        for (TextObj currText : Texts)
        {
            HashMap<Character, charObj> currFont = fonts.get(currText.font);
            offX = currText.x;
            offY = currText.y;
            for (char c : currText.text.toCharArray())
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

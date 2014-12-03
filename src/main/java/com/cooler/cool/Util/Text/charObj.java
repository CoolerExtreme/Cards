package com.cooler.cool.Util.Text;

import com.cooler.cool.Util.Text.TextRenderer;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class charObj
{
    public charObj(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    private int x;
    private int y;
    private int w;
    private int h;

    public short addToBuffer(FloatBuffer posBuf, FloatBuffer texBuf, ShortBuffer indexBuf, float size, short i)
    {
        texBuf.put(new float[]{x, y, 0,
                x + w, y, 0,
                x, y + h, 0,
                x + w, y + h, 0});
        float w1 = w * size;
        float h1 = h * size;
        posBuf.put(new float[]{TextRenderer.offX, TextRenderer.offY + h1,
                TextRenderer.offX + w1, TextRenderer.offY + h1,
                TextRenderer.offX, TextRenderer.offY,
                TextRenderer.offX + w1, TextRenderer.offY});
        TextRenderer.offX += w1;
        indexBuf.put(new short[]{i, (short) (i + 1), (short) (i + 2), (short) (i + 3), 32767});
        i += 4;
        return i;
    }
}
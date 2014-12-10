package com.cooler.cool.Util.Math;

import com.cooler.cool.Main;

import java.util.Arrays;

public class Matrix4f
{
    private float[] elements;
    private static float[] identityArr = new float[]{1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1};

    public Matrix4f()
    {
        setIdentity();
    }

    public Matrix4f(float[] data)
    {
        elements = data;
    }

    public Matrix4f(float[] pos, float[] scale)
    {
        setIdentity();
        translate(pos);
        scale(scale);
    }

    public float[] getColumnMajor()
    {
        float[] data1 = new float[16];
        float[] data = Arrays.copyOf(elements, elements.length);
        for (int i = 0; i < 16; i++)
            data1[i] = data[(i / 4) + ((i % 4) * 4)];
        if (Main.debug)
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                    System.out.print(data1[(i * 4) + j] + "              ");// + ":" + data1[(i * 4) + j] + "               ");
                System.out.println();
            }
            Main.debug = false;
        }
        return data1;
    }

    public void setIdentity()
    {
        elements = Arrays.copyOf(identityArr, identityArr.length);
    }

    public void translate(float[] pos)
    {
        elements[3] += pos[0];
        elements[7] += pos[1];
        elements[11] += pos[2];
    }

    public void setTranslation(float[] pos)
    {
        elements[3] = pos[0];
        elements[7] = pos[1];
        elements[11] = pos[2];
    }

    public void scale(float[] factor)
    {
        elements[0] *= factor[0];
        elements[5] *= factor[1];
        elements[10] *= factor[2];
    }

    public void setScale(float[] factor)
    {
        elements[0] = factor[0];
        elements[5] = factor[1];
        elements[10] = factor[2];
    }
}
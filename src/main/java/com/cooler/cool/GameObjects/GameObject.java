package com.cooler.cool.GameObjects;

import java.nio.FloatBuffer;

public abstract class GameObject
{
    public GameObject(float x, float y, float z, float w, float h, int texLayer)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.h = h;
        this.texLayer = texLayer;
    }

    public abstract boolean activeUpdate();

    public abstract void passiveUpdate();

    public void addToBuffer(FloatBuffer posBuf, FloatBuffer texBuf)
    {
        posBuf.put(new float[]{x, y + h, z,
                               x + w, y + h, z,
                               x, y, z,
                               x + w, y, z});
        texBuf.put(new float[]{0, 0, texLayer,
                               1, 0, texLayer,
                               0, 1, texLayer,
                               1, 1, texLayer});
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getZ()
    {
        return z;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public float getW()
    {
        return w;
    }

    public void setW(float w)
    {
        this.w = w;
    }

    public float getH()
    {
        return h;
    }

    public void setH(float h)
    {
        this.h = h;
    }

    public int getTexLayer()
    {
        return texLayer;
    }

    public void setTexLayer(int texLayer)
    {
        this.texLayer = texLayer;
    }

    private float x;
    private float y;
    private float z;
    private float w;
    private float h;
    private int texLayer;


}

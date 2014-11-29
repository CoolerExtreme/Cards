package com.cooler.cool.GameObjects;

import com.cooler.cool.Util.IGameObject;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public abstract class GameObject implements Comparable<GameObject>, IGameObject
{
    public GameObject(float x, float y, float z, float w, float h, int texLayer, int xoff, int yoff, int u, int v)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.h = h;
        this.texLayer = texLayer;
        this.xoff = xoff;
        this.yoff = yoff;
        this.u = u;
        this.v = v;
    }

    public abstract boolean activeUpdateKey(long window, int key, int scancode, int action, int mods);

    public abstract void activeUpdateMouseButton(long window, int button, int action, int mods);

    public abstract void activeUpdateCursorPos(long window, double xpos, double ypos);

    public abstract void passiveUpdate(double delta);

    public short addToBuffer(FloatBuffer posBuf, FloatBuffer texBuf, ShortBuffer indexBuf, short i)
    {
        posBuf.put(new float[]{x, y + h,
                               x + w, y + h,
                               x, y,
                               x + w, y});
        texBuf.put(new float[]{xoff, yoff, texLayer,
                               xoff + u, yoff, texLayer,
                               xoff, yoff + v, texLayer,
                               xoff + u, xoff + v, texLayer});
        indexBuf.put(new short[]{i, (short) (i + 1), (short) (i + 2), (short) (i + 3), 32767});
        i += 4;
        return i;
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
    private int texLayer, xoff, yoff, u, v;


    @Override
    public int compareTo(GameObject o)
    {
        float objZ = o.getZ();
        return this.z > objZ ? -1 : (this.z < objZ ? 1 : 0);
    }
}

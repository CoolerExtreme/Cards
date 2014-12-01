package com.cooler.cool.Util.UI;

import com.cooler.cool.GameObjects.GameObject;

public class GOMenu extends GameObject
{
    public GOMenu(float x, float y, float z, float w, float h, int xoff, int yoff, int u, int v)
    {
        super(x, y, z, w, h, 0, xoff, yoff, u, v);
    }

    @Override
    public boolean activeUpdateKey(long window, int key, int scancode, int action, int mods)
    {
        return false;
    }

    @Override
    public void activeUpdateMouseButton(long window, int button, int action, int mods)
    {

    }

    @Override
    public void activeUpdateCursorPos(long window, double xpos, double ypos)
    {

    }

    @Override
    public void passiveUpdate(double delta)
    {

    }

    @Override
    public void setOffsets(int x, int y)
    {

    }
}
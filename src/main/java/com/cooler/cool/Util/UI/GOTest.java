package com.cooler.cool.Util.UI;

import com.cooler.cool.GameObjects.GameObject;

public class GOTest extends GameObject
{
    private static int xoff, yoff;

    public GOTest()
    {
        super(0, 0, 0.1f, 100, 100, 0, xoff, yoff, 40, 40);
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
        xoff = x;
        yoff = y;
    }
}

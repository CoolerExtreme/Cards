package com.cooler.cool.GameObjects;

public class GOBackground extends GameObject
{
    private static int xoff, yoff;
    public GOBackground()
    {
        super(-640, -360, 1, 1280, 720, 0, xoff, yoff, 1280, 720);
    }

    public void setOffsets(int x, int y)
    {
        xoff = x;
        yoff = y;
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
}

package com.cooler.cool.Util;

import com.cooler.cool.GameObjects.GameObject;

import java.util.HashMap;

public class GOText extends GameObject
{
    public HashMap<Character, charObj> currFont;
    private char currChar;

    public GOText()
    {
        super(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
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

    public void setChar(char c)
    {
        currChar = c;
    }

    @Override
    public void setOffsets(int x, int y)
    {
        currFont.put(currChar, new charObj(x, y, (int)this.getW(), (int)this.getH()));
    }
}
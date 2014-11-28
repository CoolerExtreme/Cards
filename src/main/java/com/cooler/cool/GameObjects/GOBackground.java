package com.cooler.cool.GameObjects;

public class GOBackground extends GameObject
{
    public GOBackground()
    {
        super(-640, -360, 1, 1280, 720, 0, 0, 0, 1280, 720);
    }

    @Override
    public boolean activeUpdate()
    {
        return false;
    }

    @Override
    public void passiveUpdate(double delta)
    {

    }
}

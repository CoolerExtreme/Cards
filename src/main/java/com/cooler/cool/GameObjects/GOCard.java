package com.cooler.cool.GameObjects;

public class GOCard extends GameObject
{
    public GOCard(float x, float y, float z, float w, float h, int texLayer)
    {
        super(x, y, z, w, h, texLayer);
    }

    @Override
    public boolean activeUpdate()
    {
        return false;
    }

    @Override
    public void passiveUpdate()
    {

    }
}

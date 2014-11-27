package com.cooler.cool.GameObjects;

public class GOCard extends GameObject
{
    public GOCard(float x, float y, float z, int texLayer)
    {
        super(x, y, z, 223, 311, texLayer);
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

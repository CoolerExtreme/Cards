package com.cooler.cool.GameObjects;

import com.cooler.cool.Main;

public class GOCard extends GameObject
{
    public GOCard(float x, float y, float z, int texLayer)
    {
        super(x, y, z, 223, 311, texLayer, 0, 0, 1, 1);
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

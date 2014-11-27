package com.cooler.cool;

import com.cooler.cool.GameObjects.GOCard;
import com.cooler.cool.GameObjects.GameObject;

import java.util.ArrayList;

public class Setup
{
    ArrayList<GameObject> GOList = new ArrayList<GameObject>();

    public void setup()
    {
        GOList.add(new GOCard(0, 0, 0, 1));
        GOList.add(new GOCard(-20, 0, 0, 1));
        GOList.add(new GOCard(20, 0, 0, 2));
    }
}
package com.cooler.cool;

import com.cooler.cool.GameObjects.GOBackground;
import com.cooler.cool.GameObjects.GOCard;
import com.cooler.cool.GameObjects.GameObject;
import com.cooler.cool.GameObjects.GOMenu;
import com.cooler.cool.GameObjects.GOTest;

import java.util.ArrayList;

public class Setup
{
    ArrayList<GameObject> GOList;

    public void setup(Initialize initObj)
    {
        GOList = initObj.getGOList();
        GOList.add(new GOCard(0, -100, 0, 1));
        GOList.add(new GOCard(-200, 0, 0, 1));
        GOList.add(new GOCard(200, 0, 0, 2));
        GOList.add(new GOMenu());
        GOList.add(new GOTest());
        GOList.add(new GOBackground());
    }
}
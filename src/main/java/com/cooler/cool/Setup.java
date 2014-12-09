package com.cooler.cool;

import com.cooler.cool.GameObjects.GOBackground;
import com.cooler.cool.GameObjects.GOCard;
import com.cooler.cool.GameObjects.GameObject;
import com.cooler.cool.GameObjects.GOMenu;
import com.cooler.cool.GameObjects.GOTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Setup
{
    public ArrayList<GameObject> addList;
    public ArrayList<GameObject> removeList;
    public ArrayList<GameObject> GOList;

    public void setIterating(boolean iterating)
    {
        this.iterating = iterating;
    }

    private boolean iterating = false;
    private boolean changed = false;

    public void setup(Initialize initObj)
    {
        GOList = initObj.getGOList();
        addList = new ArrayList<GameObject>();
        removeList = new ArrayList<GameObject>();

        addGO(new GOBackground());
    }

    public void addGO(GameObject go)
    {
        if(iterating)
            addList.add(go);
        else
            GOList.add(go);
        changed = true;
    }

    public void removeGO(GameObject go)
    {
        if(iterating)
            removeList.add(go);
        else
            GOList.remove(go);
        changed = true;
    }

    public void update(double delta)
    {
        if(changed)
        {
            GOList.addAll(addList);
            GOList.removeAll(removeList);
            addList.clear();
            removeList.clear();
            changed = false;
            Collections.sort(GOList);
        }

        iterating = true;
        for(GameObject go: GOList)
        {
            go.passiveUpdate(delta);
        }
        iterating = false;
    }
}
package com.cooler.cool.Util.UI;

import com.cooler.cool.GameObjects.GameObject;
import com.cooler.cool.Util.TextObj;
import com.cooler.cool.Util.TextRenderer;

public class GOMenu extends GameObject
{
    private static int xoff;
    private static int yoff;
    private int noOfOptions;
    private boolean selected;

    public GOMenu()
    {
        super(-200, -100, 0.1f, 64, 95, 0, xoff, yoff, 64, 95);
        this.noOfOptions = 4;
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
        /*xpos -= 640;
        ypos = 360 - ypos;
        if((xpos > this.getX()) && (ypos > this.getY()) && (xpos < this.getX() + this.getW()) && (ypos < this.getY() + this.getH()))
            selected = true;
        else if(selected)
            selected = false;
        if(selected)
        {
            float optionY = (float)ypos - this.getY();
            if(optionY < 0)
                optionY *= -1;
            int option = (int)(optionY/(this.getH()/noOfOptions));
            System.out.println("Option:" + option);
        }*/
    }

    @Override
    public void passiveUpdate(double delta)
    {
        //TextRenderer.addText(new TextObj(this.getX(), this.getY(), "abcd", "Awesome", 0.2f));
        this.setW(this.getW() + (float)delta * 10f);
    }

    @Override
    public void setOffsets(int x, int y)
    {
        xoff = x;
        yoff = y;
    }
}
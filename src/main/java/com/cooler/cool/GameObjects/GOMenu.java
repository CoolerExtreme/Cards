package com.cooler.cool.GameObjects;

import com.cooler.cool.Util.Text.TextObj;
import com.cooler.cool.Util.Text.TextRenderer;

public class GOMenu extends GameObject
{
    private static int xoff;
    private static int yoff;
    private int noOfOptions;
    private boolean selected;

    public GOMenu(float x, float y)
    {
        super(x, y, 0, 64, 95, 0, xoff, yoff, 63, 94);
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
        TextRenderer.addText(new TextObj(this.getX(), this.getY(), "Option 1", "OpenSans", 0.4f));
    }

    @Override
    public void setOffsets(int x, int y)
    {
        xoff = x;
        yoff = y;
    }
}
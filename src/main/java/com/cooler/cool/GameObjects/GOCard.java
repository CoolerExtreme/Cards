package com.cooler.cool.GameObjects;

import com.cooler.cool.Main;

import static org.lwjgl.system.glfw.GLFW.*;

public class GOCard extends GameObject
{
    private double cursorX;
    private double cursorY;
    private double cOffX;
    private double cOffY;
    private boolean shouldOpenMenu = false;
    private int menuIndex = 0;
    private boolean menuClosed = true;
    private boolean shouldCloseMenu = false;
    private GOMenu menu;

    public GOCard(float x, float y, float z, int texLayer)
    {
        super(x, y, z, 223, 311, texLayer, 0, 0, 1, 1);
    }

    @Override
    public boolean activeUpdateKey(long window, int key, int scancode, int action, int mods)
    {
        if(key == GLFW_KEY_W && action == GLFW_REPEAT)
            this.setY(this.getY() + 10);
        return false;
    }

    @Override
    public void activeUpdateMouseButton(long window, int button, int action, int mods)
    {
        if(selected && button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS)
        {
            selected2 = true;
            cOffX = this.getX() - cursorX;
            cOffY = this.getY() - cursorY;
        }
        else if(selected2)
            selected2 = false;
        if(selected && button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS && menuClosed)
        {
            shouldOpenMenu = true;
        }
        else if(!menuClosed && !selected && action == GLFW_PRESS)
        {
            shouldOpenMenu = false;
            shouldCloseMenu = true;
        }
    }

    @Override
    public void activeUpdateCursorPos(long window, double xpos, double ypos)
    {
        xpos -= 640;
        ypos = 360 - ypos;
        cursorX = xpos;
        cursorY = ypos;
        if((xpos > this.getX()) && (ypos > this.getY()) && (xpos < this.getX() + this.getW()) && (ypos < this.getY() + this.getH()))
            selected = true;
        else if(selected)
            selected = false;
    }

    @Override
    public void passiveUpdate(double delta)
    {
        if(selected2)
        {
            this.setX((float) (cursorX + cOffX));
            this.setY((float) (cursorY + cOffY));
        }
        if(shouldOpenMenu && menuClosed)
        {
            menu = new GOMenu((float)cursorX, (float)cursorY);
            menuClosed = false;
            shouldOpenMenu = false;
            shouldCloseMenu = false;
            Main.getInstance().getSetupObj().addGO(menu);
        }
        if(shouldCloseMenu && !menuClosed)
        {
            menuClosed = true;
            shouldOpenMenu = false;
            shouldCloseMenu = false;
            Main.getInstance().getSetupObj().removeGO(menu);
        }
    }

    @Override
    public void setOffsets(int x, int y)
    {

    }

    private boolean selected = false;
    private boolean selected2 = false;
}

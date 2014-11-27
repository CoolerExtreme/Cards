package com.cooler.cool;

import static org.lwjgl.system.glfw.GLFW.*;

public class Cleanup
{
    public void clean(Initialize initObj)
    {
        //initObj.getShader().dispose();
        glfwDestroyWindow(initObj.getWindow());
        glfwTerminate();
    }
}
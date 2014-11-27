package com.cooler.cool;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.glfw.GLFW.*;

public class Loop
{
    public void mainLoop(Initialize initObj)
    {
        long window = initObj.getWindow();
        while(glfwWindowShouldClose(window) == GL11.GL_FALSE || !initObj.shouldWindowClose())
        {
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}

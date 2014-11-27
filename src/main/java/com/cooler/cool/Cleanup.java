package com.cooler.cool;

import static org.lwjgl.opengl.GL15.*;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.system.glfw.GLFW.*;

public class Cleanup
{
    public void clean(Initialize initObj)
    {
        initObj.getShader().dispose();
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glDeleteBuffers(initObj.getVboVertID());
        glDeleteBuffers(initObj.getVboTexID());
        glDeleteBuffers(initObj.getEboID());
        glDeleteVertexArrays(initObj.getVaoID());
        glfwDestroyWindow(initObj.getWindow());
        glfwTerminate();
    }
}
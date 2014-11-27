package com.cooler.cool;

import com.cooler.cool.GameObjects.GameObject;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.glfw.GLFW.*;

public class Loop
{
    public void mainLoop(Initialize initObj, Setup setupObj)
    {
        long window = initObj.getWindow();
        FloatBuffer pos = initObj.getPosBuf();
        FloatBuffer tex = initObj.getTexBuf();
        ShortBuffer index = initObj.getIndexBuf();
        int vaoID = initObj.getVaoID();
        int drawCount = 0;
        short i = 0;

        while (glfwWindowShouldClose(window) == GL11.GL_FALSE)
        {
            glViewport(0, 0, initObj.getWIDTH(), initObj.getHEIGHT());
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-initObj.getWIDTH()/2, initObj.getWIDTH()/2, -initObj.getHEIGHT()/2, initObj.getHEIGHT()/2, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            pos.clear();
            tex.clear();
            index.clear();
            for (GameObject go : setupObj.GOList)
            {
                i = go.addToBuffer(pos, tex, index, i);
                drawCount += 5;
            }
            pos.flip();
            tex.flip();
            index.flip();

            initObj.getShader().bind();
            glBindVertexArray(vaoID);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            glDrawElements(GL_TRIANGLE_STRIP, drawCount, GL_UNSIGNED_SHORT, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindVertexArray(0);

            initObj.getShader().unbind();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
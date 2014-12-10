package com.cooler.cool;

import com.cooler.cool.GameObjects.GameObject;
import com.cooler.cool.Util.Text.TextRenderer;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.system.glfw.GLFW.*;

public class Loop
{
    public void mainLoop(Initialize initObj, Setup setupObj)
    {
        long window = initObj.getWindow();
        FloatBuffer instanceData = initObj.getInstanceBuf();
        int vaoID = initObj.getVaoID();
        double delta, currFrame, prevFrame;
        prevFrame = glfwGetTime();
        while (glfwWindowShouldClose(window) == GL11.GL_FALSE)
        {

            glViewport(0, 0, initObj.getWIDTH(), initObj.getHEIGHT());
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-initObj.getWIDTH()/2, initObj.getWIDTH()/2, -initObj.getHEIGHT()/2, initObj.getHEIGHT()/2, -1, 1);

            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            currFrame = glfwGetTime();
            delta = currFrame - prevFrame;
            prevFrame = currFrame;
            setupObj.update(delta);

            instanceData.clear();

            setupObj.setIterating(true);
            for (GameObject go : setupObj.GOList)
            {
                go.addToBuffer(instanceData);
            }
            setupObj.setIterating(false);
            //drawCount = TextRenderer.addTextToBuffer(pos, tex, index, i, drawCount);

            instanceData.flip();

            glBindVertexArray(vaoID);

            glBindBuffer(GL_ARRAY_BUFFER, initObj.getVboInstanceID());
            glBufferData(GL_ARRAY_BUFFER, instanceData, GL_DYNAMIC_DRAW);

            initObj.getShader().bind();
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);
            glEnableVertexAttribArray(3);
            glEnableVertexAttribArray(4);
            glEnableVertexAttribArray(5);
            glEnableVertexAttribArray(6);

            glDrawElementsInstanced(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_BYTE, 0, setupObj.GOList.size());

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
            glDisableVertexAttribArray(3);
            glDisableVertexAttribArray(4);
            glDisableVertexAttribArray(5);
            glDisableVertexAttribArray(6);
            glBindVertexArray(0);

            initObj.getShader().unbind();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
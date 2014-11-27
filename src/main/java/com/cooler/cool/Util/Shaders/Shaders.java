package com.cooler.cool.Util.Shaders;

import com.cooler.cool.Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public class Shaders
{
    private final int id;
    private HashMap<Integer,Integer> shaderIds = new HashMap<Integer, Integer>();

    public Shaders()
    {
        id = glCreateProgram();
    }

    public void attachShader(int shadertype, String filename)
    {
        if(filename == null)
            return;
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("Unable to open Shader files.");
            Main.ErrorClose();
        }
        StringBuilder shaderSource = new StringBuilder();
        String line;
        try
        {
            while ((line = reader.readLine())!=null)
                shaderSource.append(line);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Unable to read Shader files.");
            Main.ErrorClose();
        }
        int shaderid = glCreateShader(shadertype);
        shaderIds.put(shadertype, shaderid);
        glShaderSource(shaderid, shaderSource);
        glCompileShader(shaderid);
        if (glGetShaderi(shaderid, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("Unable to create shader:" + shadertype);
            System.err.println(glGetShaderInfoLog(shaderid, glGetShaderi(shaderid, GL_INFO_LOG_LENGTH)));
            Main.ErrorClose();
        }
        glAttachShader(id, shaderid);
    }

    public void link()
    {
        glLinkProgram(id);
        glValidateProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE)
        {
            System.err.println("Unable to link shader program.");
            dispose();
            Main.ErrorClose();
        }
    }

    /**
     * Sets the uniforms in this shader
     *
     * @param name    The name of the uniform
     * @param values  The values of the uniforms (Max 4)
     */
    public void setUniform(String name, float... values)
    {
        if (values.length > 4)
        {
            System.err.println("Uniforms cannot have more than 4 values");
            Main.ErrorClose();
        }
        int location = glGetUniformLocation(id, name);
        switch (values.length)
        {
            case 1:
                glUniform1f(location, values[0]);
                break;
            case 2:
                glUniform2f(location, values[0], values[1]);
                break;
            case 3:
                glUniform3f(location, values[0], values[1], values[2]);
                break;
            case 4:
                glUniform4f(location, values[0], values[1], values[2], values[3]);
                break;
        }
    }

    public void bind()
    {
        glUseProgram(id);
    }

    public static void unbind()
    {
        glUseProgram(0);
    }

    public void dispose()
    {
        unbind();
        for(int shaderid:shaderIds.values())
        {
            glDetachShader(id, shaderid);
            glDeleteShader(shaderid);
        }
        glDeleteProgram(id);
    }

    public int getID()
    {
        return id;
    }
}
package com.cooler.cool;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.Util.translateGLErrorString;

public class Main
{
    public static Random rand;
    private Initialize initObj;
    private Loop loopObj;
    private Cleanup cleanupObj;
    private static Main instance;
    private Setup setupObj;

    public static void main(String[] args)
    {
        instance = new Main();
        rand = new Random();
        instance.execute();
    }

    public void execute()
    {
        initObj = new Initialize();
        setupObj = new Setup();
        loopObj = new Loop();
        cleanupObj = new Cleanup();

        init();
        setup();
        loop();
        destroy();
        cleanup();
    }

    public static void ErrorClose()
    {
        instance.destroy();
        instance.cleanup();
        System.exit(-1);
    }

    public static void exitOnGLError(String errorMessage)
    {
        int errorValue = glGetError();

        if (errorValue != GL_NO_ERROR)
        {
            String errorString = translateGLErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);
            ErrorClose();
        }
    }

    private void init()
    {
        initObj.init();
    }

    private void setup()
    {
        setupObj.setup(initObj);
    }

    private void loop()
    {
        loopObj.mainLoop(initObj, setupObj);
    }

    private void destroy()
    {

    }

    private void cleanup()
    {
        cleanupObj.clean(initObj);
    }
}
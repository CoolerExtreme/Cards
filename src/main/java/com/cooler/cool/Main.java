package com.cooler.cool;

public class Main
{
    private static Initialize initObj;
    private static Loop loopObj;
    private static Cleanup cleanupObj;

    public static void main(String[] args)
    {
        init();
        setup();
        loop();
        destroy();
        cleanup();
    }

    public static void ErrorClose()
    {
        destroy();
        cleanup();
    }

    private static void init()
    {
        initObj = new Initialize();
        initObj.init();
    }

    private static void setup()
    {

    }

    private static void loop()
    {
        loopObj = new Loop();
        loopObj.mainLoop(initObj);
    }

    private static void destroy()
    {

    }

    private static void cleanup()
    {
        cleanupObj = new Cleanup();
        cleanupObj.clean(initObj);
    }
}
package com.cooler.cool;

public class Main
{
    private Initialize initObj;
    private Loop loopObj;
    private Cleanup cleanupObj;
    private static Main instance;

    public static void main(String[] args)
    {
        instance = new Main();
        instance.execute();
    }
    public void execute()
    {
        initObj = new Initialize();
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
    }

    private void init()
    {
        initObj.init();
    }

    private void setup()
    {

    }

    private void loop()
    {
        loopObj.mainLoop(initObj);
    }

    private void destroy()
    {

    }

    private void cleanup()
    {
        cleanupObj.clean(initObj);
    }
}
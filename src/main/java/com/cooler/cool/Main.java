package com.cooler.cool;

public class Main
{
    private Initialize initObj;
    private Loop loopObj;
    private Cleanup cleanupObj;
    private static Main instance;
    private Setup setupObj;

    public static void main(String[] args)
    {
        instance = new Main();
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

    private void init()
    {
        initObj.init();
    }

    private void setup()
    {
        setupObj.setup();
    }

    private void loop()
    {
        loopObj.mainLoop(initObj,setupObj);
    }

    private void destroy()
    {

    }

    private void cleanup()
    {
        cleanupObj.clean(initObj);
    }
}
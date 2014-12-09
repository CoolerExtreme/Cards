package com.cooler.cool;

import com.cooler.cool.GameObjects.GOBackground;
import com.cooler.cool.GameObjects.GameObject;
import com.cooler.cool.Util.Shaders.Shaders;

import static com.cooler.cool.Util.References.*;

import com.cooler.cool.Util.Text.TextRenderer;
import com.cooler.cool.Util.Textures;
import com.cooler.cool.GameObjects.GOMenu;
import com.cooler.cool.GameObjects.GOTest;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.glfw.ErrorCallback;
import org.lwjgl.system.glfw.GLFWvidmode;
import org.lwjgl.system.glfw.WindowCallback;
import org.lwjgl.system.glfw.WindowCallbackAdapter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import static org.lwjgl.system.glfw.GLFW.*;

public class Initialize
{
    private Textures textureObj;
    private ArrayList<GameObject> GOList;
    private int cardCount = 3;
    private FloatBuffer vertexBuf;
    private FloatBuffer instanceBuf;
    private ByteBuffer indexBuf;
    private int vaoID;
    private int vboVertID;
    private int eboID;
    private Shaders shader;
    private int WIDTH = 1280;
    private int HEIGHT = 720;
    private long window;
    private int vboInstanceID;

    public int getVboInstanceID()
    {
        return vboInstanceID;
    }

    public FloatBuffer getInstanceBuf()
    {
        return instanceBuf;
    }

    public ArrayList<GameObject> getGOList()
    {
        return GOList;
    }
    public ByteBuffer getIndexBuf()
    {
        return indexBuf;
    }
    public int getVaoID()
    {
        return vaoID;
    }

    public int getVboVertID()
    {
        return vboVertID;
    }

    public int getEboID()
    {
        return eboID;
    }

    public Shaders getShader()
    {
        return shader;
    }

    public void setShader(Shaders shader)
    {
        this.shader = shader;
    }


    public int getWIDTH()
    {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH)
    {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT)
    {
        this.HEIGHT = HEIGHT;
    }

    public long getWindow()
    {
        return window;
    }

    public void setWindow(long window)
    {
        this.window = window;
    }

    public void init()
    {
        glfwSetErrorCallback(ErrorCallback.Util.getDefault());

        if (glfwInit() != GL11.GL_TRUE)
        {
            System.out.println("Failed to initialize.");
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "Cards", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        GOList = new ArrayList<GameObject>();
        WindowCallback.set(window, new WindowCallbackAdapter()
        {
            @Override
            public void mouseButton(long window, int button, int action, int mods)
            {
                Main.getInstance().getSetupObj().setIterating(true);
                for (GameObject go : GOList)
                    go.activeUpdateMouseButton(window, button, action, mods);
                Main.getInstance().getSetupObj().setIterating(false);
            }

            @Override
            public void cursorPos(long window, double xpos, double ypos)
            {
                Main.getInstance().getSetupObj().setIterating(true);
                for (GameObject go : GOList)
                    go.activeUpdateCursorPos(window, xpos, ypos);
                Main.getInstance().getSetupObj().setIterating(false);
            }

            @Override
            public void windowSize(long window, int width, int height)
            {
                WIDTH = width;
                HEIGHT = height;
            }

            @Override
            public void key(long window, int key, int scancode, int action, int mods)
            {
                Main.getInstance().getSetupObj().setIterating(true);
                for (GameObject go : GOList)
                    go.activeUpdateKey(window, key, scancode, action, mods);
                Main.getInstance().getSetupObj().setIterating(false);
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, GL11.GL_TRUE);
            }
        });

        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );

        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();

        glfwSwapInterval(1);
        glfwShowWindow(window);

//---------------------------------SHADERS----------------------------------//
        shader = new Shaders();
        shader.attachShader(GL_VERTEX_SHADER, SRC + "Util/Shaders/VertexShader.shader");
        shader.attachShader(GL_FRAGMENT_SHADER, SRC + "Util/Shaders/FragmentShader.shader");
        shader.link();
        shader.setUniform("texArray", 0);
        shader.setUniform("texAtlas", 1);
//---------------------------------SHADERS----------------------------------//

//---------------------------------BUFFERS----------------------------------//
        vertexBuf = BufferUtils.createFloatBuffer(8);
        vertexBuf.put(new float[]{0, 1, 1, 1, 0, 0, 1, 0});
        vertexBuf.flip();

        instanceBuf = BufferUtils.createFloatBuffer(21 * 1024);
        indexBuf = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
        indexBuf.put(new byte[]{0, 1, 2, 3});
        indexBuf.flip();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboVertID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuf, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        vboInstanceID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboInstanceID);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 21 * 4, 0);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 21 * 4, 4 * 4);
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 21 * 4, 8 * 4);
        glVertexAttribPointer(4, 4, GL_FLOAT, false, 21 * 4, 12 * 4);

        glVertexAttribPointer(5, 4, GL_FLOAT, false, 21 * 4, 16 * 4);
        glVertexAttribPointer(6, 1, GL_FLOAT, false, 21 * 4, 20 * 4);

        glVertexAttribDivisor(1, 1);
        glVertexAttribDivisor(2, 1);
        glVertexAttribDivisor(3, 1);
        glVertexAttribDivisor(4, 1);
        glVertexAttribDivisor(5, 1);
        glVertexAttribDivisor(6, 1);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuf, GL_STATIC_DRAW);

        glBindVertexArray(0);
//---------------------------------BUFFERS----------------------------------//

//---------------------------------TEXTURES----------------------------------//
        textureObj = new Textures(cardCount);
        for (int i = 0; i < cardCount; i++)
            textureObj.addToTextureArray(RES + "textures/Cards/CardImg" + i + ".png");

        textureObj.addToTextureAtlas(RES + "textures/UI/Background.png", new GOBackground(), false);
        /*textureObj.addToTextureAtlas(RES + "textures/UI/Menu.png", new GOMenu(0, 0), false);
        textureObj.addToTextureAtlas(RES + "textures/UI/Test.png", new GOTest(), false);

        TextRenderer.addFontFromFFT("OpenSans/OpenSans-Regular.ttf");
        StringBuilder defaultCharSet = new StringBuilder();
        for(char c = 0;c < 256;c++)
        {
            if(c != 9 && c != 10 && c!= 13)
                defaultCharSet.append(c);
        }
        TextRenderer.addFont(textureObj, "OpenSans", defaultCharSet.toString());
*/
        textureObj.writeTextureAtlasToFile();
//---------------------------------TEXTURES----------------------------------//

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_PRIMITIVE_RESTART);
        glPrimitiveRestartIndex(32767);
        glClearColor(0, 0, 0, 0);
    }
}
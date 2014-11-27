package com.cooler.cool;

import com.cooler.cool.Util.Shaders.Shaders;
import static com.cooler.cool.Util.References.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.glfw.ErrorCallback;
import org.lwjgl.system.glfw.GLFWvidmode;
import org.lwjgl.system.glfw.WindowCallback;
import org.lwjgl.system.glfw.WindowCallbackAdapter;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.system.glfw.GLFW.*;

public class Initialize
{
    private int vaoID;
    private int vboVertID;
    private int vboTexID;
    private int eboID;

    public Shaders getShader()
    {
        return shader;
    }

    public void setShader(Shaders shader)
    {
        this.shader = shader;
    }

    private Shaders shader;

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

    private int WIDTH = 1280;
    private int HEIGHT = 720;
    private long window;

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

        WindowCallback.set(window, new WindowCallbackAdapter()
        {
            @Override
            public void windowSize(long window, int width, int height)
            {
                WIDTH = width;
                HEIGHT = height;
            }

            @Override
            public void key(long window, int key, int scancode, int action, int mods)
            {
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

        shader = new Shaders();
        //shader.attachShader(GL_VERTEX_SHADER, SRC + "Util/Shaders/VertexShader.shader");
        //shader.attachShader(GL_FRAGMENT_SHADER, SRC + "Util/Shaders/FragmentShader.shader");
        //shader.link();
        //shader.setUniform("tex", 0);

        FloatBuffer posBuf = BufferUtils.createFloatBuffer(3 * 4 * 128);
        FloatBuffer texBuf = BufferUtils.createFloatBuffer(3 * 4 * 128);
        ShortBuffer indexBuf = BufferUtils.createShortBuffer(1024);

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboVertID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertID);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vboTexID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboTexID);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);

        glBindVertexArray(0);

        /*texture = Texture.loadTexture("src/main/resources/assets/textures/CardImg0.png");
        texture.setActiveTextureUnit(0);
        texture1 = Texture.loadTexture("src/main/resources/assets/textures/BlockBomb.png");
        texture1.setActiveTextureUnit(0);
        texture.bind();*/

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_PRIMITIVE_RESTART);
        glClearColor(0, 0, 1, 0);
        glPrimitiveRestartIndex(32767);
    }
}
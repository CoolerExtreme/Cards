package com.cooler.cool.Util;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL42.glTexStorage3D;


public class Textures
{
    private ByteBuffer arrBuffer;
    private int currentLayer = 0;
    private final int arrTexId;
    private final int atlasTexId;
    private int xoff = 0;
    private int yoff = 0;
    private int maxHeight = 0;
    private int arrayWidth = 223, arrayHeight = 311, arrayLayers;
    private int length = arrayWidth * arrayHeight;


    public int getArrayLayers()
    {
        return arrayLayers;
    }

    public Textures(int arrayLayers)
    {
        this.arrayLayers = arrayLayers;

        setActiveTextureUnit(0);
        arrTexId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY, arrTexId);
        glTexStorage3D(GL_TEXTURE_2D_ARRAY, 1, GL_RGBA8, arrayWidth, arrayHeight, arrayLayers);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        setActiveTextureUnit(1);
        atlasTexId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, atlasTexId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        arrBuffer = BufferUtils.createByteBuffer(length * 4);
    }

    public void setActiveTextureUnit(int unit)
    {
        glActiveTexture(GL_TEXTURE0 + unit);
    }

    public int addToTextureArray(String name)
    {
        BufferedImage bimg;
        int[] pixels = new int[length];

        try
        {
            bimg = ImageIO.read(new File(name));
            bimg.getRGB(0, 0, arrayWidth, arrayHeight, pixels, 0, arrayWidth);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Unable to load Texture: " + name);
        }

        arrBuffer.clear();
        for (int pixel : pixels)
        {
            arrBuffer.put((byte) ((pixel >> 16) & 0xFF));
            arrBuffer.put((byte) ((pixel >> 8) & 0xFF));
            arrBuffer.put((byte) (pixel & 0xFF));
            arrBuffer.put((byte) ((pixel >> 24) & 0xFF));
        }
        arrBuffer.flip();

        setActiveTextureUnit(0);
        glBindTexture(GL_TEXTURE_2D_ARRAY, arrTexId);
        glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, currentLayer, arrayWidth, arrayHeight, 1, GL_RGBA, GL_UNSIGNED_BYTE, arrBuffer);

        return currentLayer++;
    }

    public void addToTextureAtlas(String name)
    {
        BufferedImage bimg;
        int[] pixels = null;
        int width = 0, height = 0;

        try
        {
            bimg = ImageIO.read(new File(name));
            width = bimg.getWidth();
            height = bimg.getHeight();
            pixels = new int[width * height];
            bimg.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Unable to load Texture: " + name);
            System.exit(-1);
        }

        ByteBuffer atlasBuffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int pixel : pixels)
        {
            atlasBuffer.put((byte) ((pixel >> 16) & 0xFF));
            atlasBuffer.put((byte) ((pixel >> 8) & 0xFF));
            atlasBuffer.put((byte) (pixel & 0xFF));
            atlasBuffer.put((byte) ((pixel >> 24) & 0xFF));
        }
        atlasBuffer.flip();

        setActiveTextureUnit(1);
        glBindTexture(GL_TEXTURE_2D, atlasTexId);
        if (maxHeight == 0)
        {
            xoff += width;
            maxHeight = height;
            glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, atlasBuffer);
        } else if ((height > maxHeight) || (xoff + width > 2048))
        {
            yoff += maxHeight;
            xoff = 0;
            maxHeight = height;
            glTexSubImage2D(GL_TEXTURE_2D, 0, xoff, yoff, width, height, GL_RGBA, GL_UNSIGNED_BYTE, atlasBuffer);
        } else
        {
            xoff += width;
            glTexSubImage2D(GL_TEXTURE_2D, 0, xoff, yoff, width, height, GL_RGBA, GL_UNSIGNED_BYTE, atlasBuffer);
        }
    }
}
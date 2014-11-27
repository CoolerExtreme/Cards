#version 330 core

uniform sampler2DArray texArray;
uniform sampler2D texAtlas;

in vec3 texCoords;
out vec4 outColor;

void main()
{
    outColor = texture(texArray, texCoords);
}
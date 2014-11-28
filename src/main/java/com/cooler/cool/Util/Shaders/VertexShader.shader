#version 330 core

layout(location = 0) in vec2 pos;
layout(location = 1) in vec3 tex;

out vec3 texCoords;

void main()
{
    texCoords = tex;
    gl_Position = gl_ModelViewProjectionMatrix * vec4(pos, 0.0, 1.0);
}
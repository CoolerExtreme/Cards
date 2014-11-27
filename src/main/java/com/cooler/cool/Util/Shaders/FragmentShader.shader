#version 330 core

uniform sampler2DArray tex;

in vec3 texCoords;
out vec4 outColor;

void main()
{
    outColor = texture(tex, texCoords);
}
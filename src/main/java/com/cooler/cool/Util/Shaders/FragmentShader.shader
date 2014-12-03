#version 330 core

uniform sampler2DArray texArray;
uniform sampler2D texAtlas;

in vec3 texCoords;
out vec4 outColor;

vec4 tex1;
vec4 tex2;

void main()
{
    tex1 = texture(texArray, texCoords);
    tex2 = texelFetch(texAtlas, ivec2(texCoords.xy), 0);
    if(texCoords.z < 0.5)
        outColor = tex2;
    else
        outColor = tex1;
}
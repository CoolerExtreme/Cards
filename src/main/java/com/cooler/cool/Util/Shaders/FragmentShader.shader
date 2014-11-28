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
    tex2 = texture(texAtlas, texCoords.xy);
    float par = texCoords.z;
    if(par < 0.5)
    {
    tex1 = tex2;
    }
    outColor = tex1;
}
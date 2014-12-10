#version 330 core

layout(location = 0) in vec2 vertex;
layout(location = 1) in vec4 matrix0;
layout(location = 2) in vec4 matrix1;
layout(location = 3) in vec4 matrix2;
layout(location = 4) in vec4 matrix3;
layout(location = 5) in vec4 texOffsets;
layout(location = 6) in float texLayer;

out vec3 texCoords;

mat4 transform;
vec2 vert;

void main()
{
    vert.x = vertex.x;
    if(vertex.y < 0.5)
        vert.y = 1;
    else
        vert.y = 0;
    transform = mat4(matrix0, matrix1, matrix2, matrix3);
    texCoords = vec3(texOffsets.x + (texOffsets.z * vert.x), texOffsets.y + (texOffsets.w * vert.y), texLayer);
    gl_Position = gl_ProjectionMatrix * transform * vec4(vertex, 0.0, 1.0);
}
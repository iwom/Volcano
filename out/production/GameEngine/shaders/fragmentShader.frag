#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[3];
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour[3];
uniform float shineDamper;
uniform float reflectivity;

void main(void) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int i = 0; i < 3; i++) {
        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1, 0.0);
        vec3 lightDirection = -1 * unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float damperFactor = pow(specularFactor, shineDamper);
        totalDiffuse = totalDiffuse + brightness * lightColour[i];
        totalSpecular = totalSpecular + damperFactor * reflectivity * lightColour[i];
       }

    totalDiffuse = max(totalDiffuse, 0.2);

    vec4 textureColour = texture(textureSampler, pass_textureCoords);

    if(textureColour.a < 0.5) {
        discard;
    }

    out_Color = vec4(totalDiffuse, 1.0f) * textureColour + vec4(totalSpecular, 1.0f);
}
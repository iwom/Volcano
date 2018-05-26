package particles;

import org.lwjgl.util.vector.Vector3f;

public class SmokeSystem extends ParticleSystem {

    public SmokeSystem(ParticleTexture systemTexture, float pps, float speed, float gravityComplient, float lifeLength) {
        super(systemTexture, pps, speed, gravityComplient, lifeLength);
    }

    @Override
    protected void emitParticle(Vector3f center) {
        float dirX = randomWithRange(40, 500);
        float dirZ = randomWithRange(40, 400);
        float dirY = randomWithRange(30, 100);
        Vector3f velocity = new Vector3f(dirX, dirY, dirZ);
        velocity.normalise();
        velocity.scale(speed);
        new Particle(systemTexture, new Vector3f(center), velocity, gravityComplient, lifeLength, 0, 5f);
    }
}

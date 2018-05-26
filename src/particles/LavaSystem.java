package particles;

import org.lwjgl.util.vector.Vector3f;

public class LavaSystem extends ParticleSystem {

    public LavaSystem(ParticleTexture systemTexture, float pps, float speed, float gravityComplient, float lifeLength) {
        super(systemTexture, pps, speed, gravityComplient, lifeLength);
    }

    @Override
    protected void emitParticle(Vector3f center) {
        super.emitParticle(center);
    }
}

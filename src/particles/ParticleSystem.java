package particles;


import org.lwjgl.util.vector.Vector3f;

import game.DisplayManager;

public abstract class ParticleSystem {

    protected float pps;
    protected float speed;
    protected float gravityComplient;
    protected float lifeLength;

    protected ParticleTexture systemTexture;

    public ParticleSystem(ParticleTexture systemTexture, float pps, float speed, float gravityComplient, float lifeLength) {
        this.systemTexture = systemTexture;
        this.pps = pps;
        this.speed = speed;
        this.gravityComplient = gravityComplient;
        this.lifeLength = lifeLength;
    }

    public void generateParticles(Vector3f systemCenter){
        float delta = 0.001f;
        float particlesToCreate = pps * delta;
        int count = (int) Math.floor(particlesToCreate);
        float partialParticle = particlesToCreate % 1;
        for(int i=0;i<count;i++){
            emitParticle(systemCenter);
        }
        if(Math.random() < partialParticle){
            emitParticle(systemCenter);
        }
    }

    protected void emitParticle(Vector3f center){
        float dirX = randomWithRange(-40,40);
        float dirZ = randomWithRange(-40,40);
        float dirY = randomWithRange(80,1000);
        Vector3f velocity = new Vector3f(dirX, dirY, dirZ);

        velocity.normalise();
        velocity.scale(speed);
        System.out.println(velocity.toString());
        new Particle(systemTexture, new Vector3f(center), velocity, gravityComplient, lifeLength, 0, 1);
    }

    public void setPps (float pps) {
        this.pps = pps;
    }

    protected float randomWithRange(float min, float max)
    {
        float range = (float) Math.abs(max - min);
        return (float) ((float) (Math.random() * range) + (min <= max ? min : max));
    }



}

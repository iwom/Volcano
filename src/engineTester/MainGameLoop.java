package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import game.*;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import particles.*;
import terrain.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel volcanoRawModel = OBJLoader.loadObjModel("vol4", loader);
        ModelTexture volacnoTexture = new ModelTexture(loader.loadTexture("stone"));
        //volacnoTexture.setShineDamper(20);
        //volacnoTexture.setReflectivity(0);
        TexturedModel volcanoModel = new TexturedModel(volcanoRawModel, volacnoTexture);

        /*RawModel stoneRawModel = OBJLoader.loadObjModel("stone", loader);
        ModelTexture stoneTexture = new ModelTexture(loader.loadTexture("gravel"));
        stoneTexture.setReflectivity(0);
        stoneTexture.setShineDamper(10);
        TexturedModel stoneModel = new TexturedModel(stoneRawModel, stoneTexture);*/


        RawModel grassRawModel = OBJLoader.loadObjModel("grassModel", loader);
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
        grassTexture.setTransparent(true);
        grassTexture.setFakeLightning(true);
        TexturedModel grassModel = new TexturedModel(grassRawModel, grassTexture);

        Terrain terrain0 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain1 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain3 = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("grass")));


        List <Light> lights = new ArrayList<>();
        lights.add(new Light(new Vector3f(0,40,0), new Vector3f(1,1,1)));
        //lights.add(new Light(new Vector3f(50,40,-50), new Vector3f(0,1,0)));
        Camera camera = new Camera();

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        float x = 0.0f;
        float y = 2.0f;
        float z = -10.0f;
        entities.add(new Entity(volcanoModel, new Vector3f(x,y,z),
                0,0,0,1));

        for(int i = 0; i < 1000; i++) {
            entities.add(new Entity(grassModel, new Vector3f(
                    random.nextFloat() * 200 - 100, 0, random.nextFloat() * 200 - 100), 0,0,0,0.4f
            ));
        }

        MasterRenderer renderer = new MasterRenderer();

        ParticleMaster.init(loader, renderer.getProjectionMatrix());
        ParticleTexture lavaParticleTexture = new ParticleTexture(loader.loadTexture("particleAtlas"), 4);
        ParticleTexture smokeParticleTexture = new ParticleTexture(loader.loadTexture("smoke"), 8);
        ParticleSystem lavaSystem = new LavaSystem(lavaParticleTexture, 600, 0.25f, 0.55f, 0.027f);
        ParticleSystem smokeSystem = new SmokeSystem(smokeParticleTexture, 150, 0.05f, 0.001f, 0.5f);

        Vector3f volcanoPosition = entities.get(0).getPosition();
        while(!Display.isCloseRequested()) {
            camera.move();

            lavaSystem.generateParticles(new Vector3f(volcanoPosition.x - 2.3f,
                    volcanoPosition.y + 7.8f,
                    volcanoPosition.z));
            smokeSystem.generateParticles(new Vector3f(volcanoPosition.x - 2.3f,
                    volcanoPosition.y + 9.9f,
                    volcanoPosition.z));
            ParticleMaster.update();
            for(Entity entity: entities) {
                renderer.processEntity(entity);
            }

            renderer.render(lights, camera);
            ParticleMaster.renderParticles(camera);
            renderer.processTerrain(terrain0);
            renderer.processTerrain(terrain1);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        ParticleMaster.cleanUp();
        DisplayManager.closeDisplay();
    }
}

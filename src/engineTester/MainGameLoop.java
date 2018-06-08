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

        RawModel volcanoRawModel = OBJLoader.loadObjModel("vol5", loader);
        ModelTexture volacnoTexture = new ModelTexture(loader.loadTexture("volcano"));
        TexturedModel volcanoModel = new TexturedModel(volcanoRawModel, volacnoTexture);


        RawModel grassRawModel = OBJLoader.loadObjModel("grassModel", loader);
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
        grassTexture.setTransparent(true);
        grassTexture.setFakeLightning(true);
        TexturedModel grassModel = new TexturedModel(grassRawModel, grassTexture);


        Terrain terrain0 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain1 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain3 = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        float x = 0.0f;
        float y = -4.0f;
        float z = -20.0f;
        entities.add(new Entity(volcanoModel, new Vector3f(x,y,z),
                0,0,0,1));

        for(int i = 0; i < 1000; i++) {
            entities.add(new Entity(grassModel, new Vector3f(
                    random.nextFloat() * 200 - 100, 0, random.nextFloat() * 200 - 100), 0,0,0,0.4f
            ));
        }
        Vector3f volcanoPosition = entities.get(0).getPosition();


        List <Light> lights = new ArrayList<>();
        Light mainLight = new Light(new Vector3f(0,40,0), new Vector3f(0.4f,0.4f,0.4f));
        Light lavaLight = new Light(new Vector3f(volcanoPosition.x - 5.0f,
                volcanoPosition.y + 12.9f,
                volcanoPosition.z + 2.6f), new Vector3f(0.15f, 0.075f, 0));
        lights.add(mainLight);
        lights.add(lavaLight);

        MasterRenderer renderer = new MasterRenderer();
        ParticleMaster.init(loader, renderer.getProjectionMatrix());
        ParticleTexture lavaParticleTexture = new ParticleTexture(loader.loadTexture("particleAtlas"), 4);
        ParticleTexture smokeParticleTexture = new ParticleTexture(loader.loadTexture("smoke"), 8);
        ParticleSystem lavaSystem = new LavaSystem(lavaParticleTexture, 800, 0.27f, 0.53f, 0.026f);
        ParticleSystem smokeSystem = new SmokeSystem(smokeParticleTexture, 150, 0.05f, 0.001f, 0.5f);

        while(!Display.isCloseRequested()) {
            camera.move();

            if(Keyboard.isKeyDown(Keyboard.KEY_Y)) {
                lavaSystem.setPps(1200);
                lavaSystem.setGravityComplient(0.51f);
                lavaSystem.setLifeLength(0.027f);
                smokeSystem.setPps(180);
                lavaLight.setColour(new Vector3f(0.4f, 0.2f, 0));
                lavaLight.emit(true);
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_U)) {
                lavaSystem.setPps(800);
                lavaSystem.setGravityComplient(0.53f);
                lavaSystem.setLifeLength(0.025f);
                smokeSystem.setPps(130);
                lavaLight.setColour(new Vector3f(0.3f, 0.15f, 0));
                lavaLight.emit(true);
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_I)) {
                lavaSystem.setPps(600);
                lavaSystem.setGravityComplient(0.6f);
                lavaSystem.setLifeLength(0.022f);
                smokeSystem.setPps(80);
                lavaLight.setColour(new Vector3f(0.2f, 0.1f, 0));
                lavaLight.emit(true);
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
                lavaSystem.setPps(300);
                lavaSystem.setGravityComplient(0.78f);
                lavaSystem.setLifeLength(0.02f);
                smokeSystem.setPps(50);
                lavaLight.setColour(new Vector3f(0.1f, 0.05f, 0));
                lavaLight.emit(true);
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
                lavaSystem.setPps(0);
                smokeSystem.setPps(0);
                lavaLight.emit(false);
            }


            lavaSystem.generateParticles(new Vector3f(volcanoPosition.x - 5.0f,
                    volcanoPosition.y + 9.2f,
                    volcanoPosition.z + 2.6f));
            smokeSystem.generateParticles(new Vector3f(volcanoPosition.x - 5.0f,
                    volcanoPosition.y + 11.3f,
                    volcanoPosition.z +2.6f));
            ParticleMaster.update();
            for(Entity entity: entities) {
                renderer.processEntity(entity);
            }
            lavaLight.randomizeColour();
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

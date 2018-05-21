package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import game.*;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import terrain.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel volcanoRawModel = OBJLoader.loadObjModel("vol3", loader);
        ModelTexture volacnoTexture = new ModelTexture(loader.loadTexture("bamboo"));
        volacnoTexture.setShineDamper(20);
        volacnoTexture.setReflectivity(0);
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


        Light light = new Light(new Vector3f(0,40,-10), new Vector3f(1,1,1));
        Camera camera = new Camera();

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        float x = 0.0f;
        float y = -2.0f;
        float z = -10.0f;
        entities.add(new Entity(volcanoModel, new Vector3f(x,y,z),
                0,0,0,1));

        for(int i = 0; i < 1000; i++) {
            entities.add(new Entity(grassModel, new Vector3f(
                    random.nextFloat() * 300 - 150, 0, random.nextFloat() * 300 -150), 0,0,0,0.8f
            ));
        }

        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {
            camera.move();
            renderer.processTerrain(terrain0);
            renderer.processTerrain(terrain1);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            for(Entity entity: entities) {
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        /*shader.cleanUp();*/
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

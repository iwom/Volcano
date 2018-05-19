package textures;

public class ModelTexture {
    private int textureId;

    private float shineDamper = 1;
    private float reflectivity = 0;



    public ModelTexture(int id) {
        this.textureId = id;
    }

    public int getTextureId() {
        return textureId;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}

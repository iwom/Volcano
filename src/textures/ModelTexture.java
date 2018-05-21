package textures;

public class ModelTexture {
    private int textureId;

    private float shineDamper = 1;
    private float reflectivity = 0;
    private boolean transparent = false;
    private boolean fakeLightning = false;

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

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }
    public boolean isFakeLightning() {
        return fakeLightning;
    }

    public void setFakeLightning(boolean fakeLightning) {
        this.fakeLightning = fakeLightning;
    }

}

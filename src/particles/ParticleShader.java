package particles;

import org.lwjgl.util.vector.Matrix4f;

import org.lwjgl.util.vector.Vector2f;
import shaders.ShaderProgram;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/particles/particleVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/particles/particleFragmentShader.frag";

	private int location_numberOfRows;
	private int location_projectionMatrix;

	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
	}

	@Override
	protected void bindAttributes() {
	    super.bindAttribute(0, "position");
	    super.bindAttribute(1, "modelViewMatrix");
	    super.bindAttribute(5, "texOffsets");
	    super.bindAttribute(6, "blendFactor");
	}


	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

	protected void loadNumberOfRows(float numberOfRows) {
	    super.loadFloat(location_numberOfRows, numberOfRows);
    }

	protected void loadTextureCoordInfo(Vector2f offset1, Vector2f offset2, float numRows, float blend) {

    }

}

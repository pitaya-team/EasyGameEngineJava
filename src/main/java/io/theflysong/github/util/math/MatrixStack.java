package io.theflysong.github.util.math;

import org.joml.Matrix4f;
import org.joml.Quaternionfc;

import java.util.Stack;

public class MatrixStack {
    protected Stack<Matrix4f> matrixStack = new Stack();
    Matrix4f matrix = new Matrix4f();

    public void push() {
        matrixStack.push(matrix);
    }

    public void pop() {
        matrix = matrixStack.pop();
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    public void translate(float x, float y, float z) {
        matrix.translate(x, y, z);
    }

    public void scale(float x, float y, float z) {
        matrix.scale(x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        matrix.rotate(angle, x, y, z);
    }

    public void rotate(Quaternionfc quaternion) {
        matrix.rotate(quaternion);
    }
}

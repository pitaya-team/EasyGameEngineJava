package io.theflysong.github.util.math;

import org.joml.Matrix3f;

import java.util.Stack;

public class MatrixStack {
    protected Stack<Matrix3f> matrixStack = new Stack();
    Matrix3f matrix = new Matrix3f();

    public void push() {
        matrixStack.push(matrix);
    }

    public void pop() {
        matrix = matrixStack.pop();
    }

    public Matrix3f getMatrix() {
        return matrix;
    }
}

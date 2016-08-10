package com.common.android.utils.misc;

/**
 * Created by jan.rabe on 27/07/16.
 */

public class Vector2 {

    public final static Vector2 X = new Vector2(1, 0);
    public final static Vector2 Y = new Vector2(0, 1);
    public final static Vector2 Zero = new Vector2(0, 0);

    /**
     * the x-component of this vector
     **/
    public float x;
    /**
     * the y-component of this vector
     **/
    public float y;

    public Vector2() {
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this(v.x, v.y);
    }

    public Vector2 setX(float x) {
        this.x = x;
        return this;
    }

    public Vector2 setY(float y) {
        this.y = y;
        return this;
    }

    public float len() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static Vector2 nor(Vector2 v) {
        return new Vector2(v.x, v.y).nor();
    }

    public Vector2 nor() {
        float len = len();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    /**
     * @return the angle in degrees of this vector (point) relative to the x-axis. Angles are towards the positive y-axis (typically
     * counter-clockwise) and between 0 and 360.
     */
    public float angle() {
        float angle = (float) Math.toDegrees(Math.atan2(y, x));
        if (angle < 0) angle += 360;
        return angle;
    }

    /**
     * @return the angle in degrees of this vector (point) relative to the given vector. Angles are towards the positive y-axis
     * (typically counter-clockwise.) between -180 and +180
     */
    public float angle(Vector2 reference) {
        return (float) Math.toDegrees(Math.atan2(crs(reference), dot(reference)));
    }

    /**
     * Calculates the 2D cross product between this and the given vector.
     *
     * @param v the other vector
     * @return the cross product
     */
    public float crs(Vector2 v) {
        return this.x * v.y - this.y * v.x;
    }

    /**
     * Calculates the 2D cross product between this and the given vector.
     *
     * @param x the x-coordinate of the other vector
     * @param y the y-coordinate of the other vector
     * @return the cross product
     */
    public float crs(float x, float y) {
        return this.x * y - this.y * x;
    }

    public static float dot(float x1, float y1, float x2, float y2) {
        return x1 * x2 + y1 * y2;
    }

    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    public float dot(float ox, float oy) {
        return x * ox + y * oy;
    }

    public Vector2 sub(Vector2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Substracts the other vector from this vector.
     *
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return This vector for chaining
     */
    public Vector2 sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2 vector2 = (Vector2) o;

        if (Float.compare(vector2.x, x) != 0) return false;
        return Float.compare(vector2.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

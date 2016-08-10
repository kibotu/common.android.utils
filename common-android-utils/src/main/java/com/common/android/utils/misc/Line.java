package com.common.android.utils.misc;

/**
 * Created by jan.rabe on 27/07/16.
 */

public class Line {

    public Vector2 start;
    public Vector2 end;

    public Line(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
    }

    public Line setStart(Vector2 start) {
        this.start = start;
        return this;
    }

    public Line setEnd(Vector2 end) {
        this.end = end;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (start != null ? !start.equals(line.start) : line.start != null) return false;
        return end != null ? end.equals(line.end) : line.end == null;

    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return start + " -> " + end;
    }
}

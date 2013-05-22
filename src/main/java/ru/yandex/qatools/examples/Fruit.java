package ru.yandex.qatools.examples;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 19.05.13
 * Time: 15:22
 */
public class Fruit {

    public static final boolean SWEET = true;

    public static final boolean NOT_SWEET = false;

    private boolean sweet;

    private Shape shape;

    private Color color;

    public Fruit(boolean sweet, Shape shape, Color color) {
        this.sweet = sweet;
        this.shape = shape;
        this.color = color;
    }

    public boolean isSweet() {
        return sweet;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

}

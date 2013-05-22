package ru.yandex.qatools.examples;

/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 19.05.13
 * Time: 15:22
 */
public class Fruit {
    public static final boolean SWEET = true;
    public static final boolean NOT_SWEET = false;
    public static final String ORANGE_COLOR = "orange";

    private boolean sweet = true;
    private Shape shape = Shape.ROUND;
    private String color = "orange";

    public Fruit() {
        // init with default values
    }

    public Fruit(boolean sweet, Shape shape, String color) {
        this.sweet = sweet;
        this.shape = shape;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public boolean isSweet() {
        return sweet;
    }

    public Shape getShape() {
        return shape;
    }
}

package ru.yandex.qatools.examples;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static ru.yandex.qatools.examples.Fruit.NOT_SWEET;
import static ru.yandex.qatools.examples.Fruit.SWEET;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/22/13, 3:43 PM
 */
public class FruitFactory {

    public static Collection<Fruit> createFruitsList() {
        return Arrays.asList(
                new Fruit(NOT_SWEET, Shape.SQUARE, Color.ORANGE),
                new Fruit(SWEET, Shape.ROUND, Color.ORANGE),
                new Fruit(NOT_SWEET, Shape.ROUND, Color.YELLOW),
                new Fruit(SWEET, Shape.SQUARE, Color.ORANGE),
                new Fruit(SWEET, Shape.ROUND, Color.GREEN)
        );
    }

}

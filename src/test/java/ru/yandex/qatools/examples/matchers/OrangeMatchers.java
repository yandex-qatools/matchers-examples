package ru.yandex.qatools.examples.matchers;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import ru.yandex.qatools.examples.Fruit;
import ru.yandex.qatools.examples.Shape;

import java.awt.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 19.05.13
 * Time: 15:51
 */
public class OrangeMatchers {

    public static Matcher<Fruit> hasShape(final Shape shape) {
        return new FeatureMatcher<Fruit, Shape>(equalTo(shape), "fruit has shape - ", "shape -") {
            @Override
            protected Shape featureValueOf(Fruit fruit) {
                return fruit.getShape();
            }
        };
    }

    public static Matcher<Fruit> round() {
        return hasShape(Shape.ROUND);
    }


    public static Matcher<Fruit> sweet() {
        return new FeatureMatcher<Fruit, Boolean>(is(true), "fruit should be sweet", "sweet -") {
            @Override
            protected Boolean featureValueOf(Fruit fruit) {
                return fruit.isSweet();
            }
        };
    }


    public static Matcher<Fruit> hasColor(final Color color) {
        return new FeatureMatcher<Fruit, Color>(equalTo(color), "fruit have color - ", "color -") {
            @Override
            protected Color featureValueOf(Fruit fruit) {
                return fruit.getColor();
            }
        };
    }

}

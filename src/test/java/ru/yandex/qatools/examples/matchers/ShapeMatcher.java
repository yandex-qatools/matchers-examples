package ru.yandex.qatools.examples.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import ru.yandex.qatools.examples.Fruit;
import ru.yandex.qatools.examples.Shape;

/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 19.05.13
 * Time: 15:51
 */
public class ShapeMatcher extends TypeSafeMatcher<Fruit> {
    private Shape expected;

    public ShapeMatcher(Shape expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Fruit fruit) {
        return expected.equals(fruit.getShape());
    }

    @Override
    protected void describeMismatchSafely(Fruit item, Description mismatchDescription) {
        mismatchDescription.appendText("fruit has shape - ").appendValue(item.getShape());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("shape - ").appendValue(expected);
    }

    @Factory
    public static ShapeMatcher round() {
        return new ShapeMatcher(Shape.ROUND);
    }
}

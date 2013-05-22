package ru.yandex.qatools.examples;

import ch.lambdaj.function.convert.Converter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.examples.data.FruitFactory;

import java.awt.*;
import java.util.Collection;

import static ch.lambdaj.Lambda.convert;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static ru.yandex.qatools.examples.matchers.OrangeMatchers.*;


/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 19.05.13
 * Time: 15:45
 */
@RunWith(Parameterized.class)
public class FruitDegustationTest {

    private Fruit fruit;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Fruit> fruitList = FruitFactory.createFruitsList();
        return convert(fruitList, new Converter<Fruit, Object[]>() {
            @Override
            public Object[] convert(Fruit o) {
                return new Object[]{o};
            }
        });
    }

    public FruitDegustationTest(Fruit fruit) {
        this.fruit = fruit;
    }


    // -------

    @Test
    public void orangeIsRoundWithAssert() throws Exception {
        assertEquals("Expected shape - " + Shape.ROUND + ", but was - " + fruit.getShape(),
                fruit.getShape(), Shape.ROUND);
    }


    @Test
    public void orangeIsRoundWithMatcher() throws Exception {
        assertThat(fruit, is(round()));
    }


    // -------


    @Test
    public void orangeIsSweetWithAssert() {
        assertTrue("Fruit should be sweet - expected TRUE", fruit.isSweet());
    }


    @Test
    public void orangeIsSweetWithMatcher() {
        assertThat(fruit, is(sweet()));
    }


    //-------


    @Test
    public void orangeBothSweetRoundAndOrangeColor() throws Exception {
        assertTrue("Expected shape - " + Shape.ROUND + ", but was - " + fruit.getShape(),
                fruit.getShape().equals(Shape.ROUND));
        assertTrue("Fruit should be sweet - expected TRUE", fruit.isSweet());
        assertEquals("Orange has orange color, but was - " + fruit.getColor(),
                fruit.getColor(), Color.ORANGE);
    }

    @Test
    public void orangeBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(fruit, both(round()).and(sweet()).and(hasColor(Color.ORANGE)));
    }


    //------

    @Test
    public void degusto() {
        assumeTrue("Expected shape - " + Shape.ROUND + ", but was - " + fruit.getShape(),
                fruit.getShape().equals(Shape.ROUND));
        assumeTrue("Fruit should be sweet - expected TRUE", fruit.isSweet());
        assumeTrue("Orange has orange color, but was - " + fruit.getColor(),
                fruit.getColor().equals(Color.ORANGE));

        // some other checks
    }


    @Test
    public void degustoWithMatchers() throws Exception {
        assumeThat(fruit, both(round()).and(sweet()).and(hasColor(Color.ORANGE)));
    }


}

package ru.yandex.qatools.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static ru.yandex.qatools.examples.Fruit.*;
import static ru.yandex.qatools.examples.matchers.OrangeMatchers.*;


/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 19.05.13
 * Time: 15:45
 */
@RunWith(Parameterized.class)
public class ProductionLine {

    private Fruit someFruit;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{new Fruit()},
                new Object[]{new Fruit(NOT_SWEET, Shape.SQUARE, ORANGE_COLOR)},
                new Object[]{new Fruit(SWEET, Shape.ROUND, "ORANGE")},
                new Object[]{new Fruit(NOT_SWEET, Shape.ROUND, "yellow")},
                new Object[]{new Fruit(SWEET, Shape.SQUARE, "orange")},
                new Object[]{new Fruit(SWEET, Shape.ROUND, "GREEN")}
        );
    }


    public ProductionLine(Fruit someFruit) {
        this.someFruit = someFruit;
    }


    // -------

    @Test
    public void orangeIsRoundWithAssert() throws Exception {
        assertEquals("Expected shape - " + Shape.ROUND + ", but was - " + someFruit.getShape(),
                someFruit.getShape(), Shape.ROUND);
    }


    @Test
    public void orangeIsRoundWithMatcher() throws Exception {
        assertThat(someFruit, is(round()));
    }


    // -------


    @Test
    public void orangeIsSweetWithAssert() {
        assertTrue("Fruit should be sweet - expected TRUE", someFruit.isSweet());
    }


    @Test
    public void orangeIsSweetWithMatcher() {
        assertThat(someFruit, is(sweet()));
    }


    //-------


    @Test
    public void orangeBothSweetRoundAndOrangeColor() throws Exception {
        assertTrue("Expected shape - " + Shape.ROUND + ", but was - " + someFruit.getShape(),
                someFruit.getShape().equals(Shape.ROUND));
        assertTrue("Fruit should be sweet - expected TRUE", someFruit.isSweet());
        assertEquals("Orange has orange color, but was - " + someFruit.getColor(),
                someFruit.getColor().toLowerCase(), ORANGE_COLOR);
    }

    @Test
    public void orangeBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(someFruit, both(round()).and(sweet()).and(hasColor(ORANGE_COLOR)));
    }


    //------

    @Test
    public void degusto() {
        assumeTrue("Expected shape - " + Shape.ROUND + ", but was - " + someFruit.getShape(),
                someFruit.getShape().equals(Shape.ROUND));
        assumeTrue("Fruit should be sweet - expected TRUE", someFruit.isSweet());
        assumeTrue("Orange has orange color, but was - " + someFruit.getColor(),
                someFruit.getColor().toLowerCase().equals(ORANGE_COLOR));

        // some other checks
    }


    @Test
    public void degustoWithMatchers() throws Exception {
        assumeThat(someFruit, both(round()).and(sweet()).and(hasColor(ORANGE_COLOR)));

        // other asserts
    }


}

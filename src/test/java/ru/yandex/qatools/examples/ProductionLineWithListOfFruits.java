package ru.yandex.qatools.examples;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.examples.Fruit.*;
import static ru.yandex.qatools.examples.matchers.OrangeMatchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 22.05.13
 * Time: 13:06
 */
public class ProductionLineWithListOfFruits {

    @Test
    public void orangeListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(asList(new Fruit(), new Fruit(), new Fruit()),
                everyItem(both(round()).and(sweet()).and(hasColor(ORANGE_COLOR))));
    }

    @Test
    public void notAllOrangeListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(
                asList(
                        new Fruit(NOT_SWEET, Shape.SQUARE, ORANGE_COLOR),
                        new Fruit(),
                        new Fruit()
                ),
                everyItem(both(round()).and(sweet()).and(hasColor(ORANGE_COLOR))));
    }

    @Test
    public void hasOrangeInListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(
                asList(
                        new Fruit(NOT_SWEET, Shape.SQUARE, ORANGE_COLOR),
                        new Fruit()
                ),
                hasItem(both(round()).and(sweet()).and(hasColor(ORANGE_COLOR))));
    }

    @Test
    public void hasntOrangeInListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(
                asList(
                        new Fruit(NOT_SWEET, Shape.SQUARE, ORANGE_COLOR),
                        new Fruit(SWEET, Shape.SQUARE, ORANGE_COLOR)
                ),
                hasItem(both(round()).and(sweet()).and(hasColor(ORANGE_COLOR))));
    }

}

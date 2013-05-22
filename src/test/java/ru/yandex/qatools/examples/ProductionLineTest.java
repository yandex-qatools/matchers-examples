package ru.yandex.qatools.examples;

import org.junit.Test;
import ru.yandex.qatools.examples.data.FruitFactory;

import java.awt.*;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.examples.matchers.OrangeMatchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: lanwen
 * Date: 22.05.13
 * Time: 13:06
 */
public class ProductionLineTest {

    private Collection<Fruit> fruitList = FruitFactory.createFruitsList();

    @Test
    public void orangeListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(fruitList, everyItem(both(round()).and(sweet()).and(hasColor(Color.ORANGE))));
    }

    @Test
    public void notAllOrangeListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(fruitList, everyItem(both(round()).and(sweet()).and(hasColor(Color.ORANGE))));
    }

    @Test
    public void hasOrangeInListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(fruitList, hasItem(both(round()).and(sweet()).and(hasColor(Color.ORANGE))));
    }

    @Test
    public void hasntOrangeInListBothSweetRoundAndOrangeColorWithMatchers() throws Exception {
        assertThat(fruitList, hasItem(both(round()).and(sweet()).and(hasColor(Color.ORANGE))));
    }

}

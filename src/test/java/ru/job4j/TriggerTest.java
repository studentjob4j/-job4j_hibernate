package ru.job4j;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void whenGetSum() {
        Trigger trigger = new Trigger();
        assertThat(2, is(trigger.sum(1, 1)));
    }

}
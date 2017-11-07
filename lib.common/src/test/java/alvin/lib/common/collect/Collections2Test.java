package alvin.lib.common.collect;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Collections2Test {

    @Test
    public void test_null_or_empty() throws Exception {
        boolean actual = Collections2.nullOrEmpty(new ArrayList<>());
        assertThat(actual, is(true));
    }
}
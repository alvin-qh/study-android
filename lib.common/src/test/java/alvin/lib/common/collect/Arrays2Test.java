package alvin.lib.common.collect;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Arrays2Test {

    @Test
    public void test_null_or_empty() throws Exception {
        boolean actual = Arrays2.nullOrEmpty(new Integer[0]);
        assertThat(actual, is(true));
    }
}
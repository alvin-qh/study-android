package alvin.common.util;

import org.junit.Test;

import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CacheTest {

    @Test
    public void test_put_with_max_size() throws Exception {
        Cache<String> cache = new Cache<>(2);

        cache.put("C-1", "C-1");
        Thread.sleep(10);

        cache.put("C-2", "C-2");
        Thread.sleep(10);

        cache.put("C-3", "C-3");

        assertThat(cache.size(), is(2));
        assertThat(cache.get("C-1").isPresent(), is(false));
        assertThat(cache.get("C-2").isPresent(), is(true));
        assertThat(cache.get("C-3").isPresent(), is(true));

        Thread.sleep(10);
        cache.put("C-4", "C-4");

        assertThat(cache.size(), is(2));
        assertThat(cache.get("C-2").isPresent(), is(false));
        assertThat(cache.get("C-4").isPresent(), is(true));
    }

    @Test
    public void test_put_with_expired() throws Exception {
        Cache<String> cache = new Cache<>(500L, ChronoUnit.MILLIS);

        cache.put("C-1", "C-1");
        Thread.sleep(100);

        cache.put("C-2", "C-2");

        assertThat(cache.size(), is(2));
        assertThat(cache.get("C-1").isPresent(), is(true));
        assertThat(cache.get("C-2").isPresent(), is(true));

        Thread.sleep(400);

        cache.put("C-3", "C-3");

        assertThat(cache.size(), is(2));

        assertThat(cache.get("C-1").isPresent(), is(false));
        assertThat(cache.get("C-2").isPresent(), is(true));
        assertThat(cache.get("C-3").isPresent(), is(true));

        Thread.sleep(100);

        cache.put("C-4", "C-4");

        assertThat(cache.size(), is(2));

        assertThat(cache.get("C-2").isPresent(), is(false));
        assertThat(cache.get("C-3").isPresent(), is(true));
        assertThat(cache.get("C-4").isPresent(), is(true));
    }

    @Test
    public void test_put_with_all() throws Exception {
        Cache<String> cache = new Cache<>(2, 500L, ChronoUnit.MILLIS);

        cache.put("C-1", "C-1");
        cache.put("C-2", "C-2");
        cache.put("C-3", "C-3");

        assertThat(cache.size(), is(2));

        Thread.sleep(500);

        cache.put("C-4", "C-4");

        assertThat(cache.size(), is(1));

        assertThat(cache.get("C-1").isPresent(), is(false));
        assertThat(cache.get("C-2").isPresent(), is(false));
        assertThat(cache.get("C-3").isPresent(), is(false));
        assertThat(cache.get("C-4").isPresent(), is(true));
    }

    @Test
    public void test_clear() throws Exception {
        Cache<String> cache = new Cache<>(100, 500L, ChronoUnit.MILLIS);

        cache.put("C-1", "C-1");
        cache.put("C-2", "C-2");
        cache.put("C-3", "C-3");

        cache.clear();

        assertThat(cache.size(), is(0));
        assertThat(cache.isEmpty(), is(true));
    }
}
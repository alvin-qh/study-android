package alvin.common.util;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MD5Test {

    @Test
    public void test_update() throws Exception {
        MD5 md5 = new MD5();
        md5.update("Hello");

        String actual = md5.digestToString();
        assertThat(actual, is("8b1a9953c4611296a827abf8c47804d7"));

        md5 = new MD5();
        md5.update("Hello");
        actual = md5.digestToString(false);
        assertThat(actual, is("8B1A9953C4611296A827ABF8C47804D7"));
    }

    @Test
    public void test_digest() throws Exception {
        MD5 md5 = new MD5();
        String actual = md5.digestToString("Hello");
        assertThat(actual, is("8b1a9953c4611296a827abf8c47804d7"));
    }
}
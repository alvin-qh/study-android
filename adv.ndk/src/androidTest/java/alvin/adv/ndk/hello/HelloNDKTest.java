package alvin.adv.ndk.hello;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class HelloNDKTest {

    @Test
    public void helloNDK() {

        final HelloNDK ndk = new HelloNDK();
        assertThat(ndk.helloNDK(), is("Hello NDK"));
    }
}

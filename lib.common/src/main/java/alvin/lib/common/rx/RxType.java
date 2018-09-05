package alvin.lib.common.rx;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface RxType {
    @Qualifier
    @Documented
    @Retention(RUNTIME)
    @interface Compute {
    }

    @Qualifier
    @Documented
    @Retention(RUNTIME)
    @interface IO {
    }
}

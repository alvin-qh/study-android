package alvin.base.mvp.scope.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
public @interface Scopes {

    @Scope
    @Documented
    @Retention(RUNTIME)
    @interface Activity {
    }

    @Scope
    @Documented
    @Retention(RUNTIME)
    @interface Fragment {
    }
}

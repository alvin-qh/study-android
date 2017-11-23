package alvin.base.dagger.multibindings;

import dagger.MapKey;

@MapKey
public @interface StringMapKey {
    String value();
}

package alvin.adv.dagger.multibindings;

import dagger.MapKey;

@MapKey
public @interface StringMapKey {
    String value();
}

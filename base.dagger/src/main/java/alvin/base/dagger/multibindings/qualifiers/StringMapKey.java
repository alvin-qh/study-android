package alvin.base.dagger.multibindings.qualifiers;

import dagger.MapKey;

@MapKey
public @interface StringMapKey {
    String value();
}

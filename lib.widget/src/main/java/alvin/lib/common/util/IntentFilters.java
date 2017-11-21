package alvin.lib.common.util;

import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.Set;

import alvin.lib.common.collect.Collections2;

public final class IntentFilters {

    private IntentFilters() {
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder(null, null, null);
    }

    @NonNull
    public static Builder newBuilder(@NonNull String action) {
        return new Builder(action, null, null);
    }

    @NonNull
    public static Builder newBuilder(@NonNull String action,
                                     @NonNull String dataType) {
        return new Builder(action, dataType, null);
    }

    @NonNull
    public static Builder newBuilder(@NonNull IntentFilter other) {
        return new Builder(null, null, other);
    }

    public static class Builder {

        private IntentFilter other;
        private String dataType;
        private Set<String> actions;
        private Set<String> categories;

        public Builder(@Nullable String action,
                       @Nullable String dataType,
                       @Nullable IntentFilter other) {
            addAction(action);
            this.other = other;
            this.dataType = dataType;
        }

        @NonNull
        public Builder addAction(@Nullable String action) {
            if (!Strings.isNullOrEmpty(action)) {
                if (actions == null) {
                    actions = new HashSet<>();
                }
                actions.add(action);
            }
            return this;
        }

        @NonNull
        public Builder addCategory(@Nullable String category) {
            if (!Strings.isNullOrEmpty(category)) {
                if (categories == null) {
                    categories = new HashSet<>();
                }
                categories.add(category);
            }
            return this;
        }

        public IntentFilter build() {
            IntentFilter filter = new IntentFilter();

            if (Collections2.notNullOrEmpty(actions)) {
                actions.forEach(filter::addAction);
            }

            if (Collections2.notNullOrEmpty(categories)) {
                categories.forEach(filter::addCategory);
            }

            return filter;
        }
    }
}

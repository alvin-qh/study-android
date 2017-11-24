package alvin.base.mvp.namecard.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import alvin.base.mvp.domain.models.NameCard;

public final class NameCardFragmentHelper {
    private static final String ARGUMENT_NAME_CARD = "name_card";

    private final Fragment fragment;

    public NameCardFragmentHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public NameCard getArguments() {
        final Bundle arguments = fragment.getArguments();
        if (arguments == null) {
            throw new NullPointerException("argument is null");
        }

        NameCard nameCard = arguments.getParcelable(ARGUMENT_NAME_CARD);
        if (nameCard == null) {
            throw new IllegalArgumentException("nameCard argument missed");
        }
        return nameCard;
    }

    public void setArguments(@NonNull final NameCard nameCard) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_NAME_CARD, nameCard);
        fragment.setArguments(arguments);
    }
}

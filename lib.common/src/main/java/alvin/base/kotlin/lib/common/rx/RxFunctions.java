package alvin.base.kotlin.lib.common.rx;

import io.reactivex.internal.functions.Functions;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public interface RxFunctions {

    Function0<Unit> EMPTY_FUNCTION_0 = () -> Unit.INSTANCE;

    Function1<Throwable, Unit> ON_ERROR_MISSING = throwable -> {
        try {
            Functions.ON_ERROR_MISSING.accept(throwable);
            return Unit.INSTANCE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    Function1<Object, Unit> EMPTY_FUNCTION_1 = disposable -> Unit.INSTANCE;
}

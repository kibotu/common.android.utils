package common.android.utils.network;

/**
 * Created by conrad on 2015/03/09.
 */
public abstract class RequestExecutor {

    public abstract void cleanCookies();

    abstract public <T> void executeRequest(final Request<T> request, final RequestExecutorListener<T> listener);

    abstract public <T> void cancelRequest(final Request<T> request);

    public interface RequestExecutorListener<E> {
        void onSuccess(E result);

        void onError(Exception error);
    }

}

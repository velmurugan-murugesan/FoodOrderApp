package app.com.foodorderapp.data.callback;

public interface DaoResponse<T> {
    void onSuccess(T message);
    void onFailure(String errorMessage);
}

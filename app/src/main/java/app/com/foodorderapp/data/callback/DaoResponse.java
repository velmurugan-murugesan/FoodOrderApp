package app.com.foodorderapp.data.callback;

public interface DaoResponse {
    void onSuccess(String message);
    void onFailure(String errorMessage);
}

package Result;

import java.util.Optional;

public class Result<T> {
    private final boolean success;
    private String message;
    private final T value;
    private final Exception error;

    private Result(boolean success, String message, T value, Exception error) {
        this.success = success;
        this.message = message;
        this.value = value;
        this.error = error;
    }

    public static <T> Result<T> success(T value) {
        return new Result<T>(true, "Succesfully executed", value, null);
    }

    public static <T> Result<T> failure(Exception error) {
        return new Result<T>(false, "Problem is occured", null, error);
    }

    public static <T> Result<T> failure(Exception error, String message) {
        return new Result<T>(false, message, null, error);
    }

    public boolean isSuccess() {
        return success;
    }

    public Optional<T> getValue() {
        return success ? Optional.ofNullable(value) : Optional.empty();
    }

    public Optional<Exception> getError() {
        return success ? Optional.empty() : Optional.ofNullable(error);
    }

    public String getMessage(){
        return message;
    }

}

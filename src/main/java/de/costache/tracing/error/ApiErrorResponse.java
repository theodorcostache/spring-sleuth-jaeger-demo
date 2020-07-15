package de.costache.tracing.error;

public class ApiErrorResponse {

    private Integer code;
    private String message;
    private String errorClass;
    private String errorMessage;

    public ApiErrorResponse(Integer code, String message, String errorClass, String errorMessage) {
        this.code = code;
        this.message = message;
        this.errorClass = errorClass;
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public String getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

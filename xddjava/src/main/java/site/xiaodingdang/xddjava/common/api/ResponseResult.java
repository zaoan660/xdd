package site.xiaodingdang.xddjava.common.api;

public class ResponseResult<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(Integer code) {
        this.code = code;
    }
    public static <T> ResponseResult<T> ok(){
        return new ResponseResult<T>(ResultCode.OK.getCode(),ResultCode.OK.getMessage());
    }
    public static <T> ResponseResult<T> ok(T data){
        return new ResponseResult<T>(ResultCode.OK.getCode(),ResultCode.OK.getMessage(),data);
    }
    public static <T> ResponseResult<T> success(String message, T data){
        return new ResponseResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }
    public static <T> ResponseResult<T> success(String message){
        return new ResponseResult<T>(ResultCode.SUCCESS.getCode(), message);
    }
    public static <T> ResponseResult<T> error(String message){
        return new ResponseResult<T>(ResultCode.ERROR.getCode(),message);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


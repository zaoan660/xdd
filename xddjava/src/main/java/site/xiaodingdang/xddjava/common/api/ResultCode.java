package site.xiaodingdang.xddjava.common.api;

public enum ResultCode {
    OK(200, "操作成功"),
    SUCCESS(201,"操作成功"),
    ERROR(400, "客户端错误"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

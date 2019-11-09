package application.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 后台响应包装�?
 */
public class ResEnv<T> {
	
	
    private int code;
    private String msg;

    private T content;

    private final Map<String, Object> options = new HashMap<>();

    public static <T> ResEnv<T> success(String msg, T object) {
        return new ResEnv<T>(CommonConstant.RES_CODE_OK, msg, object);
    }

    public static <T> ResEnv<T> success(String msg) {
        return new ResEnv<>(CommonConstant.RES_CODE_OK, msg, null);
    }

    public static <T> ResEnv<T> success(T object) {
        return new ResEnv<>(CommonConstant.RES_CODE_OK,CommonConstant.DEF_SUCC_MSG, object);
    }

    public static <T> ResEnv<T> success() {
        return success(CommonConstant.DEF_SUCC_MSG);
    }

    public static <T> ResEnv<T> fail(String msg) {
        return new ResEnv<>(CommonConstant.RES_CODE_ERROR, msg, null);
    }
    public static <T> ResEnv<T> fail(int code, String msg) {
        return new ResEnv<>(code, msg, null);
    }

    public static <T> ResEnv<T> fail() {
        return fail(CommonConstant.DEF_FAIL_MSG);
    }

    public ResEnv() {
    }

    public ResEnv(int code) {
        this();
        this.code = code;
    }

    public ResEnv(int code, String msg) {
        this(code);
        this.msg = msg;
    }

    public ResEnv(int code, String msg, T content) {
        this(code, msg);
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    @SuppressWarnings("rawtypes")
	public ResEnv addOption(String key, Object value) {
        options.put(key, value);
        return this;
    }

    @SuppressWarnings({ "unchecked", "hiding" })
	public <T> T getOption(String key) {
        return (T) options.get(key);
    }

    public boolean hasOption(String key) {
        return options.containsKey(key);
    }
}

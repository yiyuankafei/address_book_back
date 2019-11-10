package application.constant;

/**
 *全局常量
 */
public class CommonConstant {

	
	/**
     * 默认成功消息
     */
    public static final String DEF_SUCC_MSG = "操作成功";
    
    /**
     * 默认失败消息
     */
    public static final String DEF_FAIL_MSG = "操作失败";
    
    
    /**
     * 返回代码：成功
     */
    public static final int RES_CODE_OK = 200;
    
    /**
     * 返回代码：失敗
     */
    public static final int RES_CODE_ERROR = 500;
    
    /**
     * 返回代码：token过期
     */
    public static final int RES_CODE_TOKEN_EXPIRE = 600;
    
    /**
     * 返回代码：token不存在
     */
    public static final int RES_CODE_TOKEN_NOT_EXISTS = 601;
    
    /**
     * 返回代码：认证失败
     */
    public static final int RES_CODE_TOKEN_AUTH_FAIL = 602;
    
    /**
     * 返回代码：用户名重复
     */
    public static final int RES_CODE_EXISTS_USERNAME = 701;
    
    /**
     * 返回代码：手机号重复
     */
    public static final int RES_CODE_EXISTS_PHONE = 702;
    
    /**
     * 返回代码：用户信息不存在
     */
    public static final int RES_CODE_USERINFO = 703;
    
    /**
     * 返回代码：密码错误
     */
    public static final int RES_CODE_PASSWORD_MISTAKE = 704;
    
    /**
     * 返回代码：账号冻结
     */
    public static final int RES_CODE_USER_FREEZE = 705;
    
    /**
	 * 分隔符
	 */
	public static final String FILE_SEPARATOR = "/";
	
	/**
	 * 字符编码
	 */
	public static final String UTF8 = "UTF-8";


	/**
	 * 环境常量
	 */
	public static final String ENVIRONMENT = "env";
	public static final String ENVIRONMENT_DEV = "dev";
	public static final String ENVIRONMENT_TEST = "test";
	public static final String ENVIRONMENT_PROD = "prod";

	/**
	 * 符号常量
	 */
	public static final String SPACE = " ";
	public static final String VERTICAL_LINE = "|";
	public static final String DASH = "-";
	public static final String UNDERSCORE = "_";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String EMPTY = "";
	public static final String SEMICOLON = ";";

	/**
	 * 当前用户
	 */
	public static final String LOGIN_USER = "loginUser";
	
	/**
	 * JWT密钥
	 */
	public static final String JWT_SECRET = "abcdefg01234567890";
	/**
	 * JWT  token
	 */
	public static final Integer JWT_TTL = 60 * 60 * 1000; 
	
	/**
	 * 是否删除  true-删除
	 */
	public static final Boolean DEL_Y = true;
	public static final Boolean DEL_N = false;
	
	/**
	 * 是否启用  true-启用
	 */
	public static final Boolean ENB_Y = true;
	public static final Boolean ENB_N = false;
    
}
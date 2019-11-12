package application.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class OssConfig {
	
	private String endPoint;
	
	private String bucketName;
	
	private String accessKeyId;
	
	private String accessKeySecret;
	
}

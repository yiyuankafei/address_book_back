package application.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import application.config.OssConfig;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

/**
 * 
 * OSS工具
 */
public class OssUtil {
	
	public static OSSClient getInstance(OssConfig config) {
		ClientConfiguration configure = new ClientConfiguration();
		configure.setConnectionTimeout(10000);
		OSSClient client = new OSSClient(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret(), configure);
		return client;
	}
	
	
	/**
	 * 
	 * 上传文件，传入字节数组
	 * @return 图片的公网路径
	 */
	public static String upload(OSSClient ossClient,String filePath,byte[] bytes, String bucketName) {
		//上传图片至OSS
		ossClient.putObject(bucketName,filePath,new ByteArrayInputStream(bytes));
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 3);
		//图片存储在OSS上的公网路径
	    URL url = ossClient.generatePresignedUrl(bucketName, filePath, expiration);
	    return url.toString();
	}
	
	/**
	 * 
	 * 下载文件，返回字节流
	 */
	public static byte[] download(OSSClient ossClient,String filePath, String bucketName) throws Exception {
		OSSObject ossObject = ossClient.getObject(bucketName,filePath);
		
		byte[] buffer = new byte[1024];
		int length = 0;
		InputStream in = ossObject.getObjectContent();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}
		return out.toByteArray();
	}
	
	/**
	 * 
	 * 下载到本地文件
	 */
	public static void download4File(OSSClient ossClient,String filePath,File file, String bucketName) throws Exception {
		ossClient.getObject(new GetObjectRequest(bucketName,filePath),file);
	}
	
}

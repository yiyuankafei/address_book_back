package application.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import application.config.OssConfig;
import application.constant.CommonConstant;
import application.constant.ResEnv;
import application.util.OssUtil;

import com.aliyun.oss.OSSClient;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	OssConfig ossConfig;
	
	private static SimpleDateFormat dateForamt = new SimpleDateFormat("yyyyMMddHHmmss");

	@RequestMapping("/upload")
	public ResEnv<String> upload(MultipartFile file) throws Exception {
		OSSClient client = OssUtil.getInstance(ossConfig);
		String webPath = "";
		try {
			String originalName = file.getOriginalFilename();
			String path = dateForamt.format(new Date()) + CommonConstant.FILE_SEPARATOR + UUID.randomUUID() + 
																originalName.substring(originalName.lastIndexOf("."));
			webPath = OssUtil.upload(client, path, file.getBytes(), ossConfig.getBucketName());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.shutdown();
		}
		return ResEnv.success(CommonConstant.DEF_SUCC_MSG,webPath);
	}
	
}
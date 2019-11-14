package com.chuangxin.musicroom.client.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.chuangxin.musicroom.core.dto.BaseResult;
import com.chuangxin.musicroom.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DaiDong
 * @since 2018-01-05
 * @email 755144556@qq.com
 */
@Api(tags = "FileUploadController", description = "文件上传")
@RestController
@Slf4j
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Value("${path.uploadPath}")
    String uploadPath;
    
    @Autowired 
    HttpServletRequest request;

	/**
     * 文件上传
     */
	@ApiOperation(value="文件上传", notes="")
    @RequestMapping(value="/{subPath}", method={RequestMethod.POST})
	public BaseResult uploadCommonFile(MultipartFile file,
                                       @PathVariable("subPath") String subPath) throws Exception {
    	//服务器存放文件路径
//    	String projectPath = req.getServletContext().getRealPath("/");
//    	projectPath = projectPath.replace("\\", "/");
//    	projectPath = projectPath.replace("static/", "");//savedUrl已经有static文件夹路径，这里重复了
    	
    	//原文件路径
    	String fileName = file.getOriginalFilename();
    	String suffix = fileName.substring(fileName.lastIndexOf("."));
    	
    	//新文件存放路径及文件名
    	Date now = new Date();
		//String newFilePath = uploadPath + "static/uploadFile/";
    	String newFilePath = uploadPath;
    	String newFileName = StringUtil.getRandomString(10) + suffix;
    	String relativePath = "/" + subPath + "/" + newFileName;  
    	if (null != subPath && !"".equals(uploadPath)) {
    		newFilePath = newFilePath + "/" + subPath + "/";
    	}
		String newFilePathAndName = newFilePath + newFileName;  
		//新建存放文件夹
		File f = new File(newFilePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		//存文件
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(newFilePathAndName)));  
		out.write(file.getBytes());  
		out.flush();  
		out.close();

		//返回文件url
		return new BaseResult("1", "成功", relativePath);
	} 
    
}

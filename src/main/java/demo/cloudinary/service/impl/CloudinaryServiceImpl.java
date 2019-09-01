package demo.cloudinary.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import cloudinary.pojo.constant.CloudinaryConstant;
import cloudinary.pojo.result.CloudinaryUploadResult;
import cloudinary.util.CloudinaryCore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.cloudinary.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl extends CommonService implements CloudinaryService {

	@Autowired
	private SystemConstantService constantService;
	
	@Override
	public CloudinaryUploadResult upload(File f) {
		String userName = constantService.getValByName(CloudinaryConstant.cloudName);
		String apiKey = constantService.getValByName(CloudinaryConstant.apiKey);
		String apiSecret = constantService.getValByName(CloudinaryConstant.apiSecret);
		
		CloudinaryCore cloudCore = new CloudinaryCore();
		Cloudinary c = cloudCore.buildCloudinary(userName, apiKey, apiSecret);
		CloudinaryUploadResult result = cloudCore.upload(c, f);
		return result;
	}
}

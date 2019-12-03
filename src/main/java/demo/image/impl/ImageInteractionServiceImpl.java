package demo.image.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import auxiliaryCommon.pojo.constant.ServerHost;
import demo.baseCommon.service.CommonService;
import demo.image.ImageInteractionService;
import httpHandel.HttpUtil;
import image.pojo.constant.ImageInteractionUrl;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;
import net.sf.json.JSONObject;

@Service
public class ImageInteractionServiceImpl extends CommonService implements ImageInteractionService {
	
	@Autowired
	private HttpUtil httpUtil;
	
	@Override
	public UploadImageToCloudinaryResult uploadImageToCloudinary(@RequestBody UploadImageToCloudinaryDTO dto) {
		UploadImageToCloudinaryResult r = null;
		try {
			JSONObject j = JSONObject.fromObject(dto);
	        
			String url = ServerHost.host1 + ImageInteractionUrl.root + ImageInteractionUrl.uploadImageToCloudinary;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			JSONObject resultJ = JSONObject.fromObject(response);
			
			r = new ObjectMapper().readValue(resultJ.toString(), UploadImageToCloudinaryResult.class);
			
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

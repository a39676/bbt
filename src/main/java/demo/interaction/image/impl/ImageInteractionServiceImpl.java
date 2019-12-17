package demo.interaction.image.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import auxiliaryCommon.pojo.constant.ServerHost;
import demo.baseCommon.service.CommonService;
import demo.interaction.image.ImageInteractionService;
import image.pojo.constant.ImageInteractionUrl;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class ImageInteractionServiceImpl extends CommonService implements ImageInteractionService {
	
	@Autowired
	private HttpUtil httpUtil;
	
	@Override
	public UploadImageToCloudinaryResult uploadImageToCloudinary(@RequestBody UploadImageToCloudinaryDTO dto) {
		UploadImageToCloudinaryResult r = new UploadImageToCloudinaryResult();
		try {
			JSONObject j = JSONObject.fromObject(dto);
	        
			String url = ServerHost.localHost10001 + ImageInteractionUrl.root + ImageInteractionUrl.uploadImageToCloudinary;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			JSONObject resultJ = JSONObject.fromObject(response);
			
			r = new ObjectMapper().readValue(resultJ.toString(), UploadImageToCloudinaryResult.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

}

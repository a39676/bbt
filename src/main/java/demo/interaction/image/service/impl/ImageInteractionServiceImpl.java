package demo.interaction.image.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import auxiliaryCommon.pojo.constant.ServerHost;
import demo.baseCommon.service.CommonService;
import demo.interaction.image.service.ImageInteractionService;
import image.pojo.constant.ImageInteractionUrl;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.ImageSavingResult;
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
	        
			String url = ServerHost.CX_HOST + ImageInteractionUrl.ROOT + ImageInteractionUrl.UPLOAD_IMAGE_TO_CLOUDINARY;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			JSONObject resultJ = JSONObject.fromObject(response);
			
			r = new ObjectMapper().readValue(resultJ.toString(), UploadImageToCloudinaryResult.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	public ImageSavingResult saveImgToCX(ImageSavingTransDTO dto) {
		ImageSavingResult r = new ImageSavingResult();
		try {

			JSONObject j = JSONObject.fromObject(dto);
			j.put("validTime", localDateTimeHandler.dateToStr(dto.getValidTime()));
	        
			String url = ServerHost.CX_HOST + ImageInteractionUrl.ROOT + ImageInteractionUrl.IMAGE_SAVING_FROM_BBT;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			JSONObject resultJ = JSONObject.fromObject(response);
			
			r.setCode(resultJ.getString("code"));
			if("0".equals(r.getCode())) {
				r.setIsSuccess();
			} else {
				r.setIsFail();
			}
			if(resultJ.containsKey("message")) {
				r.setMessage(resultJ.getString("message"));
			}
			if(resultJ.containsKey("imgPK")) {
				r.setImgPK(resultJ.getString("imgPK"));
			}
			if(resultJ.containsKey("imgUrl")) {
				r.setImgUrl(resultJ.getString("imgUrl"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
		
	}

}

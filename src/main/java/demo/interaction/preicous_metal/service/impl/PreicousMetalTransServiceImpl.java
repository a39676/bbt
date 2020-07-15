package demo.interaction.preicous_metal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import auxiliaryCommon.pojo.constant.ServerHost;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.service.CommonService;
import demo.interaction.preicous_metal.service.PreciousMetalTransService;
import net.sf.json.JSONObject;
import precious_metal.pojo.constant.PreciousMetalPriceCommonUrl;
import precious_metal.pojo.dto.TransmissionPreciousMetalPriceDTO;
import toolPack.httpHandel.HttpUtil;

@Service
public class PreicousMetalTransServiceImpl extends CommonService implements PreciousMetalTransService {

	@Autowired
	private HttpUtil httpUtil;
	
	@Override
	public CommonResult transPreciousMetalPriceToCX(@RequestBody TransmissionPreciousMetalPriceDTO dto) {
		CommonResult r = new CommonResult();
		try {
			JSONObject j = JSONObject.fromObject(dto);
	        
			String url = ServerHost.localHost10001 + PreciousMetalPriceCommonUrl.root + PreciousMetalPriceCommonUrl.transPreciousMetalPrice;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			JSONObject resultJ = JSONObject.fromObject(response);
			
			r = new ObjectMapper().readValue(resultJ.toString(), CommonResult.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
}

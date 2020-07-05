package demo.interaction.preicous_metal.service;

import auxiliaryCommon.pojo.result.CommonResult;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import precious_metal.pojo.dto.TransPreciousMetalPriceDTO;

public interface PreciousMetalTransService {

	CommonResult transPreciousMetalPriceToCX(TransPreciousMetalPriceDTO dto);

}

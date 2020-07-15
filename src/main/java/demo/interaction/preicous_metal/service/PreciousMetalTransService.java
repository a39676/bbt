package demo.interaction.preicous_metal.service;

import auxiliaryCommon.pojo.result.CommonResult;
import precious_metal.pojo.dto.TransmissionPreciousMetalPriceDTO;

public interface PreciousMetalTransService {

	CommonResult transPreciousMetalPriceToCX(TransmissionPreciousMetalPriceDTO dto);

}

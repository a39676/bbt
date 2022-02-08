package demo.scriptCore.localClawing.service;

import demo.scriptCore.localClawing.pojo.dto.HsbcOptionDTO;
import demo.scriptCore.localClawing.pojo.dto.HsbcTabletQuickFixDataDTO;

public interface HsbcService {

	void weixinPreRegBatch();

	void weixinPreReg(HsbcTabletQuickFixDataDTO dto, HsbcOptionDTO optionDTO);

}

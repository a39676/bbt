package demo.movie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.movie.pojo.constant.MovieClawingConstant;
import demo.movie.service.MovieClawingOptionService;

@Service
public class MovieClawingOptionServiceImpl extends CommonService implements MovieClawingOptionService {

	@Autowired
	private SystemConstantService constantService;
	
	@Override
	public String getHomeFeiUsername() {
		return constantService.getValByName(MovieClawingConstant.homeFeiUsername);
	}
	
	@Override
	public String getHomeFeiPwd() {
		return constantService.getValByName(MovieClawingConstant.homeFeiPwd);
	}
}

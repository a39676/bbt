package demo.movie.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.movie.mapper.MovieClickCountMapper;
import demo.movie.mapper.MovieImageMapper;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieMagnetUrlMapper;
import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.constant.MovieInteractionConstant;
import demo.movie.pojo.po.MovieClickCountExample;
import demo.movie.pojo.po.MovieImageExample;
import demo.movie.pojo.po.MovieInfoExample;
import demo.movie.pojo.po.MovieIntroductionExample;
import demo.movie.pojo.po.MovieMagnetUrlExample;
import demo.movie.pojo.po.MovieRecordExample;
import demo.movie.service.MovieManagerService;

@Service
public class MovieManagerServiceImpl extends CommonService implements MovieManagerService {

	@Autowired
	private MovieInfoMapper infoMapper;
	@Autowired
	private MovieRecordMapper recordMapper;
	@Autowired
	private MovieClickCountMapper clickCountMapper;
	@Autowired
	private MovieImageMapper movieImgMapper;
	@Autowired
	private MovieIntroductionMapper introductionMapper;
	@Autowired
	private MovieMagnetUrlMapper magnetUrlMapper;
	
	@Override
	public void deleteOldHistory() {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(MovieInteractionConstant.maxHistoryMonth);
		
		MovieInfoExample infoE = new MovieInfoExample();
		infoE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		infoMapper.deleteByExample(infoE);
		
		MovieRecordExample racordE = new MovieRecordExample();
		racordE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		recordMapper.deleteByExample(racordE);

		MovieClickCountExample clickE = new MovieClickCountExample();
		clickE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		clickCountMapper.deleteByExample(clickE);
		
		MovieImageExample movImgE = new MovieImageExample();
		movImgE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		movieImgMapper.deleteByExample(movImgE);
		
		MovieIntroductionExample introE = new MovieIntroductionExample();
		introE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		introductionMapper.deleteByExample(introE);
		
		MovieMagnetUrlExample magnetUrlE = new MovieMagnetUrlExample();
		magnetUrlE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		magnetUrlMapper.deleteByExample(magnetUrlE);
		
	}
}

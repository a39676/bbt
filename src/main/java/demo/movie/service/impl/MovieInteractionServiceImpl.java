package demo.movie.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.pojo.constant.MovieInteractionConstant;
import demo.movie.pojo.dto.FindMovieListByConditionDTO;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.result.FindMovieSummaryListResult;
import demo.movie.service.MovieInteractionService;
import movie.pojo.dto.FindMovieSummaryListDTO;

@Service
public class MovieInteractionServiceImpl extends CommonService implements MovieInteractionService {

	@Autowired
	private MovieInfoMapper infoMapper;
	
	@Override
	public FindMovieSummaryListResult findMovieSummaryList(FindMovieSummaryListDTO dto) {
		FindMovieSummaryListResult r = new FindMovieSummaryListResult();
		
		dto.setPageParam();
		
		LocalDateTime earliestHistoryTime = LocalDateTime.now().minusMonths(MovieInteractionConstant.maxHistoryMonth);
		FindMovieListByConditionDTO mapperDTO = new FindMovieListByConditionDTO();
		BeanUtils.copyProperties(dto, mapperDTO);
		mapperDTO.setCreateTimeStart(earliestHistoryTime);
		
		List<MovieInfo> movieInfoList = infoMapper.findListByCondition(mapperDTO);
		r.setMovieInfoList(movieInfoList);
		r.setIsSuccess();
		return r;
	}
}

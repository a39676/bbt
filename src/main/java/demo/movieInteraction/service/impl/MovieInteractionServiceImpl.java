package demo.movieInteraction.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageStoreExample;
import demo.movie.mapper.MovieImageMapper;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieMagnetUrlMapper;
import demo.movie.pojo.constant.MovieInteractionConstant;
import demo.movie.pojo.dto.FindMovieListByConditionDTO;
import demo.movie.pojo.po.MovieImage;
import demo.movie.pojo.po.MovieImageExample;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieMagnetUrl;
import demo.movie.pojo.po.MovieMagnetUrlExample;
import demo.movie.pojo.result.FindMovieDetailResult;
import demo.movie.pojo.result.FindMovieSummaryListResult;
import demo.movieInteraction.service.MovieInteractionService;
import demo.tool.service.VisitDataService;
import ioHandle.FileUtilCustom;
import movie.pojo.dto.FindMovieDetailDTO;
import movie.pojo.dto.FindMovieSummaryListDTO;
import numericHandel.NumericUtilCustom;
import tool.pojo.bo.IpRecordBO;

@Service
public class MovieInteractionServiceImpl extends CommonService implements MovieInteractionService {

	@Autowired
	private FileUtilCustom fileUtil;
	@Autowired
	private VisitDataService visitDataService;
	@Autowired
	private NumericUtilCustom numberUtil;
	
	@Autowired
	private MovieInfoMapper infoMapper;
	@Autowired
	private MovieImageMapper movieImageMapper;
	@Autowired
	private ImageStoreMapper imageStoreMapper;
	@Autowired
	private MovieIntroductionMapper introductionMapper;
	@Autowired
	private MovieMagnetUrlMapper magnetUrlMapper;
	
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
	
	@Override
	public FindMovieDetailResult findMovieDetail(FindMovieDetailDTO dto) {
		FindMovieDetailResult r = new FindMovieDetailResult();
		if(dto.getMovieId() == null) {
			return r;
		}
		
		MovieInfo movieInfo = infoMapper.selectByPrimaryKey(dto.getMovieId());
		r.setMovieId(dto.getMovieId());
		r.setCnTitle(movieInfo.getCnTitle());
		r.setOriginalTitle(movieInfo.getOriginalTitle());
		
		MovieImageExample movImgExample = new MovieImageExample();
		movImgExample.createCriteria().andMovidIdEqualTo(dto.getMovieId());
		List<MovieImage> movieImages = movieImageMapper.selectByExample(movImgExample);
		List<Long> imgIds = movieImages.stream().map(img -> img.getImageId()).collect(Collectors.toList());
		ImageStoreExample imgExample = new ImageStoreExample();
		imgExample.createCriteria().andIdIn(imgIds);
		List<ImageStore> imgs = imageStoreMapper.selectByExample(imgExample);
		List<String> imgUrls = imgs.stream().map(img -> img.getImagePath()).collect(Collectors.toList());
		r.setImgList(imgUrls);
		
		MovieIntroduction introductionPO = introductionMapper.selectByPrimaryKey(dto.getMovieId());
		if(introductionPO != null) {
			try {
				r.setIntroduction(fileUtil.getStringFromFile(introductionPO.getIntroPath()));
			} catch (Exception e) {
			}
		}
		
		MovieMagnetUrlExample magExample = new MovieMagnetUrlExample();
		magExample.createCriteria().andMovieIdEqualTo(dto.getMovieId());
		List<MovieMagnetUrl> magnetPOList = magnetUrlMapper.selectByExample(magExample);
		List<String> magnetUrls = magnetPOList.stream().map(m -> m.getUrl()).collect(Collectors.toList());
		r.setMagnetUrlList(magnetUrls);
		
		r.setIsSuccess();
		return r;
	}

	public void insertMovieClickCounting(HttpServletRequest request) {
		/*
		 * TODO
		 */
//		Set<String> keys = redisTemplate.keys(MovieInteractionRedisKey.MOVIE_CLICK_COUNTING_REDIS_KEY_PREFIX);
//		
//		Long movieId = null;
		
		IpRecordBO record = visitDataService.getIp(request);
		Long l = numberUtil.ipToLong(record.getRemoteAddr());
		if(l == 0) {
			l = numberUtil.ipToLong(record.getForwardAddr());
		}
		redisTemplate.opsForSet().add(String.valueOf(l));
	}
}

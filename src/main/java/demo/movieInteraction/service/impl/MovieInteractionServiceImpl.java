package demo.movieInteraction.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageStoreExample;
import demo.movie.mapper.MovieClickCountMapper;
import demo.movie.mapper.MovieImageMapper;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieMagnetUrlMapper;
import demo.movie.pojo.constant.MovieInteractionConstant;
import demo.movie.pojo.dto.FindMovieListByConditionDTO;
import demo.movie.pojo.dto.InsertOrUpdateMovieClickCountDTO;
import demo.movie.pojo.po.MovieClickCount;
import demo.movie.pojo.po.MovieImage;
import demo.movie.pojo.po.MovieImageExample;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieMagnetUrl;
import demo.movie.pojo.po.MovieMagnetUrlExample;
import demo.movie.pojo.result.FindMovieDetailResult;
import demo.movie.pojo.result.FindMovieSummaryListResult;
import demo.movieInteraction.MovieInteractionRedisKey;
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
	@Autowired
	private MovieClickCountMapper movieClickCountMapper;
	
	
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
	public FindMovieDetailResult findMovieDetail(HttpServletRequest request, FindMovieDetailDTO dto) {
		FindMovieDetailResult r = new FindMovieDetailResult();
		if(dto.getMovieId() == null) {
			return r;
		}
		
		insertMovieClickCounting(request, dto.getMovieId());
		
		MovieInfo movieInfo = infoMapper.selectByPrimaryKey(dto.getMovieId());
		r.setMovieId(dto.getMovieId());
		r.setCnTitle(movieInfo.getCnTitle());
		r.setOriginalTitle(movieInfo.getOriginalTitle());
		
		Long clickCounting = findMovieClickCount(dto.getMovieId());
		r.setClickCounting(clickCounting);
		
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
	

	private void insertMovieClickCounting(HttpServletRequest request, Long movieId) {
		IpRecordBO record = visitDataService.getIp(request);
		Long l = numberUtil.ipToLong(record.getRemoteAddr());
		if(l == 0) {
			l = numberUtil.ipToLong(record.getForwardAddr());
		}
		redisTemplate.opsForSet().add(MovieInteractionRedisKey.MOVIE_CLICK_COUNTING_REDIS_KEY_PREFIX + movieId, String.valueOf(l));
	}
	
	@Override
	public void movieClickCountingRedisToOrm() {
		Set<String> keys = redisTemplate.keys(MovieInteractionRedisKey.MOVIE_CLICK_COUNTING_REDIS_KEY_PREFIX + "*");
		
		for(String key : keys) {
			subMovieClickCountingRedisToOrm(key);
		}
	}
	
	
	private void subMovieClickCountingRedisToOrm(String key) {
		Long movieId = null;
		String idStr = key.replaceAll(MovieInteractionRedisKey.MOVIE_CLICK_COUNTING_REDIS_KEY_PREFIX, "");
		try {
			movieId = Long.parseLong(idStr);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Long size = redisTemplate.opsForSet().size(key);
		
		InsertOrUpdateMovieClickCountDTO dto = new InsertOrUpdateMovieClickCountDTO();
		dto.setCounting(size);
		dto.setMovieId(movieId);
		movieClickCountMapper.insertOrUpdateClickCount(dto);
		
		redisTemplate.opsForSet().pop(key);
	}

	@Override
	public Long findMovieClickCount(Long movieId) {
		MovieClickCount po = movieClickCountMapper.selectByPrimaryKey(movieId);
		if(po == null) {
			po = new MovieClickCount();
			po.setCounting(0L);
		}
		Long size = redisTemplate.opsForSet().size(MovieInteractionRedisKey.MOVIE_CLICK_COUNTING_REDIS_KEY_PREFIX + movieId);
		
		return size + po.getCounting();
	}
}

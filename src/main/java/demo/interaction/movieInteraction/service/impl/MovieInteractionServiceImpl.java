package demo.interaction.movieInteraction.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.clawing.movie.mapper.MovieClickCountMapper;
import demo.clawing.movie.mapper.MovieImageMapper;
import demo.clawing.movie.mapper.MovieInfoMapper;
import demo.clawing.movie.mapper.MovieIntroductionMapper;
import demo.clawing.movie.mapper.MovieMagnetUrlMapper;
import demo.clawing.movie.pojo.constant.MovieInteractionConstant;
import demo.clawing.movie.pojo.dto.FindMovieListByConditionDTO;
import demo.clawing.movie.pojo.dto.InsertOrUpdateMovieClickCountDTO;
import demo.clawing.movie.pojo.po.MovieClickCount;
import demo.clawing.movie.pojo.po.MovieClickCountExample;
import demo.clawing.movie.pojo.po.MovieImage;
import demo.clawing.movie.pojo.po.MovieImageExample;
import demo.clawing.movie.pojo.po.MovieInfo;
import demo.clawing.movie.pojo.po.MovieInfoExample;
import demo.clawing.movie.pojo.po.MovieIntroduction;
import demo.clawing.movie.pojo.po.MovieIntroductionExample;
import demo.clawing.movie.pojo.po.MovieMagnetUrl;
import demo.clawing.movie.pojo.po.MovieMagnetUrlExample;
import demo.clawing.movie.pojo.result.FindMovieDetailResult;
import demo.clawing.movie.pojo.result.FindMovieSummaryElementResult;
import demo.clawing.movie.pojo.result.FindMovieSummaryListResult;
import demo.interaction.image.mapper.ImageStoreMapper;
import demo.interaction.image.pojo.po.ImageStore;
import demo.interaction.image.pojo.po.ImageStoreExample;
import demo.interaction.movieInteraction.MovieInteractionRedisKey;
import demo.interaction.movieInteraction.pojo.dto.FindLastHotClickDTO;
import demo.interaction.movieInteraction.pojo.dto.FindPosterIdByMovieIdListDTO;
import demo.interaction.movieInteraction.pojo.result.FindMovieRecommendResult;
import demo.interaction.movieInteraction.pojo.vo.MovieRecommendVO;
import demo.interaction.movieInteraction.service.MovieInteractionService;
import demo.tool.service.VisitDataService;
import movie.pojo.dto.FindMovieDetailDTO;
import movie.pojo.dto.FindMovieSummaryListDTO;
import tool.pojo.bo.IpRecordBO;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;

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
		
		LocalDateTime earliestHistoryTime = LocalDateTime.now().minusMonths(MovieInteractionConstant.maxQueryHistoryMonth);
		FindMovieListByConditionDTO mapperDTO = new FindMovieListByConditionDTO();
		BeanUtils.copyProperties(dto, mapperDTO);
		mapperDTO.setCreateTimeStart(earliestHistoryTime);
		
		List<MovieInfo> movieInfoList = infoMapper.findListByCondition(mapperDTO);
		if(movieInfoList.size() < 1) {
			r.setIsSuccess();
			return r;
		}
		
		List<Long> movieIdList = movieInfoList.stream().map(p -> p.getId()).collect(Collectors.toList());
		
		MovieClickCountExample findMovieClickCountExample = new MovieClickCountExample();
		findMovieClickCountExample.createCriteria().andMovieIdIn(movieIdList);
		List<MovieClickCount> movieClickCountList = movieClickCountMapper.selectByExample(findMovieClickCountExample);
		Map<Long, MovieClickCount> clickCountMap = movieClickCountList.stream().collect(Collectors.toMap(MovieClickCount::getMovieId, Function.identity()));
		
		MovieIntroductionExample findIntroductionExample = new MovieIntroductionExample();
		findIntroductionExample.createCriteria().andMovieIdIn(movieIdList);
		List<MovieIntroduction> movieIntroductionList = introductionMapper.selectByExample(findIntroductionExample);
		Map<Long, MovieIntroduction> introductionMap = movieIntroductionList.stream().collect(Collectors.toMap(MovieIntroduction::getMovieId, Function.identity()));
		
		FindMovieSummaryElementResult subR = null;
		String introPath = null;
		List<FindMovieSummaryElementResult> list = new ArrayList<FindMovieSummaryElementResult>();
		for(MovieInfo info : movieInfoList) {
			subR = new FindMovieSummaryElementResult();
			subR.setReleaseTime(info.getReleaseTime());
			if(clickCountMap.get(info.getId()) != null) {
				subR.setClickCounting(clickCountMap.get(info.getId()).getCounting());
			} else {
				subR.setClickCounting(0L);
			}
			if(introductionMap.get(info.getId()) != null) {
				introPath = introductionMap.get(info.getId()).getIntroPath();
				subR.setIntroduction(fileUtil.getStringFromFile(introPath));
			}
			subR.setId(info.getId());
			subR.setCnTitle(info.getCnTitle());
			list.add(subR);
		}
		
		r.setMovieInfoList(list);
		
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
		movImgExample.createCriteria().andMovieIdEqualTo(dto.getMovieId());
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

	@Override
	public FindMovieRecommendResult findMovieRecommend() {
		FindMovieRecommendResult r = new FindMovieRecommendResult();
		Integer recommendNormalSize = 20;

		MovieInfoExample infoExample = new MovieInfoExample();
		infoExample.createCriteria().andCreateTimeGreaterThanOrEqualTo(LocalDateTime.now().minusWeeks(2));
		List<MovieInfo> infos = infoMapper.selectByExample(infoExample);
		if(infos == null || infos.size() < 1) {
			r.setIsSuccess();
			return r;
		}
		
		List<Long> sourceMovieIdList = infos.stream().map(MovieInfo::getId).collect(Collectors.toList());
		Map<Long, MovieInfo> movieInfoMap = infos.stream().collect(Collectors.toMap(MovieInfo::getId, Function.identity()));
		
		FindLastHotClickDTO dto = new FindLastHotClickDTO();
		dto.setMovieIdList(sourceMovieIdList);
		dto.setLimit(recommendNormalSize.longValue());
		List<MovieClickCount> clickList = movieClickCountMapper.findLastHotClick(dto);
		Map<Long, Long> movieIdMapClickCount = clickList.stream().collect(Collectors.toMap(MovieClickCount::getMovieId, MovieClickCount::getCounting));

		// maybe not every movie had at least one click
		int index = sourceMovieIdList.size() - 1;
		List<Long> finalMovieIdList = clickList.stream().map(MovieClickCount::getMovieId).collect(Collectors.toList());
		while(movieIdMapClickCount.size() < recommendNormalSize && movieIdMapClickCount.size() < sourceMovieIdList.size() && index > 0) {
			movieIdMapClickCount.put(sourceMovieIdList.get(index), 0L);
			finalMovieIdList.add(sourceMovieIdList.get(index));
			index--;
		}
		
		Map<Long, ImageStore> movieIdMapImage = matchInfoAndPosterImage(finalMovieIdList);
		
		MovieRecommendVO v = null;
		MovieInfo tmpMovieInfo = null;
		ImageStore tmpImg = null;
		List<MovieRecommendVO> voList = new ArrayList<MovieRecommendVO>();
		for(Long i : finalMovieIdList) {
			v = new MovieRecommendVO();
			v.setMovieId(i);
			v.setClickCount(movieIdMapClickCount.get(i));
			tmpMovieInfo = movieInfoMap.get(i);
			v.setCreateTime(tmpMovieInfo.getCreateTime());
			v.setMovieTitle(tmpMovieInfo.getCnTitle());
			v.setReleaseTime(tmpMovieInfo.getReleaseTime());
			tmpImg = movieIdMapImage.get(i);
			if(tmpImg != null) {
				v.setImgUrl(tmpImg.getImagePath());
			}
			voList.add(v);
			tmpImg = null;
		}
		
		r.setVoList(voList);
		r.setIsSuccess();
		
		return r;
	}
	
	private Map<Long, ImageStore> matchInfoAndPosterImage(List<Long> movieIdList) {
		FindPosterIdByMovieIdListDTO dto = new FindPosterIdByMovieIdListDTO();
		dto.setMovieIdList(movieIdList);
		List<MovieImage> moviePosterImage = movieImageMapper.findPosterIdByMovieIdList(dto);
		
		Map<Long, Long> movieIdMapImageId = moviePosterImage.stream().collect(Collectors.toMap(MovieImage::getMovieId, MovieImage::getImageId));
		List<Long> imgIdList = moviePosterImage.stream().map(MovieImage::getImageId).collect(Collectors.toList());
		
		ImageStoreExample imageStoreExample = new ImageStoreExample();
		imageStoreExample.createCriteria().andIsDeleteEqualTo(false).andIdIn(imgIdList);
		List<ImageStore> imgPOList = imageStoreMapper.selectByExample(imageStoreExample);
		Map<Long, ImageStore> imgPOMap = imgPOList.stream().collect(Collectors.toMap(ImageStore::getId, Function.identity()));
		
		Map<Long, ImageStore> movieIdMapImage = new HashMap<Long, ImageStore>();
		Long tmpImgId = null;
		for(Entry<Long, Long> movieIdMapImgIdEntry : movieIdMapImageId.entrySet()) {
			tmpImgId = movieIdMapImgIdEntry.getValue();
			movieIdMapImage.put(movieIdMapImgIdEntry.getKey(), imgPOMap.get(tmpImgId));
		}
		
		return movieIdMapImage;
	}
}

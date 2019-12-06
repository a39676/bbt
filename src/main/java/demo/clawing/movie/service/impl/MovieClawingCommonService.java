package demo.clawing.movie.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.bittorrent.TorrentFile;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import demo.clawing.movie.mapper.MovieImageMapper;
import demo.clawing.movie.mapper.MovieMagnetUrlMapper;
import demo.clawing.movie.pojo.constant.MovieClawingConstant;
import demo.clawing.movie.pojo.po.MovieImage;
import demo.clawing.movie.pojo.po.MovieMagnetUrl;
import demo.clawing.movie.service.DoubanClawingService;
import demo.clawing.movie.service.MovieClawingOptionService;
import demo.interaction.image.mapper.ImageStoreMapper;
import demo.interaction.image.pojo.po.ImageStore;
import demo.interaction.image.pojo.type.ImageType;
import demo.selenium.service.impl.SeleniumCommonService;
import movie.pojo.type.MovieRegionType;

public abstract class MovieClawingCommonService extends SeleniumCommonService {

	protected final String mainSavePath = "/home/u2/movieClawing";
	protected final String introductionSavePath = mainSavePath + "/introduction";
	
	@Autowired
	protected ImageStoreMapper imageStoreMapper;
	@Autowired
	protected MovieImageMapper movieImageMapper;
	@Autowired
	private MovieMagnetUrlMapper magnetUrlMapper;
	@Autowired
	protected MovieClawingOptionService optionService;
	@Autowired
	protected DoubanClawingService doubanService;
	
	
	protected String getMangetUrlFromTorrent(String path) {
		File t = new File(path);
		if(!t.exists()) {
			return null;
		}
		try {
			TorrentFile f = new TorrentFile(t);
			return MovieClawingConstant.magnetPrefix + f.getHexHash();
		} catch (IllegalArgumentException | IOException e) {
			log.error("read torrent error path {}", path);
			return null;
		}
	}
	
	protected void saveMovieMagnetUrl(List<String> magnetUrls, Long movieId) {
		for(String url : magnetUrls) {
			saveMovieMagnetUrl(url, movieId);
		}
	}
	
	protected void saveMovieMagnetUrl(String magnetUrl, Long movieId) {
		Long newMovieMagnetUrlId = snowFlake.getNextId();
		MovieMagnetUrl po = new MovieMagnetUrl();
		po.setId(newMovieMagnetUrlId);
		po.setMovieId(movieId);
		po.setUrl(magnetUrl);
		magnetUrlMapper.insertSelective(po);
	}
	
	protected void saveMovieImg(List<WebElement> imgs, Long movieId) {
		String src = null;
		WebElement img = null;
		for (int i = 0; i < imgs.size(); i++) {
			img = imgs.get(i);
			src = img.getAttribute("src");
			Dimension s = img.getSize();
			if (s.height > 200 && s.width > 200) {
				Long newImgId = snowFlake.getNextId();
				ImageStore po = new ImageStore();
				po.setId(newImgId);
				po.setImagePath(src);
				po.setImageType(ImageType.moviePoster.getCode().byteValue());
				imageStoreMapper.insertSelective(po);

				MovieImage imgPO = new MovieImage();
				if(i == 0) {
					imgPO.setIsPoster(true);
				}
				imgPO.setCreateTime(LocalDateTime.now());
				imgPO.setMovieId(movieId);
				imgPO.setImageId(newImgId);
				movieImageMapper.insertSelective(imgPO);
			}
		}
	}
	
	protected Integer detectMovieRegion(String countryDesc) {
		if(countryDesc == null) {
			return MovieRegionType.otherMovie.getCode();
			
		} else if (StringUtils.containsAny(countryDesc, "美", "英", "欧", "俄", "法", "加", "意", "德")) {
			return MovieRegionType.eurAndAmerica.getCode();
			
		} else if (StringUtils.containsAny(countryDesc, "日", "韩")) {
			return MovieRegionType.jpAndKr.getCode();
			
		} else if (StringUtils.containsAny(countryDesc, "中")) {
			return MovieRegionType.domestic.getCode();
			
		} else if (StringUtils.containsAny(countryDesc, "香", "港", "台", "澳门")) {
			return MovieRegionType.hongKongMarcoTaiwan.getCode();
			
		} 
		
		return MovieRegionType.otherMovie.getCode();
	}

	protected String getIntroductionSavePath() {
		if(isWindows()) {
			return pathChangeByDetectOS("d:/" + introductionSavePath);
		} else {
			return pathChangeByDetectOS(introductionSavePath);
		}
	}
}

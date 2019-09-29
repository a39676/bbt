package demo.movie.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.bittorrent.TorrentFile;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import demo.baseCommon.service.CommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.type.ImageType;
import demo.movie.mapper.MovieImageMapper;
import demo.movie.pojo.po.MovieImage;
import movie.pojo.type.MovieRegionType;

public abstract class MovieClawingCommonService extends CommonService {

	protected final String mainSavePath = "/home/u2/movieClawing";
	protected final String introductionSavePath = mainSavePath + "/introduction";
	
	@Autowired
	protected ImageStoreMapper imageStoreMapper;
	@Autowired
	protected MovieImageMapper movieImageMapper;
	
	public String getMangetUrlFromTorrent(String path) {
		File t = new File(path);
		if(!t.exists()) {
			return null;
		}
		try {
			TorrentFile f = new TorrentFile(t);
			return "magnet:?xt=urn:btih:" + f.getHexHash();
		} catch (IllegalArgumentException | IOException e) {
			log.error("read torrent error path {}", path);
			return null;
		}
	}
	
	protected void saveMovieImg(List<WebElement> imgs, Long movieId) {
		String src = null;
		for (WebElement i : imgs) {
			src = i.getAttribute("src");
			Dimension s = i.getSize();
			if (s.height > 200 && s.width > 200) {
				Long newImgId = snowFlake.getNextId();
				ImageStore po = new ImageStore();
				po.setId(newImgId);
				po.setImagePath(src);
				po.setImageType(ImageType.moviePoster.getCode().byteValue());
				imageStoreMapper.insertSelective(po);

				MovieImage record = new MovieImage();
				record.setMovidId(movieId);
				record.setImageId(newImgId);
				movieImageMapper.insertSelective(record);
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
			
		} else if (StringUtils.containsAny(countryDesc, "中", "大陆", "香", "台")) {
			return MovieRegionType.domestic.getCode();
		} 
		
		return MovieRegionType.otherMovie.getCode();
	}
}

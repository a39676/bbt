package demo.movie.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dateTimeHandle.DateTimeUtilCommon;
import demo.image.mapper.ImageStoreMapper;
import demo.image.pojo.po.ImageStoreExample;
import demo.movie.mapper.MovieClickCountMapper;
import demo.movie.mapper.MovieImageMapper;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieMagnetUrlMapper;
import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.constant.MovieInteractionConstant;
import demo.movie.pojo.po.MovieClickCountExample;
import demo.movie.pojo.po.MovieImage;
import demo.movie.pojo.po.MovieImageExample;
import demo.movie.pojo.po.MovieInfoExample;
import demo.movie.pojo.po.MovieIntroductionExample;
import demo.movie.pojo.po.MovieMagnetUrlExample;
import demo.movie.pojo.po.MovieRecordExample;
import demo.movie.service.MovieManagerService;

@Service
public class MovieManagerServiceImpl extends MovieClawingCommonService implements MovieManagerService {

	@Autowired
	private ImageStoreMapper imgStoreMapper;
	
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
	public void deleteOldHistory() throws IOException {
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
		List<MovieImage> movImgList = movieImgMapper.selectByExample(movImgE);
		List<Long> imgIdList = movImgList.stream().map(MovieImage::getImageId).collect(Collectors.toList());
		ImageStoreExample imgE = new ImageStoreExample();
		imgE.createCriteria().andIdIn(imgIdList);
		imgStoreMapper.deleteByExample(imgE);
		movieImgMapper.deleteByExample(movImgE);
		
		MovieMagnetUrlExample magnetUrlE = new MovieMagnetUrlExample();
		magnetUrlE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		magnetUrlMapper.deleteByExample(magnetUrlE);
		
		MovieIntroductionExample introE = new MovieIntroductionExample();
		introE.createCriteria().andCreateTimeLessThanOrEqualTo(oldHistoryLimit);
		introductionMapper.deleteByExample(introE);
		String introSavePath = getIntroductionSavePath();
		File introSaveDir = new File(introSavePath);
		File[] introFiles = introSaveDir.listFiles();
		Path file = null;
		BasicFileAttributes attr = null;
		Date createDate = null;
		LocalDateTime createDateTime = null;
		for(File f : introFiles) {
			file = f.toPath();
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			createDate = new Date(attr.creationTime().toMillis());
			createDateTime = DateTimeUtilCommon.dateToLocalDateTime(createDate);
			if(createDateTime.isBefore(oldHistoryLimit)) {
				f.delete();
			}
		}
		
	}
}

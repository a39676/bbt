package demo.clawing.movie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.clawing.movie.mapper.MovieCrewMapper;
import demo.clawing.movie.pojo.dto.MovieCrewfindByConditionDTO;
import demo.clawing.movie.pojo.po.MovieCrew;
import demo.clawing.movie.service.MovieCrewService;

@Service
public final class MovieCrewServiceImpl extends MovieClawingCommonService implements MovieCrewService {

//	@Autowired
//	private MovieAssociationCrewMapper movieAndCrewMapper;
	@Autowired
	private MovieCrewMapper crewMapper;
	
	public void findCrewByNameOrInsert(MovieCrewfindByConditionDTO dto) {
		/*
		 * TODO
		 * create result pojo
		 */
		
		List<MovieCrew> crewList = crewMapper.findByCondition(dto);
		
		if(crewList == null || crewList.size() < 1) {
			/*
			 * TODO
			 */
			
		}
	}
	
	public MovieCrew addNewCrew(MovieCrewfindByConditionDTO dto) {
		Long newCrewId = snowFlake.getNextId();
		MovieCrew po = new MovieCrew();
		po.setId(newCrewId);
		po.setCrewType(dto.getCrewType());
		po.setCnName(dto.getCnName());
		po.setEngName(dto.getEngName());
		crewMapper.insertSelective(po);
		return po;
	}

}
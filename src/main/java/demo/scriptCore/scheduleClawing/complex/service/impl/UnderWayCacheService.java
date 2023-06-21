package demo.scriptCore.scheduleClawing.complex.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import demo.scriptCore.scheduleClawing.complex.pojo.dto.UnderWayCourseDoneRequestDTO;

@Scope("singleton")
@Component
public class UnderWayCacheService {

	private List<UnderWayCourseDoneRequestDTO> cacheRequestList = new ArrayList<>();

	public List<UnderWayCourseDoneRequestDTO> getCacheRequestList() {
		return cacheRequestList;
	}

	public void setCacheRequestList(List<UnderWayCourseDoneRequestDTO> cacheRequestList) {
		this.cacheRequestList = cacheRequestList;
	}

}

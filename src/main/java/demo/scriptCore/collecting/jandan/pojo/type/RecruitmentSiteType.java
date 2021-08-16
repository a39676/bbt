package demo.scriptCore.collecting.jandan.pojo.type;

public enum RecruitmentSiteType {

	/** wuYiJob */
	wuYiJob(1L, "wuYiJob"),
	
	/** boss */
	boss(2L, "boss"),
	
	/** laGou */
	laGou(3L, "laGou"),
	
	/** zhiLian */
	zhiLian(4L, "zhiLian"),
	
	;

	private Long id;
	private String name;

	RecruitmentSiteType(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getCode() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static RecruitmentSiteType getType(String name) {
		for(RecruitmentSiteType t : RecruitmentSiteType.values()) {
			if(t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
	public static RecruitmentSiteType getType(Long code) {
		for(RecruitmentSiteType t : RecruitmentSiteType.values()) {
			if(t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}

}

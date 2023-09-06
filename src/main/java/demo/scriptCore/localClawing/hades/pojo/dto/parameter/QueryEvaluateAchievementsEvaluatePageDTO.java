package demo.scriptCore.localClawing.hades.pojo.dto.parameter;

import java.util.List;

/**
 * 绩效管理_评分管理_分页查询
 */
public class QueryEvaluateAchievementsEvaluatePageDTO {

//	{"page":1,"pageSize":10,"achievementCode":"num","categoryCode":"100701","achievementName":"name",
//	"supplierCode":"10053112","sponsor":"requester",
//	"createTime":["2023-09-04","2023-10-19"],"createStartTime":"2023-09-04",
//	"createEndTime":"2023-10-19","size":10}

	private Integer page = 1;
	private Integer pagesize = 10;
	private Integer size = 10;
	/** 考评单号 */
	private String achievementCode;
	/** 品类 */
	private String categoryCode;
	/** 考评表名称 */
	private String achievementName;
	/** 供应商 */
	private String supplierCode;
	/** 发起人 */
	private String sponsor;
	private String createStartTime;
	private String createEndTime;
	private List<String> createTime;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getAchievementCode() {
		return achievementCode;
	}

	public void setAchievementCode(String achievementCode) {
		this.achievementCode = achievementCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getAchievementName() {
		return achievementName;
	}

	public void setAchievementName(String achievementName) {
		this.achievementName = achievementName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public List<String> getCreateTime() {
		return createTime;
	}

	public void setCreateTime(List<String> createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "QueryEvaluateAchievementsEvaluatePageDTO [page=" + page + ", pagesize=" + pagesize + ", size=" + size
				+ ", achievementCode=" + achievementCode + ", categoryCode=" + categoryCode + ", achievementName="
				+ achievementName + ", supplierCode=" + supplierCode + ", sponsor=" + sponsor + ", createStartTime="
				+ createStartTime + ", createEndTime=" + createEndTime + ", createTime=" + createTime + "]";
	}

}

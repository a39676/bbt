package demo.test.pojo.param;

import java.util.Date;

public class TestParam {

	private Long userId;
	private Long articleTagId;
	private Date createTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getArticleTagId() {
		return articleTagId;
	}

	public void setArticleTagId(Long articleTagId) {
		this.articleTagId = articleTagId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
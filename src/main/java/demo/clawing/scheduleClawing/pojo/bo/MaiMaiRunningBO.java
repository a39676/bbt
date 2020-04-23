package demo.clawing.scheduleClawing.pojo.bo;

public class MaiMaiRunningBO {

	/** 分享 和 点赞 的 span 的 class 属性, 会动态变化, 并因此需要动态获取 */
	private String shareAndLikeSpanClass;

	private int clickLikeCount = 0;

	public int getClickLikeCount() {
		return clickLikeCount;
	}

	public void setClickLikeCount(int clickLikeCount) {
		this.clickLikeCount = clickLikeCount;
	}

	public String getShareAndLikeSpanClass() {
		return shareAndLikeSpanClass;
	}

	public void setShareAndLikeSpanClass(String shareAndLikeSpanClass) {
		this.shareAndLikeSpanClass = shareAndLikeSpanClass;
	}

	@Override
	public String toString() {
		return "MaiMaiRunningBO [shareAndLikeSpanClass=" + shareAndLikeSpanClass + ", clickLikeCount=" + clickLikeCount
				+ "]";
	}

}

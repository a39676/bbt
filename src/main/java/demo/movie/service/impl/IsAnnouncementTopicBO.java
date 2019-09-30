package demo.movie.service.impl;

public class IsAnnouncementTopicBO {

	private boolean isAnnouncement = false;
	private String readLinkUrl;

	public boolean getIsAnnouncement() {
		return isAnnouncement;
	}

	public void setAnnouncement(boolean isAnnouncement) {
		this.isAnnouncement = isAnnouncement;
	}

	public String getReadLinkUrl() {
		return readLinkUrl;
	}

	public void setReadLinkUrl(String readLinkUrl) {
		this.readLinkUrl = readLinkUrl;
	}

	@Override
	public String toString() {
		return "IsAnnouncementTopicBO [isAnnouncement=" + isAnnouncement + ", readLinkUrl=" + readLinkUrl + "]";
	}

}

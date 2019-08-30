package demo.selenium.pojo.dto;

import java.io.File;

public class ScreenshotSaveDTO {

	private String eventName;
	private Long eventId;
	private File screenShotFile;
	private String fileName;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public File getScreenShotFile() {
		return screenShotFile;
	}

	public void setScreenShotFile(File screenShotFile) {
		this.screenShotFile = screenShotFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "SavingScreenShotDTO [eventName=" + eventName + ", eventId=" + eventId + ", screenShotFile="
				+ screenShotFile + ", fileName=" + fileName + "]";
	}

}

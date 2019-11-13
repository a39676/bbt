package demo.selenium.service;

import java.io.IOException;

public interface OldDataDeleteService {

	/**
	 * 定期任务调用
	 * 删除超长期限的资源(物理删除)
	 * @throws IOException
	 */
	void deleteOldDownload() throws IOException;
	void deleteOldScreenshot() throws IOException;
	void deleteOldCaptchaImg() throws IOException;

}

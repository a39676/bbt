package demo.tool.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface DownloadService {

	void downloadFile(HttpServletResponse response, String filePath) throws IOException;

}

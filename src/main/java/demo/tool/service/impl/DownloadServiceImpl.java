package demo.tool.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import demo.tool.service.DownloadService;
import ioHandle.FileUtilCustom;

@Service
public class DownloadServiceImpl implements DownloadService {

	@Override
	public void downloadFile(HttpServletResponse response, String filePath) throws IOException {
		File inputFile = new File(filePath);
		
		if(!inputFile.exists()) {
			// TODO
			return;
		}
		File outputZip = null;
		if(inputFile.isFile()) {
			outputZip = new File(inputFile.getAbsolutePath().replaceAll("\\.\\w{1,4}$", ".zip"));
		} else {
			outputZip = new File(inputFile.getAbsolutePath() + ".zip");
		}
		
		FileUtilCustom fileUtil = new FileUtilCustom();
		File targetFile = new File(filePath);
		if(targetFile.isFile()) {
			fileUtil.fileToZip(outputZip.getAbsolutePath(), filePath);
		} else {
			fileUtil.folderToZip(outputZip.getAbsolutePath(), filePath);
		}
		
		String mimeType= URLConnection.guessContentTypeFromName(outputZip.getName());
        if(mimeType==null){
            mimeType = "application/octet-stream";
        }
         
        response.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + outputZip.getName() +"\""));
 
         
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
        response.setContentLength((int)outputZip.length());
 
        InputStream inputStream;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(outputZip));
			//Copy bytes from source to destination(outputstream in this example), closes both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		outputZip.delete();
	}

}

package demo.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.service.CommonService;
import demo.tool.service.ComplexToolService;

@Service
public class ComplexToolServiceImpl extends CommonService implements ComplexToolService {
	
	@Override
	public CommonResult cleanTmpFiles(String targetFolder, String extensionName, LocalDateTime oldestCreateTime) {
		CommonResult r = new CommonResult();

		File folder = new File(targetFolder);
		if(!folder.exists()) {
			return r;
		}
		
		File[] files = folder.listFiles();
		BasicFileAttributes attrs;
		Path p;
		Date fileTime;
		LocalDateTime fileLocalDateTime;
		for(File f : files) {
			if(f.isDirectory()) {
				cleanTmpFiles(f.getAbsolutePath(), extensionName, oldestCreateTime);
			}
			if(f.isFile()) {
				p = f.toPath();
				try {
					attrs = Files.readAttributes(p, BasicFileAttributes.class);
					fileTime = new Date(attrs.creationTime().toMillis());
					fileLocalDateTime = localDateTimeHandler.dateToLocalDateTime(fileTime);
					if(fileLocalDateTime.isBefore(oldestCreateTime)) {
						if(StringUtils.isBlank(extensionName)) {
							f.delete();
						} else {
							if(f.getName().endsWith(extensionName)) {
								f.delete();
							}
						}
					}
				} catch (IOException e) {
				}
			}
		}
		
		r.setIsSuccess();
		return r;
	}
}

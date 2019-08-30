package demo.tool.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.tool.mapper.ToolMapper;
import demo.tool.pojo.constant.ToolPathConstant;
import demo.tool.pojo.constant.ToolUrlConstant;
import demo.tool.service.DownloadService;
import demo.tool.service.impl.ComplexToolServiceImpl;
import ioHandle.FileUtilCustom;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ToolUrlConstant.root)
public class ComplexToolController extends CommonController {

	@Autowired
	private ToolMapper toolMapper;

	@Autowired
	private DownloadService downloadService;
	
	@Autowired
	private ComplexToolServiceImpl complexToolServiceImpl;

	public List<File> getLocalTxt(String inputPath) {
		File mainFolder = new File(inputPath);
		File[] fileArray = mainFolder.listFiles();
		List<File> fileList = new ArrayList<File>();
		for (File subFile : fileArray) {
			if (subFile.isFile() && subFile.getName().endsWith("txt")) {
				fileList.add(subFile);
			}
		}
		return fileList;
	}

	private List<List<String>> getTxtInfo(File file) {

		List<List<String>> resultList = new ArrayList<List<String>>();
		FileUtilCustom fuc = new FileUtilCustom();

		try {
			String fileInfo = new String(fuc.getByteFromFile(file.getAbsolutePath()), "utf8");
			List<String> fileLines = Arrays.asList(fileInfo.split("\n"));
			List<String> currentLine = null;

			for (int i = 0; i < fileLines.size(); i++) {
				currentLine = Arrays.asList(fileLines.get(i).split("\t"));
				resultList.add(currentLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@GetMapping(value = "/databaseImportFromTxt")
	public void databaseImportFromTxt(@RequestParam String inputPath) {
		String filePerfixname = null;
		List<File> fileList = getLocalTxt(inputPath);
		List<List<String>> txtInfo = null;
		List<String> line = null;

		StringBuffer sb = new StringBuffer();

		for (File file : fileList) {
			sb.setLength(0);
			int lastPoint = file.getName().lastIndexOf(".");
			filePerfixname = file.getName().substring(0, lastPoint);
			txtInfo = getTxtInfo(file);

			for (int lineCount = 0; lineCount < txtInfo.size(); lineCount++) {
				sb.append("(");
				line = txtInfo.get(lineCount);
				for (int columnIndex = 0; columnIndex < line.size(); columnIndex++) {
					if (line.get(columnIndex) != null && !"null".equals(line.get(columnIndex))) {
						sb.append(String.valueOf("'" + line.get(columnIndex)) + "'");
					} else {
						sb.append("null");
					}
					if (columnIndex < line.size() - 1) {
						sb.append(",");
					}
				}
				sb.append(")");
				if (lineCount < txtInfo.size() - 1) {
					sb.append(",");
				}
			}
			// System.out.println(sb.toString());
			toolMapper.complexInsert(filePerfixname, sb.toString());
		}
	}

	
	@PostMapping(value = "/cleanTmpFiles")
	public void cleanTmpFiles(@RequestBody String data, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		
		// TODO 已经有几个临时文件夹,请参照 ToolPathConstant
		JSONObject jsonOutput = JSONObject.fromObject(complexToolServiceImpl.cleanTmpFiles(jsonInput));
		try {
			response.getWriter().print(jsonOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping(value = "/getTomcatOut")
	public void getTomcatOut(HttpServletResponse response) throws IOException {
		downloadService.downloadFile(response, ToolPathConstant.getTomcatOutPath());
	}
	
	@GetMapping(value = "/systemOption")
	public ModelAndView systemOption() {
		return new ModelAndView("toolJSP/systemOption");
	}
}

package demo.toyParts.dfsw.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.tool.service.TextFilter;
import demo.toyParts.dfsw.pojo.dto.BuildSVNUpdateCommondLineDTO;
import demo.toyParts.dfsw.pojo.result.BuildSVNUpdateCommondLineResult;
import demo.toyParts.dfsw.service.ForDfswServcie;

@Service
public class ForDfswServiceImpl extends CommonService implements ForDfswServcie {
	
	@Autowired
	protected TextFilter textFilter;
	
	@Override
	public BuildSVNUpdateCommondLineResult buildSVNUpdateCommondLine(BuildSVNUpdateCommondLineDTO dto) {
		BuildSVNUpdateCommondLineResult r = new BuildSVNUpdateCommondLineResult();
		
		if(StringUtils.isBlank(dto.getLocalPathPrefix())) {
			r.failWithMessage("请输入本地存放目录, 如 \"D:\\work/合同系统\\03实现/001程序代码\"");
			return r;
		}
		
		if(StringUtils.isBlank(dto.getInput())) {
			r.failWithMessage("请输入 bugFree 上说明需要替换的文件路径, 如\"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/ApplicationServices/impl/OpfOrgService.cs\", 可多行复制");
			return r;
		}
		
		PolicyFactory filter = textFilter.getFilter();
		dto.setInput(filter.sanitize(dto.getInput()));
		dto.setLocalPathPrefix(filter.sanitize(dto.getLocalPathPrefix()));
		
		dto.setInput(dto.getInput().replaceAll("\\\\", "/"));
		dto.setLocalPathPrefix(dto.getLocalPathPrefix().replaceAll("\\\\", "/"));
		
		String lines = dto.getInput();
		String localPathPrefix = dto.getLocalPathPrefix();

		List<String> resultLines = batchReplace(localPathPrefix, lines);
		StringBuffer sb = new StringBuffer();
		for(String l : resultLines) {
			sb.append(l + System.lineSeparator());
		}
		
		r.setOutput(sb.toString());
		r.setIsSuccess();
		return r;
	}
	
	private List<String> batchReplace(String localPathPrefix, String lines) {
		String[] sourceLineArray = lines.split("Modified");
		List<String> resultLines = new ArrayList<String>();
		String resultLine = null;
		for (String sourceLine : sourceLineArray) {
			resultLine = replace(localPathPrefix, sourceLine);
			resultLines.add(resultLine);
		}
		return resultLines;
	}
	
	private String replace(String localPath, String bugFreePath) {
		List<String> localPathSplit = Arrays.asList(localPath.split("/"));
		List<String> bugFreeSplit = Arrays.asList(bugFreePath.split("/"));
		
		int bugFreeTargetIndex = -1;
		int localPathIndex = -1;
		String targetWord = null;
		for(int i = 0; i < localPathSplit.size() && bugFreeTargetIndex < 0; i++) {
			targetWord = localPathSplit.get(i);
			if(bugFreeSplit.contains(targetWord)) {
				bugFreeTargetIndex = bugFreeSplit.indexOf(targetWord);
				localPathIndex = i;
			}
		}
		
		if(bugFreeTargetIndex < 0) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer("svn update ");
		for(int i = 0; i < localPathIndex; i++) {
			sb.append(localPathSplit.get(i) + File.separator);
		}
		
		for(int i = bugFreeTargetIndex; i < bugFreeSplit.size(); i++) {
			sb.append(bugFreeSplit.get(i) + File.separator);
		}
		
		String result = sb.substring(0, sb.length() - 1);
		
		return result;
	}
	
//	public static void main(String[] args) {
//		ForDfswServiceImpl s = new ForDfswServiceImpl();
//		
//		BuildSVNUpdateCommondLineDTO dto = new BuildSVNUpdateCommondLineDTO();
//		dto.setInput("Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/ApplicationServices/impl/OpfOrgService.cs\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/Company.cs\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/Department.cs\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/Employee.cs\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/PosiEmployee.cs\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/Company.hbm.xml\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/Department.hbm.xml\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/Employee.hbm.xml\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/ViewOrganize.hbm.xml\r\n" + 
//				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF.IAPIs/Services/IOpfOrgService.cs");
//		dto.setLocalPathPrefix("D:\\work\\合同系统\\03实现\\001程序代码");
//		BuildSVNUpdateCommondLineResult result = s.buildSVNUpdateCommondLine(dto);
//		System.out.println(result.getOutput());
//	}
}

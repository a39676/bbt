package demo.toyParts.dfsw.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.toyParts.dfsw.pojo.dto.BuildSVNUpdateCommondLineDTO;
import demo.toyParts.dfsw.pojo.result.BuildSVNUpdateCommondLineResult;
import demo.toyParts.dfsw.service.ForDfswServcie;

@Service
public class ForDfswServiceImpl extends CommonService implements ForDfswServcie {
	
	/*
	 * TODO
	 * 不过应该没机会再部署给东方思维了
	 */
//	@Autowired
//	protected TextFilter textFilter;
	
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
		
		/*
		 * TODO
		 * 不过应该没机会再部署给东方思维了
		 */
//		PolicyFactory filter = textFilter.getFilter();
//		dto.setInput(filter.sanitize(dto.getInput()));
//		dto.setLocalPathPrefix(filter.sanitize(dto.getLocalPathPrefix()));
		
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
	
	public static void main(String[] args) {
		ForDfswServiceImpl s = new ForDfswServiceImpl();
		
		BuildSVNUpdateCommondLineDTO dto = new BuildSVNUpdateCommondLineDTO();
		dto.setInput(""
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Base/SignedObjectCreditLimit/SignedObjectSelect.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/Contract/ChangesVo.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/Contract/EndVo.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/Contract/SettlementVo.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/ChangesController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/EndController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/SettlementController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Views/Shared/SubFlowApprove.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.ApplicationServices/SysManage/GenCodeService.cs\r\n" + 
				""
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Home/WarningList.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.ApplicationServices/Contract/ReceivePayInfo/PaymentApplyService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.ApplicationServices/SysManage/ParamValueService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/Services/Contract/IPaymentApplyService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/Services/SysManage/IParamValueService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/Contract/ReceivePayInfo/PaymentApplyVo.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Repository/Contract/IPaymentApplyDao.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Repository/Contract/Impl/ReceivePayInfo/PaymentApplyDao.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Repository/SysManage/IParamValueDao.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Repository/SysManage/Impl/ParamValueDao.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/PaymentApplyController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/ReceivePayInfo/PaymentApplyEdit.cshtml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Shared/_PaymentApplyDetail.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.ApplicationServices/Contract/ReceivePayInfo/PaymentApplyService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/Services/Contract/IPaymentApplyService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Repository/Contract/IPaymentApplyDao.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Repository/Contract/Impl/ReceivePayInfo/PaymentApplyDao.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/PaymentApplyController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/ReceivePayInfo/PaymentApplyEdit.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/ReceivePayInfo/PaymentApplyEdit.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/ReceivePayInfo/PaymentApplyEdit.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/ReceivePayInfo/PaymentApplyEdit.cshtml\r\n" + 
				" Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Shared/_PaymentApplyDetail.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Base/SignedObject/Edit.cshtml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Base/SignedObject/SignedObjectExInfoEdit.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Base/SignedObject/Edit.cshtml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Base/SignedObject/SignedObjectExInfoEdit.cshtml"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/ApplicationServices/impl/OpfOrgService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/Company.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/Department.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/Employee.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/PosiEmployee.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/Company.hbm.xml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/Department.hbm.xml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/Employee.hbm.xml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/Repository/impl/Mapping/ViewOrganize.hbm.xml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF.IAPIs/Services/IOpfOrgService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.WebSite/Config/Wf-Extend.xml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Workflow4/OT.Workflow.IAPIs/IWorkflowConfigureService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Workflow4/OT.Workflow3.APIs/ApplicationServices/WorkflowAnalysisService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Workflow4/OT.Workflow3.APIs/ApplicationServices/WorkflowConfigureService.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Workflow4/OT.Workflow3.APIs/ApplicationServices/WorkflowExecutionService.cs"
				+ "Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/Contract/ContractVo.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/Contract/ReceivePayInfo/PaymentApplyVo.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/ChangesController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/SettlementController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Reports/ContractChange.rdlc\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Reports/ContractPayment.rdlc\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/BalanceModels.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/ContractChange.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Reports/ContractBalance.rdlc\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/Cancel/Details.cshtml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Repository/Contract/Impl/CancelDao.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.IAPIs/ViewModels/ContractEnd.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.Contract/OT.CNT.Web/Contract/EndController.cs\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Reports/ContractEnd.rdlc\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Reports/ContractSign.rdlc\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/End/Details.cshtml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Contract/End/EndView.cshtml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Views/Shared/_Cancling.cshtml\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Images/yizuofei.png\r\n" + 
				"Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/OT.CNT/OT.WebSite/Areas/CNT/Reports/ContractPayment_IsNotCnt.rdlc"
				+ "");
		
		dto.setLocalPathPrefix("D:\\work\\合同系统\\03实现\\001程序代码");
		BuildSVNUpdateCommondLineResult result = s.buildSVNUpdateCommondLine(dto);
		System.out.println(result.getOutput());
	}
}

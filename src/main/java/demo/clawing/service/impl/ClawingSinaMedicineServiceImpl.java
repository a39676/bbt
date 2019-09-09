package demo.clawing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.clawing.mapper.MedicineInfoMapper;
import demo.clawing.pojo.po.MedicineInfo;
import demo.clawing.pojo.result.SinaMedicineDetailHeadHandleResult;
import demo.clawing.pojo.result.SinaMedicineDetailMainHandleResult;
import demo.clawing.service.ClawingSinaMedicineFactoryService;
import demo.clawing.service.ClawingSinaMedicineService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

@Service
public class ClawingSinaMedicineServiceImpl extends CommonService implements ClawingSinaMedicineService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private JavaScriptCommonUtil jsUtil;
	
	@Autowired
	private ClawingSinaMedicineFactoryService factoryService;
	@Autowired
	private MedicineInfoMapper medicineMapper;
	
	public void sinaMedicineClawing() {

	}

	@Override
	public void medicineTest() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("clawingMedicineTest");
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			String url = "https://med.sina.com/drug/leeDetail_A_15036.html";
			d.get(url);

			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "class", "xx2");
			By medicineDetailBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement medicineDetail = d.findElement(medicineDetailBy);
			System.out.println(medicineDetail.getText());

			auxTool.takeScreenshot(d, te);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}

	public void sinaMedicineDetailHandle(WebDriver d, TestEvent te) {
		/*
		 * TODO
		 */
		try {
			auxTool.takeScreenshot(d, te);

			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "class", "xx1_text");
			By medicineDetailHeadBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement medicineDetailHead = d.findElement(medicineDetailHeadBy);
			SinaMedicineDetailHeadHandleResult detailHeadResult = sinaMedicineDetailHeadHandle(medicineDetailHead);
			if (!detailHeadResult.isSuccess()) {
				/*
				 * TODO insert new fail record and handle it later
				 */
			}
			Long factoryId = factoryService.findFactoryId(detailHeadResult.getFactoryName());
			
			byXpathConditionBo = ByXpathConditionBO.build("div", "class", "xx2");
			By medicineDetailMainBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement medicineDetailMain = d.findElement(medicineDetailMainBy);
			SinaMedicineDetailMainHandleResult detailMainResult = sinaMedicineDetailMainHandle(medicineDetailMain);
			if (!detailMainResult.isSuccess()) {
				/*
				 * TODO insert new fail record and handle it later
				 */
			}
			
//			byXpathConditionBo = ByXpathConditionBO.build("div", "class", "news");
//			By medicineDetailMainBy = auxTool.byXpathBuilder(byXpathConditionBo);
//			WebElement medicineDetailMain = d.findElement(medicineDetailMainBy);
			
			List<WebElement> tagAList = d.findElements(ByTagName.tagName("a"));
			sinaMedicineTagAListHandle (detailHeadResult, detailMainResult, tagAList);
			
			MedicineInfo po = new MedicineInfo();
			po.setMedicineFactoryId(factoryId);
			
			medicineMapper.insertSelective(po);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}

	private SinaMedicineDetailHeadHandleResult sinaMedicineDetailHeadHandle(WebElement ele) {
		SinaMedicineDetailHeadHandleResult r = new SinaMedicineDetailHeadHandleResult();
		if (ele == null || StringUtils.isBlank(ele.getText())) {
			return r;
		}

		final String commodityNameKeyword = "【商品名】";
		final String commonNameKeyword = "【通用名称】";
		final String medicineManagerCodeKeyword = "【国药准字号】";
		final String factoryNameKeyword = "【生产厂家】";
		final String isMedicineInsuranceKeyword = "【是否医保】";
		final String isPrescriptionKeyword = "【是否非处方】";
		final String isNationalBasicMedicineKeyword = "【是否国家基础药物】";

		List<String> contentLines = Arrays.asList(ele.getText().split("\n"));
		for (String line : contentLines) {
			if (line.contains(commodityNameKeyword)) {
				r.setCommodityName(line.replaceAll(commodityNameKeyword, ""));
			} else if (line.contains(commonNameKeyword)) {
				r.setCommonName(line.replaceAll(commonNameKeyword, ""));
			} else if (line.contains(medicineManagerCodeKeyword)) {
				line = line.replaceAll(medicineManagerCodeKeyword, "");
				r.setMedicineManagerCodePrefix(line.substring(0, 1));
				r.setMedicineManagerCodeNumber(Integer.parseInt(line.replaceAll("[^\\d]", "")));
			} else if (line.contains(factoryNameKeyword)) {
				r.setFactoryName(line.replaceAll(factoryNameKeyword, ""));
			} else if (line.contains(isMedicineInsuranceKeyword)) {
				if (line.contains("是")) {
					r.setIsMedicineInsurance(true);
				} else {
					r.setIsMedicineInsurance(false);
				}
			} else if (line.contains(isPrescriptionKeyword)) {
				if (line.contains("是")) {
					r.setIsPrescription(true);
				} else {
					r.setIsPrescription(false);
				}
			} else if (line.contains(isNationalBasicMedicineKeyword)) {
				if (line.contains("是")) {
					r.setIsNationalBasicMedicine(true);
				} else {
					r.setIsNationalBasicMedicine(false);
				}
			}
		}

		/* 理想情况下 所有字段不可能为空 */
		if (StringUtils.isBlank(r.getCommodityName()) || StringUtils.isBlank(r.getCommonName())
				|| StringUtils.isBlank(r.getMedicineManagerCodePrefix()) || r.getMedicineManagerCodeNumber() == null
				|| StringUtils.isBlank(r.getFactoryName()) || r.getIsMedicineInsurance() == null
				|| r.getIsPrescription() == null || r.getIsNationalBasicMedicine() == null) {
		} else {
			r.setIsSuccess();
		}

		return r;
	}

	private SinaMedicineDetailMainHandleResult sinaMedicineDetailMainHandle(WebElement ele) {
		SinaMedicineDetailMainHandleResult r = new SinaMedicineDetailMainHandleResult();
		if (ele == null || StringUtils.isBlank(ele.getText())) {
			return r;
		}

		final String mainIngredientKeyword = "【主要成分】";
		final String medicineTypeKeyword = "【药品类型】";
		final String medicineSpecificationsKeyword = "【药品规格】";
		final String indicationKeyword = "【功能主治】";
		final String dosageKeyword = "【用法用量】";
		final String adverseReactionsKeyword = "【不良反应】";
		final String precautionssKeyword = "【注意事项】";

		List<String> contentLines = Arrays.asList(ele.getText().split("\n"));
		for (String line : contentLines) {
			if (line.contains(mainIngredientKeyword)) {
				r.setMainIngredient(line.replaceAll(mainIngredientKeyword, ""));
			} else if (line.contains(medicineTypeKeyword)) {
				r.setMedicineType(line.replaceAll(medicineTypeKeyword, ""));
			} else if (line.contains(medicineSpecificationsKeyword)) {
				r.setMedicineSpecifications(line.replaceAll(medicineSpecificationsKeyword, ""));
			} else if (line.contains(indicationKeyword)) {
				r.setIndication(line.replaceAll(indicationKeyword, ""));
			} else if (line.contains(dosageKeyword)) {
				r.setDosage(line.replaceAll(dosageKeyword, ""));
			} else if (line.contains(adverseReactionsKeyword)) {
				r.setAdverseReactions(line.replaceAll(adverseReactionsKeyword, ""));
			} else if (line.contains(precautionssKeyword)) {
				r.setPrecautionss(line.replaceAll(precautionssKeyword, ""));
			}
		}

		/* 理想情况下 所有字段不可能为空 */
		if (r.getMainIngredient() == null || r.getMedicineType() == null || r.getMedicineSpecifications() == null
				|| r.getIndication() == null || r.getDosage() == null || r.getAdverseReactions() == null
				|| r.getPrecautionss() == null) {
		} else {
			r.setIsSuccess();
		}

		return r;
	}

	public void sinaMedicineTagAListHandle(SinaMedicineDetailHeadHandleResult head, SinaMedicineDetailMainHandleResult main, List<WebElement> tagAList) {
		/*
		 * TODO
		 * 脚本难以辨别页面上
		 * 不良反应
		 * 临床资料/疗效分析
		 * 药理毒理
		 * 此三栏目
		 * 
		 * 需要批量获取tagA标签后, 排查
		 */
	}
}

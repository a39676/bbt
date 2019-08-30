package demo.mobile.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.mobile.mapper.MobileNumMapper;
import demo.mobile.pojo.bo.MobileDataBO;
import demo.mobile.pojo.bo.MobileOperatorType;
import demo.mobile.pojo.param.mapperParam.MobileNumBatchInsertParam;
import demo.mobile.pojo.po.MobileNum;
import demo.mobile.service.__MobileNumImportService;
import ioHandle.FileUtilCustom;
import net.sf.json.JSONObject;

/**
 * --仅用于导入数据时使用 
 *
 */
@Service
public class __MobileNumImportServiceImpl extends CommonService implements __MobileNumImportService{

	@Autowired
	private MobileNumMapper mobileNumMapper;
	
	private static HashMap<String, Integer> geoMap;
	
	static {
		geoMap = new HashMap<String, Integer>();
		geoMap.put("七台河", 87);
		geoMap.put("三亚", 241);
		geoMap.put("三明", 135);
		geoMap.put("三沙", 242);
		geoMap.put("三门峡", 188);
		geoMap.put("上海", 9);
		geoMap.put("上饶", 151);
		geoMap.put("东莞", 235);
		geoMap.put("东营", 164);
		geoMap.put("中卫", 332);
		geoMap.put("中山", 236);
		geoMap.put("临汾", 55);
		geoMap.put("临沂", 174);
		geoMap.put("临沧", 274);
		geoMap.put("丹东", 62);
		geoMap.put("丽水", 113);
		geoMap.put("丽江", 272);
		geoMap.put("乌兰察布", 307);
		geoMap.put("乌海", 301);
		geoMap.put("乌鲁木齐", 333);
		geoMap.put("乐山", 252);
		geoMap.put("九江", 150);
		geoMap.put("云南", 25);
		geoMap.put("云浮", 239);
		geoMap.put("亳州", 130);
		geoMap.put("伊春", 86);
		geoMap.put("佛山", 223);
		geoMap.put("佳木斯", 82);
		geoMap.put("保定", 40);
		geoMap.put("保山", 271);
		geoMap.put("信阳", 193);
		geoMap.put("克拉玛依", 334);
		geoMap.put("六安", 127);
		geoMap.put("六盘水", 263);
		geoMap.put("兰州", 285);
		geoMap.put("内江", 251);
		geoMap.put("内蒙古", 5);
		geoMap.put("包头", 300);
		geoMap.put("北京", 1);
		geoMap.put("北海", 312);
		geoMap.put("十堰", 196);
		geoMap.put("南京", 91);
		geoMap.put("南充", 255);
		geoMap.put("南宁", 308);
		geoMap.put("南平", 137);
		geoMap.put("南昌", 149);
		geoMap.put("南通", 96);
		geoMap.put("南阳", 192);
		geoMap.put("厦门", 132);
		geoMap.put("双鸭山", 85);
		geoMap.put("台中", 143);
		geoMap.put("台北", 140);
		geoMap.put("台南", 144);
		geoMap.put("台州", 112);
		geoMap.put("台湾", 34);
		geoMap.put("合肥", 115);
		geoMap.put("吉安", 154);
		geoMap.put("吉林", 7);
		geoMap.put("吉林", 72);
		geoMap.put("吐鲁番", 335);
		geoMap.put("吕梁", 51);
		geoMap.put("吴忠", 330);
		geoMap.put("周口", 190);
		geoMap.put("呼伦贝尔", 305);
		geoMap.put("呼和浩特", 299);
		geoMap.put("咸宁", 204);
		geoMap.put("咸阳", 277);
		geoMap.put("哈密", 336);
		geoMap.put("哈尔滨", 79);
		geoMap.put("唐山", 36);
		geoMap.put("商丘", 189);
		geoMap.put("商洛", 284);
		geoMap.put("嘉义", 148);
		geoMap.put("嘉兴", 109);
		geoMap.put("嘉峪关", 286);
		geoMap.put("四川", 23);
		geoMap.put("四平", 73);
		geoMap.put("固原", 331);
		geoMap.put("基隆", 146);
		geoMap.put("大同", 47);
		geoMap.put("大庆", 83);
		geoMap.put("大连", 58);
		geoMap.put("天水", 289);
		geoMap.put("天津", 2);
		geoMap.put("太原", 46);
		geoMap.put("威海", 169);
		geoMap.put("娄底", 215);
		geoMap.put("孝感", 201);
		geoMap.put("宁夏", 30);
		geoMap.put("宁德", 139);
		geoMap.put("宁波", 105);
		geoMap.put("安庆", 122);
		geoMap.put("安康", 283);
		geoMap.put("安徽", 12);
		geoMap.put("安阳", 181);
		geoMap.put("安顺", 264);
		geoMap.put("定西", 293);
		geoMap.put("宜宾", 254);
		geoMap.put("宜昌", 197);
		geoMap.put("宜春", 153);
		geoMap.put("宝鸡", 276);
		geoMap.put("宣城", 128);
		geoMap.put("宿州", 125);
		geoMap.put("宿迁", 103);
		geoMap.put("山东", 15);
		geoMap.put("山南", 326);
		geoMap.put("山西", 4);
		geoMap.put("岳阳", 211);
		geoMap.put("崇左", 321);
		geoMap.put("巴中", 259);
		geoMap.put("巴彦淖尔", 306);
		geoMap.put("常州", 94);
		geoMap.put("常德", 212);
		geoMap.put("平凉", 295);
		geoMap.put("平顶山", 180);
		geoMap.put("广东", 19);
		geoMap.put("广元", 249);
		geoMap.put("广安", 258);
		geoMap.put("广州", 219);
		geoMap.put("广西", 20);
		geoMap.put("庆阳", 296);
		geoMap.put("廊坊", 44);
		geoMap.put("延安", 280);
		geoMap.put("开封", 178);
		geoMap.put("张家口", 41);
		geoMap.put("张家界", 213);
		geoMap.put("张掖", 291);
		geoMap.put("徐州", 93);
		geoMap.put("德州", 172);
		geoMap.put("德阳", 248);
		geoMap.put("忻州", 49);
		geoMap.put("怀化", 218);
		geoMap.put("惠州", 229);
		geoMap.put("成都", 243);
		geoMap.put("扬州", 100);
		geoMap.put("承德", 42);
		geoMap.put("抚州", 152);
		geoMap.put("抚顺", 60);
		geoMap.put("拉萨", 322);
		geoMap.put("揭阳", 238);
		geoMap.put("攀枝花", 246);
		geoMap.put("新乡", 183);
		geoMap.put("新余", 158);
		geoMap.put("新北", 141);
		geoMap.put("新疆", 31);
		geoMap.put("新竹", 147);
		geoMap.put("无锡", 92);
		geoMap.put("日喀则", 323);
		geoMap.put("日照", 170);
		geoMap.put("昆明", 267);
		geoMap.put("昌都", 324);
		geoMap.put("昭通", 270);
		geoMap.put("晋中", 52);
		geoMap.put("晋城", 54);
		geoMap.put("普洱", 273);
		geoMap.put("景德镇", 156);
		geoMap.put("曲靖", 268);
		geoMap.put("朔州", 48);
		geoMap.put("朝阳", 69);
		geoMap.put("本溪", 61);
		geoMap.put("来宾", 320);
		geoMap.put("杭州", 104);
		geoMap.put("松原", 78);
		geoMap.put("林芝", 325);
		geoMap.put("枣庄", 163);
		geoMap.put("柳州", 309);
		geoMap.put("株洲", 207);
		geoMap.put("桂林", 310);
		geoMap.put("桃园", 142);
		geoMap.put("梅州", 230);
		geoMap.put("梧州", 311);
		geoMap.put("榆林", 281);
		geoMap.put("武威", 292);
		geoMap.put("武汉", 194);
		geoMap.put("毕节", 265);
		geoMap.put("永州", 217);
		geoMap.put("汉中", 282);
		geoMap.put("汕头", 222);
		geoMap.put("汕尾", 231);
		geoMap.put("江苏", 10);
		geoMap.put("江西", 14);
		geoMap.put("江门", 227);
		geoMap.put("池州", 129);
		geoMap.put("沈阳", 57);
		geoMap.put("沧州", 43);
		geoMap.put("河北", 3);
		geoMap.put("河南", 16);
		geoMap.put("河池", 319);
		geoMap.put("河源", 232);
		geoMap.put("泉州", 134);
		geoMap.put("泰安", 168);
		geoMap.put("泰州", 102);
		geoMap.put("泸州", 247);
		geoMap.put("洛阳", 179);
		geoMap.put("济南", 160);
		geoMap.put("济宁", 167);
		geoMap.put("浙江", 11);
		geoMap.put("海东", 298);
		geoMap.put("海南", 21);
		geoMap.put("海口", 240);
		geoMap.put("淄博", 162);
		geoMap.put("淮北", 120);
		geoMap.put("淮南", 118);
		geoMap.put("淮安", 98);
		geoMap.put("深圳", 220);
		geoMap.put("清远", 234);
		geoMap.put("温州", 106);
		geoMap.put("渭南", 279);
		geoMap.put("湖北", 17);
		geoMap.put("湖南", 18);
		geoMap.put("湖州", 108);
		geoMap.put("湘潭", 208);
		geoMap.put("湛江", 225);
		geoMap.put("滁州", 126);
		geoMap.put("滨州", 171);
		geoMap.put("漯河", 187);
		geoMap.put("漳州", 133);
		geoMap.put("潍坊", 166);
		geoMap.put("潮州", 237);
		geoMap.put("澳门", 33);
		geoMap.put("濮阳", 185);
		geoMap.put("烟台", 165);
		geoMap.put("焦作", 184);
		geoMap.put("牡丹江", 81);
		geoMap.put("玉林", 316);
		geoMap.put("玉溪", 269);
		geoMap.put("珠海", 221);
		geoMap.put("甘肃", 28);
		geoMap.put("白城", 77);
		geoMap.put("白山", 76);
		geoMap.put("白银", 288);
		geoMap.put("百色", 317);
		geoMap.put("益阳", 214);
		geoMap.put("盐城", 99);
		geoMap.put("盘锦", 67);
		geoMap.put("眉山", 260);
		geoMap.put("石嘴山", 329);
		geoMap.put("石家庄", 35);
		geoMap.put("福州", 131);
		geoMap.put("福建", 13);
		geoMap.put("秦皇岛", 37);
		geoMap.put("绍兴", 107);
		geoMap.put("绥化", 90);
		geoMap.put("绵阳", 244);
		geoMap.put("聊城", 173);
		geoMap.put("肇庆", 226);
		geoMap.put("自贡", 245);
		geoMap.put("舟山", 114);
		geoMap.put("芜湖", 116);
		geoMap.put("苏州", 95);
		geoMap.put("茂名", 228);
		geoMap.put("荆州", 202);
		geoMap.put("荆门", 200);
		geoMap.put("莆田", 136);
		geoMap.put("莱芜", 176);
		geoMap.put("菏泽", 175);
		geoMap.put("萍乡", 157);
		geoMap.put("营口", 64);
		geoMap.put("葫芦岛", 70);
		geoMap.put("蚌埠", 117);
		geoMap.put("衡水", 45);
		geoMap.put("衡阳", 209);
		geoMap.put("衢州", 111);
		geoMap.put("襄阳", 198);
		geoMap.put("西宁", 297);
		geoMap.put("西安", 275);
		geoMap.put("西藏", 26);
		geoMap.put("许昌", 186);
		geoMap.put("贵州", 24);
		geoMap.put("贵港", 315);
		geoMap.put("贵阳", 261);
		geoMap.put("贺州", 318);
		geoMap.put("资阳", 253);
		geoMap.put("赣州", 155);
		geoMap.put("赤峰", 302);
		geoMap.put("辽宁", 6);
		geoMap.put("辽源", 74);
		geoMap.put("辽阳", 66);
		geoMap.put("达州", 256);
		geoMap.put("运城", 56);
		geoMap.put("连云港", 97);
		geoMap.put("通化", 75);
		geoMap.put("通辽", 303);
		geoMap.put("遂宁", 250);
		geoMap.put("遵义", 262);
		geoMap.put("邢台", 39);
		geoMap.put("那曲", 327);
		geoMap.put("邯郸", 38);
		geoMap.put("邵阳", 210);
		geoMap.put("郑州", 177);
		geoMap.put("郴州", 216);
		geoMap.put("鄂尔多斯", 304);
		geoMap.put("鄂州", 199);
		geoMap.put("酒泉", 290);
		geoMap.put("重庆", 22);
		geoMap.put("金华", 110);
		geoMap.put("金昌", 287);
		geoMap.put("钦州", 314);
		geoMap.put("铁岭", 68);
		geoMap.put("铜仁", 266);
		geoMap.put("铜川", 278);
		geoMap.put("铜陵", 121);
		geoMap.put("银川", 328);
		geoMap.put("锦州", 63);
		geoMap.put("镇江", 101);
		geoMap.put("长春", 71);
		geoMap.put("长沙", 206);
		geoMap.put("长治", 53);
		geoMap.put("阜新", 65);
		geoMap.put("阜阳", 124);
		geoMap.put("防城港", 313);
		geoMap.put("阳江", 233);
		geoMap.put("阳泉", 50);
		geoMap.put("陇南", 294);
		geoMap.put("陕西", 27);
		geoMap.put("随州", 205);
		geoMap.put("雅安", 257);
		geoMap.put("青岛", 161);
		geoMap.put("青海", 29);
		geoMap.put("鞍山", 59);
		geoMap.put("韶关", 224);
		geoMap.put("香港", 32);
		geoMap.put("马鞍山", 119);
		geoMap.put("驻马店", 191);
		geoMap.put("高雄", 145);
		geoMap.put("鸡西", 84);
		geoMap.put("鹤壁", 182);
		geoMap.put("鹤岗", 88);
		geoMap.put("鹰潭", 159);
		geoMap.put("黄冈", 203);
		geoMap.put("黄山", 123);
		geoMap.put("黄石", 195);
		geoMap.put("黑河", 89);
		geoMap.put("黑龙江", 8);
		geoMap.put("齐齐哈尔", 80);
		geoMap.put("龙岩", 138);
	}

	/**
	 * 准备将手机号资料导入数据库
	 */
	@Override
	public void mobileDataToDB() {
		List<String> fileNameList = Arrays.asList(
//				小众运营商  暂不处理
//				"145", "147", 
//				"170", "173", "175", "176", "177", "178",
				"132", "133", "134", "135", "136", "137", "138", "139", 
				"150", "151", "152", "153", "155", "156", "157", "158", "159",
				"180", "181", "182", "183", "184", "185", "186", "187", "188", "189");
		
		for(String fileName : fileNameList) {
			List<MobileDataBO> boList = getDataFromFile("d:/auxiliary/phoneProject/" + fileName + ".txt");
			List<MobileNum> mobileNumList = new ArrayList<MobileNum>();
			for(MobileDataBO bo : boList) {
				mobileNumList.add(mobileBoToPo(bo, Integer.parseInt(fileName)));
			}
			
			MobileNumBatchInsertParam param = new MobileNumBatchInsertParam();
			param.setMobileNumList(mobileNumList);
			mobileNumMapper.insertIgnoreSelectiveMultiple(param);
		}
	}
	
	public MobileNum mobileBoToPo(MobileDataBO bo, Integer prefix) {
		MobileNum po = new MobileNum();
		po.setPrefix(prefix);
		po.setMidNum(bo.getMidNum());
		if(bo.getCompany() != null) {
			po.setMobileOperatorId(0L + bo.getCompany());
		} else {
			po.setMobileOperatorId(0L);
		}
		if(bo.getAreaId() != null) {
			po.setGeographicalId(0L + bo.getAreaId());
		}
		return po;
	}
	
	private List<MobileDataBO> getDataFromFile(String filePath) {
		List<MobileDataBO> boList = new ArrayList<MobileDataBO>();
		File f = new File(filePath);
		if(!f.exists()) {
			return boList;
		}
		FileUtilCustom io = new FileUtilCustom();
		String str = io.getStringFromFile(filePath);
		List<String> lines = Arrays.asList(str.split("\n"));
		

		for(int i = 0; i < 10000 && i < lines.size(); i++) {
			boList.add(lineToBO(lines.get(i)));
		}
		
		return boList;
	}
	
	private MobileDataBO lineToBO(String line) {
		if(StringUtils.isBlank(line)) {
			return null;
		}
		JSONObject json = null;
		try {
			json = JSONObject.fromObject(line.substring(7, line.length()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		String resultCode = json.getString("resultcode");
		if(StringUtils.isBlank(resultCode) || !resultCode.equals("200")) {
			return null;
		}
		
		MobileDataBO bo = new MobileDataBO();
		bo.setJson(json);
		bo.setMidNum(Integer.parseInt(line.substring(0, 4)));
		bo.setCity(json.getJSONObject("result").getString("city"));
		bo.setProvince(json.getJSONObject("result").getString("province"));
		MobileOperatorType opType = MobileOperatorType.getType(json.getJSONObject("result").getString("company"));
		if(opType != null) {
			bo.setCompany(opType.getTypeCode());
		}
		
		String areaName = null;
		if(StringUtils.isBlank(bo.getCity())) {
			areaName = compareKey(bo.getCity());
		} else {
			areaName = compareKey(bo.getProvince());
		}
		
		if(areaName != null) {
			bo.setAreaId(geoMap.get(areaName));
		}
		
		return bo;
	}
	
	private String compareKey(String inputKey) {
		Set<String> keySet = geoMap.keySet();
		for(String k : keySet) {
			if(k.contains(inputKey)) {
				return k;
			}
		}
		return null;
	}
	
}

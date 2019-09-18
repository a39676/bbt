package demo.dfsw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.config.costom_component.SnowFlake;
import demo.testCase.mapper.TestCaseMapper;
import demo.testCase.pojo.po.TestCase;

@Service
public class InsertCommonCase {
	
	@Autowired
	private SnowFlake snowFlake;
	@Autowired
	private TestCaseMapper caseMapper;
	
	public void insertCommonCase() {
		TestCase po = null;
		
		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC01001");
		po.setRemark("登录界面	中英文双语言版本	中英文版本选择	中英文版本选择	能成功转换中英文版本");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC01002");
		po.setRemark("用户名	输入正确密码	大小写敏感	对用户名中字母部分﹐输入大小写与用户名实际不一致	提示用户名不正确或无此用户﹐登录失败");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC01003");
		po.setRemark("大小写不敏感	对用户名中字母部分﹐输入任意大小写	正确登录系统");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC01004");
		po.setRemark("密码	输入正确用户名	大小写敏感	对密码中字母部分﹐输入大小写与用户名实际不一致	提示密码不正确﹐登录失败");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC01005");
		po.setRemark("大小写不敏感	对密码中字母部分﹐输入任意大小写	正确登录系统");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC01006");
		po.setRemark("其它	输入错误的用户名或密码﹐登录	提示用户名或密码不正确﹐登录失败");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC01007");
		po.setRemark("不输入用户名或密码﹐登录	提示用户名或密码为空(或不正确)﹐登录失败");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC02001");
		po.setRemark("菜单	检查页面上的菜单或工具条	菜单风格一致﹐相同功能的菜单﹐名称一致");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC02002");
		po.setRemark("控件	检查页面上的控件	1.控件摆放整齐﹐宽度一致协调； 2.所有控件均可正常操作。");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC02003");
		po.setRemark("文字拼写	检查页面上文字信息	1.文字拼写正确﹐没有乱码及语法错误； 2.文字大小设制规范、一致，文字大小不会随着IE的设制发生改变。");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC02004");
		po.setRemark("标点符号	检查页面上标点	1.符号使用正确﹐没有乱码。2.标点区分全角和半角(中文/英文输入法的标点)");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC02005");
		po.setRemark("图片	检查页面上图片信息	图片显示正确﹐大小正确。");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC02006");
		po.setRemark("链接	检查页面上的链接	1.链接信息能正确打开﹐没有错误页面(找不到相应页面)﹔2.页面若有提示信息﹐提示信息正确");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC02007");
		po.setRemark("将某一页的URL拷贝，粘贴到一个新打开的IE浏览器中运行	不能访问该页面，弹出系统登陆窗口或提示信息提示无权访问。");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03001");
		po.setRemark("新增	单击”新增”按钮	正确打开相应的新增页面");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03002");
		po.setRemark("必需输入	所有的必输项未输入时	系统应有正确的提示");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03003");
		po.setRemark("输入框为多行设置时﹐长度输入将不能限制	字符长度	对输入的字符长度有限制的编辑框，输入超过其长度的字符	1.超过其长度的字符不允许输入；2.输入框为多行设置时﹐长度暂时不能限制的﹐应该有输入长度的提示﹐且在保存要做判断");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03004");
		po.setRemark("时间字段	需输入时间段时	1.起始时间不能大于终止时间；2.可能会要求起始时间不能小于当前时间；3.时间格式标准﹐异常有提示");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03005");
		po.setRemark("不可编辑	编辑系统自动产生或不可编辑的输入框	不可输入﹐光标不能聚焦﹐且输入框的背景为灰色");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03006");
		po.setRemark("自动带出	输入框中可自动带出的数据	应能正确自动带出");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03007");
		po.setRemark("数据校验	从下拉列表或选择数据页面选择数据	1.下拉列表或选择页面能正确打开﹐显示的数据必须是正确的； 2.若数据来源于其它模块(如﹕基础数据)﹐要检查其是否已正确过滤");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03008");
		po.setRemark("保存	单击”保存”按钮	1.成功保存所输入的信息﹐提示信息正确；2.数据列表中应有此新增的记录；3.保存数据异常时应该有正确和到位的提示信息");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03009");
		po.setRemark("若有自动生成的字段(如文件编号﹑流水号等)	保存后该字段的值正确自动生成﹐与约定的格式一致");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03010");
		po.setRemark("取消	单击”取消”按钮	所输入(或修改)的信息不保存");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03011");
		po.setRemark("异常输入保存	增加一条与已存在的记录相同的记录	提示相同记录存在﹐新增不成功。");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC03012");
		po.setRemark("界面上没有必输项﹐在没有输入任何信息时单击保存按钮	根据各系统实际需求﹐查看系统是否提示不允许空记录");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC04001");
		po.setRemark("删除确认	选择要删除的数据﹐单击”删除”按钮	弹出提示信息窗口﹐询问是否确认删除");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC04002");
		po.setRemark("正常删除	选择要删除的数据﹐单击”删除”按钮﹐弹出提示信息窗口	1.单击”取消”按钮﹐提示窗口关闭﹐当前数据或所选中的数据不会被删除；2.单击”确认”按钮﹐确认提示窗口关闭﹐当前数据或所选中的数据被删除﹔");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC04003");
		po.setRemark("数据已被引用或提交	异常删除	选中一条已被引用的数据(已被其它模块使用)﹐单击”删除”按钮	弹出相应的提示信息﹐如﹕”该数据已被引用﹐不能删除”﹐单击”确定”按钮﹐当前数据或所选中的数据不会被删除。");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC04004");
		po.setRemark("数据带有子数据，且与父数据有关联	异常删除	选中一条带有子数据的父数据，单击“删除”按钮	父数据确认删除后，验证所带的子数据是否一并删除。验证方法： 1，再新建一条完全相同的父数据（或关联字段相同的数据），保存后查看其是否已带有子数据； 2，检查数据库中子数据表中是否还有相关子数据存在。");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05001");
		po.setRemark("修改	选中数据﹐单击”修改”按钮	当前数据进入编辑状态");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05002");
		po.setRemark("必需输入	所有的必输项未输入时	系统应有正确的提示");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05003");
		po.setRemark("输入框为多行设置时﹐长度输入将不能限制	字符长度	对输入的字符长度有限制的编辑框，输入超过其长度的字符	1.超过其长度的字符不允许输入；2.输入框为多行设置时﹐长度暂时不能限制的﹐应该有输入长度的提示﹐且在保存要做判断");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05004");
		po.setRemark("时间字段	需输入时间段时	1.起始时间不能大于终止时间；2.可能会要求起始时间不能小于当前时间");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05005");
		po.setRemark("不可编辑	修改系统自动产生或不可编辑的输入框	不可输入﹐光标不能聚焦﹐且输入框的背景为灰色");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05006");
		po.setRemark("自动带出	输入框中可自动带出的数据	应能正确自动带出");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05007");
		po.setRemark("数据校验	从下拉列表或选择数据页面选择数据	1.下拉列表或选择页面能正确打开﹐显示的数据必须是正确的； 2.若数据来源于基础数据﹐要检查其是否已正确过滤﹔");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05008");
		po.setRemark("保存	单击”保存”按钮	1.成功保存所输入的信息﹐提示信息正确；2.查询列表中的数据是否是修改后的数据");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05009");
		po.setRemark("若有自动生成的字段(如文件编号﹑流水号等)	1.该字段不可修改； 2.保存后该字段仍为原值﹔");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC05010");
		po.setRemark("取消	单击”取消”按钮	所输入(或修改)的信息不保存");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06001");
		po.setRemark("正常查询	输入正确的查询条件﹐单击”查询”按钮	可正确查询到相应的数据﹐查询按钮可用");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06002");
		po.setRemark("异常查询	输入错误的查询条件﹐单击”查询”按钮	无法查询到数据");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06003");
		po.setRemark("精确查询	在查询条件中输入完整数据﹐单击”查询”按钮	可正确查询到数据﹐支持精确查询");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06004");
		po.setRemark("模糊查询	在查询条件中输入部分数据﹐单击”查询”按钮	可正确查询到数据﹐支持模糊查询");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06005");
		po.setRemark("条件为空	在查询条件中不输入数据﹐单击”查询”按钮	可正确查询出全部数据");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06006");
		po.setRemark("组合条件查询	交叉汇编正确的查询条件﹐单击\"查询\"按钮	能正确查询出符合组合条件的数据");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06007");
		po.setRemark("时间段查询	输入时间段时进行查询	1.起始时间不能大于终止时间；2.可能会要求起始时间不能小于当前时间；3.查询出所选时间段之间的所有数据；4.时间格式不标准﹐异常有提示");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06008");
		po.setRemark("只输入起始时间	查询出所有大于等于起始时间的记录");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06009");
		po.setRemark("只输入终止时间	查询出所有小于(或等于)终止时间的记录");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC06010");
		po.setRemark("查询条件中有下拉列表	数据校验	检查下拉列表中的数据	1﹐下拉列表能正确打开﹐显示的数据必须是正确的；2﹐若数据来源于其它模块(如﹕基础数据)﹐要检查其是否已正确过滤");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC07001");
		po.setRemark("查询列表中有多页有效数据	首页	将列表翻到非第一页﹐单击\"首页\"链接	1.列表显示第一页中所有数据； 2.\"首页\"﹑\"上一页\"变为灰色不可用﹐\"下一页\"﹑\"尾页\"可用； 3.\"页码\"中的当前页码﹑翻页输入框中的数据都为1；4.\"条形码数\"﹑\"每页条数\"不变");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC07002");
		po.setRemark("上一页	将列表翻到非第一页(假设为第N页)﹐单击\"上一页\"链接	1.列表显示第(N-1)页数据；2.若(N-1)不等于1(不是第一页)﹐\"首页\"﹑\"上一页\"﹑\"下一页\"﹑\"尾页\"均可用； 3.\"页码\"中的当前页码﹑翻页输入框中的数据都为(N-1)； 4.\"条形码数\"﹑\"每页条数\"不变");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC07003");
		po.setRemark("尾页	将列表翻到非最后页﹐单击\"尾页\"链接	1.列表显示最后一页中所有数据；2.\"尾页\"﹑\"下一页\"变为灰色不可用﹐\"首页\"﹑\"上一页\"可用；3.\"页码\"中的当前页码﹑翻页输入框中的数据都为总页码；4.\"条形码数\"﹑\"每页条数\"不变");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC07004");
		po.setRemark("下一页	将列表翻到非最后页(假设为第N页)﹐单击\"下一页\"链接	1.列表显示第(N+1)页数据；2.若(N+1)不等于总页码(不是最后一页)﹐\"首页\"﹑\"上一页\"﹑\"下一页\"﹑\"尾页\"均可用； 3.\"页码\"中的当前页码﹑翻页输入框中的数据都为(N+1)； 4.\"条形码数\"﹑\"每页条数\"不变");
		caseMapper.insertSelective(po);

		po = new TestCase();
		po.setId(snowFlake.getNextId());
		po.setCaseCode("STUC_CC07005");
		po.setRemark("GO	在翻页输入框中输入一个页码值N(不大于总页码)﹐单击\"GO\"链接	1.列表显示第N页数据；2.若N既不等于1﹐也不等于总页码﹐\"首页\"﹑\"上一页\"﹑\"下一页\"﹑\"尾页\"均可用；3.\"页码\"中的当前页码﹑翻页输入框中的数据都为N； 4.\"条形码数\"﹑\"每页条数\"不变");
		caseMapper.insertSelective(po);
	}

}

package net.itw.wcms.ship.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.common.log.annotation.OperateLog;
import net.itw.wcms.ship.entity.Unloader;
import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.ship.service.IUnloaderService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.utils.CollectionUtil;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.ExcelTool;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.SessionUtil;

@RestController
@RequestMapping(value = "/unloader")
public class UnloaderController {

	@Autowired
	private IUnloaderService unloaderService;

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected HttpSession session;
	protected ModelMap modelMap;

	private static final String PATH = "./core/unloader/";

	/**
	 * 初始化全局资源
	 * 
	 * @param req
	 * @param res
	 * @param modelMap
	 */
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
		this.req = req;
		this.res = res;
		this.session = req.getSession();
		this.modelMap = modelMap;
		modelMap.put("IncPath", req.getContextPath());
		modelMap.put("BasePath", req.getContextPath());
		modelMap.put("jsVersion", System.currentTimeMillis());
		User operator = SessionUtil.getSessionUser(req);
		modelMap.put("user", operator!=null?operator.getRealName():"");
	}

	/**
	 * 跳转至卸船机数据页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main() {
		return new ModelAndView(PATH + "list");
	}
	
	/**
	 * 返回卸船机数据
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getUnloaderList", produces = "text/json;charset=UTF-8")
	public String getUserDataTables(@RequestParam Map<String, String> params, ModelMap map) {
		int pageSize = Integer.parseInt(params.get("limit"));
		int pageNum = Integer.parseInt(params.get("offset"));
		String sortType = params.get("sortName");
		String direction = params.get("sortOrder");
		
		Pageable pageable = PageUtils.buildPageRequest(pageNum, pageSize, sortType, direction);
		return unloaderService.getUnloaderList(pageable, params);
	}
	
	@OperateLog(
            bussType=ConstantUtil.BusinessType_GLZX
            ,bussTypeDesc="管理中心"
            ,moudleName = "卸船机实时数据"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "新增卸船机数据"
    )
	@RequestMapping(value = "/addUnloader", produces = "text/json;charset=UTF-8")
	public void addUnloader(@RequestParam Map<String, String> params) throws IOException {
		UnloaderAll unloader = new UnloaderAll();		
		JSONObject jsonObject = new JSONObject();
		String tableName="";
		String cmsId= params.get("fcmsid");
		unloader.setOperationType(params.get("operationtype"));		
		unloader.setTime(DateTimeUtils.strDateTime2Date(params.get("operationtime")));
		unloader.setPushTime(new Date());
//		unloader.setDirection(params.get("direction"));
		unloader.setUnloaderMove(Float.parseFloat(params.get("move")));	
		if("1".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_1");
			tableName="tab_unloader_1";
		} else if ("2".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_2");
			tableName="tab_unloader_2";
		} else if ("3".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_3");
			tableName="tab_unloader_3";
		} else if ("4".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_4");
			tableName="tab_unloader_4";
		} else if ("5".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_5");
			tableName="tab_unloader_5";
		} else if ("6".equals(cmsId)) {
			unloader.setCmsId("ABB_GSU_6");
			tableName="tab_unloader_6";
		}

		if ("0".equals(params.get("operationtype"))) {
			unloader.setOneTask(0f);
		}else if("1".equals(params.get("operationtype"))){
			unloader.setOneTask(Float.parseFloat(params.get("onetask")));
		}
		
		try {
			int val = unloaderService.addUnloader(unloader , tableName);
			if (val > 0) {
				jsonObject.put("success", true);
				jsonObject.put("msg", "保存成功！");
			} else {
				jsonObject.put("failure", true);
				jsonObject.put("msg", "保存失败！");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("failure", true);
			jsonObject.put("msg", "保存失败！");
		}
		res.getWriter().write(jsonObject.toString());

	}
	
	/**
	 * 添加卸船机作业信息
	 * 
	 * @param unloader
	 * @return
	 */
	@RequestMapping(value = "/add")
	public Map<String, Object> add(@ModelAttribute("unloader") Unloader unloader) {
		User operator = SessionUtil.getSessionUser(req);
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "数据保存成功！");
		try {
			unloaderService.createUnloader(unloader, operator);
		} catch (Exception e) {
			e.printStackTrace();
			mo.msg = e.getMessage();
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("code", mo.code);
		return map;
	}

	/**
	 * 导出卸船机数据
	 * 
	 * @param unloader
	 * @return
	 */
	@OperateLog(
            bussType=ConstantUtil.BusinessType_GLZX
            ,bussTypeDesc="管理中心"
            ,moudleName = "卸船机实时数据"
            ,operateType = ConstantUtil.LogOperateType_Execu
            ,operateTypeDesc = "导出卸船机数据"
    )
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(@RequestParam Map<String, String> params, ModelMap modelMap) throws UnsupportedEncodingException {
		String startDate = params.get("startDate") == null ? "" : params.get("startDate");
		String endDate = params.get("endDate") == null ? "" : params.get("endDate");
		List unloaderList = unloaderService.findListByParams(params);

		// 设置Excel的格式
		HSSFWorkbook workbook = new HSSFWorkbook();
		String nameDector = "";
		String cmsId = params.get("cmsId");
		if("1".equals(cmsId)) {
			nameDector = "1_卸船机数据";
		} else if ("2".equals(cmsId)) {
			nameDector = "2_卸船机数据";
		} else if ("3".equals(cmsId)) {
			nameDector = "3_卸船机数据";
		} else if ("4".equals(cmsId)) {
			nameDector = "4_卸船机数据";
		} else if ("5".equals(cmsId)) {
			nameDector = "5_卸船机数据";
		} else if ("6".equals(cmsId)) {
			nameDector = "6_卸船机数据";
		} else {
			nameDector = "卸船机数据";
		}
		HSSFSheet sheet = ExcelTool.createSheet(workbook, 0, nameDector);
		int i = 0;
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);

		HSSFRow row = ExcelTool.createRow(sheet, i++);
		ExcelTool.createCellValueStr(row, 0, "Cms卸船机编号");
		ExcelTool.createCellValueStr(row, 1, "操作类型");
		ExcelTool.createCellValueStr(row, 2, "操作时间");
		ExcelTool.createCellValueStr(row, 3, "推送时间");
		ExcelTool.createCellValueStr(row, 4, "卸船机移动位置");
		ExcelTool.createCellValueStr(row, 5, "一次抓钩作业量");
		for (Iterator iterator = unloaderList.iterator(); iterator.hasNext();) {
			UnloaderAll unloader = (UnloaderAll) iterator.next();
			// 创建excel值和列的对应关系
			row = ExcelTool.createRow(sheet, i++);
			ExcelTool.createCellValueStr(row, 0, unloader.getCmsId());
			ExcelTool.createCellValueStr(row, 1, "0".equals(unloader.getOperationType()) ? "位移"
					: ("1".equals(unloader.getOperationType()) ? "作业" : "在线"));
			ExcelTool.createCellValueStr(row, 2, DateTimeUtils.date2StrDateTime(unloader.getTime()));
			ExcelTool.createCellValueStr(row, 3, DateTimeUtils.date2StrDateTime(unloader.getPushTime()));
			ExcelTool.createCellValueStr(row, 4, unloader.getUnloaderMove().toString());
			ExcelTool.createCellValueStr(row, 5, unloader.getOneTask().toString());
		}
		String fileName = nameDector + ".xls";
		try {
			fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			fileName = "unloader";
		}
		res.reset();
		res.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		res.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outPut = null;
		try {
			outPut = res.getOutputStream();
			workbook.write(outPut);
			outPut.flush();
		} catch (IOException ex) {
			//ex.printStackTrace();
		} finally {
			try {
				outPut.close();
			} catch (IOException ex) {
			//	ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	@RequestMapping(value = "/getDifferenceDays")
	public Map<String, Object>  differentDaysByMillisecond(@RequestParam Map<String, String> params) {
		String startDate= params.get("startDate");
		String endDate= params.get("endDate");
//		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "卸船机数据量大，仅支持导出7天范围内的数据！");
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "请确认是否导出！");
		int days = (int) ((DateTimeUtils.strDateTime2Date(endDate).getTime() - (DateTimeUtils.strDateTime2Date(startDate).getTime())) / (1000 * 3600 * 24));
		if (days > 5) {
			mo.msg = "日期范围不能大于5天，请重新选择日期！";
			mo.code = ConstantUtil.FailInt;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("msg", mo.msg);
		map.put("code", mo.code);
		return map;
	}	
}
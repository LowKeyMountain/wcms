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

import net.itw.wcms.ship.entity.Unloader;
import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.ship.service.IUnloaderService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
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
	 * 跳转至卸船历史参数列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/historyParamList")
	public ModelAndView historyParamList() {
		return new ModelAndView(PATH + "history_param_list");
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
	 * 跳转至卸船机参数配置展示页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/paramConfigList")
	public ModelAndView paramConfigList() {
		return new ModelAndView(PATH + "param_config_list");
	}
	
	/**
	 * 获取卸船机参数数据
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getParamListDatas", produces = "text/json;charset=UTF-8")
	public String getParamListDatas(@RequestParam Map<String, String> params, ModelMap map) {
		Pageable pageable = PageUtils.buildPageRequest(6, 1, "", "");
		return unloaderService.getParamListDatas(pageable, params);
	}
	
	/**
	 * 返回卸船机历史参数数据
	 * 
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getUnloaderHistoryParamList", produces = "text/json;charset=UTF-8")
	public String getHistoryParamDatas(@RequestParam Map<String, String> params, ModelMap map) {
		int pageSize = Integer.parseInt(params.get("limit"));
		int pageNum = Integer.parseInt(params.get("offset"));
		String sortType = params.get("sortName");
		String direction = params.get("sortOrder");
		
		Pageable pageable = PageUtils.buildPageRequest(pageNum, pageSize, sortType, direction);
		return unloaderService.getHistoryParamDatas(pageable, params);
	}
	
	/**
	 * 返回卸船机参数数据
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
	
	@RequestMapping(value = "/addUnloaderParam", produces = "text/json;charset=UTF-8")
	public void addUnloaderParam(@RequestParam Map<String, String> params) throws IOException {
		UnloaderAll unloader = new UnloaderAll();		
		JSONObject jsonObject = new JSONObject();
		String tableName="";
		String cmsId= params.get("fcmsid");
		unloader.setOperationType(params.get("operationtype"));		
		unloader.setTime(DateTimeUtils.strDateTime2Date(params.get("operationtime")));
		unloader.setPushTime(new Date());
		try {
			unloader.setDeliveryRate(Float.parseFloat(params.get("deliveryRate")));
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("failure", true);
			jsonObject.put("msg", "给料速度数据格式错误！");
			res.getWriter().write(jsonObject.toString());
			return;
		}
		try {
			unloader.setDoumenOpeningDegree(Float.parseFloat(params.get("doumenOpeningDegree")));
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("failure", true);
			jsonObject.put("msg", "斗门开度数据格式错误！");
			res.getWriter().write(jsonObject.toString());
			return;
		}
		try {
			unloader.setHopperLoad(Float.parseFloat(params.get("hopperLoad")));
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("failure", true);
			jsonObject.put("msg", "料斗荷载数据格式错误！");
			res.getWriter().write(jsonObject.toString());
			return;
		}
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

		if ("3".equals(params.get("operationtype"))) {
			unloader.setOneTask(0f);
			unloader.setUnloaderMove(0f);
		}
		
		try {
			int val = unloaderService.addUnloaderParam(unloader , tableName);
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
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(@RequestParam Map<String, String> params, ModelMap modelMap) throws UnsupportedEncodingException {

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
}
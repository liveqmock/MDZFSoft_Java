package com.njmd.zfms.web.controller;

import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.commons.Tree;
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.HibernateWebUtils;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.zfms.web.constants.ConfigConstants;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.file.FileUploadInfo;
import com.njmd.zfms.web.service.FileTypeInfoService;
import com.njmd.zfms.web.service.FileUploadInfoService;
import com.njmd.zfms.web.service.SysCorpService;

@Controller
@RequestMapping("/fileMgr")
public class FileMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "文件" };
	// 基础目录
	private final String BASE_DIR = "/file_mgr/";

	@Autowired
	private FileUploadInfoService fileUploadInfoService;

	@Autowired
	private FileTypeInfoService fileTypeService;

	@Autowired
	private SysCorpService sysCorpService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.DESC);
			page.setOrderBy("fileId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);

		// 机构查看范围
		PropertyFilter filter = new PropertyFilter("uploadCorpInfo.treeCode", PropertyFilter.MatchType.START, this.getLoginToken().getSysCorp().getTreeCode());
		filters.add(filter);
		List<String> statusList = new ArrayList<String>();
		statusList.add(FileUploadInfo.FILE_STATUS_CLIPING);
		statusList.add(FileUploadInfo.FILE_STATUS_EXPIRED);
		statusList.add(FileUploadInfo.FILE_STATUS_VALID);
		statusList.add(FileUploadInfo.FILE_STATUS_UNCLIP);
		// 状态过滤
		PropertyFilter filter2 = new PropertyFilter("fileStatus", PropertyFilter.MatchType.IN, statusList);
		filters.add(filter2);
		Page pageResult = fileUploadInfoService.query(page, filters);
		Tree tree = sysCorpService.getCorpTree(request, false);
		model.addAttribute("fileTypeList", fileTypeService.findAll());
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "/file_list/file_list";
	}

	/** 文件查看 */
	@RequestMapping(value = "/fileView/{id}")
	public String fileView(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		FileUploadInfo fileInfo = fileUploadInfoService.findById(id);
		String filePath = fileInfo.getFileContextPath() + "//" + fileInfo.getFilePlayPath();
		model.addAttribute("filePath", filePath);
		if (FileUploadInfo.FILE_TYPE_IMAGE.equals(fileInfo.getFileType()))
		{
			return "forward:/plugins/imageShow.jsp?filePath=" + URLEncoder.encode(filePath, "utf-8") + "&width=648&height=486&r=" + Math.random();
		}
		else if (FileUploadInfo.FILE_TYPE_AUDIO.equals(fileInfo.getFileType()))
		{
			return "forward:/plugins/audioPlayer.jsp?filePath=" + URLEncoder.encode(filePath, "utf-8") + "&r=" + Math.random();
		}
		else if (FileUploadInfo.FILE_TYPE_VIDEO.equals(fileInfo.getFileType()))
		{
			return "redirect:/plugins/videoPlayer.jsp?filePath=" + URLEncoder.encode(filePath, "utf-8") + "&r=" + Math.random();
		}
		return "";
	}

	/** 文件详情 */
	@RequestMapping(value = "/detail/{id}")
	public String detail(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		FileUploadInfo fileInfo = fileUploadInfoService.findById(id);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, fileInfo);
		return BASE_DIR + "file_detail";
	}

	/** 文件查看 */
	@RequestMapping(value = "/download/{id}")
	public String download(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		FileUploadInfo fileInfo = fileUploadInfoService.findById(id);
		String filePath = fileInfo.getFileContextPath() + "//" + fileInfo.getFileSavePath();
		return "forward:/servlet/downloadFile?sourceFilePath=" + URLEncoder.encode(filePath, "utf-8") + "&targetFileName="
				+ URLEncoder.encode(fileInfo.getFileUploadName(), "utf-8");
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResultInfo save(HttpServletRequest request, Model model, FileUploadInfo fileUploadInfo) throws Exception
	{
		try
		{
			int resultTag = fileUploadInfoService.save(fileUploadInfo);
			if (resultTag == ResultConstants.SAVE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error("用户保存异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}

	}

	/**
	 * 检查服务器磁盘空间大小
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkDisk")
	@ResponseBody
	public String checkDisk(HttpServletRequest request, Model model) throws Exception
	{
		int result = ResultConstants.SUCCESS;
		File f = new File(ConfigConstants.CHECK_DISK + "/");
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		if (Double.parseDouble((df.format(f.getFreeSpace() / 1024.0 / 1024 / 1024)).replace(",", "")) < Double.parseDouble(ConfigConstants.MIN_FREE_SPACE))
		{
			result = ResultConstants.FAILED;
		}
		return String.valueOf(result);
	}

	/** 列表查询 */
	@RequestMapping(value = "/myFileList")
	public String myFileList(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.DESC);
			page.setOrderBy("fileId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);

		// 机构查看范围
		PropertyFilter filter = new PropertyFilter("uploadUserInfo.loginId", PropertyFilter.MatchType.EQ, this.getLoginToken().getSysLogin().getLoginId());
		filters.add(filter);
		List<String> statusList = new ArrayList<String>();
		statusList.add(FileUploadInfo.FILE_STATUS_CLIPING);
		statusList.add(FileUploadInfo.FILE_STATUS_EXPIRED);
		statusList.add(FileUploadInfo.FILE_STATUS_VALID);
		statusList.add(FileUploadInfo.FILE_STATUS_UNCLIP);
		// 状态过滤
		PropertyFilter filter2 = new PropertyFilter("fileStatus", PropertyFilter.MatchType.IN, statusList);
		filters.add(filter2);
		Page pageResult = fileUploadInfoService.query(page, filters);
		model.addAttribute("fileTypeList", fileTypeService.findAll());
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "/my_file/file_list";
	}

	/** 文件修改 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		FileUploadInfo fileInfo = fileUploadInfoService.findById(id);
		model.addAttribute("fileTypeList", fileTypeService.findAll());
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, fileInfo);
		return BASE_DIR + "/my_file/file_edit";
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResultInfo update(HttpServletRequest request, Model model, FileUploadInfo entity) throws Exception
	{
		try
		{
			int resultTag = fileUploadInfoService.update(entity);
			if (resultTag == ResultConstants.UPDATE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error("文件修改异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}

	}

	/** 列表查询 */
	@RequestMapping(value = "/deleteList")
	public String deleteList(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.DESC);
			page.setOrderBy("fileId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);

		// 机构查看范围
		PropertyFilter filter = new PropertyFilter("uploadCorpInfo.treeCode", PropertyFilter.MatchType.START, this.getLoginToken().getSysCorp().getTreeCode());
		filters.add(filter);
		List<String> statusList = new ArrayList<String>();
		statusList.add(FileUploadInfo.FILE_STATUS_CLIPING);
		statusList.add(FileUploadInfo.FILE_STATUS_EXPIRED);
		statusList.add(FileUploadInfo.FILE_STATUS_VALID);
		statusList.add(FileUploadInfo.FILE_STATUS_UNCLIP);
		// 业务状态过滤
		PropertyFilter filter2 = new PropertyFilter("fileStatus", PropertyFilter.MatchType.IN, statusList);
		filters.add(filter2);
		Page pageResult = fileUploadInfoService.query(page, filters);
		Tree tree = sysCorpService.getCorpTree(request, false);
		model.addAttribute("fileTypeList", fileTypeService.findAll());
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "/file_delete/file_list";
	}

	/** 删除 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public ResultInfo delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		try
		{
			int resultTag = fileUploadInfoService.delete(id);
			if (resultTag == ResultConstants.DELETE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error("文件删除异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}
	}

	/** 批量删除 */
	@RequestMapping(value = "/batchDelete")
	@ResponseBody
	public ResultInfo batchDelete(HttpServletRequest request, Model model, String ids) throws Exception
	{
		try
		{
			int resultTag = fileUploadInfoService.batchDelete(ids);
			if (resultTag == ResultConstants.DELETE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error("文件删除异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}

	}
}

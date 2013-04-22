package com.njmd.zfms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.commons.Tree;
import com.njmd.framework.controller.BaseController;
import com.njmd.zfms.web.constants.CommonConstants;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.service.SysCorpService;

@Controller
@RequestMapping("/fileMgr")
public class FileMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "部门", "部门名称" };
	// 基础目录
	private final String BASE_DIR = "/file_mgr/file_upload/";

	@Autowired
	private SysCorpService sysCorpService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Model model) throws Exception
	{
		// 设置默认排序方式
		Tree tree = sysCorpService.getCorpTree(request, false);
		model.addAttribute("tree", tree);
		return BASE_DIR + "file_upload";
	}

}

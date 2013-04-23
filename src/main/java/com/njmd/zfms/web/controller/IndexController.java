package com.njmd.zfms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.HibernateWebUtils;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.framework.dao.PropertyFilter.MatchType;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.service.NoticeInfoService;
import com.njmd.zfms.web.service.SysCorpService;
import com.njmd.zfms.web.service.SysPermissionService;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "公告" };
	// 基础目录
	private final String BASE_DIR = "/";

	@Autowired
	private NoticeInfoService noticeInfoService;

	@Autowired
	private SysCorpService sysCorpService;

	@Autowired
	private SysPermissionService sysPermissionService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.DESC);
			page.setOrderBy("noticeId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);

		SysLogin sysLogin = this.getLoginToken().getSysLogin();
		filters.add(new PropertyFilter("targetIds", MatchType.LIKE, sysLogin.getSysCorp().getCorpId()));
		Page pageResult = noticeInfoService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "/homepage";
	}
}

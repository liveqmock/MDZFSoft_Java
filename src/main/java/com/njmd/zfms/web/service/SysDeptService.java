package com.njmd.zfms.web.service;

import java.util.List;

import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.entity.sys.SysDept;

public interface SysDeptService extends BaseCrudService<SysDept, Long>
{
	public List<SysDept> findByCorpId(Long corpId);
	
}

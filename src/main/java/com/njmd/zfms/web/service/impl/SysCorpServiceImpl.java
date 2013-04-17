package com.njmd.zfms.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.commons.Tree;
import com.njmd.framework.commons.TreeNode;
import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.CommonConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.service.SysCorpService;
import com.njmd.zfms.web.service.SysLogService;

/**
 * @title: FSysCorpBOImpl.java
 * @description: 单位管理业务操作类
 * 
 * 
 * 
 * @author: dongyuese
 */
@Service
public class SysCorpServiceImpl extends BaseCrudServiceImpl<SysCorp, Long> implements SysCorpService
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 系统日志操作接口
	 */
	@Autowired
	private SysLogService sysLogBO;

	/**
	 * 保存单位管理信息
	 * 
	 * @param sysCorpDTO
	 *            单位管理信息数据传输对象
	 * @return 保存成功或失败
	 * 
	 * 
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int save(SysCorp sysCorpDTO) throws Exception
	{
		SysCorp sysCorp = new SysCorp();
		BeanUtils.copyProperties(sysCorp, sysCorpDTO);
		// 判断是否已经存在同名单位
		if (baseDao.findUnique("corpName", sysCorpDTO.getCorpName()) == null)
		{
			baseDao.save(sysCorp);
			// 处理组织机构树编码，编码格式为：上级机构树编码.本机构ID
			String treeCode = String.valueOf(sysCorp.getCorpId());
			if (!CommonConstants.NO_PARENT_ID.equals(sysCorp.getParentCorpId()))
			{
				String parentTreeCode = baseDao.findById(sysCorp.getParentCorpId()).getTreeCode();
				treeCode = parentTreeCode + "." + sysCorp.getCorpId();
			}
			sysCorp.setTreeCode(treeCode);
			baseDao.update(sysCorp);
			sysLogBO.save(SysLog.OPERATE_TYPE_ADD, "【部门新增】部门名称：" + sysCorp.getCorpName());
			return ResultConstants.SAVE_SUCCEED;
		}
		return ResultConstants.SAVE_FAILED;
	}

	/**
	 * 根据单位ID查询单位信息
	 * 
	 * @param corpId
	 *            单位ID
	 * @return 单位管理信息数据传输对象
	 */
	@Override
	public SysCorp findById(Long corpId) throws Exception
	{
		SysCorp sysCorp = baseDao.findById(corpId);
		if (sysCorp != null)
		{
			SysCorp parentCorp = baseDao.findById(sysCorp.getParentCorpId());
			if (parentCorp != null)
			{
				sysCorp.setParentCorpName(parentCorp.getCorpName());
			}
		}
		return sysCorp;
	}

	/**
	 * 根据单位ID删除单位信息
	 * 
	 * @param corpId
	 *            单位ID
	 * @return 删除成功或失败
	 * 
	 * 
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long corpId) throws Exception
	{
		// 判断是否有下级单位，如果有下级单位则不允许删除
		List<SysCorp> corpList = this.findByParentId(corpId);
		if ((corpList != null) && (!corpList.isEmpty()))
			return ResultConstants.DELETE_FAILED_IS_REF;

		SysCorp sysCorp = baseDao.findById(corpId);
		baseDao.delete(sysCorp);
		sysLogBO.save(SysLog.OPERATE_TYPE_DELETE, "【部门删除】部门名称：" + sysCorp.getCorpName());
		return ResultConstants.DELETE_SUCCEED;
	}

	/**
	 * 按照条件分页查询单位信息
	 * 
	 * @param page
	 *            分页信息
	 * @param filters
	 *            过滤条件列表
	 * @return 单位信息集合
	 * @throws Exception
	 */
	@Override
	public Page query(Page page, List<PropertyFilter> filters) throws Exception
	{
		Page pageTemp = baseDao.findByPage(page, filters);
		List tempList = pageTemp.getResult();
		List resultList = new ArrayList();
		if (tempList != null)
		{
			SysCorp sysCorp = null;
			SysCorp tempCorp = null;
			for (int i = 0; i < tempList.size(); i++)
			{
				sysCorp = (SysCorp) tempList.get(i);
				// 如果存在上级单位，则将父ID转化为上级单位名称

				if (sysCorp.getParentCorpId() != 0)
				{
					tempCorp = baseDao.findById(sysCorp.getParentCorpId());
					if (tempCorp != null)
					{
						sysCorp.setParentCorpName(tempCorp.getCorpName());
					}
				}
				resultList.add(sysCorp);
			}
		}
		pageTemp.setResult(resultList);
		return pageTemp;
	}

	/**
	 * 按照单位类型获取单位信息
	 * 
	 * @param corpType
	 *            属性值
	 * 
	 * 
	 * @return 单位信息集合
	 * @throws Exception
	 */
	@Override
	public List<SysCorp> findByCorpType(final Long corpType) throws Exception
	{
		return baseDao.findByProperty("corpType", corpType);
	}

	/**
	 * 修改单位管理信息
	 * 
	 * @param sysCorp
	 *            单位管理信息数据传输对象
	 * @return 修改成功或失败
	 * 
	 * 
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int update(SysCorp sysCorp) throws Exception
	{
		SysCorp sysCorpTemp = baseDao.findUnique("corpName", sysCorp.getCorpName());

		// 修改后的单位名是否跟原有的单位名存在重复
		if ((sysCorpTemp != null) && (sysCorpTemp.getCorpId().longValue() != sysCorp.getCorpId().longValue()))
			return ResultConstants.UPDATE_FAILED_NAME_IS_EXIST;

		// 如果将单位修改为无效状态，则需要判断该单位是否被用户引用，已经引用则不能修改为无效
		// if ((CommonConstants.STATUS_INVALID == sysCorpDTO.getStatus()) &&
		// (this.validationCorp(sysCorpDTO.getCorpId())))
		// return ResultConstants.CORP_USER_QUOTED;

		if (sysCorpTemp == null)
		{
			sysCorpTemp = new SysCorp();
		}
		BeanUtils.copyProperties(sysCorpTemp, sysCorp);
		// 处理组织机构树编码，编码格式为：上级机构树编码.本机构ID
		String treeCode = String.valueOf(sysCorp.getCorpId());
		if (!CommonConstants.NO_PARENT_ID.equals(sysCorp.getParentCorpId()))
		{
			String parentTreeCode = baseDao.findById(sysCorp.getParentCorpId()).getTreeCode();
			treeCode = parentTreeCode + "." + sysCorp.getCorpId();
		}
		sysCorpTemp.setTreeCode(treeCode);

		baseDao.update(sysCorpTemp);
		sysLogBO.save(SysLog.OPERATE_TYPE_UPDATE, "【部门更新】部门名称：" + sysCorp.getCorpName());
		return ResultConstants.UPDATE_SUCCEED;
	}

	/**
	 * 根据父单位ID查询下级单位
	 * 
	 * @param parentId
	 *            父单位ID
	 * @return 返回下级单位
	 * @throws Exception
	 */
	@Override
	public List<SysCorp> findByParentId(Long parentId) throws Exception
	{
		return baseDao.findByProperty("parentCorpId", parentId);
	}

	/**
	 * 根据父单位id和单位类型查询合作商单位
	 * 
	 * @param parentId
	 *            父单位id
	 * @param corpType
	 *            单位类型
	 * @return 合作商单位集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysCorp> findByParentIdAndCorpType(Long parentId, Long corpType) throws Exception
	{
		Object[] values = new Object[] { parentId, corpType };
		String hql = "from SysCorp as model where model.parentCorpId = ? and model.corpType = ?";
		return baseDao.findByHql(hql, values);
	}

	@Autowired
	@Override
	@Qualifier(value = "sysCorpDAO")
	public void setBaseDao(BaseHibernateDAO<SysCorp, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	public Tree getCorpTree(HttpServletRequest request) throws Exception
	{
		String context = request.getContextPath();
		Tree tree = new Tree();
		LoginToken loginToken = WebContextHolder.getCurrLoginToken();
		List<SysCorp> corpList = null;
		if (loginToken.getSysCorp() == null)
		{
			corpList = this.findAll();
		}
		else
		{
			Object[] values = new Object[] {};
			String hql = "from SysCorp as model where model.treeCode like '" + loginToken.getSysCorp().getTreeCode() + "%' and status ="
					+ CommonConstants.STATUS_VALID;
			corpList = baseDao.findByHql(hql, values);
		}
		for (SysCorp sysCorp : corpList)
		{
			TreeNode treeNode = new TreeNode();

			treeNode.setId(sysCorp.getCorpId().toString());
			if (sysCorp.getParentCorpId() != null)
				treeNode.setpId(sysCorp.getParentCorpId().toString());
			treeNode.setName(sysCorp.getCorpName());
			if (sysCorp.getParentCorpId() == null || sysCorp.getParentCorpId().intValue() == CommonConstants.NO_PARENT_ID)
			{
				treeNode.setIconOpen(context + "/plugins/zTree/css/zTreeStyle/img/diy/1_open.png");
				treeNode.setIconClose(context + "/plugins/zTree/css/zTreeStyle/img/diy/1_close.png");
			}
			else
				treeNode.setIcon(context + "/plugins/zTree/css/zTreeStyle/img/diy/8.png");
			treeNode.setNocheck(true);
			tree.addNode(treeNode);
		}

		return tree;
	}
}

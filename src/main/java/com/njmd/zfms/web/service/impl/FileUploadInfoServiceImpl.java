package com.njmd.zfms.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.io.ImageUtils;
import com.njmd.framework.utils.io.VideoUtils;
import com.njmd.framework.utils.web.RequestUtils;
import com.njmd.zfms.web.constants.ConfigConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.dao.FileTypeInfoDAO;
import com.njmd.zfms.web.entity.dev.DevTypeInfo;
import com.njmd.zfms.web.entity.file.FileTypeInfo;
import com.njmd.zfms.web.entity.file.FileUploadInfo;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysLoginRole;
import com.njmd.zfms.web.service.FileUploadInfoService;
import com.njmd.zfms.web.service.SysLogService;

/**
 * 系统公告业务处理实现类
 * 
 * @author Yao
 * 
 */
@Service
public class FileUploadInfoServiceImpl extends BaseCrudServiceImpl<FileUploadInfo, Long> implements FileUploadInfoService
{
	/**
	 * 系统日志操作接口
	 */
	@Autowired
	private SysLogService sysLogBO;

	@Autowired
	private FileTypeInfoDAO fileTypeInfoDAO;

	/**
	 * 上传文件保存
	 */
	@Transactional(readOnly = false)
	@Override
	public int save(FileUploadInfo fileUploadInfo) throws Exception
	{
		String fileSavePath = fileUploadInfo.getFileSavePath();
		SysLogin sysLogin = this.getLoginToken().getSysLogin();
		SysCorp sysCorp = this.getLoginToken().getSysCorp();
		// fileUploadInfo.setUploadUserId(sysLogin.getLoginId());
		fileUploadInfo.setUploadUserInfo(sysLogin);
		fileUploadInfo.setUploadUserIp(RequestUtils.getIpAddr(this.getRequest()));
		fileUploadInfo.setFileUploadTime(DateTimeUtil.getChar14());
		fileUploadInfo.setFilePlayPath(fileSavePath);
		fileUploadInfo.setFileShowPath(fileSavePath);
		fileUploadInfo.setFileRemark("");
		fileUploadInfo.setFileStatus("A");
		fileUploadInfo.setFileContextPath(sysCorp.getSysFtp().getFileRootUrl() + "/");
		//Edit by 孙强伟,保存文件服务器保存文件时的目录
		fileUploadInfo.setFileStorageRoot(ConfigConstants.FILE_STORAGE_ROOT);
		
		//Edit by 孙强伟，当上传的文件没有选择文件分类时，其文件分类设置为系统默认分类。
		if(null!=fileUploadInfo && fileUploadInfo.getFileTypeInfo()==null){
			FileTypeInfo fileTypeInfo=fileTypeInfoDAO.findById(0l);
			fileUploadInfo.setFileTypeInfo(fileTypeInfo);
		}

		if (fileSavePath.toLowerCase().lastIndexOf(".jpg") > 0)
		{
			fileUploadInfo.setFileType(FileUploadInfo.FILE_TYPE_IMAGE);
			String showPath = fileSavePath.substring(0, fileSavePath.toLowerCase().lastIndexOf(".jpg")) + "_small.jpg";
			ImageUtils.compress(ConfigConstants.FILE_STORAGE_ROOT + fileSavePath, ConfigConstants.FILE_STORAGE_ROOT + showPath);
			fileUploadInfo.setFileShowPath(showPath);
		}
		if (fileSavePath.toLowerCase().lastIndexOf(".avi") > 0)
		{
			fileUploadInfo.setFileType(FileUploadInfo.FILE_TYPE_VIDEO);
			String showPath = fileSavePath.substring(0, fileSavePath.toLowerCase().lastIndexOf(".avi")) + ".jpg";
			VideoUtils
					.makeImgbyMP4(ConfigConstants.FFMPEG_HOME, ConfigConstants.FILE_STORAGE_ROOT + fileSavePath, ConfigConstants.FILE_STORAGE_ROOT + showPath);
			fileUploadInfo.setFileShowPath(showPath);
			fileUploadInfo.setFileStatus("C");
		}
		if (fileSavePath.toLowerCase().lastIndexOf(".mp4") > 0)
		{
			fileUploadInfo.setFileType(FileUploadInfo.FILE_TYPE_VIDEO);
			String showPath = fileSavePath.substring(0, fileSavePath.toLowerCase().lastIndexOf(".mp4")) + ".jpg";
			VideoUtils
					.makeImgbyMP4(ConfigConstants.FFMPEG_HOME, ConfigConstants.FILE_STORAGE_ROOT + fileSavePath, ConfigConstants.FILE_STORAGE_ROOT + showPath);
			fileUploadInfo.setFileShowPath(showPath);
			fileUploadInfo.setFilePlayPath(fileSavePath);
		}
		if (fileSavePath.toLowerCase().lastIndexOf(".wav") > 0)
		{
			fileUploadInfo.setFileType(FileUploadInfo.FILE_TYPE_AUDIO);
			String showPath = "images/WAV.png";
			fileUploadInfo.setFileShowPath(showPath);
		}
		fileUploadInfo.setFileCreateTime(fileUploadInfo.getFileCreateTime().replace(":", "").replace("-", "").replace(" ", ""));
		fileUploadInfo.setUploadCorpInfo(sysCorp);

		baseDao.save(fileUploadInfo);
		return ResultConstants.SAVE_SUCCEED;
	}

	@Transactional(readOnly = false)
	@Override
	public int update(FileUploadInfo fileUploadInfo) throws Exception
	{
		FileUploadInfo entity = baseDao.findById(fileUploadInfo.getFileId());
		if (fileUploadInfo.getFileTypeInfo() != null)
			entity.setFileTypeInfo(fileUploadInfo.getFileTypeInfo());
		if (fileUploadInfo.getPoliceCode() != null)
			entity.setPoliceCode(fileUploadInfo.getPoliceCode());
		if (fileUploadInfo.getPoliceDesc() != null)
			entity.setPoliceDesc(fileUploadInfo.getPoliceDesc());
		if (fileUploadInfo.getPoliceTime() != null)
			entity.setPoliceTime(fileUploadInfo.getPoliceTime());
		if (fileUploadInfo.getFileRecordTime() != null)
			entity.setFileRecordTime(fileUploadInfo.getFileRecordTime());
		if (fileUploadInfo.getEditUserInfo()!=null)
			entity.setEditUserInfo(fileUploadInfo.getEditUserInfo());

		baseDao.update(entity);
		fileUploadInfo.setFileUploadName(entity.getFileUploadName());
		sysLogBO.save(SysLog.OPERATE_TYPE_UPDATE, "【文件更新】文件名称：" + entity.getFileUploadName());
		return ResultConstants.UPDATE_SUCCEED;
	}

	@Autowired
	@Override
	@Qualifier(value = "fileUploadInfoDAO")
	public void setBaseDao(BaseHibernateDAO<FileUploadInfo, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getCorpChartData(Integer queryType, Long corpId, String year, String month, String startDate, String endDate)
	{
		Map<String, Integer> re = new HashMap<String, Integer>();
		String hql = "";
		if (queryType == 1)
		{
			// 按年统计
			hql = "select model.uploadCorpInfo.id,substr(model.fileUploadTime,0,6)   ,count(*)   from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + "%' and model.uploadCorpInfo.parentCorpId=" + corpId
					+ " group by  substr(model.fileUploadTime,0,6),model.uploadCorpInfo.id ";
		}
		if (queryType == 2)
		{
			// 按月统计
			hql = "select model.uploadCorpInfo.id,substr(model.fileUploadTime,7,2),count(*)   from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + month + "%' and model.uploadCorpInfo.parentCorpId=" + corpId
					+ "   group by  substr(model.fileUploadTime,7,2),model.uploadCorpInfo.id  ";
		}
		if (queryType == 3)
		{
			// 按天统计
			hql = "select model.uploadCorpInfo.id,substr(model.fileUploadTime,0,8)   ,count(*)   from FileUploadInfo as model "
					+ "where   substr(model.fileUploadTime,0,8)>= '" + startDate + "' and substr(model.fileUploadTime,0,8)<='" + endDate
					+ "' and model.uploadCorpInfo.parentCorpId=" + corpId + "   group by  substr(model.fileUploadTime,0,8),model.uploadCorpInfo.id ";
		}
		Object[] param = new Object[] {};
		List<Object[]> list = (List<Object[]>) baseDao.findByHql(hql, param);
		for (Object[] obj : list)
		{
			re.put(obj[0] + "_" + obj[1], obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
		}

		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getCorpChartData(Integer queryType, Long corpId,String treeCode, String year, String month, String startDate, String endDate)
	{
		Map<String, Integer> re = new HashMap<String, Integer>();
		String hql = "";
		if (queryType == 1)
		{
			// 按年统计
			hql = "select '"+corpId+"',substr(model.fileUploadTime,0,6)   ,count(*)   from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + "%' and model.uploadCorpInfo.treeCode like '" + treeCode+"%' "
					+ " group by  substr(model.fileUploadTime,0,6) ";
		}
		if (queryType == 2)
		{
			// 按月统计
			hql = "select '"+corpId+"',substr(model.fileUploadTime,7,2),count(*)   from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + month + "%' and model.uploadCorpInfo.treeCode like '" + treeCode+"%' "
					+ "   group by  substr(model.fileUploadTime,7,2)  ";
		}
		if (queryType == 3)
		{
			// 按天统计
			hql = "select '"+corpId+"',substr(model.fileUploadTime,0,8)   ,count(*)   from FileUploadInfo as model "
					+ "where   substr(model.fileUploadTime,0,8)>= '" + startDate + "' and substr(model.fileUploadTime,0,8)<='" + endDate
					+ "' and model.uploadCorpInfo.treeCode like '" + treeCode + "%'   group by  substr(model.fileUploadTime,0,8) ";
		}
		Object[] param = new Object[] {};
		List<Object[]> list = (List<Object[]>) baseDao.findByHql(hql, param);
		for (Object[] obj : list)
		{
			re.put(obj[0] + "_" + obj[1], obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
		}

		return re;
	}
	
	@Override
	public Map<String, Integer> getCorpDetialData(Integer queryType, Long corpId, String year, String month, String startDate, String endDate)
	{
		Map<String, Integer> re = new HashMap<String, Integer>();
		String hql = "";
		if (queryType == 1)
		{
			// 按年统计
			hql = "select model.uploadCorpInfo.id,model.fileTypeInfo.id,substr(model.fileUploadTime,0,6),count(*)from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + "%'  and model.uploadCorpInfo.parentCorpId=" + corpId
					+ "  group by  substr(model.fileUploadTime,0,6),model.uploadCorpInfo.id ,model.fileTypeInfo.id";
		}
		if (queryType == 2)
		{
			// 按月统计
			hql = "select model.uploadCorpInfo.id,model.fileTypeInfo.id,substr(model.fileUploadTime,7,2),count(*)   from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + month + "%'  and model.uploadCorpInfo.parentCorpId=" + corpId
					+ "  group by  substr(model.fileUploadTime,7,2),model.uploadCorpInfo.id,model.fileTypeInfo.id  ";
		}
		if (queryType == 3)
		{
			// 按天统计
			hql = "select model.uploadCorpInfo.id,model.fileTypeInfo.id,substr(model.fileUploadTime,0,8)   ,count(*)   from FileUploadInfo as model "
					+ "where   substr(model.fileUploadTime,0,8)>= '" + startDate + "' and substr(model.fileUploadTime,0,8)<='" + endDate
					+ "'  and model.uploadCorpInfo.parentCorpId=" + corpId
					+ "  group by  substr(model.fileUploadTime,0,8),model.uploadCorpInfo.id,model.fileTypeInfo.id ";
		}
		Object[] param = new Object[] {};
		List<Object[]> list = (List<Object[]>) baseDao.findByHql(hql, param);
		for (Object[] obj : list)
		{
			re.put(obj[0] + "_" + obj[1] + "_" + obj[2], obj[3] == null ? 0 : Integer.parseInt(obj[3].toString()));
		}

		return re;
	}
	
	public Map<String,Integer> getCorpDetialData(Integer queryType, Long corpId, String treeCode,String year,String month,String startDate,String endDate){
		Map<String, Integer> re = new HashMap<String, Integer>();
		String hql = "";
		if (queryType == 1)
		{
			// 按年统计
			hql = "select '"+corpId+"',model.fileTypeInfo.id,substr(model.fileUploadTime,0,6),count(*)from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + "%'  and model.uploadCorpInfo.treeCode like '" + treeCode+"%' "
					+ "  group by  substr(model.fileUploadTime,0,6),model.fileTypeInfo.id";
		}
		if (queryType == 2)
		{
			// 按月统计
			hql = "select '"+corpId+"',model.fileTypeInfo.id,substr(model.fileUploadTime,7,2),count(*)   from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + month + "%'  and model.uploadCorpInfo.treeCode like '" + treeCode+"%' "
					+ "  group by  substr(model.fileUploadTime,7,2),model.fileTypeInfo.id  ";
		}
		if (queryType == 3)
		{
			// 按天统计
			hql = "select '"+corpId+"',model.fileTypeInfo.id,substr(model.fileUploadTime,0,8)   ,count(*)   from FileUploadInfo as model "
					+ "where   substr(model.fileUploadTime,0,8)>= '" + startDate + "' and substr(model.fileUploadTime,0,8)<='" + endDate
					+ "'  and model.uploadCorpInfo.treeCode like '" + treeCode+"%' "
					+ "  group by  substr(model.fileUploadTime,0,8),model.fileTypeInfo.id ";
		}
		Object[] param = new Object[] {};
		List<Object[]> list = (List<Object[]>) baseDao.findByHql(hql, param);
		for (Object[] obj : list)
		{
			re.put(obj[0] + "_" + obj[1] + "_" + obj[2], obj[3] == null ? 0 : Integer.parseInt(obj[3].toString()));
		}

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getUserChartData(Integer queryType, Long userId, String year, String month, String startDate, String endDate)
	{
		Map<String, Integer> re = new HashMap<String, Integer>();
		String hql = "";
		if (queryType == 1)
		{
			// 按年统计
			hql = "select  model.fileTypeInfo.id,substr(model.fileUploadTime,0,6),count(*)from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + "%'  and model.editUserInfo.loginId=" + userId
					+ "  group by  substr(model.fileUploadTime,0,6), model.fileTypeInfo.id";
		}
		if (queryType == 2)
		{
			// 按月统计
			hql = "select  model.fileTypeInfo.id,substr(model.fileUploadTime,7,2),count(*)   from FileUploadInfo as model "
					+ "where   model.fileUploadTime like '" + year + month + "%'  and model.editUserInfo.loginId=" + userId
					+ "   group by  substr(model.fileUploadTime,7,2), model.fileTypeInfo.id  ";
		}
		if (queryType == 3)
		{
			// 按天统计
			hql = "select  model.fileTypeInfo.id,substr(model.fileUploadTime,0,8)   ,count(*)   from FileUploadInfo as model "
					+ "where   substr(model.fileUploadTime,0,8)>= '" + startDate + "' and substr(model.fileUploadTime,0,8)<='" + endDate
					+ "'  and model.editUserInfo.loginId=" + userId + "   group by  substr(model.fileUploadTime,0,8), model.fileTypeInfo.id ";
		}
		Object[] param = new Object[] {};
		List<Object[]> list = (List<Object[]>) baseDao.findByHql(hql, param);
		for (Object[] obj : list)
		{
			re.put(obj[0] + "_" + obj[1], obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
		}

		return re;
	}

	@Override
	public long getCountByTypeId(Long typeId)
	{
		String hql = "select count(model.fileId) from FileUploadInfo model where model.fileTypeInfo.typeId=?";
		return baseDao.findLong(hql, typeId);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long id) throws Exception
	{
		FileUploadInfo entity = baseDao.findById(id);
		entity.setFileStatus(FileUploadInfo.FILE_STATUS_WAIT_DELETE);
		entity.setDeleteBy(this.getLoginToken().getSysLogin().getLoginId());
		entity.setDeleteTime(DateTimeUtil.getChar14());
		baseDao.update(entity);
		sysLogBO.save(SysLog.OPERATE_TYPE_DELETE, "【文件删除】文件名称：" + entity.getFileUploadName());
		return ResultConstants.DELETE_SUCCEED;
	}

	@Transactional(readOnly = false)
	@Override
	public int batchDelete(String ids) throws Exception
	{
		String[] idArray = ids.split(",");
		for (String id : idArray)
		{
			this.delete(Long.valueOf(id));
		}
		return ResultConstants.DELETE_SUCCEED;
	}

}

package com.njmd.framework.utils.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.ReflectionUtil;

public class ExcelUtils<T> {

	/**
	 * <p>
	 * Description:[导出excel]
	 * </p>
	 * 
	 * @param titleAndFieldList(String["表头","对应的字段"])
	 * @param sheetName（sheet名称）
	 * @param dataList （数据集合）
	 * @param dateFormate (日期格式)
	 * @param dateFieldList (需要显示成时间的字段)
	 * @param out (输出流)
	 */
	public  void exportExcel(List<String[]> titleAndFieldList,String sheetName,Collection<T> dataList,String dateFormate,List<String> dateFieldList,OutputStream out){
		//表头
		List<String> headers = new ArrayList<String>();
		//表头对应的字段
		List<String> fields = new ArrayList<String>();
		
		// 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格,sheet名称为title
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        
        // 获取表头样式
        HSSFCellStyle titleStyle = setListTitleStyle(workbook);
        //获取列表样式
        HSSFCellStyle listStyle = setListStyle(workbook);
        
        for(int i =0;i<titleAndFieldList.size();i++){
        	String[] str = titleAndFieldList.get(i);
        	headers.add(str[0]);
        	fields.add(str[1]);
        }
        
        //产生表格标题行(及表头)
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }
        
        Iterator<T> it = dataList.iterator();
        int index = 0;
        
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            
            for(int j = 0; j < fields.size(); j++){
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(listStyle);
                String fieldName = fields.get(j);
                
                Object fieldValue = ReflectionUtil.getFieldValue(t, fieldName);
                String textValue = "";
                if (fieldValue instanceof byte[]) {
                    // 有图片时，设置行高为60px;
                    row.setHeightInPoints(60);
                    // 设置图片所在列宽度为80px,注意这里单位的一个换算
                    sheet.setColumnWidth(j, (short) (35.7 * 80));
                    // sheet.autoSizeColumn(i);
                    byte[] bsValue = (byte[]) fieldValue;
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                            1023, 255, (short) 6, index, (short) 6, index);
                    anchor.setAnchorType(2);
                    patriarch.createPicture(anchor, workbook.addPicture(
                            bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                } else {
                    // 其它数据类型都当作字符串简单处理
                    textValue = fieldValue.toString();
                    
                    if(dateFieldList.contains(fieldName)){
                    	textValue = DateTimeUtil.formatChar14(textValue);
                    }
                }
                
                   cell.setCellValue(textValue);
                } 
            }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * <p>
	 * Description:[返回列表表头样式]
	 * </p>
	 * 
	 * @param workbook 
	 */
	public static HSSFCellStyle setListTitleStyle(HSSFWorkbook workbook){
		 HSSFCellStyle style = workbook.createCellStyle();
		 HSSFFont font = workbook.createFont();
		 
		 style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
	     style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	     style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	     style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	     style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	     style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	     style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	     
	     font.setColor(HSSFColor.VIOLET.index);
	     font.setFontHeightInPoints((short) 12);
	     font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	     // 把字体应用到当前的样式
	     style.setFont(font);
	     return style;
	}
	
	/**
	 * <p>
	 * Description:[返回列表样式]
	 * </p>
	 * 
	 * @param workbook 
	 */
	public static HSSFCellStyle setListStyle(HSSFWorkbook workbook){
		 HSSFCellStyle style = workbook.createCellStyle();
		 HSSFFont font = workbook.createFont();
		 
		 style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		 style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		 style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	     
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	     // 把字体应用到当前的样式
	     style.setFont(font);
	     return style;
	}
}

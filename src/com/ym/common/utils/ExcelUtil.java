package com.ym.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 
 *                       
 * @Filename ExcelUtil.java
 *
 * @Description excel导出工具类
 *
 * @Version 1.0
 *
 * @Author lixingbiao
 *
 *       
 * @History
 *<li>Author: lixingbiao</li>
 *<li>Date: 2014-2-12</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class ExcelUtil {
	/**
	 * 生成excel文件
	 * @param response  HttpServletResponse对象
	 * @param loger 日志对象
	 * @param list 内容集合
	 * @param fileName excel文件名
	 */
	public static void createExcel(HttpServletRequest request, HttpServletResponse response, Log loger, List list,
									String fileName) {
		try {
			//得到当前web项目根目录绝对路径
			String localPath =  request.getSession().getServletContext().getRealPath("/");
			HSSFWorkbook wb = new HSSFWorkbook();
			
			//创建excel单元格数据
			Sheet sheet = wb.createSheet();
			createExcelData(list, sheet, wb);
			
			//定义excel文件名称
			StringBuffer stringBuffer = new StringBuffer(fileName);
			stringBuffer.append(Long.valueOf(System.currentTimeMillis()).toString() + "_.xls");
			fileName = stringBuffer.toString();
			
			//执行excel写操作
			OutputStream os = new FileOutputStream(new File(localPath + fileName));
			wb.write(os);
			os.close();
				
			response.setContentType("application/vnd.ms-excel;charset=gbk");
			String dispositionValue = "attachment; filename=\""
										+ new String(fileName.getBytes(), "iso8859-1") + "\" ";
			response.setHeader("Content-Disposition", dispositionValue);
			
			//执行excel读操作
			int len = 0;
			byte[] buffer = new byte[1024];
			OutputStream out = response.getOutputStream();
			InputStream in = new FileInputStream(localPath + fileName);
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.close();
			
		} catch (Exception e) {
			loger.error("导出excel异常:" + fileName, e);
		}
	}
	
	/**
	 * 创建excel单元格数据
	 * @param list 
	 * @param sheet    excel表格对象
	 * @param workbook 工作薄
	 */
	private static void createExcelData(List list, Sheet sheet, HSSFWorkbook workbook) {
		//得总行数与总列数
		int rowCount = list.size();
		int cellCount = ((List) list.get(0)).size();
		
		//新建一个字体对象
		HSSFFont font = (HSSFFont) workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		
		//新建单元格样式
		HSSFCellStyle TLCellStyle = getHSSFCellStyleOfTopLeft(workbook, font);
		HSSFCellStyle TLRCellStyle = getHSSFCellStyleOfTopLeftRight(workbook, font);
		HSSFCellStyle TLBCellStyle = getHSSFCellStyleOfTopLeftButtom(workbook, font);
		HSSFCellStyle TLRBCellStyle = getHSSFCellStyleOfTopLeftRightButtom(workbook, font);
		
		for (int i = 0; i < rowCount; i++) {
			List listTemp = (List) list.get(i);
			//创建行
			Row row = sheet.createRow(i);
			
			for (int j = 0; j < cellCount; j++) {
				Object obj = listTemp.get(j);
				
				//创建列
				Cell cell = row.createCell(j);
				
				//设置单元格边样式				
				if (j == cellCount - 1) {
					if (i == rowCount - 1) {
						cell.setCellStyle(TLRBCellStyle);
					} else {
						cell.setCellStyle(TLRCellStyle);
					}
				} else {
					if (i == rowCount - 1) {
						cell.setCellStyle(TLBCellStyle);
					} else {
						cell.setCellStyle(TLCellStyle);
					}
				}
				
				//设置单元格内容
				if (obj != null) {
					if (obj.getClass().getName().toLowerCase().indexOf("string") > 0) {
						cell.setCellValue(obj.toString());
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					} else {
						BigDecimal bigDecimal = new BigDecimal(obj.toString());
						if (obj.getClass().getName().toLowerCase().indexOf("integer") > 0) {
							cell.setCellValue(bigDecimal.intValue());
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
						if (obj.getClass().getName().toLowerCase().indexOf("double") > 0) {
							cell.setCellValue(bigDecimal.doubleValue());
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
						if (obj.getClass().getName().toLowerCase().indexOf("float") > 0) {
							cell.setCellValue(bigDecimal.floatValue());
						}
					}
				} else {
					cell.setCellValue("");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
		}
		
	}
	
	/**
	 * 返回单元格样式（左框线上框线）
	 * @param workbook 工作簿
	 * @param font 字体
	 * @return
	 */
	private static HSSFCellStyle getHSSFCellStyleOfTopLeft(HSSFWorkbook workbook, HSSFFont font) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	/**
	 * 返回单元格样式（左框线上框线右框线）
	 * @param workbook 工作簿
	 * @param font 字体
	 * @return
	 */
	private static HSSFCellStyle getHSSFCellStyleOfTopLeftRight(HSSFWorkbook workbook, HSSFFont font) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	/**
	 * 返回单元格样式（左框线上框线下框线）
	 * @param workbook 工作簿
	 * @param font 字体
	 * @return
	 */
	private static HSSFCellStyle getHSSFCellStyleOfTopLeftButtom(HSSFWorkbook workbook,
																	HSSFFont font) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	/**
	 * 返回单元格样式（左框线上框线下框线右框线）
	 * @param workbook 工作簿
	 * @param font 字体
	 * @return
	 */
	private static HSSFCellStyle getHSSFCellStyleOfTopLeftRightButtom(HSSFWorkbook workbook,
																		HSSFFont font) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);
		return cellStyle;
	}
}

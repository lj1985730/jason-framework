package com.yoogun.utils.infrastructure.excel;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.Reflections;
import com.yoogun.utils.infrastructure.DictUtils;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelImport;
import com.yoogun.utils.infrastructure.excel.vo.ExcelVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel文件导入器（支持“XLS”和“XLSX”格式）
 * @author ThinkGem
 * @version 2013-03-10
 */
public class ExcelImporter {
	
	private static Logger log = LoggerFactory.getLogger(ExcelImporter.class);

	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 * 标题行号
	 */
	private int headNum;

	/**
	 * 导入分组
	 */
	private String[] groups;

	/**
	 * 租户ID
	 */
	private String tenantId;
	
	/**
	 * 构造函数
	 * @param fileName 导入文件，读取第一个工作表
	 * @param headNum 标题行号，数据行号=标题行号+1
	 */
	public ExcelImporter(String fileName, int headNum)  throws InvalidFormatException, IOException {
		this(new File(fileName), headNum);
	}
	
	/**
	 * 构造函数
	 * @param file 导入文件对象，读取第一个工作表
	 * @param headNum 标题行号，数据行号=标题行号 + 1
	 */
	public ExcelImporter(File file, int headNum) throws InvalidFormatException, IOException {
		this(file, headNum, 0);
	}

	/**
	 * 构造函数
	 * @param fileName 导入文件
	 * @param headNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 */
	public ExcelImporter(String fileName, int headNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(new File(fileName), headNum, sheetIndex);
	}
	
	/**
	 * 构造函数
	 * @param file 导入文件对象
	 * @param headNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 */
	public ExcelImporter(File file, int headNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headNum, sheetIndex);
	}
	
	/**
	 * 构造函数
	 * @param multipartFile 导入文件对象
	 * @param headNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 */
	public ExcelImporter(MultipartFile multipartFile, int headNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headNum, sheetIndex);
	}

	/**
	 * 构造函数
	 * @param fileName 导入文件名
	 * @param is 导入文件流
	 * @param headNum 标题行号，数据行号 = 标题行号+1
	 * @param sheetIndex 工作表编号
	 */
	public ExcelImporter(String fileName, InputStream is, int headNum, int sheetIndex) throws InvalidFormatException, IOException {
		Workbook workbook;	//工作薄对象
		if (StringUtils.isBlank(fileName)){
			throw new RuntimeException("导入文档为空!");
		} else if(fileName.toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(is);
        } else if(fileName.toLowerCase().endsWith("xlsx")) {
        	workbook = new XSSFWorkbook(is);
        } else {
        	throw new RuntimeException("文档格式不正确!");
        }  
		if (workbook.getNumberOfSheets() < sheetIndex) {
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = workbook.getSheetAt(sheetIndex);
		this.headNum = headNum;
	}

	/**
	 * 设置导出分组
	 * @return self
	 */
	public ExcelImporter groups(String... groups) {
		this.groups = groups;
		return this;
	}

	/**
	 * 设置租户ID
	 * @return self
	 */
	public ExcelImporter tenant(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}
	
	/**
	 * 获取行对象
	 * @param rowNum 行号
	 * @return 行对象
	 */
	public Row getRow(int rowNum){
		return this.sheet.getRow(rowNum);
	}

	/**
	 * 获取数据行号
	 * @return 数据行号
	 */
	public int getDataRowNum(){
		return this.headNum + 1;
	}
	
	/**
	 * 获取最后一个数据行号
	 * @return 最后一个数据行号
	 */
	public int getLastDataRowNum(){
		return this.sheet.getLastRowNum() + this.headNum;
	}
	
	/**
	 * 获取最后一个列号
	 * @return 最后一个列号
	 */
	public int getLastCellNum(){
		return this.getRow(this.headNum).getLastCellNum();
	}
	
	/**
	 * 获取单元格值
	 * @param row 获取的行
	 * @param column 获取单元格列号
	 * @return 单元格值
	 */
	private Object getCellValue(Row row, int column){
		Object val = "";
		try{
			Cell cell = row.getCell(column);
			if (cell != null){
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					val = cell.getNumericCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
					val = cell.getStringCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					val = cell.getCellFormula();
				}else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
					val = cell.getBooleanCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
					val = cell.getErrorCellValue();
				}
			}
		} catch (Exception e) {
			return val;
		}
		return val;
	}
	
	/**
	 * 获取导入数据列表
	 * @param clazz 导入对象类型
	 */
	public <E extends BaseEntity> List<E> getDataList(Class<E> clazz) throws InstantiationException, IllegalAccessException{

		List<ExcelVo> excelVoList = ExcelFieldLoader.loadImport(clazz, this.groups);	//读取excel相关属性值对象

		// Get excel data
		List<E> dataList = new ArrayList<>();
		for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
			E entity = clazz.newInstance();
			int column = 0;
			Row row = this.getRow(i);
			for (ExcelVo vo : excelVoList) {
				Object val = this.getCellValue(row, column++);
				if (val != null) {
					ExcelImport annotation = vo.getImportAnnotation();
					// If is dict type, get dict value
					if (StringUtils.isNotBlank(annotation.dictType())) {
						val = DictUtils.getDictCode(val.toString(), annotation.dictType(), this.tenantId, "");
					}
					// Get field type and type cast
					Class<?> fieldType = vo.getField().getType();
					try {
						if (fieldType == String.class) {
							String s = String.valueOf(val.toString());
							if(StringUtils.endsWith(s, ".0")) {
								val = StringUtils.substringBefore(s, ".0");
							} else {
								val = String.valueOf(val.toString());
							}
						} else if (fieldType == Integer.class) {
							val = Double.valueOf(val.toString()).intValue();
						} else if (fieldType == Long.class) {
							val = Double.valueOf(val.toString()).longValue();
						} else if (fieldType == Double.class) {
							val = Double.valueOf(val.toString());
						} else if (fieldType == Float.class) {
							val = Float.valueOf(val.toString());
						} else if (fieldType == BigDecimal.class) {
							val = BigDecimal.valueOf(Double.valueOf(val.toString()));
						} else if (fieldType == Date.class) {
							val = DateUtil.getJavaDate(Double.valueOf(val.toString()));
						} else {
							if (annotation.fieldType() != Class.class) {	//如果定义了特殊的类型
								val = annotation.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
							}else{
								val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
										"fieldtype."+fieldType.getSimpleName()+"Type")).getMethod("getValue", String.class).invoke(null, val.toString());
							}
						}
					} catch (Exception ex) {
						log.info("Get cell value [" + i + "," + column + "] error: " + ex.toString());
						val = null;
					}

					Reflections.invokeSetter(entity, vo.getField().getName(), val);	// set entity value
				}
			}
			dataList.add(entity);
		}

		return dataList;
	}
}

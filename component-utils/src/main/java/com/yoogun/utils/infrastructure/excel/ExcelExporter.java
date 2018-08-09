package com.yoogun.utils.infrastructure.excel;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.Reflections;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.DictUtils;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelExport;
import com.yoogun.utils.infrastructure.excel.vo.ExcelVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Excel文件导出器（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExcelExporter {
	
	private static Logger log = LoggerFactory.getLogger(ExcelExporter.class);
			
	/**
	 * 工作薄对象
	 */
	private SXSSFWorkbook workbook;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 * 样式列表
	 */
	private StyleGenerator styleGenerator;
	
	/**
	 * 当前行号
	 */
	private int rowNum;

	/**
	 * 导出分组
	 */
	private String[] groups;

	/**
	 * 租户ID
	 */
	private String tenantId;
	
	/**
	 * 注解列表（Object[]{ ExcelAnnotation, Field }）
	 */
	List<ExcelVo> excelVoList = new ArrayList<>();

	/**
	 * 是否只导出模板（只有title和head，无数据行）
	 */
	private Boolean onlyTemplate;

	/**
	 * 构造器
	 * @param title 表格标题，传null表示无标题
	 * @param onlyTemplate 是否只导出模板
	 */
	public <E  extends BaseEntity> ExcelExporter(Class<E> clazz, String title, Boolean onlyTemplate) {
		this.workbook = new SXSSFWorkbook(500);	//500条一写入
		this.sheet = workbook.createSheet(title);
		this.onlyTemplate = onlyTemplate;
		this.initialize(this.sheet, clazz, title);
	}

	/**
	 * 构造器-导出数据
	 * @param title 表格标题，传null表示无标题
	 */
	public <E  extends BaseEntity> ExcelExporter(Class<E> clazz, String title) {
		this(clazz, title, false);
	}

	/**
	 * 构造器
	 * @param title 表格标题，传null表示无标题
	 * @param headers 表头列表
	 */
	public ExcelExporter(String title, List<String> headers) {
		this.workbook = new SXSSFWorkbook(500);	//500条一写入
		this.sheet = workbook.createSheet(title);
		this.initialize(this.sheet, title, headers);
	}

	/**
	 * 构造器
	 * @param title 表格标题，传null表示无标题
	 * @param headers 表头数组
	 */
	public ExcelExporter(String title, String[] headers) {
		this(title, Arrays.asList(headers));
	}

	/**
	 * 设置导出分组
	 * @return self
	 */
	public ExcelExporter groups(String... groups) {
		this.groups = groups;
		return this;
	}

	/**
	 * 设置租户ID
	 * @return self
	 */
	public ExcelExporter tenant(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	/**
	 *  初始化方法
	 * @param sheet 表格
	 * @param clazz 数据类型
	 * @param title 表格标题，传null表示无标题
	 */
	private <E  extends BaseEntity> void initialize(Sheet sheet, Class<E> clazz, String title) {
		this.excelVoList = ExcelFieldLoader.loadExport(clazz, this.groups);	//读取excel相关属性值对象

		// Initialize heads
		List<String> heads = new ArrayList<>();
		for (ExcelVo vo : excelVoList) {
			String colTitle = vo.getExportAnnotation().title();
			// 如果是导出数据，则去掉注释
			if (!this.onlyTemplate) {
				colTitle = StringUtils.substringBefore(colTitle, "**");
			}
			heads.add(colTitle);
		}
		this.initialize(sheet, title, heads);
	}

	/**
	 * 初始化-生成标题,列头,样式
	 * @param sheet 表格
	 * @param title 表格标题，传null表示无标题
	 * @param heads 表头列表
	 */
	private void initialize(Sheet sheet, String title, List<String> heads) {
		if(this.styleGenerator == null) {
			this.styleGenerator = new StyleGenerator(this.workbook);	//样式生成器
		}
		this.rowNum = 0;
		this.createTitleRow(sheet, this.rowNum++, title, heads.size());
		this.createHeadRow(sheet, this.rowNum++, heads);
	}

	/**
	 *  生成标题行
	 * @param sheet 工作表
	 * @param title 标题内容
	 * @param rowNum 标题行号
	 * @param colCount	总列数
	 */
	private void createTitleRow(Sheet sheet, int rowNum, String title, int colCount) {
		if (StringUtils.isBlank(title)){
			return;
		}
		Row row = sheet.createRow(rowNum);
		row.setHeightInPoints(30);
		Cell cell = row.createCell(0);
		cell.setCellStyle(this.styleGenerator.titleStyle());
		cell.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
					row.getRowNum(), row.getRowNum(), colCount - 1));
	}

	/**
	 *  生成列头行
	 * @param sheet 工作表
	 * @param heads	列头
	 */
	private void createHeadRow(Sheet sheet, int rowNum, List<String> heads) {
		if (heads == null) {
			throw new BusinessException("export excel: heads should not null!");
		}
		Row row = sheet.createRow(rowNum);
		row.setHeightInPoints(16);
		String head;
		for (int i = 0; i < heads.size(); i++) {
			head = heads.get(i);
			Cell cell = row.createCell(i);
			cell.setCellStyle(this.styleGenerator.headStyle());
			//增加标注
			String[] headSplit = StringUtils.split(head, "**", 2);
			if (headSplit.length == 2) {	//存在标题
				cell.setCellValue(headSplit[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(headSplit[1]));
				cell.setCellComment(comment);
			} else {
				cell.setCellValue(head);
			}
			sheet.autoSizeColumn(i);
		}
		//从新调整列宽, 列宽最小值为3000
		for (int i = 0; i < heads.size(); i++) {
			int colWidth = sheet.getColumnWidth(i) * 2;
			sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : (colWidth > 10000 ? 10000 : colWidth));
		}
	}

	/**
	 * 添加数据（通过annotation添加数据）
	 * @return 自身对象
	 */
	public <E extends BaseEntity> ExcelExporter put(List<E> datas) {
		this.appendDatas(this.sheet, datas);
		return this;
	}

	/**
	 *  追加数据，每次追加数据会新增sheet单独显示
	 * @param subClass 追加数据Class
	 * @param subTitle 追加数据标题，sheet name和title值
	 * @param datas 追加数据
	 * @return 自身对象
	 */
	public <T extends BaseEntity> ExcelExporter append(Class<T> subClass, String subTitle, List<T> datas) {
		if(datas != null && !datas.isEmpty()) {
			Sheet sheet = this.workbook.createSheet(subTitle);
			this.initialize(sheet, subClass, subTitle);	//初始化sheet，增加title、heads、style
			this.appendDatas(sheet, datas);
		}
		return this;
	}

	/**
	 *  写入数据
	 * @param sheet 网格
	 * @param datas 数据
	 */
	private <E extends BaseEntity> void appendDatas(Sheet sheet, List<E> datas) {
		for (E data : datas) {
			int colNum = 0;
			Row row = this.addRow(sheet);
			for (ExcelVo vo : excelVoList) {
				ExcelExport annotation = vo.getExportAnnotation();
				Object val;
				// Get entity value
				try {
					if (StringUtils.isNotBlank(annotation.value())) {
						val = Reflections.invokeGetter(data, annotation.value());
					} else {
						val = Reflections.invokeGetter(data, vo.getField().getName());
					}
					// If is dict, get dict label
					if (StringUtils.isNotBlank(annotation.dictType())) {
						val = DictUtils.getDictName(val == null ? "" : val.toString(), annotation.dictType(), this.tenantId, "");
					}
				} catch(Exception ex) {
					val = "";
					// Failure to ignore
					log.info(ex.toString());
				}
				this.addCell(row, colNum ++, val, annotation.align(), annotation.fieldType());
			}
		}
	}
	
	/**
	 * 输出数据流
	 * @param os 输出数据流
	 */
	public void write(OutputStream os) throws IOException {
		workbook.write(os);
	}

	/**
	 * 输出byte数组
	 */
	public byte[] write() {
		try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			workbook.write(os);
			return os.toByteArray();
		} catch (IOException ioe) {
			 throw new BusinessException("读出Excel文件失败！", ioe);
		}
	}

	/**
	 * 输出到文件
	 * @param filePath 输出文件名
	 */
	public ExcelExporter writeFile(String filePath) throws IOException {
		try(FileOutputStream os = new FileOutputStream(filePath)) {
			this.write(os);
			return this;
		} catch (IOException e) {
			throw new BusinessException("导出文件出错！", e);
		}
	}
	
	/**
	 * 清理临时文件
	 */
	public void dispose() {
		workbook.dispose();
	}


	/**
	 * 添加一行
	 * @return 行对象
	 */
	private Row addRow(Sheet sheet) {
		return sheet.createRow(rowNum++);
	}

	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @param align 对齐方式
	 * @param fieldType 属性类型
	 * @return 单元格对象
	 */
	private Cell addCell(Row row, int column, Object val, CellAlign align, Class<?> fieldType) {
		Cell cell = row.createCell(column);
		String cellFormatString = "@";
		try {
			if(val == null){
				cell.setCellValue("");
			} else if(fieldType != Class.class) {
				cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
			} else {
				if(val instanceof String) {
					cell.setCellValue((String) val);
				} else if(val instanceof Integer) {
					cell.setCellValue((Integer) val);
					cellFormatString = "0";
				} else if(val instanceof Boolean) {
					cell.setCellValue((Boolean)val ? "是" : "否");
				}else if(val instanceof Long) {
					cell.setCellValue((Long) val);
					cellFormatString = "0";
				}else if(val instanceof Double) {
					cell.setCellValue((Double) val);
					cellFormatString = "0.00";
				}else if(val instanceof Float) {
					cell.setCellValue((Float) val);
					cellFormatString = "0.00";
				}else if(val instanceof Date) {
					cell.setCellValue((Date) val);
					cellFormatString = "yyyy-MM-dd HH:mm";
				} else {
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
							"fieldtype." + val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
			//设置单元格样式
			CellStyle style = styleGenerator.getStyle("data_column_" + column);
			if (style == null) {
				switch (align) {
					case LEFT:
						style = styleGenerator.leftDataStyle();
						break;
					case CENTER:
						style = styleGenerator.centerDataStyle();
						break;
					case RIGHT:
						style = styleGenerator.rightDataStyle();
						break;
					default:
						style = styleGenerator.centerDataStyle();
				}
				style.setDataFormat(workbook.createDataFormat().getFormat(cellFormatString));
				styleGenerator.putStyle("data_column_" + column, style);
			}
			cell.setCellStyle(style);
		} catch (Exception ex) {
			log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
			cell.setCellValue(val == null ? "" : val.toString());
		}
		return cell;
	}

}

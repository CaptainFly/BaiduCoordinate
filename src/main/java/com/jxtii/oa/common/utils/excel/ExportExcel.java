package com.jxtii.oa.common.utils.excel;

import com.google.common.collect.Lists;
import com.jxtii.oa.common.utils.Encodes;
import com.jxtii.oa.common.utils.Reflections;
import com.jxtii.oa.common.utils.excel.annotation.ExcelEntity;
import com.jxtii.oa.common.utils.excel.annotation.ExcelField;
import com.jxtii.oa.common.utils.excel.fieldtype.OfficeType;
import com.jxtii.oa.modules.sys.entity.Dict;
import com.jxtii.oa.modules.sys.entity.Office;
import com.jxtii.oa.modules.sys.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 *
 * @author Fly
 * @version 2018-12-01
 */
public class ExportExcel {

    private static Logger log = LoggerFactory.getLogger(ExportExcel.class);

    /**
     * 工作薄对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;
    
    /**
     * 工作表对象集合
     */
    private List<Sheet> sheetList = Lists.newArrayList();

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    List<Object[]> annotationList = Lists.newArrayList();
    
    /**
     * 工作表情况
     */
    private Map<Integer, Integer> sheetMap = new HashMap<Integer, Integer>();

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param cls   实体对象，通过annotation.ExportField获取标题
     */
    public ExportExcel(String title, Class<?> cls) {
        this(title, cls, 1);
    }

    /**
     * 构造函数
     *
     * @param title  表格标题，传“空值”，表示无标题
     * @param cls    实体对象，通过annotation.ExportField获取标题
     * @param type   导出类型（1:导出数据；2：导出模板）
     * @param groups 导入分组
     */
    public ExportExcel(String title, Class<?> cls, int type, int... groups) {
        // Get annotation field
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, f});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, f});
                }
            }
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, m});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, m});
                }
            }
        }
        // Field sorting
        Collections.sort(annotationList, new Comparator<Object[]>() {
        	@Override
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ExcelField) o1[0]).sort()).compareTo(
                        new Integer(((ExcelField) o2[0]).sort()));
            }

            ;
        });
        // Initialize
        List<String> headerList = Lists.newArrayList();
        for (Object[] os : annotationList) {
            String t = ((ExcelField) os[0]).title();
            // 如果是导出，则去掉注释
            if (type == 1) {
                String[] ss = StringUtils.split(t, "**", 2);
                if (ss.length == 2) {
                    t = ss[0];
                }
            }
            headerList.add(t);
        }
        initialize(title, headerList);
    }
    
    
    
    /**
	 * 构造函数(导出模板配置)
	 * @param title 表格富文本标题，传“空值”，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 * @param type 导出类型（1:导出数据；2：导出模板）
	 * @param sheetNum 导出模板sheet页个数
	 * @param sheetName 导出模板sheet页名称
	 */
	public ExportExcel(String title, Class<?> cls, int type, int sheetNum, List<String> sheetName){
		// 获取注释字段
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
				annotationList.add(new Object[]{ef, f});
			}
		}
		// 获取注释方法
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms){
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
				annotationList.add(new Object[]{ef, m});
			}
		}
		// 字段排序
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		// 初始化数据
		List<ExcelEntity> sheetList = Lists.newArrayList();
		for (Object[] os : annotationList){
			//sheet页编号
			int sheetIndex = ((ExcelField)os[0]).sheetIndx();
			//富文本标题
			String t = ((ExcelField)os[0]).title();
			//必填字段
			int validate = 0;
			if(((ExcelField)os[0]).validate() == 1) {
				validate = ((ExcelField)os[0]).validate();
			}
			//如果是导出，则去掉注释
			if (type==1){
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length==2){
					t = ss[0];
				}
			}
			
			ExcelEntity ExcelEntity = new ExcelEntity(t, sheetIndex, validate); 
			sheetList.add(ExcelEntity);			
		}
		
		initialize_multiple(title, sheetList, sheetNum, sheetName);
	}
    
    

    /**
     * 构造函数
     *
     * @param title   表格标题，传“空值”，表示无标题
     * @param headers 表头数组
     */
    public ExportExcel(String title, String[] headers) {
        initialize(title, Lists.newArrayList(headers));
    }

    /**
     * 构造函数
     *
     * @param title      表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    public ExportExcel(String title, List<String> headerList) {
        initialize(title, headerList);
    }

    /**
     * 初始化函数
     *
     * @param title      表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    private void initialize(String title, List<String> headerList) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet("Export");
        this.styles = createStyles(wb);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), titleRow.getRowNum(), headerList.size() - 1));
        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("header"));
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length == 2) {
                cell.setCellValue(ss[0]);
                Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            } else {
                cell.setCellValue(headerList.get(i));
            }
            sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        log.debug("Initialize success.");
    }
    
    
    
    /**
	 * 初始化函数（导出多个sheet页）
	 * @param title 表格富文本标题，传“空值”，表示无标题
	 * @param sheetList 数据列表
	 * @param sheetNum sheet页个数
	 * @param sheetName sheet页名称
	 */
	private void initialize_multiple(String title, List<ExcelEntity> sheetList, int sheetNum, List<String> sheetName ) {
		this.wb = new SXSSFWorkbook(500);
		//创建sheet页
		for(int n=0; n<sheetNum; n++) {
			//当前行号
			this.rownum = 0;
			//当前列号
			int columnIndex = 0;
			//列名数据
			List<String> headerList = Lists.newArrayList();
			//校验数据
			List<ExcelEntity> validateList = Lists.newArrayList();
			//数据获取
			for (ExcelEntity ex : sheetList) {
				if(ex.getSheetIndx() == n) {
					headerList.add(ex.getTitle());					
					if(ex.getValidate() == 1) {
						ExcelEntity excel = new ExcelEntity();
						excel.setColumnIndex(columnIndex);
						excel.setTitle(ex.getTitle());
						validateList.add(excel);
					}
					columnIndex ++;
				} 
			}
			
			//每个sheet页字段总数
			this.sheetMap.put(n, columnIndex);
			
			//sheet页设置
			for (int sn=0; sn<sheetName.size(); sn++) {
				if(sn == n) {
					this.sheet = wb.createSheet(sheetName.get(sn));
					this.sheetList.add(this.sheet);
					break;
				}
			}			
			//创建模板样式
			this.styles = createModelStyles(wb);					
			//创建富文本标题
			if (StringUtils.isNotBlank(title)){
				Row titleRow = sheet.createRow(rownum++);
				titleRow.setHeightInPoints(40);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellStyle(styles.get("titleText"));
				titleCell.setCellValue(title);			
				sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
						titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
			}
			//创建列名
			if (headerList == null){
				throw new RuntimeException("headerList not null!");
			}
			Row headerRow = sheet.createRow(rownum++);
			headerRow.setHeightInPoints(16);
			for (int i = 0; i < headerList.size(); i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellStyle(styles.get("header"));
				String[] ss = StringUtils.split(headerList.get(i), "**", 2);
				if (ss.length==2){
					cell.setCellValue(ss[0]);
					Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
							new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
					comment.setString(new XSSFRichTextString(ss[1]));
					cell.setCellComment(comment);
				}else{					
					for (ExcelEntity validate : validateList) {
						if (validate.getTitle().equals(headerList.get(i))) {
							//必填字段设置为红色
							cell.setCellStyle(styles.get("columnColor"));
							break;
						}
					}					
					cell.setCellValue(headerList.get(i));
				}
				
				sheet.autoSizeColumn(i);
			}
			for (int i = 0; i < headerList.size(); i++) {  
				int colWidth = sheet.getColumnWidth(i)*2;
				sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
			}
		}
		
		log.debug("Initialize success.");
	}
    

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_LEFT);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        styles.put("data3", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }
    
    
    
    /**
     * 创建模板表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createModelStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        
        //设置字体颜色
		CellStyle style = wb.createCellStyle();
		Font titleFont = wb.createFont();
		titleFont.setColor(Font.COLOR_RED);		
		style.setFont(titleFont);
		styles.put("columnColor", style);
		//设置自动换行(富文本操作)
		style = wb.createCellStyle();
		style.setWrapText(true);
		styles.put("titleText", style);	
        
        return styles;
    }
    

    /**
     * 添加一行
     *
     * @return 行对象
     */
    public Row addRow() {
        return sheet.createRow(rownum++);
    }


    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val) {
        return this.addCell(row, column, val, 0, Class.class);
    }

    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @param align  对齐方式（1：靠左；2：居中；3：靠右）
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
        Cell cell = row.createCell(column);
        String cellFormatString = "@";
        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (fieldType != Class.class) {
                cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
            } else {
                if (val instanceof String) {
                    cell.setCellValue((String) val);
                } else if (val instanceof Integer) {
                    cell.setCellValue((Integer) val);
                    cellFormatString = "0";
                } else if (val instanceof Long) {
                    cell.setCellValue((Long) val);
                    cellFormatString = "0";
                } else if (val instanceof Double) {
                    cell.setCellValue((Double) val);
                    cellFormatString = "0.00";
                } else if (val instanceof Float) {
                    cell.setCellValue((Float) val);
                    cellFormatString = "0.00";
                } else if (val instanceof Date) {
                    cell.setCellValue((Date) val);
                    cellFormatString = "yyyy-MM-dd HH:mm";
                } else {
                    cell.setCellValue((String) Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                            "fieldtype." + val.getClass().getSimpleName() + "Type")).getMethod("setValue", Object.class).invoke(null, val));
                }
            }
            if (val != null) {
                CellStyle style = styles.get("data_column_" + column);
                if (style == null) {
                    style = wb.createCellStyle();
                    style.cloneStyleFrom(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));
                    style.setDataFormat(wb.createDataFormat().getFormat(cellFormatString));
                    styles.put("data_column_" + column, style);
                }
                cell.setCellStyle(style);
            }
        } catch (Exception ex) {
            log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        return cell;
    }

    /**
     * 添加数据（通过annotation.ExportField添加数据）
     *
     * @return list 数据列表
     */
    public <E> ExportExcel setDataList(List<E> list) {
        for (E e : list) {
            int colunm = 0;
            Row row = this.addRow();
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                ExcelField ef = (ExcelField) os[0];
                Object val = null;
                // Get entity value
                try {
                    if (StringUtils.isNotBlank(ef.value())) {
                        val = Reflections.invokeGetter(e, ef.value());
                    } else {
                        if (os[1] instanceof Field) {
                            val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
                        } else if (os[1] instanceof Method) {
                            val = Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[]{}, new Object[]{});
                        }
                    }
                    // If is dict, get dict label
                    if (StringUtils.isNotBlank(ef.dictType())) {
                        val = DictUtils.getDictLabel(val == null ? "" : val.toString(), ef.dictType(), "");
                    }
                } catch (Exception ex) {
                    // Failure to ignore
                    log.info(ex.toString());
                    val = "";
                }
                this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
                sb.append(val + ", ");
            }
            log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
        }
        return this;
    }

    /**
     * 输出数据流
     *
     * @param os 输出数据流
     */
    public ExportExcel write(OutputStream os) throws IOException {
        wb.write(os);
        return this;
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public ExportExcel write(HttpServletResponse response, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到文件
     *
     * @param fileName 输出文件名
     */
    public ExportExcel writeFile(String name) throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(name);
        this.write(os);
        return this;
    }

    /**
     * 清理临时文件
     */
    public ExportExcel dispose() {
        wb.dispose();
        return this;
    }
    
    
    /**
     * 获取当前sheet页信息
     * @param sheetIndex sheet页下标
     */
    public void getCurrentSheet (int sheetIndex) {
    	for (int n = 0; n < this.sheetList.size(); n++) {
			if (n == sheetIndex) {
				this.sheet = this.sheetList.get(n);				
			}
		} 	
    }
    
    
    /**
	 * 添加初始数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public <E> ExportExcel setInitList(List<E> list){
		
		int column = 0 ;
		for (Object[] objects : annotationList) {
			String[] strings = null;
			ExcelField ef = (ExcelField) objects[0];		
			if(ef!=null && !StringUtils.isEmpty(ef.dictType())){
				// 字典类型，设置下拉
				List<Dict> dictList = DictUtils.getDictList(ef.dictType());
				strings = new String[dictList.size()];
				for (int i = 0; i < dictList.size(); i++) {
					strings[i] = dictList.get(i).getLabel();
				}				
			} else if (ef.fieldType() != Class.class) {
				//反射类型，设置下拉
				String className = ef.fieldType().getSimpleName();
				if ("OfficeType".equals(className)) {
					List<Office> officeList = OfficeType.getDataList();
					strings = new String[officeList.size()];
					for (int i = 0; i < officeList.size(); i++) {
						strings[i] = officeList.get(i).getName();
					}
				}
			}
			if (strings != null) {
				// 下拉数据设置
				getCurrentSheet(ef.sheetIndx());			
				combobox(2, 500, column, column, strings);
			}
						
			for (Integer key : this.sheetMap.keySet()) {
				if (key == ef.sheetIndx()) {
					if (column == (this.sheetMap.get(key)-1)) {
						column = 0;
					} else {
						column ++;					
					}
				}
			}

		}
				
		for (E e : list){
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				ExcelField ef = (ExcelField)os[0];
				Object val = null;
				// Get entity value
				try{
					if (StringUtils.isNotBlank(ef.value())){
						val = Reflections.invokeGetter(e, ef.value());
					}else{
						if (os[1] instanceof Field){
							val = Reflections.invokeGetter(e, ((Field)os[1]).getName());
						}else if (os[1] instanceof Method){
							val = Reflections.invokeMethod(e, ((Method)os[1]).getName(), new Class[] {}, new Object[] {});
						}
					}
					// If is dict, get dict label
					if (StringUtils.isNotBlank(ef.dictType())){
						val = DictUtils.getDictLabel(val==null?"":val.toString(), ef.dictType(), "");
					}
				}catch(Exception ex) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
				sb.append(val + ", ");
			}
			log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
		}
		return this;
	}
    
    
    
    /**
	 * 下拉框设置
	 * 
	 * @param beginRow	开始行数
	 * @param endRow	结束行数
	 * @param beginCol	开始列数
	 * @param endCol	结束列数
	 * @param param		下拉框数据
	 */
	public void combobox(int beginRow, int endRow, int beginCol, int endCol, String[] param) {

		DataValidationHelper helper = this.sheet.getDataValidationHelper();
		//设置行列范围
		CellRangeAddressList addressList = new CellRangeAddressList(beginRow, endRow, beginCol, endCol);
		//设置下拉框数据
		DataValidationConstraint constraint = helper.createExplicitListConstraint(param);
		
		DataValidation dataValidation = helper.createValidation(constraint,addressList);
		// 处理Excel兼容性问题
		if (dataValidation instanceof XSSFDataValidation) {
			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowErrorBox(true);
		} else {
			dataValidation.setSuppressDropDownArrow(false);
		}
		this.sheet.addValidationData(dataValidation);
	}
    
    
    
    

//	/**
//	 * 导出测试
//	 */
//	public static void main(String[] args) throws Throwable {
//		
//		List<String> headerList = Lists.newArrayList();
//		for (int i = 1; i <= 10; i++) {
//			headerList.add("表头"+i);
//		}
//		
//		List<String> dataRowList = Lists.newArrayList();
//		for (int i = 1; i <= headerList.size(); i++) {
//			dataRowList.add("数据"+i);
//		}
//		
//		List<List<String>> dataList = Lists.newArrayList();
//		for (int i = 1; i <=1000000; i++) {
//			dataList.add(dataRowList);
//		}
//
//		ExportExcel ee = new ExportExcel("表格标题", headerList);
//		
//		for (int i = 0; i < dataList.size(); i++) {
//			Row row = ee.addRow();
//			for (int j = 0; j < dataList.get(i).size(); j++) {
//				ee.addCell(row, j, dataList.get(i).get(j));
//			}
//		}
//		
//		ee.writeFile("target/export.xlsx");
//
//		ee.dispose();
//		
//		log.debug("Export success.");
//		
//	}

}

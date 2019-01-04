package com.jxtii.oa.common.utils.excel.annotation;

/**
 * ExcelField指定参数封装类
 * @author Fly
 * @version 2018-12-01
 */
public class ExcelEntity {
	
	/**
	 * 导出字段标题
	 */
	private String title;
	
	
	/**
	 * 导出字段所属sheet页
	 */
	private int sheetIndx;
	
	
	/**
	 * 字典类型
	 */
	private String dictType;
	
	
	/**
	 * 列位置
	 */
	private int columnIndex;
	
	
	/**
	 * 类名
	 */
	private String className;
	
	
	/**
	 * 必填校验（0：非必填；1：必填）
	 */
	private int validate;

	
	
	/**
	 * 无参构造方法
	 */
	public ExcelEntity() {
		super();
	}

	
	/**
	 * @param columnIndex 列位置
	 * @param dictType 字典类型
	 */
	public ExcelEntity(int columnIndex, String dictType) {
		super();
		this.columnIndex = columnIndex;
		this.dictType = dictType;
	}
	
	/**
	 * @param title 导出字段标题
	 * @param sheetIndx 导出字段所属sheet页
	 * @param validate 必填校验
	 */
	public ExcelEntity(String title, int sheetIndx, int validate) {
		super();
		this.title = title;
		this.sheetIndx = sheetIndx;
		this.validate = validate;
	}
	
	

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getSheetIndx() {
		return sheetIndx;
	}


	public void setSheetIndx(int sheetIndx) {
		this.sheetIndx = sheetIndx;
	}


	public String getDictType() {
		return dictType;
	}


	public void setDictType(String dictType) {
		this.dictType = dictType;
	}


	public int getColumnIndex() {
		return columnIndex;
	}


	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}

	public int getValidate() {
		return validate;
	}

	public void setValidate(int validate) {
		this.validate = validate;
	}


	
	
	
}

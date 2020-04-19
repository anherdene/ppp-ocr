/**
 * 
 */
package com.altimetrik.pppocr.service.model;

/**
 * @author lakshmi
 *
 */
public class FieldCoordinate
{
	private String fieldName;
	private int pageno;
	private int xposition;
	private int yposition;
	private int width;
	private int height;
	private int zoneType;
	/**
	 * @param fieldName
	 * @param pageno
	 * @param xposition
	 * @param yposition
	 * @param width
	 * @param height
	 */
	public FieldCoordinate(String fieldName, int pageno, int xposition, int yposition, int width,
			int height,int zoneType)
	{
		this.fieldName = fieldName;
		this.pageno = pageno;
		this.xposition = xposition;
		this.yposition = yposition;
		this.width = width;
		this.height = height;
		this.zoneType = zoneType;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName()
	{
		return fieldName;
	}
	/**
	 * @return the pageno
	 */
	public int getPageno()
	{
		return pageno;
	}
	/**
	 * @return the xposition
	 */
	public int getXposition()
	{
		return xposition;
	}
	/**
	 * @return the yposition
	 */
	public int getYposition()
	{
		return yposition;
	}
	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	public int getZoneType() {
		return zoneType;
	}

	public void setZoneType(int zoneType) {
		this.zoneType = zoneType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "FieldCoordinate [fieldName=" + fieldName + ", pageno=" + pageno + ", xposition=" + xposition
				+ ", yposition=" + yposition + ", width=" + width + ", height=" + height +", zoneType=" + zoneType + "]";
	}


}

package com.oceancloud.grampus.framework.oss.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ImageInfoDTO
 *
 * @author Beck
 * @since 2022-02-17
 */
@Data
public class ImageInfoDTO implements Serializable {
	private static final long serialVersionUID = -7673533634571737850L;

	/**
	 * FileSize : {"value":"237073"}
	 * Format : {"value":"jpg"}
	 * ImageHeight : {"value":"500"}
	 * ImageWidth : {"value":"800"}
	 * ResolutionUnit : {"value":"2"}
	 * XResolution : {"value":"300/1"}
	 * YResolution : {"value":"300/1"}
	 */
	@JsonProperty("FileSize")
	private FileSizeBean fileSize;
	@JsonProperty("Format")
	private FormatBean format;
	@JsonProperty("ImageHeight")
	private ImageHeightBean imageHeight;
	@JsonProperty("ImageWidth")
	private ImageWidthBean imageWidth;
	@JsonProperty("ResolutionUnit")
	private ResolutionUnitBean resolutionUnit;
	@JsonProperty("XResolution")
	private XResolutionBean xResolution;
	@JsonProperty("YResolution")
	private YResolutionBean yResolution;

	@Data
	public static class FileSizeBean {
		/**
		 * value : 237073
		 */
		private String value;
	}

	@Data
	public static class FormatBean {
		/**
		 * value : jpg
		 */
		private String value;
	}

	@Data
	public static class ImageHeightBean {
		/**
		 * value : 500
		 */
		private String value;
	}

	@Data
	public static class ImageWidthBean {
		/**
		 * value : 800
		 */
		private String value;
	}

	@Data
	public static class ResolutionUnitBean {
		/**
		 * value : 2
		 */
		private String value;
	}

	@Data
	public static class XResolutionBean {
		/**
		 * value : 300/1
		 */
		private String value;
	}

	@Data
	public static class YResolutionBean {
		/**
		 * value : 300/1
		 */
		private String value;
	}
}

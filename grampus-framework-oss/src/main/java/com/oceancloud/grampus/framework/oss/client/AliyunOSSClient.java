package com.oceancloud.grampus.framework.oss.client;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.oceancloud.grampus.framework.core.utils.Exceptions;
import com.oceancloud.grampus.framework.core.utils.IOUtil;
import com.oceancloud.grampus.framework.core.utils.JSONUtil;
import com.oceancloud.grampus.framework.oss.dto.ImageInfoDTO;
import com.oceancloud.grampus.framework.oss.properties.AliyunOssProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 阿里云OSS操作工具
 *
 * @author Beck
 * @since 2022-07-20
 */
@Slf4j
@AllArgsConstructor
public class AliyunOSSClient {

	private final String endpoint;
	private final String accessKeyId;
	private final String accessKeySecret;
	private final String bucketName;

	public AliyunOSSClient(AliyunOssProperties properties) {
		this.endpoint = properties.getEndpoint();
		this.accessKeyId = properties.getAccesskeyId();
		this.accessKeySecret = properties.getAccesskeySecret();
		this.bucketName = properties.getBucketName();
	}

	/**
	 * 创建OSSClient实例
	 * 多个方法共用OSSClient实例时，前端连续提交会出现异常，因此OSSClinet要在每次文件操作的时候创建。
	 *
	 * @return OSSClient
	 */
	private OSS buildOSSClient() {
		return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
	}

	/**
	 * 文件上传
	 *
	 * @param path 上传路径
	 * @param data 上传数据
	 */
	public void upload(String path, byte[] data) {
		OSS client = buildOSSClient();
		try {
			client.putObject(bucketName, path, new ByteArrayInputStream(data));
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	/**
	 * 删除文件或目录。如果要删除目录，目录必须为空。
	 *
	 * @param path 文件路径
	 */
	public void delete(String path) {
		OSS client = buildOSSClient();

		try {
			client.deleteObject(bucketName, path);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	/**
	 * 下载文件
	 *
	 * @param path 文件路径
	 * @return 文件数据
	 */
	public byte[] download(String path) {
		OSS client = buildOSSClient();
		try {
			// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
			OSSObject ossObject = client.getObject(bucketName, path);

			// 读取文件内容。
			InputStream objectContent = ossObject.getObjectContent();
			byte[] bytes = IOUtil.readToByteArray(objectContent);
			// ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
			ossObject.close();
			return bytes;
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	/**
	 * 视频截帧
	 *
	 * @param path     视频相对路径
	 * @param duration 视频总时长(ms)
	 * @param slices   视频分割总张数
	 * @param pieces   视频分割第几张
	 * @return 截帧图的路径
	 */
	public String videoSnapshotUrl(String path, Long duration, Integer slices, Integer pieces) {
		long t = duration * pieces / slices;
		return this.videoSnapshotUrl(path, t);
	}

	/**
	 * 视频截帧 https://help.aliyun.com/document_detail/64555.html?spm=a2c4g.11186623.6.758.16023bd3hvcXY4
	 * <p>
	 * 参数说明
	 * 操作分类：video
	 * 操作名称：snapshot
	 * 参数	描述	取值范围
	 * t	指定截图时间。 -[0,视频时长] 单位：ms
	 * w	指定截图宽度，如果指定为0，则自动计算。    -[0,视频宽度] 单位：像素（px）
	 * h	指定截图高度，如果指定为0，则自动计算；如果w和h都为0，则输出为原视频宽高。	-[0,视频高度] 单位：像素（px）
	 * m	指定截图模式，不指定则为默认模式，根据时间精确截图。如果指定为fast，则截取该时间点之前的最近的一个关键帧。	-枚举值：fast
	 * f	指定输出图片的格式。	-枚举值：jpg、png
	 * ar	指定是否根据视频信息自动旋转图片。如果指定为auto，则会在截图生成之后根据视频旋转信息进行自动旋转。	-枚举值：auto
	 *
	 * @param path 视频相对路径
	 * @param t    指定截图时间。 -[0,视频时长] 单位：ms
	 * @return 截图访问路径
	 */
	public String videoSnapshotUrl(String path, Long t) {
//		String style = MessageFormat.format("video/snapshot,t_{0},f_jpg,w_{1},h_{2},m_fast,ar_auto", String.valueOf(t), 0, 0);
		String style = String.format("video/snapshot,t_%s,f_jpg,w_%s,h_%s,m_fast,ar_auto", t, 0, 0);
		OSS client = buildOSSClient();
		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, path, HttpMethod.GET);
		req.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000));
		req.setProcess(style);
		URL signedUrl = client.generatePresignedUrl(req);
		client.shutdown();
		return signedUrl.toString();
	}

	/**
	 * 获取图片信息
	 *
	 * @param path 图片路径
	 * @return 图片信息
	 */
	public ImageInfoDTO getImageInfo(String path) {
		String style = "image/info";
		OSS client = buildOSSClient();
		GetObjectRequest request = new GetObjectRequest(bucketName, path);
		request.setProcess(style);
		try {
			OSSObject ossObject = client.getObject(request);
			InputStream content = ossObject.getResponse().getHttpResponse().getEntity().getContent();
			String response = IOUtil.readToString(content, StandardCharsets.UTF_8);
			return JSONUtil.readValue(response, ImageInfoDTO.class);
//			StringBuilder responseStrBuilder = new StringBuilder();
//			BufferedReader streamReader = new BufferedReader(new InputStreamReader(ossObject.getResponse().getHttpResponse().getEntity().getContent(), StandardCharsets.UTF_8));
//			String inputStr;
//			while ((inputStr = streamReader.readLine()) != null) {
//				responseStrBuilder.append(inputStr);
//			}
//			return JSONUtil.readValue(responseStrBuilder.toString(), ImageInfoDTO.class);
		} catch (Exception e) {
			log.error("OSS unable to execute HTTP request requestUri:{} imagePath:{}", request.getAbsoluteUri(), path, e);
		}
		return null;
	}
}

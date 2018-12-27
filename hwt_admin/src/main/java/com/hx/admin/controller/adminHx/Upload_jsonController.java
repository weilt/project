package com.hx.admin.controller.adminHx;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hx.admin.base.ResultEntity;
import com.hx.admin.file.service.OSSService;
import com.hx.core.exception.BaseException;
import com.hx.core.utils.FileUtils;
import com.hx.core.utils.IDUtils;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.PropertiesUtils;
import com.hx.core.utils.UUIDHelper;
import com.qcloud.vod.VodApi;
import com.qcloud.vod.response.VodUploadCommitResponse;



@RestController
public class Upload_jsonController {

	@Value("${tempfile}")
	private String tempfile;
	
	@Value("${ffmpeg_path}")
	private String ffmpeg_path;
	
	@Resource
	private OSSService ossService;
	
	@RequestMapping("/pic/upload/{address}")
	public String upload_jsonUserImageController(String token,HttpServletRequest request
			,@RequestParam(name="uploadFile",required = true)MultipartFile uploadFile,@PathVariable("address") String address) {
		if (uploadFile==null){
			throw new RuntimeException("请选择图片");
		}
		
		Map<String, Object> map = new HashMap<>();
		String url = address+"/"+UUIDHelper.createUUId()+uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf('.'));
		try {
			ossService.saveImgSync(uploadFile, url);
			String EXTERNAL_ENDPOINT = PropertiesUtils.getValue("EXTERNAL_ENDPOINT", "aliyun.properties");
			String IMG_OSS_BUCKET_NAME = PropertiesUtils.getValue("IMG_OSS_BUCKET_NAME", "aliyun.properties");
			
			url = EXTERNAL_ENDPOINT.replace("http://", "https://"+IMG_OSS_BUCKET_NAME+".") + "/" + url;
			
			map.put("error", 0);
			map.put("url", url);
		} catch (IOException e) {
			map.put("error", 1);
			map.put("message", "上传失败");
			e.printStackTrace();
		}
		String json = JsonUtils.Bean2Json(map);
		return json;
		
		
		
	}
	
	
	/**
	 * 腾讯云视频上传
	 * @throws Exception 
	 */
	@RequestMapping("/tencent/video/upload")
	public ResultEntity tencentvideoupload(@RequestParam(name="uploadFile",required = true)MultipartFile uploadFile) throws Exception{
		//将文件保存在本地
		String originalFilename = uploadFile.getOriginalFilename();
		
		StringBuffer buffer = new StringBuffer(tempfile);
		 
		if(!new File(buffer.toString()).exists()) {
	            new File(buffer.toString()).mkdirs();
	       }
		
//		if (buffer.indexOf("/")==0){
//			buffer.replace(0, 1, "");
//		}
		//临时文件名称
		String createUUId = UUIDHelper.createUUId();
		
		//视频后缀
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		
		System.out.println(buffer.toString()+"---------------------");
		//传入本地
		FileUtils.uploadFile(buffer.toString(), uploadFile,createUUId+suffix);
		
		//生成封面
		//fetchFrame(buffer.toString()+createUUId+suffix, buffer.toString()+createUUId+".jpg");
		boolean processImg = processImg(buffer.toString()+createUUId+suffix, ffmpeg_path);
		if (!processImg){
			System.out.println(processImg);
			throw new BaseException("上传失败！");
		}
		System.out.println(processImg);
		System.out.println("------------"+new File(buffer.toString()+createUUId+".jpg").exists());
		
		do {
			Thread.sleep(2000);
		} while (!new File(buffer.toString()+createUUId+".jpg").exists());
		Thread.sleep(5000);
		
		System.out.println("+++++++++++++"+new File(buffer.toString()+createUUId+".jpg").exists());
		
		//上传到腾讯云
		VodApi vodApi = new VodApi("AKIDZ2KUWMtUlqkSzKczc4LQL4e2FG9rthpI", "RhleMI76wc7ScaMhttkT1NYXLFSZVJwN");
		System.out.println(uploadFile.getName());
		System.out.println();
		VodUploadCommitResponse response = vodApi.upload(buffer.toString()+createUUId+suffix,buffer.toString()+createUUId+".jpg");
		System.out.println(response.getFileId());
		
		
		//删除临时文件
		new File(buffer.toString()+createUUId+suffix).delete();
		new File(buffer.toString()+createUUId+".jpg").delete();
		
		//结果返回
		if (response.getCode()==0){
			Map<String, Object> map = new HashMap<>();
			
			map.put("fileId", response.getFileId());
			map.put("videoUrl", response.getVideo().getUrl());
			map.put("coverUrl", response.getCover().getUrl());
			
			return new ResultEntity(map);
		} else {
			throw new BaseException("上传失败！");
		}
	}
	
	
	
	/**
	 * 获取指定视频的帧并保存为图片至指定目录
	 * @param videofile  源视频文件路径
	 * @param framefile  截取帧的图片存放路径
	 * @throws Exception
	 */
	public static void fetchFrame(String videofile, String framefile)
	        throws Exception {
		System.out.println("videofile-------------"+videofile);
		System.out.println("framefile-------------"+framefile);
		
	    long start = System.currentTimeMillis();
	    File targetFile = new File(framefile);
	    FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile); 
	    ff.start();
	    int lenght = ff.getLengthInFrames();
	    int i = 0;
	    Frame f = null;
	    while (i < lenght) {
	        // 过滤前5帧，避免出现全黑的图片，依自己情况而定
	        f = ff.grabFrame();
	        if ((i > 5) && (f.image != null)) {
	            break;
	        }
	        i++;
	    }
	    IplImage img = f.image;
	    int owidth = img.width();
	    int oheight = img.height();
	    // 对截取的帧进行等比例缩放
	    int width = 800;
	    int height = (int) (((double) width / owidth) * oheight);
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
	            0, 0, null);
	    ImageIO.write(bi, "jpg", targetFile);
	    //ff.flush();
	    ff.stop();
	    System.out.println(System.currentTimeMillis() - start);
	}
	
	
	public static boolean processImg(String veido_path, String ffmpeg_path) {
		File file = new File(veido_path);
		if (!file.exists()) {
			System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
			return false;
		}
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(veido_path);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("1");// 这个参数是设置截取视频多少秒时的画面
		// commands.add("-t");
		// commands.add("0.001");
		commands.add("-s");
		commands.add("700x525");
		commands.add(veido_path.substring(0, veido_path.lastIndexOf("."))
				.replaceFirst("vedio", "file") + ".jpg");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
			System.out.println("截取成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		processImg("C:/Users/Administrator/Desktop/抖音短视频解析下载 - 抖音视频去水印保存到本地(1).mp4",
				"C:/Users/Administrator/Desktop/ffmpeg-20181128-e695b0b-win64-static/bin/ffmpeg.exe");
	}


}

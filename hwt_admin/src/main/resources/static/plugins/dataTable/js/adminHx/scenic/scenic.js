

/**
 * 上传轮播视频点击
 */
function banner_video_click(){
	//console.log($("#banner_video_cover").val())
	if (validation(["banner_video_cover"],[],["请先上传封面！"])){
		var cover = $("#banner_video_cover").val();
		if (isImage(cover,"封面图格式有误！")){
			$('#banner_video').click();
		} 
	}
}

//轮播小视频输入监听
function banner_video_change(){
	
	//console.log(11111);
	var video = $("#banner_video").val();
	if (isVDOType(video,"视频格式有误！")){
		layer_load();
		//$("#controls").attr("src",video)
		//上传文件
		var res = tencent_uploud("banner_video");
		
		//service_video_tencent("banner_video");
	} else {
		
		return;
	}
}



/**
 * 上传长视频点击
 */
function big_video_click(){
	
	if (validation(["big_video_cover"],[],["请先上传封面！"])){
		var cover = $("#big_video_cover").val();
		if (isImage(cover,"封面图格式有误！")){
			$('#big_video').click();
		} 
	}
}

//长视频输入监听
function big_video_change(){
	
	//console.log(11111);
	var video = $("#big_video").val();
	if (isVDOType(video)){
		layer_load();
		//上传文件
		var res = tencent_uploud("big_video");
		
		
		//service_video_tencent("big_video")
		
		
		
		//$("#banner_video").val("")
	} else {
		console.log(111)
		return;
	}
}

/**
 * 由服务器上传到腾讯云服务器
 */
function service_video_tencent(id){
	//$("#imgWait").show();
    var formData = new FormData();
    formData.append("uploadFile", document.getElementById(id).files[0]);   
    $.ajax({
        url: "/tencent/video/upload",
        type: "POST",
        data: formData,
        /**
        *必须false才会自动加上正确的Content-Type
        */
        contentType: false,
        /**
        * 必须false才会避开jQuery对 formdata 的默认处理
        * XMLHttpRequest会对 formdata 进行正确的处理
        */
        processData: false,
        success: function (res) {
           res = strToJson(res);
           if (id=="banner_video"){
        	   banner_video_huixian(res.data);
           } else {
        	   big_video_huixian(res.data)
           }
           layer.closeAll('loading');
           
        },
        error: function (data) {
        	console.log(data);
        	layer.closeAll('loading');
        }
    });
	
}

var getSignature = function(callback){
    $.ajax({
    	
        url: 'account/getAppTencentToken',  //获取客户端上传签名的 URL
        type: 'POST',
        dataType: 'json',
        success: function(result){//result 是派发签名服务器的回包
        	result = strToJson(result);
            //假设回包为 { "code": 0, "signature": "xxxx"  }
            //将签名传入 callback，SDK 则能获取这个上传签名，用于后续的上传视频步骤。
            return callback(result.data.signa);
        }
    });
};


/**
 * 腾讯上传视频云
 * @param video
 */
function tencent_uploud(id){
	
	var videoFiles = $("#"+id)[0];
	var videoFile = videoFiles.files[0];
	
	var coverFiles = $("#"+id+"_cover")[0];
	var coverFile = coverFiles.files[0];
	
	
	
	var res = qcVideo.ugcUploader.start({
	   
	    coverFile: coverFile,//封面，类型为 File
	    getSignature: getSignature,
	    videoFile: videoFile,
	    async:true, 
	   
	    error: function(result){
	        console.log('上传失败的文件类型：' + result.type);
	        console.log('上传失败的原因：' + result.msg);
	        layer.closeAll('loading');
	        return result;
	    },
//	    progress: function(result){
//	        console.log('上传进度：' + result.curr);
//	        //console.log(result)
//	        //res = result;
//	       // return result;
//	    },
	    finish: function(result){
	        console.log('上传结果的 fileId：' + result.fileId);
	        console.log('上传结果的视频名称：' + result.videoName);
	        console.log('上传结果的视频地址：' + result.videoUrl);
	        console.log('上传结果的封面名称：' + result.coverName);
	        console.log('上传结果的封面地址：' + result.coverUrl);
	       
	        if (id=="banner_video"){
	        	 //说明是轮播图
	        	banner_video_huixian(result);
	        	
	    	} else {
	    		 //说明不是轮播图
	    		big_video_huixian(result);
	    	}
	        layer.closeAll('loading');
	        return result;
	    }
	});
}

/**
 * 轮播图封装
 * @param result
 */
function banner_video_huixian(result){
	var fileId = result.fileId;
	var videoUrl = result.videoUrl;
	var coverUrl = result.coverUrl;
	var video_="";
	video_+='<div class="div">'
		+'<video controls="controls" poster="'+coverUrl+'" coverUrl="'+coverUrl+'" fileId="'+fileId+'" class="v_boxL" src="'+videoUrl+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
	+'<div class="line">'
	
	+'<p class="setTop" onclick="addlable(this)">置顶</p>'
	+'<p class="del" onclick="dellabe(this)">删除</p>'	
	+'</div></div>'	
	
	$("#bigbox").append(video_);
}
/**
 * 长视频封装
 * @param result
 */
function big_video_huixian(result){
	var fileId = result.fileId;
	var videoUrl = result.videoUrl;
	var coverUrl = result.coverUrl;
	var video_="";
	video_+='<div class="div">'
		+'<video controls="controls"  poster="'+coverUrl+'"  coverUrl="'+coverUrl+'" fileId="'+fileId+'"  class="v_boxL" src="'+videoUrl+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
		+'<input type="text" class="textp" placeholder="请输入视频标题"/ >'
		+'<div class="line">'
		+'<p class="setTop" onclick="addlable(this)">置顶</p>'
		+'<p class="del" onclick="dellabe(this)">删除</p>'	
		+'</div></div>'	
		
		$("#bigVideo").append(video_);
}


/**
 * 验证是否是视频
 * @param s
 * @returns {Boolean}
 */
function isVDOType(s, msg) {
	
	var index= s.indexOf("."); //（考虑严谨用lastIndexOf(".")得到）得到"."在第几位
	s=s.substring(index); //截断"."之前的，得到后缀
	if(s==".mp4"||s==".rmvb"||s==".avi"||s==".flv"||s==".wmv"){ //根据后缀，判断是否符合视频格式
		
		return true;
	} else {
		layer.msg(msg);
		return false;
	}
	
//	var patrn = /\w+(.flv|.rvmb|.mp4|.avi|.wmv)$/;
//	if (!patrn.exec(s)) {
//		 console.log(false);
//		return false;
//	}
//		
//	return true
	
}

function isImage(path, msg){
    if (path.length == 0) {
    	layer.msg(msg);
        return false;
    } else {
        var extStart = path.lastIndexOf('.'),
            ext = path.substring(extStart, path.length).toUpperCase();
        if (ext !== '.PNG' && ext !== '.JPG' && ext !== '.JPEG' && ext !== '.GIF') {
        	layer.msg(msg);
            //resetFile();
            return false;
        }
    }
    
    return true;

}

/**
 * 上传加载
 */
function layer_load(){
	
	layer.load(1,{
		//content: '上传中...'
	});
}
/**
 * 加载关闭
 */
function layerLoadClose(){
	layer.closeAll('loading');
}

/**
 * 轮播图处理
 */
function bannerHandle(){
	var banner_videos = [];
	
	var image = "[";
	
	var banners = $("#bigbox .div");
	if (banners.length>0){
		for (var i = 0; i < banners.length; i++) {
			
			var banner = banners[i];
			
			
			if ($(banner).find('video').length > 0){
				//说明封面是视频
				var video = $(banner).find('video');
				if (i == 0){
					$("#cover_type").val(2);
				} else {
					$("#cover_type").val(1);
				}
				var coverUrl = $(video).attr("coverurl");
				var videoUrl = $(video).attr("src");
				var fileId = $(video).attr("fileid");
				
				var banner_video = {
						"coverUrl":coverUrl,
						"videoUrl":videoUrl,
						"fileId":fileId
				}
				//banner_videos.push(jsonToStr(banner_video));
				banner_videos.push(banner_video);
				
			} else {
				var img = $(banner).find('img');
				image+=$(img).attr("src")+","
			}
				
			
		}
	}
	var banner_video = jsonToStr(banner_videos);
	console.log(banner_video)
	$("input[name = 'banner_videos']").val(banner_video);
	
	if ( image.charAt(image.length - 1) == ","){
		image = image.substring(0,image.length-1)
	}
	image+="]"
		$("input[name = 'imageUrls']").val(image);
}

/**
 * 长视频处理
 */
function bivideoHandle(){
	var big_videos = [];
	
	var bigVideos = $("#bigVideo .div");
	
	if (bigVideos.length>0){
		for (var i = 0; i < bigVideos.length; i++) {
			
			var bigVideo = bigVideos[i];
			
			var video = $(bigVideo).find('video');
			
			var coverUrl = $(video).attr("coverurl");
			var videoUrl = $(video).attr("src");
			var fileId = $(video).attr("fileid");
			var dec = $(video).next("input")
			var dect = $(dec).val();
			console.log(dec);
			if(dect.length < i+1) {
				
				layer.msg("长视频描述没有填！");
				$(dect).focus();
				return false;
			} 
			
			var big_video = {
					"coverUrl":coverUrl,
					"videoUrl":videoUrl,
					"fileId":fileId,
					"dec":dect,
			}
			//big_videos.push(jsonToStr(big_video));
			big_videos.push(big_video);
			
		}
	}
	
	$("input[name = 'big_videos']").val(jsonToStr(big_videos));
	return true;
}

	
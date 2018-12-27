function scenicInfo(spotId,url,dec){
    		
			$.ajax({
    	        url : url,
    	        type : "POST",
    	        data : {"spotId":spotId},
    	        success : function (res) {
    	        	
    	        	res = strToJson(res);
    	        	 
    	        		var scenicInfo = res.data;
    	        		var spotId = scenicInfo.spotId == null||scenicInfo.spotId == "null"?"":scenicInfo.spotId;
    	        		var spotName = scenicInfo.spotName == null||scenicInfo.spotName == "null"?"":scenicInfo.spotName;
    	        		var country = scenicInfo.country == null||scenicInfo.country == "null"?"":scenicInfo.country;
    	        		var city = scenicInfo.city == null||scenicInfo.city == "null"?"":scenicInfo.city;
    	        		var description = scenicInfo.description == null||scenicInfo.description == "null"?"":scenicInfo.description;
    	        		var brief = scenicInfo.brief == null||scenicInfo.brief == "null"?"":scenicInfo.brief;
    	        		var subtitle = scenicInfo.subtitle == null||scenicInfo.subtitle == "null"?"":scenicInfo.subtitle;
    	        		var rating = scenicInfo.rating == null||scenicInfo.rating == "null"?"":scenicInfo.rating;
    	        		var telephones = scenicInfo.telephones == null||scenicInfo.telephones == "null"?"":scenicInfo.telephones;
    	        		var geoPoint = scenicInfo.geoPoint == null||scenicInfo.geoPoint == "null"?"":scenicInfo.geoPoint;
    	        		var tags = scenicInfo.tags == null||scenicInfo.tags == "null"?"":scenicInfo.tags;
    	        		var imageUrls = scenicInfo.imageUrls == null||scenicInfo.imageUrls == "null"?"":scenicInfo.imageUrls;
    	        		imageUrls = imageUrls.replace("[","");
    	        		imageUrls = imageUrls.replace("]","");
    	        		
    	        		var imageHtml = "";
    	        		if ($.trim(imageUrls)!=""){
    	        			imageUrls = imageUrls.split(",");
	    	        		imageHtml +='<div id="bigbox" class="bigbox">'
	    	        		for(var i = 0; i<imageUrls.length; i++){
	    	        			var imageUrl = imageUrls[i];
	    	        			
	    	        			if ($.trim(imageUrl)!=""){
		    	        			imageHtml +='<div ><a href="javascript:void(0);" onclick="catImage(\''+imageUrl+'\')"><img class="imgbox" src="'+imageUrl+'" /></a>'
		    	        			+'</div>'
	    	        			}
	    	        		}
	    	        		imageHtml+='</div>';
    	        		}
    	        		var imageUrls = scenicInfo.imageUrls == null||scenicInfo.imageUrls == "null"?"":scenicInfo.imageUrls;
       	        		var banner_videos = scenicInfo.banner_videos == null||scenicInfo.banner_videos == "null"?"[]":scenicInfo.banner_videos;
       	        		console.log(banner_videos);
       	        		banner_videos = strToJson2(banner_videos);
       	        		var hxUserVideos = scenicInfo.hxUserVideos == null||scenicInfo.hxUserVideos == "null"?"[]":scenicInfo.hxUserVideos;
       	        		hxUserVideos = strToJson2(hxUserVideos);
       	        		console.log(hxUserVideos);
       	        		var cover_type = scenicInfo.cover_type == null||scenicInfo.cover_type == "null"?1:scenicInfo.cover_type;
       	        		imageUrls = imageUrls.replace("[","");
    	        		imageUrls = imageUrls.replace("]","");
       	        		imageUrls = imageUrls.split(",");
       	        		var imageHtml = "";
       	        		imageHtml +='<div id="bigbox" class="bigbox">'
//       	        			+'<div class="btu">'
//       	            		+'<span onclick="$(\'#banner_video\').click()">上传视频</span>'
//	       	         		+'<input type="file" id="banner_video" hidden  onchange="banner_video_change()"/>'
//	       	            		+'<span class="picFileUpload">上传图片（可多张）</span>'
//	       	         		+'</div>'
	       	         	if (cover_type==1){
		       	         	for(var i = 0; i<imageUrls.length; i++){
	       	        			var imageUrl = imageUrls[i];
	       	        			
	       	        			if ($.trim(imageUrl)!=""){
	       	        				
		       	        			imageHtml +='<div  class="div"><a href="javascript:void(0);" onclick="catImage(\''+imageUrl+'\')"><img class="imgbox" src="'+imageUrl+'" /></a>'
		       	        			//+'<div class="line"><p class="setTop" onclick="addlable(this)">置顶</p><p class="del" onclick="dellabe(this)">删除</p></div>'
		       	        			+'</div>'
	       	        			}
	       	        		}
		       	         	for (var i = 0; i < banner_videos.length; i++) {
								var banner_video = banner_videos[i]
								imageHtml+='<div class="div">'
									+'<video controls="controls" poster="'+banner_video.coverUrl+'" coverUrl="'+banner_video.coverUrl+'" fileId="'+banner_video.fileId+'" class="v_boxL" src="'+banner_video.videoUrl+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
//								+'<div class="line">'
//								
//								+'<p class="setTop" onclick="addlable(this)">置顶</p>'
//								+'<p class="del" onclick="dellabe(this)">删除</p>'	
//								+'</div>'
								+'</div>'	
							}
	       	         	} else {
	       	         		
	       	         		for (var i = 0; i < banner_videos.length; i++) {
								var banner_video = banner_videos[i]
								imageHtml+='<div class="div">'
									+'<video controls="controls" poster="'+banner_video.coverUrl+'" coverUrl="'+banner_video.coverUrl+'" fileId="'+banner_video.fileId+'" class="v_boxL" src="'+banner_video.videoUrl+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
//									+'<div class="line">'
//									
//									+'<p class="setTop" onclick="addlable(this)">置顶</p>'
//									+'<p class="del" onclick="dellabe(this)">删除</p>'	
//									+'</div>'
									+'</div>'	
							}
		       	         	for(var i = 0; i<imageUrls.length; i++){
	       	        			var imageUrl = imageUrls[i];
	       	        			
	       	        			if ($.trim(imageUrl)!=""){
	       	        				
		       	        			imageHtml +='<div  class="div"><a href="javascript:void(0);" onclick="catImage(\''+imageUrl+'\')"><img class="imgbox" src="'+imageUrl+'" /></a>'
		       	        			//+'<div class="line"><p class="setTop" onclick="addlable(this)">置顶</p><p class="del" onclick="dellabe(this)">删除</p></div>'
		       	        			+'</div>'
	       	        			}
	       	        		}
	       	         	}
       	        		
       	        		imageHtml+='</div>';
       	        		var bigVideo = "";
	       	     		bigVideo +='<div id="bigVideo" class="bigbox">'
//	       	            		+'<div class="btu">'
//	       	            		+'<span onclick="$(\'#big_video\').click()">上传视频</span>'
//	       	         		+'<input type="file" id="big_video" hidden  onchange="big_video_change()"/>'
//	       	         		+'</div>'
       	     		
       	        		//imageHtml+='<div  class="addimgbtn picFileUpload"><h1>添加</h1></div>';
       	          
       	        		for (var i = 0; i < hxUserVideos.length; i++) {
							var hxUserVideo = hxUserVideos[i]
							console.log(hxUserVideos)
							console.log(hxUserVideo)
							bigVideo+='<div class="div">'
								+'<video controls="controls" poster="'+hxUserVideo.image+'" coverUrl="'+hxUserVideo.image+'" fileId="'+hxUserVideo.file_id+'" class="v_boxL" src="'+hxUserVideo.content+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
								//+'<input type="text" class="textp" value="'+hxUserVideo.dec+'" placeholder="请输入视频标题"/ >'
								+'<span class="textp" >'+hxUserVideo.dec+'</span>'
//								+'<div class="line">'
//								
//								+'<p class="setTop" onclick="addlable(this)">置顶</p>'
//								+'<p class="del" onclick="dellabe(this)">删除</p>'	
//								+'</div>'
								+'</div>'	
							
						}
	       	     		bigVideo+='</div>';
    	        		
    	        		var rank = scenicInfo.rank == null||scenicInfo.rank == "null"?"":scenicInfo.rank;
    	        		var openingHours = scenicInfo.openingHours == null||scenicInfo.openingHours == "null"?"":scenicInfo.openingHours;
    	        		var location = scenicInfo.location == null||scenicInfo.location == "null"?"":scenicInfo.location;
    	        		var ticketInfo = scenicInfo.ticketInfo == null||scenicInfo.ticketInfo == "null"?"":scenicInfo.ticketInfo;
    	        		var dataUrl = scenicInfo.dataUrl == null||scenicInfo.dataUrl == "null"?"":scenicInfo.dataUrl;
    	        		var dataSources = scenicInfo.dataSources == null||scenicInfo.dataSources == "null"?"":scenicInfo.dataSources;
    	        		var isHide = scenicInfo.isHide == null||scenicInfo.isHide == "null"?"":scenicInfo.isHide;
    	        		var visitsNum = scenicInfo.visitsNum == null||scenicInfo.visitsNum == "null"?"":scenicInfo.visitsNum;
    	        		var remarks = scenicInfo.remarks == null||scenicInfo.remarks == "null"?"":scenicInfo.remarks;
    	        		var dataTime = scenicInfo.create_time == null||scenicInfo.create_time == "null"?"":scenicInfo.create_time;
    	        		dataTime = timestampToTime(dataTime);
    	        		var _html = '';
    	        		                                                                                                                                                                       
    	        		_html +='     <div class="br-pagebody">                                                                                                                                      '
    	        			 +'                                                                                                                                                                    '
    	        			 +'       <div class="br-section-wrapper">                                                                                                                             '
    	        			 +'           <!--内容-->                                                                                                                                              '
    	        			
    	        			 +'          <div class="row mg-t-20">                                                                                                                                 '
    	        			 +'               <p style="cursor: auto; width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点ID:</p>             '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+spotId+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'                                                                                                                                                                    '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点名称:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+spotName+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>所在国家:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+country+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>城市:</p>                                     '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+city+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点描述:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<textarea class="form-control wd-50%" rows="5"  readonly="readonly" >'+description+'</textarea>'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点简介:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+brief+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>小标题:</p>                                   '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+subtitle+'">'
    	        			 +'                                                                                                                                                                    '
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点评分:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+rating+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>联系电话:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+telephones+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'                                                                                                                                                                    '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>定位坐标:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+geoPoint+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div><div class="row mg-t-20">                                                                                                                          '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>标签:</p>                                     '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+tags+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点等级:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+rank+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>开放时间:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+openingHours+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>地址:</p>                                     '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+location+'"> '
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>门票信息:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+ticketInfo+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'            <div class="row mg-t-20">                                                                                                                               '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>来源景点URL:</p>                              '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+dataUrl+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'            <div class="row mg-t-20">                                                                                                                               '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点备注:</p>                              '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+remarks+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'            <div class="row mg-t-20">                                                                                                                               '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>数据来源:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+dataSources+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'            <div class="row mg-t-20">                                                                                                                               '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>是否隐藏:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+isHide+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'            <div class="row mg-t-20">                                                                                                                               '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>访问次数:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+visitsNum+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'                                                                                                                                                                    '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>添加时间:</p>                                 '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +'<input class="form-control wd-50%" readonly="readonly" type="text" value="'+dataTime+'">'
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点图片组:</p>                               '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +imageHtml
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 +'           <div class="row mg-t-20">                                                                                                                                '
    	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>长视频:</p>                               '
    	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
    	        			 +bigVideo
    	        			 +'               </div>                                                                                                                                               '
    	        			 +'               <!-- col -->                                                                                                                                         '
    	        			 +'           </div>                                                                                                                                                   '
    	        			 
    	        			 +'       </div><!-- br-section-wrapper -->                                                                                                                            '
    	        			 +'     </div><!-- br-pagebody -->                                                                                                                                     '
    	        			 fromEject(_html,dec,'80%','80%');
	    	        		
    	        	 
    	        }
    	    })
    		
    	}
    	
    	
    	
    	
    	function edit(spotId,url,dec,type){
    		var _html = '';
    		$.ajax({
    	        url : url,
    	        type : "POST",
    	        data : {"spotId":spotId,"type":type},
    	        success : function (res) {
    	        	console.log(res);
    	        	res = strToJson(res);
    	        	jsonState(res);
    	           
       	      
       	        		var scenicInfo = res.data;
       	        		var spotId = scenicInfo.spotId == null||scenicInfo.spotId == "null"?"":scenicInfo.spotId;
       	        		var spotName = scenicInfo.spotName == null||scenicInfo.spotName == "null"?"":scenicInfo.spotName;
       	        		var country = scenicInfo.country == null||scenicInfo.country == "null"?"":scenicInfo.country;
       	        		var city = scenicInfo.city == null||scenicInfo.city == "null"?"":scenicInfo.city;
       	        		var description = scenicInfo.description == null||scenicInfo.description == "null"?"":scenicInfo.description;
       	        		var brief = scenicInfo.brief == null||scenicInfo.brief == "null"?"":scenicInfo.brief;
       	        		var subtitle = scenicInfo.subtitle == null||scenicInfo.subtitle == "null"?"":scenicInfo.subtitle;
       	        		var rating = scenicInfo.rating == null||scenicInfo.rating == "null"?"":scenicInfo.rating;
       	        		var telephones = scenicInfo.telephones == null||scenicInfo.telephones == "null"?"":scenicInfo.telephones;
       	        		var geoPoint = scenicInfo.geoPoint == null||scenicInfo.geoPoint == "null"?"":scenicInfo.geoPoint;
       	        		var tags = scenicInfo.tags == null||scenicInfo.tags == "null"?"":scenicInfo.tags;
       	        		var imageUrls = scenicInfo.imageUrls == null||scenicInfo.imageUrls == "null"?"":scenicInfo.imageUrls;
       	        		var banner_videos = scenicInfo.banner_videos == null||scenicInfo.banner_videos == "null"?"[]":scenicInfo.banner_videos;
       	        		console.log(banner_videos);
       	        		banner_videos = strToJson2(banner_videos);
       	        		var hxUserVideos = scenicInfo.hxUserVideos == null||scenicInfo.hxUserVideos == "null"?"[]":scenicInfo.hxUserVideos;
       	        		hxUserVideos = strToJson2(hxUserVideos);
       	        		console.log(hxUserVideos);
       	        		var cover_type = scenicInfo.cover_type == null||scenicInfo.cover_type == "null"?1:scenicInfo.cover_type;
       	        		imageUrls = imageUrls.replace("[","");
    	        		imageUrls = imageUrls.replace("]","");
       	        		imageUrls = imageUrls.split(",");
       	        		var imageHtml = "";
       	        		imageHtml +='<div id="bigbox" class="bigbox">'
       	        			+'<div class="btu">'
       	        			+'<span onclick="$(\'#banner_video_cover\').click()">上传视频封面</span>'
       	            		+'<input type="file" id="banner_video_cover" hidden  "/>'
       	            		+'<span onclick="banner_video_click()">上传视频</span>'
       	         		+'<input type="file" id="banner_video" hidden  onchange="banner_video_change()"/>'
	       	            		+'<span class="picFileUpload">上传图片（可多张）</span>'
	       	         		+'</div>'
	       	         	if (cover_type==1){
		       	         	for(var i = 0; i<imageUrls.length; i++){
	       	        			var imageUrl = imageUrls[i];
	       	        			
	       	        			if ($.trim(imageUrl)!=""){
	       	        				
		       	        			imageHtml +='<div  class="div"><a href="javascript:void(0);" onclick="catImage(\''+imageUrl+'\')"><img class="imgbox" src="'+imageUrl+'" /></a>'
		       	        			+'<div class="line"><p class="setTop" onclick="addlable(this)">置顶</p><p class="del" onclick="dellabe(this)">删除</p></div>'
		       	        			+'</div>'
	       	        			}
	       	        		}
		       	         	for (var i = 0; i < banner_videos.length; i++) {
								var banner_video = banner_videos[i]
								imageHtml+='<div class="div">'
									+'<video controls="controls" poster="'+banner_video.coverUrl+'" coverUrl="'+banner_video.coverUrl+'" fileId="'+banner_video.fileId+'" class="v_boxL" src="'+banner_video.videoUrl+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
								+'<div class="line">'
								
								+'<p class="setTop" onclick="addlable(this)">置顶</p>'
								+'<p class="del" onclick="dellabe(this)">删除</p>'	
								+'</div></div>'	
							}
	       	         	} else {
	       	         		
	       	         		for (var i = 0; i < banner_videos.length; i++) {
								var banner_video = banner_videos[i]
								imageHtml+='<div class="div">'
									+'<video controls="controls" poster="'+banner_video.coverUrl+'" coverUrl="'+banner_video.coverUrl+'" fileId="'+banner_video.fileId+'" class="v_boxL" src="'+banner_video.videoUrl+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
								+'<div class="line">'
								
								+'<p class="setTop" onclick="addlable(this)">置顶</p>'
								+'<p class="del" onclick="dellabe(this)">删除</p>'	
								+'</div></div>'	
							}
		       	         	for(var i = 0; i<imageUrls.length; i++){
	       	        			var imageUrl = imageUrls[i];
	       	        			
	       	        			if ($.trim(imageUrl)!=""){
	       	        				
		       	        			imageHtml +='<div  class="div"><a href="javascript:void(0);" onclick="catImage(\''+imageUrl+'\')"><img class="imgbox" src="'+imageUrl+'" /></a>'
		       	        			+'<div class="line"><p class="setTop" onclick="addlable(this)">置顶</p><p class="del" onclick="dellabe(this)">删除</p></div>'
		       	        			+'</div>'
	       	        			}
	       	        		}
	       	         	}
       	        		
       	        		imageHtml+='</div>';
       	        		var bigVideo = "";
	       	     		bigVideo +='<div id="bigVideo" class="bigbox">'
	       	            		+'<div class="btu">'
	       	            		+'<span onclick="$(\'#big_video_cover\').click()">上传视频封面</span>'
	       	            		+'<input type="file" id="big_video_cover" hidden "/>'
	       	            		+'<span onclick="big_video_click()">上传视频</span>'
	       	         		+'<input type="file" id="big_video" hidden  onchange="big_video_change()"/>'
	       	         		+'</div>'
       	     		
       	        		//imageHtml+='<div  class="addimgbtn picFileUpload"><h1>添加</h1></div>';
       	          
       	        		for (var i = 0; i < hxUserVideos.length; i++) {
							var hxUserVideo = hxUserVideos[i]
							console.log(hxUserVideos)
							console.log(hxUserVideo)
							bigVideo+='<div class="div">'
								+'<video controls="controls" poster="'+hxUserVideo.image+'" coverUrl="'+hxUserVideo.image+'" fileId="'+hxUserVideo.file_id+'" class="v_boxL" src="'+hxUserVideo.content+'" style="width: 100%;height: 180px;"> 您的浏览器不支持 video 标签。</video>'
								+'<input type="text" class="textp" value="'+hxUserVideo.dec+'" placeholder="请输入视频标题"/ >'
								+'<div class="line">'
							
							+'<p class="setTop" onclick="addlable(this)">置顶</p>'
							+'<p class="del" onclick="dellabe(this)">删除</p>'	
							+'</div></div>'	
							
						}
	       	     		bigVideo+='</div>';
       	        		var rank = scenicInfo.rank == null||scenicInfo.rank == "null"?"":scenicInfo.rank;
       	        		var openingHours = scenicInfo.openingHours == null||scenicInfo.openingHours == "null"?"":scenicInfo.openingHours;
       	        		var location = scenicInfo.location == null||scenicInfo.location == "null"?"":scenicInfo.location;
       	        		var ticketInfo = scenicInfo.ticketInfo == null||scenicInfo.ticketInfo == "null"?"":scenicInfo.ticketInfo;
       	        		var dataUrl = scenicInfo.dataUrl == null||scenicInfo.dataUrl == "null"?"":scenicInfo.dataUrl;
       	        		var dataSources = scenicInfo.dataSources == null||scenicInfo.dataSources == "null"?"":scenicInfo.dataSources;
       	        		var isHide = scenicInfo.isHide == null||scenicInfo.isHide == "null"?"":scenicInfo.isHide;
       	        		var isHideHtml_ = '';
       	        		isHideHtml_ += '<option value="1" ';
       	        		if (isHide==1){
       	        			isHideHtml_+='selected="selected"'
       	        		}
       	        		isHideHtml_+='>显示</option><option value="0" ';
       	        		if (isHide==0){
       	        			isHideHtml_+='selected="selected"'
       	        		}
       	        		isHideHtml_+='>隐藏</option>'
       	        		var visitsNum = scenicInfo.visitsNum == null||scenicInfo.visitsNum == "null"?"":scenicInfo.visitsNum;
       	        		var dataTime = scenicInfo.create_time == null||scenicInfo.create_time == "null"?"":scenicInfo.create_time;
       	        		var remarks = scenicInfo.remarks == null||scenicInfo.remarks == "null"?"":scenicInfo.remarks;
       	        		
       	        		var _html = '';
       	        		_html+='     <div class="br-pagebody">                                                                                                                                      '
   	        			 +'     <form id="form">                                                                                                                                                               '
	        			 +'       <div class="br-section-wrapper">                                                                                                                             '
	        			 +'           <!--内容-->  <input type="hidden" id="geoPoint"  name="geoPoint" value="'+geoPoint+'"/>   <input type="hidden" id="imageUrls"  name="imageUrls" value=""/>  <input type="hidden"  id="spotId" name="spotId" value="'+spotId+'"/><input type="hidden"  name="type" value="2"/><input type="hidden" id = "create_time"  name="create_time" value="'+dataTime+'"/>                                                                                                                                   '
	        			 +'<input type="hidden" id="cover_type"  name="cover_type" value="'+cover_type+'"/> <input type="hidden" id="banner_videos"  name="banner_videos" value=""/>'
	        			 +'<input type="hidden" id="big_videos"  name="big_videos" value=""/>'
	        			 +'                                                                                                                                                                    '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点名称:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" id="spotName" name="spotName" type="text" value="'+spotName+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>所在国家:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" id="country" name="country" type="text" value="'+country+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>城市:</p>                                     '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" id="city" name="city" type="text" value="'+city+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点描述:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<textarea class="form-control wd-50%" rows="5" id="description"  name="description" >'+description+'</textarea>'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点简介:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="brief" id="brief" type="text" value="'+brief+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>小标题:</p>                                   '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="subtitle" id="subtitle" type="text" value="'+subtitle+'">'
	        			 +'                                                                                                                                                                    '
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点评分:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="rating" id="rating" type="text" value="'+rating+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>联系电话:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="telephones" id="telephones" type="text" value="'+telephones+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'                                                                                                                                                                    '
	        			 +'           <div class="row mg-t-20">                                                                                                                          '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>标签:</p>                                     '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="tags" id="tags" type="text" value="'+tags+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点等级:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="rank" id="rank" type="text" value="'+rank+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>开放时间:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="openingHours" id="openingHours" type="text" value="'+openingHours+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>地址:</p>                                     '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<div id="myPageTop" class="">'
	        			 +'	<input class="form-control wd-50%" name="location" id="location" type="text" onfocus="locationWS()" onblur="locationWE()" value="'+location+'"> '
	        			 +'</div>'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>门票信息:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="ticketInfo" id="ticketInfo" type="text" value="'+ticketInfo+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'            <div class="row mg-t-20">                                                                                                                               '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>来源景点URL:</p>                              '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="dataUrl" id="dataUrl" type="text" value="'+dataUrl+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'            <div class="row mg-t-20">                                                                                                                               '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点备注:</p>                              '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="remarks" id="remarks" type="text" value="'+remarks+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'            <div class="row mg-t-20">                                                                                                                               '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>数据来源:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="dataSources" id="dataSources" type="text" value="'+dataSources+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'            <div class="row mg-t-20">                                                                                                                               '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>是否隐藏:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'                    <select class="form-control select2 " id="isHide" name="isHide" data-placeholder="sex" tabindex="-1" aria-hidden="true">                                                           '
	                		+isHideHtml_                
	                		+'                                                                                                                                                                                                '
	                		+'                    </select>     '
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'            <div class="row mg-t-20">                                                                                                                               '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>访问次数:</p>                                 '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +'<input class="form-control wd-50%" name="visitsNum" id="visitsNum" type="text" value="'+visitsNum+'">'
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'                                                                                                                                                                    '
	        			                                                                                                                                       
	        			 
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点图片组:</p>                               '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +imageHtml
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 +'           <div class="row mg-t-20">                                                                                                                                '
	        			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>长视频:</p>                               '
	        			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
	        			 +bigVideo
	        			 +'               </div>                                                                                                                                               '
	        			 +'               <!-- col -->                                                                                                                                         '
	        			 +'           </div>                                                                                                                                                   '
	        			 
	        			 +'            <div class="row mg-t-20">                                                                                                                                                           '
	                		+'                <div class="btn btn-secondary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15" onclick="cancelFormLayer()">取消</div>                                   '
	                		+'                <div class="btn btn-outline-primary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15 mg-l-10" onclick="updateScenicSpot(\''+url+'\',\'form\')">确定</div>'
	                		+'                                                                                                                                                                                                '
	                		+'            </div>                                                                                                                                                                              '
	                		
	        			 +'       </div><!-- br-section-wrapper -->                                                                                                                            '
	        			 +'     </div></form><!-- br-pagebody -->   '                                                                                                                                                                  
	        			 fromEject(_html,dec,'80%','80%');
       	        		var itemAddEditor ;  
    	        	    //页面初始化完毕后执行此方法  
    	        	    $(function(){  
    	        	        //初始化类目选择和图片上传器  
    	        	        E3.init({fun:function(node){  
    	        	        }});  
    	        	    });  
    	        	    gaode();
    	            
    	       
    	        }
    	    })
    	}
    	
    	
    	
    	var bigbox = document.getElementById('bigbox');
    	
    	function dellabe(is){
    		$(is).parent().parent().remove();
    		/* var parentDiv =  is.parentNode.parentNode;
    		bigbox.removeChild(parentDiv); */
    	}
    	
    	function addlable(is){
    		/* var parentDiv =  is.parentNode.parentNode;
    		bigbox.removeChild(parentDiv);
    		bigbox.insertBefore(parentDiv , bigbox.children[0]); */
    		var a = $(is).parent().parent();
    		var bigbox = $(a).parent().attr("id")
    		$(is).parent().parent().remove();
    		$("#"+bigbox).prepend(a)
    	}
    	
    	function updateScenicSpot(url,form){
    		/*var imgs = $("#bigbox").find("img")
    		var image = "[";
    		for(var i = 0; i<imgs.length; i++){
    			var img = imgs[i];
    			if (i!=imgs.length-1){
    				image+=$(img).attr("src")+","
    			}else{
    				image+=$(img).attr("src")
    			}
    			
    		}
    		image+="]"
    			$("input[name = 'imageUrls']").val(image);*/
    		
    		//轮播图处理
    		bannerHandle();
    		//上视频处理
    		if (bivideoHandle()){
    			
    		} else {
    			return
    		}
    		
    		
    		if(validation(["telephones","spotId","spotName","country","city","location"],["PHONE"],[])){
    			update(url,form);
    		}else{
    			return
    		}
    	}
    	/**
    	添加
    	*/
    	function insertScenicSpot(url,form){
    		/*var imgs = $("#bigbox").find("img")
    		var image = "[";
    		for(var i = 0; i<imgs.length; i++){
    			var img = imgs[i];
    			if (i!=imgs.length-1){
    				image+=$(img).attr("src")+","
    			}else{
    				image+=$(img).attr("src")
    			}
    			
    		}
    		image+="]"
    			$("input[name = 'imageUrls']").val(image);*/
    		
    		//轮播图处理
    		bannerHandle();
    		//上视频处理
    		if (bivideoHandle()){
    			
    		} else {
    			return
    		}
    		if (validation(["telephones","spotName","country","city","location"],["PHONE"],[])){
    			
    			insert(url,form);
    		}else{
    			return
    		}
    	}
    	
    	
    	
    	
    	function add(){
    		var imageHtml = "";
       		imageHtml +='<div id="bigbox" class="bigbox">'
       		+'<div class="btu">'
       		+'<span onclick="$(\'#banner_video_cover\').click()">上传视频封面</span>'
       		+'<input type="file" id="banner_video_cover" hidden  "/>'
       		+'<span onclick="banner_video_click()">上传视频</span>'
    		+'<input type="file" id="banner_video" hidden  onchange="banner_video_change()"/>'
       		+'<span class="picFileUpload">上传图片（可多张）</span>'
    		+'</div>'
    		
    		//imageHtml+='<div  class="addimgbtn picFileUpload"><h1>添加</h1></div>';
       		imageHtml+='</div>';
    		
    		var bigVideo = "";
    		bigVideo +='<div id="bigVideo" class="bigbox">'
           		+'<div class="btu">'
           		+'<span onclick="$(\'#big_video_cover\').click()">上传视频封面</span>'
           		+'<input type="file" id="big_video_cover" hidden "/>'
           		+'<span onclick="big_video_click()">上传视频</span>'
        		+'<input type="file" id="big_video" hidden  onchange="big_video_change()"/>'
        		+'</div>'
    		
       		//imageHtml+='<div  class="addimgbtn picFileUpload"><h1>添加</h1></div>';
          	bigVideo+='</div>';
       		
       		var isHideHtml_ = '';
       		isHideHtml_ += '<option value="1" ';
       		
       			isHideHtml_+='selected="selected"';
       		
       		isHideHtml_+='>显示</option><option value="0" ';
       		
       		
       	
       		isHideHtml_+='>隐藏</option>'
    		var _html = '';
       		_html+='     <div class="br-pagebody">                                                                                                                                      '
   			 +'     <form id="form">                                                                                                                                                               '
			 +'       <div class="br-section-wrapper">                                                                                                                             '
			 +'           <!--内容-->   <input type="hidden" id="geoPoint"  name="geoPoint" value=""/>       <input type="hidden" id="imageUrls"  name="imageUrls" value=""/>                                                                                                                                     '
			 +'<input type="hidden" id="cover_type"  name="cover_type" value=""/> <input type="hidden" id="banner_videos"  name="banner_videos" value=""/>'
			 +'<input type="hidden" id="big_videos"  name="big_videos" value=""/>'
			 +'                                                                                                                                                                    '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点名称:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="spotName" id="spotName" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>所在国家:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="country" id="country" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>城市:</p>                                     '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="city" id="city" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点描述:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<textarea class="form-control wd-50%" rows="5" id="description" name="description" ></textarea>'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点简介:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="brief" id="brief" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>小标题:</p>                                   '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="subtitle" id="subtitle" type="text" value="">'
			 +'                                                                                                                                                                    '
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点评分:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="rating" id="rating" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>联系电话:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="telephones" id="telephones" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'                                                                                                                                                                    '
			 +'           <div class="row mg-t-20">                                                                                                                          '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>标签:</p>                                     '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="tags" id="tags" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点等级:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="rank" id="rank" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>开放时间:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="openingHours" id="openingHours" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>地址:</p>                                     '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			+'<div id="myPageTop" class="">'
			 +'<input class="form-control wd-50%" name="location" id="location" onfocus="locationWS()" onblur="locationWE()" type="text" value=""> '
			 +'</div>'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>门票信息:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="ticketInfo" id="ticketInfo" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'            <div class="row mg-t-20">                                                                                                                               '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>来源景点URL:</p>                              '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="dataUrl" id="dataUrl" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'            <div class="row mg-t-20">                                                                                                                               '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点备注:</p>                              '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="remarks" id="remarks" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'            <div class="row mg-t-20">                                                                                                                               '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>数据来源:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="dataSources" id="dataSources" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'            <div class="row mg-t-20">                                                                                                                               '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>是否隐藏:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'                    <select class="form-control select2 " id="isHide" name="isHide" data-placeholder="sex" tabindex="-1" aria-hidden="true">                                                           '
        		+isHideHtml_                
        		+'                                                                                                                                                                                                '
        		+'                    </select>     '
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'            <div class="row mg-t-20">                                                                                                                               '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>访问次数:</p>                                 '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +'<input class="form-control wd-50%" name="visitsNum" id="visitsNum" type="text" value="">'
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			 +'                                                                                                                                                                    '
			                                                                                                                                       
			 
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>景点图片组:</p>                               '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +imageHtml
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			
			 +'           <div class="row mg-t-20">                                                                                                                                '
			 +'               <p style="width: 79.12px;" class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>长视频:</p>                               '
			 +'               <div class="col-lg mg-l-10">                                                                                                                         '
			 +bigVideo
			 +'               </div>                                                                                                                                               '
			 +'               <!-- col -->                                                                                                                                         '
			 +'           </div>                                                                                                                                                   '
			
			 +'            <div class="row mg-t-20">                                                                                                                                                           '
        		+'                <div class="btn btn-secondary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15" onclick="cancelFormLayer()">取消</div>                                   '
        		+'                <div class="btn btn-outline-primary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15 mg-l-10" onclick="insertScenicSpot(\'adminHx/scenic/insert\',\'form\')">确定</div>'
        		+'                                                                                                                                                                                                '
        		+'            </div>                                                                                                                                                                              '
        		
			 +'       </div><!-- br-section-wrapper -->                                                                                                                            '
			 +'     </div></form><!-- br-pagebody -->   '   
			
			 fromEject(_html,"景点添加",'80%','80%');
       		var itemAddEditor ;  
    	    //页面初始化完毕后执行此方法  
    	    $(function(){  
    	        //初始化类目选择和图片上传器  
    	        E3.init({fun:function(node){  
    	        }});  
    	    });  
    	    gaode();
    	}
    	
    	//高德地图插件加载
    	function gaode(){
    		
            var autoOptions = {
                input: "location"
            };
            var auto = new AMap.Autocomplete(autoOptions);
            var placeSearch = new AMap.PlaceSearch({
//              map: map
            });  //构造地点查询类
            AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
            
            function select(e) {
            	
            	var location = e.poi.location;
            	if (location==""){
            		return;
            	}
            	geoPoint(location);
            	
                placeSearch.setCity(e.poi.adcode);
                placeSearch.search(e.poi.name);  //关键字查询查询
            }
    	}
    	
    	
    	//地址输入失去焦点
    	 function locationWE(){
        	 AMap.service(["AMap.PlaceSearch"], function() {
    	        var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
    	            pageSize: 5,
    	            pageIndex: 1,
    	        });
    	        //关键字查询
    	        placeSearch.search($("#location").val() , function(data,result){
    	        	var location = result.poiList.pois[0].location;
    	        	geoPoint(location);
    	        });
    	    });
        }
    	//地址输入得到焦点
    	function locationWS(){
    		//将坐标清除
    		$("#geoPoint").val("");
    	}
    	
    	//封装坐标
    	function geoPoint(location){
    		var lng = '{';
        	lng+= 'lon='+ location.lng;
        	lng+=',';
        	lng+= 'lat='+ location.lat;
        	
        	lng +='}';
        	$("#geoPoint").val(lng);
    	}
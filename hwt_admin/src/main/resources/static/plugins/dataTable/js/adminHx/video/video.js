
/**
 * 选择状态
 * @param status
 */
function choose_status1(status,a) {
    $(".choose-zhl").removeClass("choose-zhl");
    $(a).addClass("choose-zhl")
    status = status==null||status=='undefined'?0:status;
    $("#paramMap_status").val(status);
    sreachCommit1();
}



/**
 * 小视频详情
 * @param video_id
 * @param url
 * @param dec
 */
function order_details(video_id, url, dec){

    $.ajax({
        url : url,
        type : "POST",
        data : {video_id:video_id},
        success : function (res) {
            res = strToJson(res);
            jsonState(res);
          
                // var timestamp =new Date().getTime();
                 var hxUserVideo = res.data;
                 var dec = hxUserVideo.dec == null ? "":hxUserVideo.dec;
                 var Location = hxUserVideo.location == null ? "":hxUserVideo.location;
                 var Remark = hxUserVideo.remark == null ? "":hxUserVideo.remark;
                 var Handlers = hxUserVideo.handlers == null ? "":hxUserVideo.handlers;
                 var Operation_time = hxUserVideo.operation_time == null ? "":timestampToTime(hxUserVideo.operation_time);
                 var Content = hxUserVideo.content == null ? "":hxUserVideo.content;

                // var create_time = hwOrderVo.create_time==null?"":timestampToTime(hwOrderVo.create_time);
                var end_ = '';
                end_+='	<div class="btn">                                                                                 '
                    +'		<div>                                                                                         '

                switch (hxUserVideo.status) {
                    case (1):
                        var  Status ="未审核"
                        var  sty = "<style type='text/css'> .zry-v_remarks ,  .zry-v_btnBox{display: flex;} .zry-v_tbale>div{display: none;}</style>";
                        break;
                    case (2):
                        var  Status ="通过审核"
                        var  sty = "<style type='text/css'> .zry-v_remarks ,  .zry-v_btnBox{display: none;}.zry-v_tbale>div{display: flex;}</style>";
                        break;

                    case (3):
                        Status ="未通过审核"
                        var  sty = "<style type='text/css'> .zry-v_remarks ,  .zry-v_btnBox{display: none;}.zry-v_tbale>div{display: flex;}</style>";
                        break;
                    case (4):
                        var  Status ="已删除"
                        var  sty = "<style type='text/css'> .zry-v_remarks ,  .zry-v_btnBox{display: none;}.zry-v_tbale>div{display: flex;}</style>";
                        break;
                    default:
                }

                end_+='		</div>                                                                                        '
                    +'	</div>                                                                                            '

                var _html = ' <link rel="stylesheet" href="/plugins/dataTable/js/adminHx/bureaut/tourism/css/video_or_account.css">'+sty+'';
                _html+='<div class="zry-v_box">                                                                               '
                    +'  <div class="zry-v_boxHead">                                                                       '
                    +'	    <div class="zry-v_boxHeadL">小视频审核</div>                                                                       '
                    +'	    <div class="zry-v_boxHeadR">(状态：'+Status+')</div>                                                                       '
                    +'	</div>                                                                       '
                    +'<div class="zry-v_boxContent">                                                 '
                    +'	<video controls="controls" class="zry-v_boxL" src="'+Content+'">                                                                                        '
                    +'	      您的浏览器不支持 video 标签。                                                                                         '
                    +'	 </video>                                                                                         '
                    +'	 <div class="zry-v_boxR">                                                                                          '
                    +'	           <p>'+dec+'</p>                                                                                       '
                    +'	                                                                                                 '
                    +'	      <div>                                                                                     '
                    +'	            <span>地理位置：</span>                                                '
                    +'	            <span>'+Location+'</span>                                                                                          '
                    +'	      </div>                                                                                           '
                    +'	                                                                                                 '
                    // +'	       <div class="zry-v_p1">                                                                                     '
                    // +'	            <span>添加挑战：</span>                                                                                        '
                    // +'	            <span>啊实打实的阿萨德</span>                                                                                         '
                    // +'	       </div>                                                 '
                    +'	                                                        '
                    +'	       <div class="zry-v_p2">                                                                                      '
                    +'	            <span>添加挑战：</span>                                                                                       '
                    +'	            <span>#最美的风景</span>                                                                                     '
                    +'	       </div>                                                                                     '
                    +'	                                                                                            '
                    +'	       <div class="zry-v_remarks">                                                                            '
                    +'	            <div>备注</div>                                                  '
                    +'	            <input type="text" id="remark" />                                                                             '
                    +'	        </div>                                                                                          '
                    +'	                                                                                                 '
                    +'	        <div class="zry-v_btnBox">                                                                                   '
                    +'	            <div id="transit">通过</div>                                                                                       '
                    +'	            <div id="refuse">拒绝</div>                                                                                 '
                    +'	        </div>                                                                                 '
                    +'	                                                                                         '
                    +'	      <div class="zry-v_tbale">                                                                                  '
                    +'	       <div>                                                                                  '
                    +'	            <div>备注:</div>                                                                             '
                    +'	            <div>'+Remark+'</div>                                                                             '
                    +'	       </div>                                                                                  '
                    +'	                                                                                         '
                    +'	       <div>                                                                                  '
                    +'	         <div>操作者:</div>                                                                                '
                    +'	         <div>'+Handlers+'</div>                                                                                '
                    +'	       </div>                                                                                  '
                    +'	                                                                                         '
                    +'	       <div>                                                                                  '
                    +'	         <div>操作时间:</div>                                                                                '
                    +'	         <div>'+Operation_time+'</div>                                                                                '
                    +'	       </div>                                                                                 '
                    +'	    </div>                                                                                    '
                    +'	                                                                                         '
                    +'<input type="hidden" value="'+hxUserVideo.video_id+'" id="videoId">'
                    +'	       </div>                                                                                           '
                    +'	      </div>                                                                                           '



                _html+=end_+'</div>'

                fromEject(_html,dec,'80%','80%');
            
        }
    })

}

/**
 * 同意
 */
$(function () {
    // $("#transit").click(function () {
    //     $.post("admin/video/consent",{"status":2,"video_id":hxUserVideo.video_id},function (data) {
    //         alert(data.msg);
    //     });
    // });
    $(document).on('click' , '#transit' , function (){
           $.post("admin/video/consent",{"status":2,"video_id":$("#videoId").val()},function (res) {
               res = strToJson(res);
               jsonState(res);
              
                   layer.alert(res.msg, {icon: 6}, function () {
                       layer.closeAll();
                       reloadTable($("#dynamic-table"))
                   });
              
            });
    });

    /**
     * 拒绝
     */
    $(document).on("click","#refuse",function(){

        var reason = $("#remark").val();
        if (reason == undefined || reason == null || reason == '') {
            layer.alert("拒绝备注不能为空",{icon:6});
            return;
        }
        $.post("admin/video/consent",{"status":3,"video_id":$("#videoId").val(),"reason":reason},function (res) {
            res = strToJson(res);
          
                layer.alert(res.msg, {icon: 6}, function () {
                    layer.closeAll();
                    reloadTable($("#dynamic-table"))
                });
            
        });
    });
});


// function transita(status,video_id) {
//
//     //onclick="'+transita(2,hxUserVideo.video_id)+'"
//
//     $.post("admin/video/consent",{"status":status,"video_id":video_id},function (data) {
//         alert(data.msg);
//     });
// }

function refuse(status,video_id){

    //onclick="'+refuse(3,hxUserVideo.video_id)+'"

    var refund_cause = prompt("请输入拒绝原因", "");
    if (refund_cause == null && refund_cause == undefined && refund_cause == ''){
        return;
    }
    $.post("admin/video/consent",{"status":status,"video_id":video_id,"reason":refund_cause},function (data) {
        alert(data.msg);
    });
}

/**
 * 订单确认
 */
function order_confirm(video_id, url){
    $.ajax({
        url : url,
        type : "POST",
        data : {video_id:video_id},
        success : function (res) {
            res = strToJson(res);
          
                layer.alert(res.msg,{icon: 6},function(){
                    layer.closeAll();
                    reloadTable($("#dynamic-table"))
                });
          
        }
    })
}

/**
 * 订单拒绝
 */
function order_refuse(video_id, url){
    reson(video_id,"订单拒绝",url)
}

/**
 * 订单取消
 */
function order_cancel(video_id, url){
    reson(video_id,"订单取消",url)
}

/**
 * 原因弹出
 * @param video_id
 * @param dec
 * @param url
 */
function reson(video_id,dec,url){
    var _html = '<form  id="form">                                                                                                                                                                                         '
        +'      <div class="br-pagebody">                                                                                                                                                                           '
        +'                                                                                                                                                                                                          '
        +'        <div class="br-section-wrapper">                                                                                                                                                                  '
        +'            <!--内容-->                                                                                                                                                                                   '
        +'            <div class="row">                                                                                                                                                                             '
        +'                <p class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>输入原因:</p>                                                                          '
        +'                <div class="col-lg mg-l-10">                                                                                                                                                              '
        +'                    <input class="form-control wd-250" placeholder="输入原因" id="business_remarks" name="business_remarks" type="text">                                                                                                                '
        +'                </div><!-- col -->                                                                                                                                                                        '
        +'                                                                                                                                                                                                          '
        +'            </div>                                                                                                                                                                                        '
        +'		              <input id="video_id" name="video_id" type="hidden" value='+video_id+'>                                                                                                                                                                                      '
        +'                                                                                                                                                                                                          '
        +'            <div class="row mg-t-20" >                                                                                                                                                                    '
        +'                <div class="btn btn-secondary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15" onclick="cancelFormLayer()" >取消</div>                                                                  '
        +'                <div class="btn btn-outline-primary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15 mg-l-10" onclick="sure_order(\''+url+'\',\'form\')">确定</div>    '
        +'            </div>                                                                                                                                                                                        '
        +'                                                                                                                                                                                                          '
        +'        </div>                                                                                                                                                              '
        +'      </div>                                                                                                                                                                         '
        +'</form>'
    fromEject(_html,dec,'50%','50%');
}

/**
 * 确定
 */
function sure_order(url,form){

    if (validation(["business_remarks","video_id"],[],[])){

        sub_order(url,form);

    }else{
        return
    }

}


/**
 * 删除操作
 */
function delete_video(video_id , url , dec){
    $.ajax({
        url : url,
        type : "POST",
        data : {video_id:video_id},
        success : function (res) {
            res = strToJson(res);
          
                layer.alert(res.msg,{icon: 6},function(){
                    layer.closeAll();
                    reloadTable($("#dynamic-table"))
                });
            
        }
    })
}
/**
 * 提交操作
 */
function sub_order(url,form){
    $.ajax({
        url : url,
        type : "POST",
        data : $("#"+form).serialize(),
        success : function (res) {
            res = strToJson(res);
          
           
                layer.alert(res.msg,{icon: 6},function(){
                    layer.closeAll();
                    reloadTable($("#dynamic-table"))
                });
            
        }
    })
}

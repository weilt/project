
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
 * 音频详情
 * @param video_id
 * @param url
 * @param dec
 */
function music_details(music_id,mhd,url, dec){

    $.ajax({
        url : url,
        type : "POST",
        data : {music_id:music_id,"mhd":mhd},
        success : function (res) {
            res = strToJson(res);
            jsonState(res);
          
                // var timestamp =new Date().getTime();
                 var HxMusicVo = res.data;
                  var music_id = HxMusicVo.music_id == null ? "":HxMusicVo.music_id;
                  var music_url = HxMusicVo.music_url == null ? "":HxMusicVo.music_url;
                  var music_title = HxMusicVo.music_title == null ? "":HxMusicVo.music_title;
                  var create_time = HxMusicVo.create_time == null ? "":timestampToTime(HxMusicVo.create_time);
                  var use_account = HxMusicVo.use_account == null ? "":HxMusicVo.use_account;
                  var user_id = HxMusicVo.user_id == 0 ? "平台":HxMusicVo.user_id;
                  var music_writer = HxMusicVo.music_writer == null ? "":HxMusicVo.music_writer;

            var aliyun = '';
            var imgSrc = "";
            if (HxMusicVo.music_cover == null){
                aliyun = '<img class="onePicUpload" src="/plugins/dataTable/js/adminHx/bureaut/tourism/img/u320.png" alt="" id="uploadImg">';
                imgSrc = "/plugins/dataTable/js/adminHx/bureaut/tourism/img/u320.png";
            } else {
                aliyun = '<img class="onePicUpload" src="'+HxMusicVo.music_cover+'" alt="" id="uploadImg">';
                imgSrc=HxMusicVo.music_cover;
            }

            switch (HxMusicVo.is_open) {
                case (0):
                    var  is_open ="未隐藏"
                    break;
                case (1):
                    var  is_open ="隐藏"
                    break;
                default:
            }
            var defau0 = "";
            var defau1 = "";
            var defau2 = "";
            var defau3 = "";
            var defau4 = "";
            var defau5 = "";
            var defau6 = "";
            var defau7 = "";
            var defau8 = "";
            switch (HxMusicVo.music_tag) {
                case (0):
                    defau0 ="checked=''";
                    break;
                case (1):
                    defau1 ="checked=''";
                    break;
                case (2):
                    defau2 ="checked=''";
                    break;
                case (3):
                    defau3 ="checked=''";
                    break;
                case (4):
                    defau4 ="checked=''";
                    break;
                case (5):
                    defau5 ="checked=''";
                    break;
                case (6):
                    defau6 ="checked=''";
                    break;
                case (7):
                    defau7 ="checked=''";
                    break;
                case (8):
                    defau8 ="checked=''";
                    break;
                case (9):

                    break;
                default:
            }
            var end_ = '';
            end_+='	<div class="btn">                                                                                 '
                +'		<div>                                                                                         '
                end_+='		</div>                                                                                        '
                    +'	</div>                                                                                            '
            var mhd = 1;
                var _html = ' <link rel="stylesheet" href="/plugins/dataTable/js/adminHx/bureaut/tourism/css/index2.css">';
                _html+=' <div class="audio-box">                                                                     '
                    +'   <form id="music">                                                                           '
                    +'   <input type="hidden" name="music_id" value="'+music_id+'">                                                                            '
                    +'   <input type="hidden" name="mhd" value="'+mhd+'">                                                                         '
                    +'   <audio src="'+music_url+'" controls="controls">                                                                      '
                    +'      您的浏览器不支持 audio 标签                                               '
                    +'  </audio>                                                                      '
                    +'      <div class="audio-classBox">                                                                      '
                    +'          <div class="audio-txt">                                                                       '
                    +'          分类:                                                                       '
                    +'  </div>                                                                        '
                    +'      <div class="audio-radioBox">                                                                      '
                    +'      <ul>                                                                      '
                    +'          <li><label for="radio1"><input id="radio1" type="radio" name="radios" value="1" '+defau1+'>影视原声</label></li>                                                                      '
                    +'          <li><label for="radio2"><input id="radio2" type="radio" name="radios" value="2" '+defau2+'>日韩</label></li>                                                                      '
                    +'          <li><label for="radio3"><input id="radio3" type="radio" name="radios" value="3" '+defau3+'>生活</label></li>                                                                        '
                    +'          <li><label for="radio4"><input id="radio4" type="radio" name="radios" value="4" '+defau4+'>搞怪</label></li>                                                                        '
                    +'          <li><label for="radio5"><input id="radio5" type="radio" name="radios" value="5" '+defau5+'>流行</label></li>                                                                      '
                    +'          <li><label for="radio6"><input id="radio6" type="radio" name="radios" value="6" '+defau6+'>说唱</label></li>                                                                      '
                    +'          <li><label for="radio7"><input id="radio7" type="radio" name="radios" value="7" '+defau7+'>民谣</label></li>                                                                      '
                    +'          <li><label for="radio8"><input id="radio8" type="radio" name="radios" value="8" '+defau8+'>欧美</label></li>                                                                      '
                    +'      </ul>                                                                     '
                    +'      </div>                                                                        '
                    +'      </div>                                                                        '
                    +'      <div class="audio-inputName">                                                                     '
                    +'          歌手名：<input name="geshouname" type="text" value="'+music_writer+'">                                                                       '
                    +'          </div>                                                                        '
                    +'          <div class="audio-inputName">                                                                     '
                    +'          歌曲名：<input name="name" type="text" value="'+music_title+'">                                                                       '
                    +'          </div>                                                                        '
                    +'          <div class="audio-imgBox">                                                                        '
                    +'          <span>封面图：</span><span style="color: #999">点击上传封面图,建议封面图尺寸100 * 100</span>                                                                      '
                    +'      <div class="audio-uploadBox">                                                                     '
                    +'          '+aliyun+'<input name="url" type="hidden" value="'+imgSrc+'">                                                                         '
                    +'      </div>                                                                        '
                    +'	                                                                                        '
                    +'	        <div class="zzz-box">                                                                                   '
                    +'	            <div id="transit" class="zzz-zzzz1" onclick="save()">保存</div>                                                                                       '
                    +'	            <div id="refuse" class="zzz-zzzz2">取消</div>                                                                                 '
                    +'	        </div>                                                                                 '
                    +' </div>                                                                        '
                    +' </form>                                                                        '



                _html+=end_+'</div>'

                fromEject(_html,dec,'80%','80%');
            E3.initOnePicUpload();
            
        }
    })

}

/**
 * 保存
 */
function save(){

    $.post("admin/music/modifyHideDelete",$("#music").serializeJson(),function (res) {
        res = strToJson(res);
        jsonState(res);
        layer.alert(res.msg, {icon: 6}, function () {
            layer.closeAll();
            reloadTable($("#dynamic-table"))
        });
    });
}

$(function () {
    // $(document).on('click' , '#transit' , function (){
    //        $.post("admin/music/modifyHideDelete",{"mhd":1,"music_id":$("#music_id").val(),
    //            "music_writer":music_writer,"music_title":music_title,"music_tag":music_tag,"music_cover":aliyun},function (res) {
    //            res = strToJson(res);
    //            jsonState(res);
    //                layer.alert(res.msg, {icon: 6}, function () {
    //                    layer.closeAll();
    //                    reloadTable($("#dynamic-table"))
    //                });
    //         });
    // });

    /**
     * 取消
     */
    $(document).on("click","#refuse",function(){
        layer.closeAll();

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

    // var refund_cause = prompt("请输入拒绝原因", "");
    // if (refund_cause == null && refund_cause == undefined && refund_cause == ''){
    //     return;
    // }
    $.post("admin/video/consent",{"status":status,"music_id":music_id,
        "music_tag":music_tag,"music_writer":music_writer,"music_cover":music_cover,"music_title":music_title},function (data) {
        alert(data.msg);
    });
}

/**
 * 订单确认
 */
// function order_confirm(video_id, url){
//     $.ajax({
//         url : url,
//         type : "POST",
//         data : {video_id:video_id},
//         success : function (res) {
//             res = strToJson(res);
//
//                 layer.alert(res.msg,{icon: 6},function(){
//                     layer.closeAll();
//                     reloadTable($("#dynamic-table"))
//                 });
//
//         }
//     })
// }
//
// /**
//  * 订单拒绝
//  */
// function order_refuse(video_id, url){
//     reson(video_id,"订单拒绝",url)
// }
//
// /**
//  * 订单取消
//  */
// function order_cancel(video_id, url){
//     reson(video_id,"订单取消",url)
// }

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



function selects() {
    var ids = new Array();
    var chks = $(".checkOne");
    $.each(chks,function () {
        if (this.checked){
            ids.push($(this).val());
        }
    });
    console.log(ids)
    if (ids.length <= 1){
        layer.alert("请选择2条以上的音频进行批量编辑", {icon: 6}, function () {
            layer.closeAll();
            reloadTable($("#dynamic-table"));

        });
        return
    }
    var defaultImg='/plugins/dataTable/js/adminHx/bureaut/tourism/img/u320.png';

    $.ajax({
        type:"POST",
        url:"admin/music/selects",
        data:{"ids":ids},
        success:function (res) {

            var arr = new Array();
            $.each(res.data,function () {
                var end_ = '';
                end_+='	<div class="btn">                                                                                 '
                    +'		<div>                                                                                         '

                end_+='		</div>                                                                                        '
                    +'	</div>                                                                                            '


                var aliyun = '';
                var imgSrc = '';
                if (this.music_cover == null){
                    aliyun = '<img class="onePicUpload" src="/plugins/dataTable/js/adminHx/bureaut/tourism/img/u320.png" alt="" id="uploadImg">';
                    imgSrc = '/plugins/dataTable/js/adminHx/bureaut/tourism/img/u320.png';
                } else {
                    aliyun = '<img class="onePicUpload" src="'+this.music_cover+'" alt="" id="uploadImg">';
                    imgSrc = this.music_cover;
                }


                var defau0 = "";
                var defau1 = "";
                var defau2 = "";
                var defau3 = "";
                var defau4 = "";
                var defau5 = "";
                var defau6 = "";
                var defau7 = "";
                var defau8 = "";
                switch (this.music_tag) {
                    case (0):
                        defau0 ="checked=''";
                        break;
                    case (1):
                        defau1 ="checked=''";
                        break;
                    case (2):
                        defau2 ="checked=''";
                        break;
                    case (3):
                        defau3 ="checked=''";
                        break;
                    case (4):
                        defau4 ="checked=''";
                        break;
                    case (5):
                        defau5 ="checked=''";
                        break;
                    case (6):
                        defau6 ="checked=''";
                        break;
                    case (7):
                        defau7 ="checked=''";
                        break;
                    case (8):
                        defau8 ="checked=''";
                        break;
                    case (9):

                        break;
                    default:
                }
               var ul = this.music_url==null?'':this.music_url;
                var _html="";
                _html+='<div class="audio-box">                                                                         '
                    +'   <audio src="'+ul+'" controls="controls">                                                                      '
                    +'      您的浏览器不支持 audio 标签                                               '
                    +'  </audio>                                                                      '
                    +'      <div class="audio-classBox">                                                                      '
                    +'          <div class="audio-txt">                                                                       '
                    +'          分类:                                                                       '
                    +'  </div>                                                                        '
                    +'      <div class="audio-radioBox">                                                                      '
                    +'      <ul>                                                                      '
                    +'          <li><label for="radio1"><input id="radio1" type="radio" name="music_tag" value="1" '+defau1+'>影视原声</label></li>                              '
                    +'          <li><label for="radio2"><input id="radio2" type="radio" name="music_tag" value="2" '+defau2+'>日韩</label></li>                              '
                    +'          <li><label for="radio3"><input id="radio3" type="radio" name="music_tag" value="3" '+defau3+'>生活</label></li>                                '
                    +'          <li><label for="radio4"><input id="radio4" type="radio" name="music_tag" value="4" '+defau4+'>搞怪</label></li>                                '
                    +'          <li><label for="radio5"><input id="radio5" type="radio" name="music_tag" value="5" '+defau5+'>流行</label></li>                              '
                    +'          <li><label for="radio6"><input id="radio6" type="radio" name="music_tag" value="6" '+defau6+'>说唱</label></li>                              '
                    +'          <li><label for="radio7"><input id="radio7" type="radio" name="music_tag" value="7" '+defau7+'>民谣</label></li>                              '
                    +'          <li><label for="radio8"><input id="radio8" type="radio" name="music_tag" value="8" '+defau8+'>欧美</label></li>                              '
                    +'      </ul>                                                                     '
                    +'      </div>                                                                        '
                    +'      </div>                                                                        '
                    +'      <div class="audio-inputName">                                                                     '
                    +'     <input name="music_id" type="hidden" value="'+this.music_id+'">                                                                  '
                    +'          歌手名：<input type="text" name="music_writer" value="'+this.music_writer+'">                                                                       '
                    +'          </div>                                                                        '
                    +'          <div class="audio-inputName">                                                                     '
                    +'          歌曲名：<input type="text" name="music_title" value="'+this.music_title+'">                                                                       '
                    +'          </div>                                                                        '
                    +'          <div class="audio-imgBox">                                                                        '
                    +'          <span>封面图：</span><span style="color: #999">点击上传封面图,建议封面图尺寸100 * 100</span>                                                                      '
                    +'      <div class="audio-uploadBox">                                                                     '
                    +'          '+aliyun+'  <input name="music_cover" type="hidden" value="'+imgSrc+'">                                                                      '
                    +'      </div>                                                                        '
                    +'	                                                                                        '
                    	        // <div class="zzz-box">
                    	        //     <div id="transit" class="zzz-zzzz1">保存</div>
                    	        //     <div id="refuse" class="zzz-zzzz2">取消</div>
                    	        // </div>
                    +' </div>                                                                        '



                _html+=end_+'</div>'

                arr.push(_html);
            });
            var content = "<link rel=\"stylesheet\" href=\"/plugins/dataTable/js/adminHx/bureaut/tourism/css/index2.css\"><form>";
            // $.each(arr,function () {
            //     content+=this;
            // });
            // content+="</form>";
            // fromEject(content,'批量修改','80%','80%');
            layer.open({
                type: 1,
                closeBtn:1,
                title: "批量修改",
                area: ["80%", "80%"], //宽高
                content:"<div id='batch_box' style='overflow-y: scroll; padding: 0 50px 50px 50px'></div>",
                zIndex:100//层优先级
            });

            $("#batch_box").append("<link rel='stylesheet' href='/plugins/dataTable/js/adminHx/bureaut/tourism/css/index2.css'>")
            for (var i = 0; i < arr.length; i++) {
                $("#batch_box").append("<form class='batch_form'>"+arr[i]+"</form>");
            }
        // <div class="zzz-box"><div id="transit" class="zzz-zzzz1">保存</div><div id="refuse" class="zzz-zzzz2">取消</div></div>
            $("#batch_box").append('<div class="zzz-box"><div class="zzz-zzzz1" onclick="saveli()">保存</div><div id="refuse" class="zzz-zzzz2">取消</div></div>');
            E3.initOnePicUpload();

        }
    });
}

/**
 * 批量保存
 */
function saveli(){
    var zzz = {}
    var datas = new Array();
    var batchForms = $(".batch_form");
    var ds = new Array();

    for (var i = 0; i < batchForms.length; i++) {
        datas.push($(batchForms[i]).serializeJson());
    }
    for (var i = 0; i < datas.length; i++) {
        ds.push(JSON.stringify(datas[i]));
    }
    // console.log(datas)
    $.post("admin/music/update",{"ds":ds},function (res) {
        res = strToJson(res);
        jsonState(res);
        layer.alert(res.msg, {icon: 6}, function () {
            layer.closeAll();
            reloadTable($("#dynamic-table"))
        });
    });
}

/**
 * 选择状态
 * @param status
 */

var bill_type = 0;

function choose_status(status,a) {
    if (status == 0){
        $("#A").text("订单编号");
        $("#B").text("订单金额");
        $("#C").text("支付方式");
        $("#D").text("支付时间");

    }else if (status == 1){
        $("#A").text("交易编号");
        $("#B").text("交易金额");
        $("#C").text("支付方式");
        $("#D").text("支付时间");
    }else {                 //status=5
        $("#A").text("交易编号");
        $("#B").text("交易金额");
        $("#C").text("提现方式");
        $("#D").text("提现时间");
    }
    bill_type = status;

    $(".choose-zhl").removeClass("choose-zhl");
    $(a).addClass("choose-zhl")
    status = status==null||status=='undefined'?0:status;
    $("#paramMap_status").val(status);
    sreachCommit();
}

/**
 * 对账详情
 * @param order_id
 * @param url
 * @param dec
 */
function order_deta(order_id, url, dec){

    $.ajax({
        url : url,
        type : "POST",
        data : {order_id:order_id},
        success : function (res) {
            res = strToJson(res);
            jsonState(res);
          
                var timestamp =new Date().getTime();

                var hxUserWallAccountPageVo = res.data;

               var  listSum =hxUserWallAccountPageVo.dataList;
                var status = "";
                var source = "";
                switch (listSum[0].source) {
                            case 1:
                                source = "支付宝";
                                break;
                            case 2:
                                source = "微信";
                                break;
                            case 3:
                                source ="零钱";
                                break;
                            case 4:
                                source ="其他";
                                break;

                            default:
                        }
                        switch (listSum[0].status) {
                            case 0:
                                status = "未对账";
                                break;
                            case 1:
                                status = "已对账";
                                break;

                            default:
                        }
                var create_time = listSum[0].create_time==null?"":timestampToTime(listSum[0].create_time);
                var account_time = listSum[0].account_time==null?"":timestampToTime(listSum[0].account_time);
                var handlers = listSum[0].handlers==null?"":listSum[0].handlers;
                var accountBy ="";
                if (listSum[0].status == 1){
                     accountBy ='<tr id="datekrui">                                                                      '
                        +'<td id="one">                                                                      '
                        +'操作者                                                                      '
                        +'</td>                                                                      '
                        +'<td id="tow">                                                                      '
                        +''+handlers+'                                                                      '
                        +'</td>                                                                      '
                        +'</tr>                                                                      '
                        +'<tr id="datekrui" style="height: 50px auto;">                                                                      '
                        +'<td id="one">                                                                      '
                        +'操作时间                                                                      '
                        +'</td>                                                                      '
                        +'<td id="tow">                                                                      '
                        +''+account_time+'                                                                      '
                        +'</td>                                                                      '
                        +'</tr>                                                                     '
                }


                var end_ = '';
                end_+='	<div class="btn">                                                                                 '
                    +'		<div>                                                                                         '

                end_+='		</div>                                                                                        '
                    +'	</div>                                                                                            '

                var _html = ' <link rel="stylesheet" href="/plugins/dataTable/js/adminHx/bureaut/tourism/css/account.css">';
                _html+='<table id="z-zwz">                                                                 '
                    +'<td>交易信息</td>                                                                            '
                    +'<tr>                                                                            '
                    +'<td id="one">                                                                            '
                    +'交易单号                                                                            '
                    +'</td>                                                                            '
                    +'<td id="tow">                                                                            '
                    +''+listSum[0].trasaction+'                                                                            '
                    +'</td>                                                                            '
                    +'</tr>                                                                            '
                    +'<tr>                                                                            '
                    +'<td id="one">                                                                            '
                    +'订单编号                                                                            '
                    +'</td>                                                                            '
                    +'<td id="tow">                                                                            '
                    +''+listSum[0].trans_num+'                                                                           '
                    +'</td>                                                                            '
                    +'</tr>                                                                            '
                    +'<tr>                                                                            '
                    +'<td id="one">                                                                            '
                    +'订单金额                                                                            '
                    +'</td>                                                                            '
                    +'<td id="tow">                                                                            '
                    +''+listSum[0].operation_amount+'                                                                                '
                    +'</td>                                                                                '
                    +'</tr>                                                                                '
                    +'<tr>                                                                                '
                    +'<td id="one">                                                                                '
                    +'支付方式                                                                                '
                    +'</td>                                                                                '
                    +'<td id="tow">                                                                                '
                    +''+source+'                                                                                '
                    +'</td>                                                                                '
                    +'</tr>                                                                                '
                    +'<tr>                                                                                '
                    +'<td id="one">                                                                                '
                    +'支付时间                                                                                '
                    +'</td>                                                                                '
                    +'<td id="tow">                                                                                '
                    +''+create_time+'                                                                                '
                    +'</td>                                                                                '
                    +'</tr>                                                                                '
                    +'                                                                                                  '
                    +'</table>                                                                                '
                    +'<table id="z-break2">                                                                                '
                    +'<tr id="datekrui" style="height: 50px auto;">                                                                                '
                    +'<td id="one">                                                                                '
                    +'对账状态                                                                                '
                    +'</td>                                                                                '
                    +'<td id="tow">                                                                                '
                    +''+status+'                                                                                '
                    +'</td>                                                                      '
                    +'</tr>                                                                      '
                    +'                                                                                  '
                    +'  '+accountBy+'                                                                                '





                _html+=end_+'</table>'

                fromEject(_html,dec,'80%','80%');
            
        }
    })

}

/**
 * 充值详情
 * @param order_id
 * @param url
 * @param dec
 */
function order_details2(trans_num,bill_type, url, dec){

    $.ajax({
        url : url,
        type : "POST",
        data : {"paramMap[order_num]":trans_num,"paramMap[status]":bill_type},
        success : function (res) {
            res = strToJson(res);
            jsonState(res);
           
                var hxUserWallAccountPageVo = res.data;

                var  listSum =hxUserWallAccountPageVo.dataList;

                var status = "";
                var source = "";
                switch (listSum[0].source) {
                    case 1:
                        source = "支付宝"
                        break;
                    case 2:
                        source = "微信"
                        break;
                    case 3:
                        source ="零钱"
                        break;
                    case 4:
                        source ="其他"
                        break;

                    default:
                }
                switch (listSum[0].status) {
                    case 0:
                        status = "未对账"
                        break;
                    case 1:
                        status = "已对账"
                        break;

                    default:
                }
                var create_time = listSum[0].create_time==null?"":timestampToTime(listSum[0].create_time);
                var account_time = listSum[0].account_time==null?"":timestampToTime(listSum[0].account_time);
                var operation_amount = listSum[0].operation_amount==null?"":listSum[0].operation_amount;
                var flow_num = listSum[0].flow_num==null?"":listSum[0].flow_num;
                var handlers = listSum[0].handlers==null?"":listSum[0].handlers;
                var trans_num = listSum[0].trans_num==null?"":listSum[0].trans_num;
                var toUp ="";
                if (listSum[0].status == 1){
                    toUp = '<div>                                                                                              '
                        +'<div class="one">                                                                                              '
                        +'操作者                                                                                                '
                        +'</div>                                                                                              '
                        +'<div class="tow">                                                                                              '
                        +'       '+handlers+'                                                                                                 '
                        +'</div>                                                                                              '
                        +'</div>                                                                                              '
                        +'<div>                                                                                              '
                        +'<div class="one">                                                                                              '
                        +'操作时间                                                                                               '
                        +'</div>                                                                                              '
                        +'<div class="tow">                                                                                              '
                        +'             '+account_time+'                                                                                                 '
                        +'</div>                                                                                              '
                        +'</div>                                                                                              '
                }


                var _html = ' <link rel="stylesheet" href="/plugins/dataTable/js/adminHx/bureaut/tourism/css/account.css">';
                _html+='<div id="z-zwz" style="width: 800px;">                                                                                  '
                    +'<div>交易信息</div>                                                                                             '
                    +'                                                                                                   '
                    +'<div>                                                                                              '
                    +'<div class="one">                                                                                             '
                    +'交易单号                                                                                              '
                    +'</div>                                                                                             '
                    +'<div class="tow">                                                                                             '
                    +''+trans_num+'                                                                                                '
                    +'</div>                                                                                             '
                    +'</div>                                                                                             '
                    +'<div>                                                                                              '
                    +'<div class="one">                                                                                             '
                    +'充值金额                                                                                              '
                    +'</div>                                                                                             '
                    +'<div class="tow">                                                                                             '
                    +''+operation_amount+'                                                                                                '
                    +'</div>                                                                                             '
                    +'</div>                                                                                             '
                    +'<div>                                                                                              '
                    +'<div class="one">                                                                                             '
                    +'支付方式                                                                                              '
                    +'</div>                                                                                             '
                    +'<div class="tow">                                                                                             '
                    +''+source+' &nbsp;&nbsp;&nbsp;'+flow_num+'                                                                                         '
                    +'</div>                                                                                             '
                    +'</div>                                                                                             '
                    +'<div>                                                                                              '
                    +'<div class="one">                                                                                             '
                    +'支付时间                                                                                               '
                    +'</div>                                                                                              '
                    +'<div class="tow">                                                                                              '
                    +''+create_time+'                                                                                                 '
                    +'</div>                                                                                              '
                    +'</div>                                                                                              '
                    +'                                                                                              '
                    +'</div>                                                                                              '
                    +'                                                                                       '
                    +'<div id="z-break2" style="width: 800px;">                                                                                     '
                    +'<div style="height: 50px auto;">                                                           '
                    +'<div class="one">                                                                                              '
                    +'对账状态                                                                                               '
                    +'</div>                                                                                              '
                    +'<div class="tow">                                                                                              '
                    +''+status+'                                                                                                 '
                    +'</div>                                                                                              '
                    +'</div>                                                                                              '
                    +'    '+toUp+'                                                                     '

                // var end_ = '';
                // end_+='	<div class="btn">                                                                                 '
                //     +'		<div>                                                                                         '
                //
                // end_+='		</div>                                                                                        '
                //     +'	</div>                                                                                            '

                _html+='</div>'

                fromEject(_html,dec,'80%','80%');
            
        }
    })

}

/**
 * 提现详情
 * @param order_id
 * @param url
 * @param dec
 */
function order_details3(trans_num,bill_type, url, dec){

    $.ajax({
        url : url,
        type : "POST",
        data : {"paramMap[order_num]":trans_num,"paramMap[status]":bill_type},
        success : function (res) {
            res = strToJson(res);
            jsonState(res);
           
                var hxUserWallAccountPageVo = res.data;

                var  listSum =hxUserWallAccountPageVo.dataList;

                var status = "";
                var source = "";
                switch (listSum[0].source) {
                    case 1:
                        source = '支付宝'
                        break;
                    case 2:
                        source = '微信'
                        break;
                    case 3:
                        source ="零钱"
                        break;
                    case 4:
                        source ="其他"
                        break;

                    default:
                }
                switch (listSum[0].status) {
                    case 0:
                        status = '未对账'
                        break;
                    case 1:
                        status = '已对账'
                        break;

                    default:
                }
                var create_time = listSum[0].create_time==null?"":timestampToTime(listSum[0].create_time);
                var account_time = listSum[0].account_time==null?"":timestampToTime(listSum[0].account_time);
                var handlers = listSum[0].handlers==null?"":listSum[0].handlers;
                var card_num = listSum[0].card_num==null?"":listSum[0].card_num;
                var username = listSum[0].username==null?"":listSum[0].username;
                var bank_name = listSum[0].bank_name==null?"":listSum[0].bank_name;
                var operation_amount = listSum[0].operation_amount==null?"":listSum[0].operation_amount;
                var withdraw =""
                if (listSum[0].status == 1){
                     withdraw =+'                                                                                  '
                        +'<tr id="datekrui">                                                                         '
                        +'<td id="one">                                                                         '
                        +'操作者                                                                           '
                        +'</td>                                                                         '
                        +'<td id="tow">                                                                         '
                        +''+handlers+'                                                                            '
                        +'</td>                                                                         '
                        +'</tr>                                                                         '
                        +'<tr id="datekrui">                                                                         '
                        +'<td id="one">                                                                         '
                        +'操作时间                                                                          '
                        +'</td>                                                                         '
                        +'<td id="tow">                                                                         '
                        +''+account_time+'                                                                           '
                        +'</td>                                                                         '
                        +'</tr>                                                                         '
                }



                var end_ = '';


                var _html = ' <link rel="stylesheet" href="/plugins/dataTable/js/adminHx/bureaut/tourism/css/account.css">';


                _html+='	<table id="z-zwz">                                                                  '
                    +'<td>交易信息</td>                                                                         '
                    +'                                                                                  '
                    +'<tr>                                                                          '
                    +'<td id="one">                                                                         '
                    +'交易单号                                                                          '
                    +'</td>                                                                         '
                    +'<td id="tow">                                                                         '
                    +''+listSum[0].trans_num+'                                                                            '
                    +'</td>                                                                         '
                    +'</tr>                                                                         '
                    +'<tr>                                                                          '
                    +'<td id="one">                                                                         '
                    +'提现金额                                                                          '
                    +'</td>                                                                         '
                    +'<td id="tow">                                                                         '
                    +' '+operation_amount+'                                                         '
                    +'</td>                                                                         '
                    +'</tr>                                                                         '
                    +'<tr>                                                                          '
                    +'<td id="one">                                                                         '
                    +'提现银行                                                                          '
                    +'</td>                                                                         '
                    +'<td id="tow">                                                                         '
                    +''+bank_name+'    &nbsp;&nbsp;&nbsp;    '+card_num+'                                                      '
                    +'</td>                                                                         '
                    +'</tr>                                                                         '
                    +'<tr>                                                                          '
                    +'<td id="one">                                                                         '
                    +'银行户名                                                                          '
                    +'</td>                                                                         '
                    +'<td id="tow">                                                                         '
                    +''+username+'                                                                          '
                    +'</td>                                                                         '
                    +'</tr>                                                                         '
                    +'<tr>                                                                          '
                    +'<td id="one">                                                                         '
                    +'申请时间                                                                          '
                    +'</td>                                                                         '
                    +'<td id="tow">                                                                         '
                    +''+create_time+'                                                                            '
                    +'</td>                                                                         '
                    +'</tr>                                                                         '
                    +'                                                          '
                    +'</table>                                                                          '
                    +'<table id="z-break2">                                                                         '
                    +'<tr id="datekrui" style="height: 50px auto;">                                                                         '
                    +'<td id="one">                                                                         '
                    +'对账状态                                                                          '
                    +'</td>                                                                         '
                    +'<td id="tow">                                                                         '
                    +''+status+'                                                                            '
                    +'</td>                                                                         '
                    +'</tr>                                                                         '
                    +'              '+ withdraw+'                                                                    '


                _html+=end_+'</table>'

                fromEject(_html,dec,'80%','80%');
            
        }
    })

}


/**
 * 订单确认
 */
function order_confirm(order_id, url){
    $.ajax({
        url : url,
        type : "POST",
        data : {order_id:order_id},
        success : function (res) {
            res = strToJson(res);
            jsonState(res);
           
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
function order_refuse(order_id, url){
    reson(order_id,"订单拒绝",url)
}

/**
 * 订单取消
 */
function order_cancel(order_id, url){
    reson(order_id,"订单取消",url)
}

/**
 * 原因弹出
 * @param order_id
 * @param dec
 * @param url
 */
function reson(order_id,dec,url){
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
        +'		              <input id="order_id" name="order_id" type="hidden" value='+order_id+'>                                                                                                                                                                                      '
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

    if (validation(["business_remarks","order_id"],[],[])){

        sub_order(url,form);

    }else{
        return
    }

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
            jsonState(res);
           
                layer.alert(res.msg,{icon: 6},function(){
                    layer.closeAll();
                    reloadTable($("#dynamic-table"))
                });
            
        }
    })
}

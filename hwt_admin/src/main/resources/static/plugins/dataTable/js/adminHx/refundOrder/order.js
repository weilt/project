/**
 * 选择状态
 * @param status
 */
function choose_status(status, a) {
    $(".choose-zhl").removeClass("choose-zhl");
    $(a).addClass("choose-zhl")
    status = status == null || status == 'undefined' ? 0 : status;
    $("#paramMap_status").val(status);
    sreachCommit();
}

/**
 * 退款详情
 * @param order_id
 * @param url
 * @param dec
 */
function order_details3(order_id, url, dec) {

    $.ajax({
        url: url,
        type: "POST",
        data: {order_id: order_id},
        success: function (res) {
            res = strToJson(res);
            jsonState(res);
            
                var timestamp = new Date().getTime();

                var orderDetailsVo = res.data;

                var hwOrderVo = orderDetailsVo.hwOrderVo;
                var refuse_time = hwOrderVo.refuse_time == null ? "":timestampToTime(hwOrderVo.refuse_time);
                var order_id = hwOrderVo.order_id;
                var hwOrderUserVos = orderDetailsVo.hwOrderUserVos;
                var hwOrderUserVos_ = "";
                for (var i = 0; i < hwOrderUserVos.length; i++) {
                    hwOrderUserVos_ += '<div class="order_line">姓名：' + hwOrderUserVos[i].name + '<span>';
                    var Identity_type = '身份证';
                    var identity_type = hwOrderUserVos[i].identity_type;
                    switch (identity_type) {
                        case ("ID_card"):
                            Identity_type = '身份证'
                            break;
                        case ("passport"):
                            Identity_type = '护照'
                            break;
                        case ("Officer_card"):
                            Identity_type = "军官证"
                            break;
                        case ("Reentry_permit"):
                            Identity_type = "回乡证"
                            break;
                        case ("Taiwan_card"):
                            Identity_type = "台胞证"
                            break;
                        case ("Hong_Kong_Macau_laissez_passer"):
                            Identity_type = "港澳通行证"
                            break;

                        case ("Taiwan_pass"):
                            Identity_type = "台湾通行证"
                            break;

                        default:
                    }
                    hwOrderUserVos_ += Identity_type + '：' + hwOrderUserVos[i].identity_num + '</span></div>';
                }
                var hxOrderInfoVo = orderDetailsVo.hxOrderInfoVo;
                var hwOrderRefundVo = orderDetailsVo.hwOrderRefundVo;
                var business_remarks = "";
                if (hwOrderRefundVo != null) {
                    var consent_time = hwOrderRefundVo.consent_time == null? "":timestampToTime(hwOrderRefundVo.consent_time);
                    var Refund_sum = hwOrderRefundVo.refund_sum == null? "":hwOrderRefundVo.refund_sum;
                    var refund_time_back = hwOrderRefundVo.refund_time_back == null ? "":timestampToTime(hwOrderRefundVo.refund_time_back);

                    if (hwOrderRefundVo.contract_money != null){
                        var wei = 'checked="checked"';
                        var notWei = "";
                        var Contract_money =  "¥"+(hwOrderRefundVo.contract_money).toString();


                    }else if (hwOrderRefundVo.contract_money == null) {
                        var wei = "";
                        var notWei = 'checked="checked"';
                        var Contract_money = "0";
                    }

                    var initiator = hwOrderRefundVo.business_remarks == null ? "APP用户":"商家发起退款";
                    var operate = "";
                    var Operator = "";
                    if (initiator == "商家发起退款"){
                        //操作人
                        operate = "平台系统"
                    }else {
                        Operator = hwOrderRefundVo.operator == null? "":hwOrderRefundVo.operator;
                    }
                    var user_remarks ="";
                    if (initiator == "APP用户"){
                        if (hwOrderRefundVo.user_remarks != undefined && hwOrderRefundVo.user_remarks != null){
                            user_remarks = hwOrderRefundVo.user_remarks == null ? "":hwOrderRefundVo.user_remarks;
                        }

                    }else {
                        if (hwOrderRefundVo.business_remarks != undefined && hwOrderRefundVo.business_remarks != null){
                            business_remarks = hwOrderRefundVo.business_remarks == null ? "":hwOrderRefundVo.business_remarks;
                        }
                    }
                    var refund_cause="";
                    if (user_remarks == null && business_remarks==null){
                        refund_cause=hwOrderRefundVo.refund_cause == null ? "":hwOrderRefundVo.refund_cause;
                    }

                }else{
                    var wei = "";
                    var notWei = 'checked="checked"';
                    var Contract_money = "0";
                    var operate = "";
                    var Operator = "";
                    var consent_time = "";
                    var Refund_sum ="";
                    var refund_cause="";

                }

                var start_time = hwOrderVo.start_time;
                var end_time = hwOrderVo.end_time;
                var day = Math.round((end_time - start_time) / (24 * 60 * 60 * 1000));
                var status = hwOrderVo.status;
                var Status = "待确认";
                var apply_sales_time = hwOrderVo.apply_sales_time == null? "":timestampToTime(hwOrderVo.apply_sales_time);
                var paymen_type = hwOrderVo.paymen_type;
                var Paymen_type = '';
                switch (paymen_type) {
                    case (1):
                        Paymen_type = "支付宝"
                        break;

                    case (2):

                        Paymen_type = "微信"

                        break;
                    case (3):
                        Paymen_type = "零钱"
                        break;

                    default:
                        Paymen_type = "其他"
                }
                var Start_time = (start_time == null ? "" : timestampToTime(start_time)).split(' ')[0];
                var adult_num = hwOrderVo.adult_num;
                var children_num = hwOrderVo.children_num;
                var dec = hxOrderInfoVo.dec;
                var buyer_rate = hwOrderVo.buyer_rate;
                if (hwOrderVo.bureau_id == 0) {
                    var cicerone_id = hwOrderVo.cicerone_id;
                    var bureau_id = "";
                }
                if (hwOrderVo.cicerone_id == 0) {
                    var bureau_id = hwOrderVo.bureau_id;
                    var cicerone_id = "";
                }


                var end_ = '';
                end_ += '	<div class="btn">                                                                                 '
                    + '		<div>                                                                                         '


                switch (status) {
                    case (3):
                        Status = "已完成"
                        break;
                    case (4):
                        Status = "退款中"
                        break;
                    case (5):
                        if (business_remarks != null) {
                            Status = "已退款"
                        } else {
                            Status = "已取消"
                        }
                        break;
                    case (6):
                        Status = "已退款"
                        break;

                    case (7):
                        Status = "支付失败"
                        break;

                    case (8):
                        Status = "已关闭"
                        break;

                    default:
                }
                if (orderDetailsVo.trans_num == null){
                   var Transaction_num = "无";
                }else {
                    var Transaction_num =orderDetailsVo.trans_num;
                }

                end_ += '		</div>                                                                                        '
                    + '	</div>                                                                                            '

                var _html = ' <link rel="stylesheet" href="/plugins/dataTable/js/adminHx/bureaut/tourism/css/order.css">';
                _html += '<table id="z-zwz">                                                                                '
                    + '	     <td>交易单信息</td>                                                                                            '
                    + '	        <tr>                                                                                          '
                    + '	           <td id="one">                                                                                       '
                    + '	           产品详情                                                                                    '
                    + '	          </td>                                                                                   '
                    + '	          <td id="tow">                                                                           '
                    + '	          <a href="#" target="_blank">查看</a>                                                                                 '
                    + '	         </td>                                                                                   '
                    + '	        </tr>                                                                                   '
                    + '	         <tr>                                                                                 '
                    + '	          <td id="one">                                                                                   '
                    + '	           交易单号                                                                                   '
                    + '	          </td>                                                                                   '
                    + '	          <td id="tow">                                                                              '
                    + '	           '+Transaction_num+'                                                                                    '
                    + '	          </td>                                                                                    '
                    + '	         </tr>                                                                                    '
                    + '	         <tr>                                                                                    '
                    + '	         <td id="one">                                                                                   '
                    + '	         状态                                                                                    '
                    + '	        </td>                                                                                   '
                    + '	         <td id="tow">                                                                                    '
                    + '	         '+Status+'                                                                                    '
                    + '	         </td>                                                                                    '
                    + '	         <tr>                                                                                    '
                    + '	           <td id="one">                                                                                  '
                    + '	          订单编号                                                                                   '
                    + '	          </td>                                                                                 '
                    + '	          <td id="tow">                                                                                   '
                    + '	         '+hwOrderVo.order_num+'<a onclick="order_details('+hwOrderVo.order_id+',\'admin/order/details\')" href="javascript:void(0);" >查看</a>                                                                                  '
                    + '	          </td>                                                                                   '
                    + '	         </tr>                                                                                   '
                    + '	         <tr>                                                                                   '
                    + '	          <td id="one">                                                                                  '
                    + '	          提交时间                                                                                   '
                    + '	          </td>                                                                                   '
                    + '	         <td id="tow">                                                                                  '
                    + '	          '+apply_sales_time+'                                                                                   '
                    + '	          </td>                                                                                  '
                    + '	        </tr>                                                                                   '
                    + '	        <tr>                                                                                   '
                    + '	          <td id="one">                                                                                   '
                    + '	          用户账号                                                                                  '
                    + '	          </td>                                                                                   '
                    + '	          <td id="tow">                                                                                  '
                    + '	          '+hwOrderVo.user_id+'                                                                                   '
                    + '	          </td>                                                                                   '
                    + '	         </tr>                                                                                   '
                    + '<tr>                                                                                               '
                    + '<td id="one">                                                                                               '
                    + '订单金额                                                                                               '
                    + '</td>                                                                                               '
                    + '<td id="tow">                                                                                               '
                    + '       '+hwOrderVo.payment+'                                                                                               '
                    + '</td>                                                                                               '
                    + '</tr>                                                                                               '
                    + '<tr>                                                                                               '
                    + '<td id="one">                                                                                               '
                    + '确认退款金额                                                                                               '
                    + '</td>                                                                                               '
                    + '<td id="tow">                                                                                               '
                    + ''+Refund_sum+'                                                                    '
                    + '</td>                                                                     '
                    + '</tr>                                                                     '
                    + '<tr>                                                                     '
                    + '<td id="one">                                                                     '
                    + '退款方式                                                                     '
                    + '</td>                                                                     '
                    + '<td id="tow">                                                                      '
                    + '用户钱包余额                                                                      '
                    + '</td>                                                                      '
                    + '</tr>                                                                      '
                    + '<tr>                                                                      '
                    + '<td id="one">                                                                      '
                    + '退款类型                                                                      '
                    + '</td>                                                                      '
                    + '<td id="tow">                                                                      '
                    + '取消订单                                                                     '
                    + '</td>                                                                      '
                    + '</tr>                                                                      '
                    + '<tr>                                                                      '
                    + '<td id="one">                                                                      '
                    + '发起方                                                                      '
                    + '</td>                                                                      '
                    + '<td id="tow">                                                                      '
                    + ''+initiator+'                                                                     '
                    + '</td>                                                                      '
                    + '</tr>                                                                      '
                    + '<tr>                                                                      '
                    + '<td id="one">                                                                      '
                    + '违约金                                                                      '
                    + '</td>                                                                      '
                    + '<td id="tow">                                                                      '
                    + '<input  type="radio" '+notWei+' />无                          '
                    + '<input  type="radio" '+wei+' style="margin-left: 100px;"/>有                          '
                    + ''+Contract_money+'元                          '
                    + '</td>                          '
                    + '</tr>                          '
                    + '</table>                          '
                    + '<table id="z-break">                          '
                    + '<tr id="datekrui" style="height: 50px auto;">                          '
                    + '<td id="guider">                          '
                    + '操作者                          '
                    + '</td>                          '
                    + '<td id="guider">                          '
                    + ''+Operator+'       '+operate+'                       '
                    + '</td>                          '
                    + '<td id="guider">                          '
                    + '操作时间                          '
                    + '</td>                          '
                    + '<td id="guider">                          '
                    + ''+consent_time+' '+refuse_time+' '+refund_time_back+'                       '
                    + '</td>                          '
                    + '</tr>                          '


                _html += end_ + '</table>'

                fromEject(_html, dec, '80%', '80%');
           
        }
    })

}


/**
 * 订单确认
 */
function order_confirm(order_id, url) {
    $.ajax({
        url: url,
        type: "POST",
        data: {order_id: order_id},
        success: function (res) {
            res = strToJson(res);
            jsonState(res);
           
                layer.alert(res.msg, {icon: 6}, function () {
                    layer.closeAll();
                    reloadTable($("#dynamic-table"))
                });
            
        }
    })
}

/**
 * 订单拒绝
 */
function order_refuse(order_id, url) {
    reson(order_id, "订单拒绝", url)
}

/**
 * 订单取消
 */
function order_cancel(order_id, url) {
    reson(order_id, "订单取消", url)
}

/**
 * 原因弹出
 * @param order_id
 * @param dec
 * @param url
 */
function reson(order_id, dec, url) {
    var _html = '<form  id="form">                                                                                                                                                                                         '
        + '      <div class="br-pagebody">                                                                                                                                                                           '
        + '                                                                                                                                                                                                          '
        + '        <div class="br-section-wrapper">                                                                                                                                                                  '
        + '            <!--内容-->                                                                                                                                                                                   '
        + '            <div class="row">                                                                                                                                                                             '
        + '                <p class="tx-11 tx-uppercase tx-spacing-2 mg-b-10 mg-t-10 tx-gray-600"><span class="tx-danger">*</span>输入原因:</p>                                                                          '
        + '                <div class="col-lg mg-l-10">                                                                                                                                                              '
        + '                    <input class="form-control wd-250" placeholder="输入原因" id="business_remarks" name="business_remarks" type="text">                                                                                                                '
        + '                </div><!-- col -->                                                                                                                                                                        '
        + '                                                                                                                                                                                                          '
        + '            </div>                                                                                                                                                                                        '
        + '		              <input id="order_id" name="order_id" type="hidden" value=' + order_id + '>                                                                                                                                                                                      '
        + '                                                                                                                                                                                                          '
        + '            <div class="row mg-t-20" >                                                                                                                                                                    '
        + '                <div class="btn btn-secondary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15" onclick="cancelFormLayer()" >取消</div>                                                                  '
        + '                <div class="btn btn-outline-primary btn-oblong bd-2 pd-x-30 pd-y-10 tx-uppercase tx-bold tx-spacing-1 tx-15 mg-l-10" onclick="sure_order(\'' + url + '\',\'form\')">确定</div>    '
        + '            </div>                                                                                                                                                                                        '
        + '                                                                                                                                                                                                          '
        + '        </div>                                                                                                                                                              '
        + '      </div>                                                                                                                                                                         '
        + '</form>'
    fromEject(_html, dec, '50%', '50%');
}

/**
 * 确定
 */
function sure_order(url, form) {

    if (validation(["business_remarks", "order_id"], [], [])) {

        sub_order(url, form);

    } else {
        return
    }

}

/**
 * 提交操作
 */
function sub_order(url, form) {
    $.ajax({
        url: url,
        type: "POST",
        data: $("#" + form).serialize(),
        success: function (res) {
            res = strToJson(res);
            jsonState(res);
         
                layer.alert(res.msg, {icon: 6}, function () {
                    layer.closeAll();
                    reloadTable($("#dynamic-table"))
                });
           
        }
    })
}

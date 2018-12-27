$(function () {
    // 每页显示条数
    _settings.ro_pageSazi = 10;
    // 请求地址
    _settings.ro_requestUrl = "admin/account/query";
    // 排序设置
    _settings.ro_orderBy = "status asc";

    // 显示字段数组
    _settings.ro_columns = [
        {
            customHtml:
                function(rowData,index){
                    return '<input type="checkbox" value="'+rowData.trans_num+'" style="display: inline-block;background-color: #fff; width: 18px;height: 18px;border: 1px solid #adb5bd;border-radius: 2px;" onclick="checkOne()" class="checkOne" />';
                },
            cssText: "center"
        },

        {
            filed: 'trans_num',
            customHtml: function (rowData, index) {
                return rowData.trans_num == null ? "" : rowData.trans_num;
            }
        },
        {
            filed: 'payment_or_operation_amount',
            customHtml: function (rowData, index) {
                return rowData.operation_amount == null ? "0.00" : rowData.operation_amount;
            }
        },
        {
            filed: 'source_or_paymen_type',
            customHtml: function (rowData, index) {
                if (rowData.bill_type == 4 || rowData.bill_type == 5){
                    if (rowData.source == 1) {
                        return "支付宝";
                    }
                    if (rowData.source == 2) {
                        return "微信";
                    }
                    if (rowData.source == 3) {
                        return "钱包";
                    }
                    if (rowData.source == 4) {
                        return "其他";
                    }
                }else if (rowData.source == 1) {
                    return "支付宝";
                }
                if (rowData.source == 2) {
                    return "微信";
                }
                if (rowData.source == 3) {
                    return "零钱";
                }
                if (rowData.source == 4) {
                    return "其他";
                }
                return "";
            }
        },

//        {filed:'line_price', 
//        	customHtml : function(rowData,index){
//        		return rowData.line_price==null?"":rowData.line_price	;
//			}
//        },
        {
            filed: 'payment_time_or_create_time',
            customHtml: function (rowData, index) {
                return rowData.create_time == null ? "N/A" : timestampToTime(rowData.create_time);
            }
        },
        {
            filed: 'handlers',
            customHtml: function (rowData, index) {
                return (rowData.handlers == null ? "N/A" : rowData.handlers);
            }
        },

        {
            filed: 'account_time',
            customHtml: function (rowData, index) {
                return rowData.account_time == null ? "N/A" : timestampToTime(rowData.account_time);
            }
        },


        {
            filed: 'status',
            customHtml: function (rowData, index) {
                if (rowData.status == 0) {
                    return '未对账';
                } else if (rowData.status == 1) {
                    return '已对账';
                }
            }
        },


        //
        // {filed:'status',
        // 	customHtml : function(rowData,index){
        // 		if(rowData.status == 1) { return "<span class='label label-sm label-default'>待审核</span>"}
        // 		if(rowData.status == 2) { return "<span class='label label-sm label-success'>审核通过</span>"}
        // 		if(rowData.status == 3) { return "<span class='label label-sm label-warning'>审核未通过</span>"}
        // 	}
        // },
        {
            title: '操作', customHtml:
            // 自定义函数 用于显示td的内容 rowData 是该行的数据源 index为行索引
                function (rowData, index) {
                    var html = '';

                    // var da = rowData.trans_num;
                    if(rowData.order_id != null){
                    html += '<a onclick="order_deta('+rowData.order_id+',\'admin/account/query\',\'查询\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>查询详情</div></a>';
                    }
                    if (rowData.bill_type == 1){
                        //alert(rowData.trans_num)
                    html += '<a onclick="order_details2(\''+rowData.trans_num+'\','+rowData.bill_type+',\'admin/account/query\',\'查询\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>查询详情</div></a>';
                    }
                    if (rowData.bill_type == 5){
                        //alert(rowData.trans_num)
                    html += '<a onclick="order_details3(\''+rowData.trans_num+'\','+rowData.bill_type+',\'admin/account/query\',\'查询\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>查询详情</div></a>';
                    }


                    return html;
                }
        }
    ];
    console.info(_settings);
    loadDataTable($("#dynamic-table"));
});




/**
 * 刷新
 */
function reCommit() {
    $("input").val("");
    var SelectArr = $("select")
    for (var i = 0; i < SelectArr.length; i++) {
        SelectArr[i].options[0].selected = true;
    }

    sreachCommit()
}

/**
 * 查询
 */
function sreachCommit() {
    paramBuffer();
    // 加载数据
    loadDataTable(_myDataTables.table)
}

/**
 * 组装查询参数
 */
function paramBuffer() {
    _settings.ro_queryParams = function createParams() {
        var params = {};
        params.startNum = _settings.ro_pageSazi * (_settings.ro_currentPage - 1);
        params.pageSize = _settings.ro_pageSazi;
        params.orderBy = _settings.ro_orderBy;
        params.paramMap = {};

        if (isNotEmpty($("#paramMap_status").val()))
            params.paramMap.status = $("#paramMap_status").val();
        if (isNotEmpty($("#paramMap_order_num").val()))
            params.paramMap.order_num = $("#paramMap_order_num").val();
        // if(isNotEmpty($("#paramMap_filde").val()))
        //     params.paramMap.filde = $("#paramMap_filde").val();


        return params;
    }
}

/**
 * 设置排序
 * @param filedName
 */
function setOrder(filedName) {
    // 设置排序字段
    setOrderBy(filedName)
    // 重新加载数据
    reloadTable(_myDataTables.table)
}


/**
 * 全选
 */
function checkAll() {
    var checkAll = $("#checkAll")[0];
    if (checkAll.checked) {
        var checkOnes = $(".checkOne");
        for (var i = 0; i < checkOnes.length;i++){
            var checkOne = checkOnes[i];
            checkOne.checked = true;
        }
    }else {
        var checkOnes = $(".checkOne");
        for (var i = 0; i < checkOnes.length;i++){
            var checkOne = checkOnes[i];
            checkOne.checked = false;
        }
    }
}

/**
 * 单选控制全选状态
 */
function checkOne() {
    var checkOnes = $(".checkOne");
    var check = $("#checkAll")[0];
    for(var i = 0;i < checkOnes.length; i++){
        if (!checkOnes[i].checked){
            check.checked =false;
            return;
        }
        check.checked = true;
    }
}

/**
 * 批量对账
 */
function batchAuditer() {
    var video_ids = new Array();
    var checks = $(".checkOne");
    for (var i = 0;i < checks.length;i++){
        var check = checks[i];
        if (check.checked) {
            video_ids[i] = check.value;
        }
    }

    var ids = video_ids.join(",");



    $.post("admin/account/batchReconciliation",{"transfer":ids,"paramMap[status]":bill_type},function (res) {
        res = strToJson(res);
        jsonState(res);
        if (res.code == "200") {
            layer.alert(res.msg, {icon: 6}, function () {
                layer.closeAll();
                reloadTable($("#dynamic-table"))
            });
        } else {
            if (res.msg != null) {
                layer.alert(res.msg, {icon: 6});
            } else {
                layer.alert('调试错误', {icon: 6});
            }
        }
    });

}

/**
 * 批量导出数据
 */
function deriveData() {
    var video_ids = new Array();
    var checks = $(".checkOne");
    for (var i = 0;i < checks.length;i++){
        var check = checks[i];
        if (check.checked) {
            video_ids[i] = check.value;
        }
    }

    var ids = video_ids.join(",");
    if (ids != "" && ids !=null  && ids !=undefined){
        window.location.href = "admin/account/excel?transfer="+ids+"&paramMap[status]="+bill_type;
        reloadTable($("#dynamic-table"));

    } else {
        layer.alert("复选框不能为空", {icon: 6});
    }


   // $.post(function(){
   //          type:"get",
   //              url :"admin/account/excel",
   //              async :false
   //
   //      },function () {
   //
   //      });
}

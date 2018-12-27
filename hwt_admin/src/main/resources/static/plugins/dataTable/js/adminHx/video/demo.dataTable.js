
$(function(){
    // 每页显示条数
    _settings.ro_pageSazi = 10;
    // 请求地址
    _settings.ro_requestUrl = "admin/video/query";
    // 排序设置
    _settings.ro_orderBy = "status ASC,a.create_time DESC";
    // 显示字段数组
    _settings.ro_columns =  [
        {customHtml :
                function(rowData,index){
                    return '<input type="checkbox" value="'+rowData.video_id+'" style="display: inline-block;background-color: #fff; width: 18px;height: 18px;border: 1px solid #adb5bd;border-radius: 2px;" onclick="checkOne()" class="checkOne" />';
                },
            cssText : "center"
        },

        {filed:'video_id',
            customHtml : function(rowData,index){
                return rowData.video_id==null?"":rowData.video_id;
            }
        },
        // {filed:'image',
        //     customHtml : function(rowData,index){
        //         return rowData.image==null?"":rowData.image;
        //     }
        // },
        {filed:'image',
            customHtml : function(rowData,index){
                if (rowData.image==null||rowData.image=='') {
                    return ""
                } else {
                    var images = rowData.image;
                     var image = images.split(',')[0];
                    return '<a href="javascript:void(0);" onclick="catImage(\''+image+'\')"><img src="'+image+'" height="80" width="80" /></a>'
                }

            }
        },
       {filed:'dec',
       	customHtml : function(rowData,index){
            var openingHours = rowData.dec == null ? "" : rowData.dec;
            if (openingHours.indexOf('"') >= 0) {
                openingHours = openingHours.replace('"', "");
            }
            if (openingHours.length > 10) {
                var a = openingHours.substring(0, 10)
                a += "...";
                var _html = '';
                _html += '<p title="' + openingHours + '">' + a + '</p>'
                return _html;
            } else {
                return openingHours;
            }
			}
       },
        {filed:'account',
            customHtml : function(rowData,index){
                return (rowData.account==null?"0.00":rowData.account) ;
            }
        },
        {filed:'nickname',
            customHtml : function(rowData,index){
                return (rowData.nickname==null?"":rowData.nickname) ;
            }
        },
        {filed:'create_time',
            customHtml : function(rowData,index){
                return rowData.create_time==null?"":timestampToTime(rowData.create_time) ;
            }
        },


        {filed:'status',
            customHtml : function(rowData,index){
                var haomiao =  new Date().getTime();
                if (rowData.status==1){
                    return '未审核'
                }else if (rowData.status == 2){
                    return '已通过审核'
                }else if (rowData.status == 3){
                    return '未通过审核'
                }else if (rowData.status == 4){
                    return '已删除'
                }
                // else if (rowData.status == 3){
                // 	return '已完成'
                // }else if ((rowData.status == 4 || rowData.status == 5) && rowData.apply_sales_time != null){
                // 	return '已退款'
                // }else if ((rowData.status == 4 || rowData.status == 5) && rowData.apply_sales_time == null){
                // 	return '已取消'
                // }
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
        {title:'操作', customHtml:
            // 自定义函数 用于显示td的内容 rowData 是该行的数据源 index为行索引
                function(rowData,index){
                    var html = '';

                    html += '<a onclick="order_details('+rowData.video_id+',\'admin/video/details\',\'查询详情\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>查看</div></a>';

                    if (rowData.status != 4){
                    html += '<a onclick="delete_video('+rowData.video_id+',\'admin/video/delete\',\'删除\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>删除</div></a>';
                    }

                    // if (rowData.status==1){
                    // 	html += '<a onclick="order_confirm('+rowData.video_id+',\'bureau/order/confirm\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>确认订单</div></a>';
                    // 	html += '<a onclick="order_refuse('+rowData.video_id+',\'bureau/order/refuse\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>拒绝订单</div></a>';
                    // }
                    //
                    // if (rowData.status == 2 && new Date().getTime() < rowData.start_time){
                    // 	html += '<a onclick="order_cancel('+rowData.video_id+',\'bureau/order/cancel\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>取消订单</div></a>';
                    // }

                    return html;
                }
        }
    ];
    console.info(_settings);
    loadDataTable($("#dynamic-table"));
});

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
 * 批量审核
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

    $.post("admin/video/batchReview",{"video_ids":ids},function (res) {
        res = strToJson(res);
        jsonState(res);
        if (res.code == "200") {
            layer.alert(res.msg, {icon: 6}, function () {
                layer.closeAll();
            });
        } else {
            if (res.msg != null) {
                layer.alert(res.msg, {icon: 6});
            } else {
                layer.alert('调试错误', {icon: 6});
            }
        }
        reloadTable($("#dynamic-table"))
    });

}

/**
 * 刷新
 */
function reCommit(){
    $("input").val("");
    var SelectArr = $("select")
    for (var i = 0; i < SelectArr.length; i++) {
        SelectArr[i].options[0].selected = true;
    }

    sreachCommit1()
}
/**
 * 查询
 */
function sreachCommit1() {
    paramBuffer();
    // 加载数据
    loadDataTable(_myDataTables.table)
}

/**
 * 组装查询参数
 */
function paramBuffer() {
    _settings.ro_queryParams =  function createParams(){
        var params = {};
        params.startNum = _settings.ro_pageSazi * (_settings.ro_currentPage - 1);
        params.pageSize = _settings.ro_pageSazi;
        params.orderBy = _settings.ro_orderBy;
        params.paramMap = {};

        if(isNotEmpty($("#paramMap_status").val()))
            params.paramMap.status = $("#paramMap_status").val();
        if(isNotEmpty($("#paramMap_account").val()))
            params.paramMap.account = $("#paramMap_account").val();
        // if(isNotEmpty($("#paramMap_filde").val()))
        //     params.paramMap.filde = $("#paramMap_filde").val();
        if (isNotEmpty($("#paramMap_time").val()))
            params.paramMap.time = $("#paramMap_time").val();

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

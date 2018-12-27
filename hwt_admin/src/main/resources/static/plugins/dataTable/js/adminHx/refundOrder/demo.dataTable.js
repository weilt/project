
$(function(){
    // 每页显示条数
    _settings.ro_pageSazi = 10;
    // 请求地址
    _settings.ro_requestUrl = "admin/order/refundQuery";
    // 排序设置
    _settings.ro_orderBy = "apply_sales_time DESC";
    // 显示字段数组
    _settings.ro_columns =  [
        {customHtml :
                function(rowData,index){
                    return '<label class="ckbox " > <input type="checkbox" ><span></span> </label>';
                },
            cssText : "center"
        },

        {filed:'order_num',
            customHtml : function(rowData,index){
                return rowData.order_num==null?"":rowData.order_num;
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

//        {filed:'line_price', 
//        	customHtml : function(rowData,index){
//        		return rowData.line_price==null?"":rowData.line_price	;
//			}
//        },
        {filed:'apply_sales_time',
            customHtml : function(rowData,index){
                return rowData.apply_sales_time==null?"":timestampToTime(rowData.apply_sales_time) ;
            }
        },


        {filed:'status',
            customHtml : function(rowData,index){
                var haomiao =  new Date().getTime();
                if (rowData.status==4){
                    return '待处理'
                }else if (rowData.status == 5){
                    return '已处理'
                }else if (rowData.status == 6 ){
                    return '已退款'
                }else if (rowData.status == 7 ){
                    return '失败'
                }else if (rowData.status == 8 ){
                    return '已关闭'
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

        {filed:'payment',
            customHtml : function(rowData,index){
                return "￥" + (rowData.payment==null?"0.00":rowData.payment) ;
            }
        },
        {filed:'refund_time',
            customHtml : function(rowData,index){
            if (rowData.refundable_time!=null){
                return rowData.refundable_time==null?"N/A":timestampToTime(rowData.refundable_time);
            }

            if (rowData.consent_time!=null){
                return rowData.consent_time==null?"N/A":timestampToTime(rowData.consent_time);
            }
            if (rowData.refuse_time==null){
                    //debugger
                return  rowData.refund_time_back ==null?"N/A":timestampToTime(rowData.refund_time_back);
            }
                return rowData.refuse_time==null?"N/A":timestampToTime(rowData.refuse_time);
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
                    if (rowData.status == 4){
                    html += '<a onclick="order_details1('+rowData.order_id+',\'admin/order/refundDetails\',\'退款详情\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>退款详情</div></a>';
                    }
                    if (rowData.status == 5){
                    html += '<a onclick="order_details3('+rowData.order_id+',\'admin/order/refundDetails\',\'退款详情\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>退款详情</div></a>';
                    }
                    if (rowData.status == 6 || rowData.status == 7 || rowData.status == 8){
                    html += '<a onclick="order_details2('+rowData.order_id+',\'admin/order/refundDetails\',\'退款详情\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>退款详情</div></a>';
                    }

                    // if (rowData.status==1){
                    // 	html += '<a onclick="order_confirm('+rowData.order_id+',\'bureau/order/confirm\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>确认订单</div></a>';
                    // 	html += '<a onclick="order_refuse('+rowData.order_id+',\'bureau/order/refuse\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>拒绝订单</div></a>';
                    // }
                    //
                    // if (rowData.status == 2 && new Date().getTime() < rowData.start_time){
                    // 	html += '<a onclick="order_cancel('+rowData.order_id+',\'bureau/order/cancel\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>取消订单</div></a>';
                    // }

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
function reCommit(){
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
    _settings.ro_queryParams =  function createParams(){
        var params = {};
        params.startNum = _settings.ro_pageSazi * (_settings.ro_currentPage - 1);
        params.pageSize = _settings.ro_pageSazi;
        params.orderBy = _settings.ro_orderBy;
        params.paramMap = {};

        if(isNotEmpty($('#paramMap_status option:selected').val()))
            params.paramMap.status = $('#paramMap_status option:selected').val();
        if(isNotEmpty($("#paramMap_order_num").val()))
            params.paramMap.order_num = $("#paramMap_order_num").val();
        if(isNotEmpty($('#cicerone_id option:selected').val()))
            params.paramMap.cicerone_id = $('#cicerone_id option:selected').val();
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

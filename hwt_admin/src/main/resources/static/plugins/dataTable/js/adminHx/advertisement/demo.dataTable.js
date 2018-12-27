
$(function(){
	// 每页显示条数
	_settings.ro_pageSazi = 10;
	// 请求地址
	_settings.ro_requestUrl = "adminHx/advertisement/query";
	// 排序设置
	_settings.ro_orderBy = "create_time desc";
	// 显示字段数组
	_settings.ro_columns =  [
        {customHtml : 
        	function(rowData,index){
        		return '<label class="ckbox " > <input type="checkbox" value="'+rowData.information_id+'" ><span></span> </label>';
        	},
         cssText : "center"
        },
        {filed:'ad_id', 
        	customHtml : function(rowData,index){
				return rowData.ad_id==null?"":rowData.ad_id;
			}
        },
       
       
        {filed:'title',
        	customHtml : function(rowData,index){
        		return rowData.title==null?"":rowData.title;
        	}
        },
        {filed:'ad_position',
        	customHtml : function(rowData,index){
				return rowData.ad_position==null?"":rowData.ad_position;
			}
        },
        {filed:'is_online',
        	customHtml : function(rowData,index){
        		if (rowData.is_online==1){
        			return "上线";
        		} else {
        			return "下线";
        		}
        	}
        },
        {filed:'ad_type',
        	customHtml : function(rowData,index){
				return rowData.ad_type==null?"":rowData.ad_type;
			}
        },
        {filed:'create_time', 
        	customHtml : function(rowData,index){
        		return rowData.create_time==null?"":timestampToTime(rowData.create_time);
			}
        },
        {filed:'click_num',
        	customHtml : function(rowData,index){
				return rowData.click_num==null?"0":rowData.click_num;
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
				html += '<a href="javascript:edit_advertisement('+rowData.ad_id+',\'adminHx/advertisement/queryInfo\',\'广告管理-编辑（'+rowData.tilte+'）\');" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>修改</div></a>';
				if (rowData.is_hide==0){
        			html += '<a onclick="is_hideAndRecoveryById('+rowData.ad_id+',\'adminHx/advertisement/is_hide\',\'确定要删除广告---'+rowData.tilte+'？\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-trash"></i>删除</div></a>';
        		}else{
        			html += '<a onclick="is_not_hideAndRecoveryById('+rowData.ad_id+',\'adminHx/advertisement/is_not_hide\',\'确定要恢复广告---'+rowData.tilte+'？\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>恢复</div></a>';
        		}
				if (rowData.is_online==1){
					html += '<a onclick="is_not_displayAndRecoveryById('+rowData.ad_id+',\'adminHx/advertisement/not_online\',\'确定要下线广告---'+rowData.tilte+'？\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-trash"></i>隐藏</div></a>';
				}else{
					html += '<a onclick="is_displayAndRecoveryById('+rowData.ad_id+',\'adminHx/advertisement/online\',\'确定要上线广告---'+rowData.tilte+'？\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>显示</div></a>';
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
		params.currentPage = _settings.ro_currentPage;
		params.pageSize = _settings.ro_pageSazi;
		params.orderBy = _settings.ro_orderBy;
		params.paramMap = {};
		
	
		if(isNotEmpty($("#paramMap_city").val()))
			params.paramMap.city = $("#paramMap_city").val();
		if(isNotEmpty($("#paramMap_filed").val()))
			params.paramMap.filed = $("#paramMap_filed").val();
		
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



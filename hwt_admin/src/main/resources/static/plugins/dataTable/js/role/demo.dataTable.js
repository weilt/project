
$(function(){
	// 每页显示条数
	_settings.ro_pageSazi = 10;
	// 请求地址
	_settings.ro_requestUrl = "admin/role/query";
	// 排序设置
	_settings.ro_orderBy = "createTime asc";
	// 显示字段数组
	_settings.ro_columns =  [
        {customHtml : 
        	function(rowData,index){
        		return '<label class="ckbox " > <input type="checkbox" ><span></span> </label>';
        	},
         cssText : "center"
        },
        {filed:'id', 
        	customHtml : function(rowData,index){
				return rowData.id;
			}
        },
        {filed:'name', 
        	customHtml : function(rowData,index){
        		if (rowData.id==1){
        			return '<font color="red">'+rowData.name+'</font>';
        		}
        		
				return rowData.name;
			}
        },
        {filed:'number',
        	customHtml : function(rowData,index){
				return rowData.number;
			}
        },
        {filed:'description', 
        	customHtml : function(rowData,index){
				return rowData.description;
			}
        },
        {filed:'user_account', 
        	customHtml : function(rowData,index){
				return rowData.user_account;
			}
        },
        {filed:'createTime', 
        	customHtml : function(rowData,index){
				return rowData.createTime;
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
				html += '<a href="javascript:edit('+rowData.id+',\'admin/role/update\',\'角色管理-编辑（'+rowData.name+'）\','+1+');" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>修改</div></a>';
				if (rowData.id!=1){
					html += '<a onclick="deleteById(this,'+rowData.id+',\'admin/role/delete\',\'确定要删除角色---'+rowData.name+'？\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-trash"></i>删除</div></a>';
					html+='<a onclick="adpower(this,1,'+rowData.id+',\'admin/role/adpower\',\'角色授权---'+rowData.name+'\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>授权</div></a>'
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
		params.startNum = _settings.ro_pageSazi * (_settings.ro_currentPage - 1);
		params.pageSize = _settings.ro_pageSazi;
		params.orderBy = _settings.ro_orderBy;
		params.paramMap = {};
		
	
		if(isNotEmpty($("#paramMap_name").val()))
			params.paramMap.name = $("#paramMap_name").val();
		
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

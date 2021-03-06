
$(function(){
	// 每页显示条数
	_settings.ro_pageSazi = 10;
	// 请求地址
	_settings.ro_requestUrl = "adminHx/user/query";
	// 排序设置
	_settings.ro_orderBy = "recently_login_time desc";
	// 显示字段数组
	_settings.ro_columns =  [
        {customHtml : 
        	function(rowData,index){
        		return '<label class="ckbox " > <input type="checkbox" ><span></span> </label>';
        	},
         cssText : "center"
        },
        
        {filed:'account', 
        	customHtml : function(rowData,index){
        		return rowData.account;
			}
        },
        {filed:'nickname', 
        	customHtml : function(rowData,index){
        		return rowData.nickname;
        	}
        },
        {filed:'account_phone',
        	customHtml : function(rowData,index){
				return rowData.account_phone==null?"":rowData.account_phone;
			}
        },
        {filed:'user_sex', 
        	customHtml : function(rowData,index){
				if (rowData.user_sex==1){
					return '男'
				}else if (rowData.user_sex==2){
					return '女'
				}else{
					return '保密'
				}
			}
        },
        {filed:'userType', 
        	customHtml : function(rowData,index){
				if (rowData.userType==0){
					return '普通用户'
				}else if (rowData.userType==1){
					return 'VIP用户'
				}else{
					return '其他'
				}
			}
        },
        {filed:'recently_login_time',
        	customHtml : function(rowData,index){
        		
        			 return rowData.recently_login_time==null?"":timestampToTime(rowData.recently_login_time) ;
        		
        	}
        },
        {filed:'recently_login_ip',
        	customHtml : function(rowData,index){
        		return rowData.recently_login_ip == null?"":rowData.recently_login_ip;
        	}
        },
        {filed:'login_state', 
        	customHtml : function(rowData,index){
				if (rowData.login_state==1){
					return '正常'
				}else if (rowData.login_state==2){
					return '<font color="red">冻结</font>'
				}else{
					return '<font color="red">永久冻结</font>'
				}
			}
        },
        {filed:'balance',
        	customHtml : function(rowData,index){
        		return rowData.balance == null?"0.00":rowData.balance;
        	}
        },
        {filed:'order_num',
        	customHtml : function(rowData,index){
        		return rowData.order_num == null?"0":rowData.order_num;
        	}
        },
        {filed:'is_order_num',
        	customHtml : function(rowData,index){
        		return rowData.is_order_num == null?"0":rowData.is_order_num;
        	}
        },
        {filed:'video_num',
        	customHtml : function(rowData,index){
        		return rowData.video_num == null?"0":rowData.video_num;
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
				html += '<a onclick="userInfo('+rowData.user_id+',\'adminHx/user/userInfo\',\'用户资料-查看（'+rowData.account+'）\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>用户资料</div></a>';
				if (rowData.login_state==1){
        			//html += '<a onclick="frozen(2,'+rowData.id+',\'admin/module/frozen\',\'确定要删除模块---'+rowData.name+'？\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-trash"></i>冻结</div></a>';
        			html += '<a onclick="deleteAndRecoveryById(3,'+rowData.user_id+',\'admin/module/frozen\',\'确定要冻结---'+rowData.account+'？\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-trash"></i>永久冻结</div></a>';
        		}else{
        			html += '<a onclick="deleteAndRecoveryById(1,'+rowData.user_id+',\'admin/module/frozen\',\'确定要解冻---'+rowData.name+'？\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>解冻</div></a>';
        		}
				html += '<a onclick="webpageEject(\'adminHx/user/userFriend?id='+rowData.user_id+'\',\'用户好友-查看（'+rowData.account+'）\',\'80%\',\'80%\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>用户好友</div></a>';
				html += '<a onclick="webpageEject(\'adminHx/user/order?user_id='+rowData.user_id+'\',\'用户订单-查看（'+rowData.account+'）\',\'80%\',\'80%\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>用户订单</div></a>';
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
		
	
		if(isNotEmpty($("#paramMap_account").val()))
			params.paramMap.account = $("#paramMap_account").val();
		if(isNotEmpty($("#paramMap_account_phone").val()))
			params.paramMap.account_phone = $("#paramMap_account_phone").val();
		if(isNotEmpty($("#paramMap_userType").val()))
			params.paramMap.userType = $("#paramMap_userType").val();
		if(isNotEmpty($("#paramMap_user_sex").val()))
			params.paramMap.user_sex = $("#paramMap_user_sex").val();
		if(isNotEmpty($("#paramMap_login_state").val()))
			params.paramMap.login_state = $("#paramMap_login_state").val();
		
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

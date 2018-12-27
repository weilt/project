$(function () {
    // 每页显示条数
    _settings.ro_pageSazi = 10;
    // 请求地址
    _settings.ro_requestUrl = "admin/salesList/query";
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
            filed: 'payRow',
            customHtml: function (rowData, index) {
                return rowData.payRow == null ? "" : rowData.payRow;
            }
        },
        {
            filed: 'real_name',
            customHtml: function (rowData, index) {
                return (rowData.real_name == null ? "N/A" : rowData.real_name);
            }
        },
        {
            filed: 'orderCount',
            customHtml: function (rowData, index) {
                return rowData.orderCount == null ? "0.00" : rowData.orderCount;
            }
        },
        {
            filed: 'orderSum',
            customHtml: function (rowData, index) {
                return (rowData.orderSum == null ? "0.00" : rowData.orderSum);
            }
        },


        // {
        //     title: '操作', customHtml:
        //     // 自定义函数 用于显示td的内容 rowData 是该行的数据源 index为行索引
        //         function (rowData, index) {
        //             var html = '';
        //
        //             // var da = rowData.trans_num;
        //
        //             html += '<a onclick="order_deta('+rowData.order_id+',\'admin/salesList/query\',\'查询\')" href="javascrip:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>查询详情</div></a>';
        //
        //
        //
        //             return html;
        //         }
        // }
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
function sreachCommit(time) {
    paramBuffer(time);
    // 加载数据
    loadDataTable(_myDataTables.table)
}

/**
 * 组装查询参数
 */
function paramBuffer(time) {
    _settings.ro_queryParams = function createParams() {
        var params = {};
        params.startNum = _settings.ro_pageSazi * (_settings.ro_currentPage - 1);
        params.pageSize = _settings.ro_pageSazi;
        params.orderBy = _settings.ro_orderBy;
        params.paramMap = {};

        if (isNotEmpty($("#paramMap_time1").val()))
            params.paramMap.date_time1 = $("#paramMap_time1").val();
        if (isNotEmpty($("#paramMap_time2").val()))
            params.paramMap.date_time2 = $("#paramMap_time2").val();
        if (isNotEmpty($("#paramMap_param").val()))
            params.paramMap.cicerone_id = $("#paramMap_param").val();
            params.paramMap.bill_type = time;

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

function find(time){

    var cicerone_id = $("#param").val();

    alert(time +"=="+cicerone_id);
    $.post("admin/salesList/query",{"paramMap[param]":cicerone_id,"bill_type":time},function (res) {

    });
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

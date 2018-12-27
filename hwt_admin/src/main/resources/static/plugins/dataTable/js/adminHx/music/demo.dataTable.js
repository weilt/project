$(function () {
    // 每页显示条数
    _settings.ro_pageSazi = 10;
    // 请求地址
    _settings.ro_requestUrl = "admin/music/query";
    // 排序设置
    // _settings.ro_orderBy = "use_account DESC";
    // 显示字段数组
    _settings.ro_columns = [
        {
            customHtml:
                function (rowData, index) {
                    return '<input type="checkbox" value="' + rowData.music_id + '" style="display: inline-block;background-color: #fff; width: 18px;height: 18px;border: 1px solid #adb5bd;border-radius: 2px;" onclick="checkOne()" class="checkOne" />';
                },
            cssText: "center"
        },

        {
            filed: 'music_tag',
            customHtml: function (rowData, index) {
                if (rowData.music_tag == null) {
                    return '无分类'
                } else if (rowData.music_tag == 1) {
                    return '影视原声'
                } else if (rowData.music_tag == 2) {
                    return '日韩'
                } else if (rowData.music_tag == 3) {
                    return '生活'
                } else if (rowData.music_tag == 4) {
                    return '搞怪'
                } else if (rowData.music_tag == 5) {
                    return '流行'
                } else if (rowData.music_tag == 6) {
                    return '说唱'
                } else if (rowData.music_tag == 7) {
                    return '民谣'
                } else if (rowData.music_tag == 8) {
                    return '欧美'
                }
                // return rowData.music_tag==null?"":rowData.music_tag;
            }
        },
        {
            filed: 'music_writer',
            customHtml: function (rowData, index) {
                return (rowData.music_writer == null ? "" : rowData.music_writer);
            }
        },
        {
            filed: 'music_title',
            customHtml: function (rowData, index) {
                return (rowData.music_title == null ? "" : rowData.music_title);
            }
        },
        // {filed:'image',
        //     customHtml : function(rowData,index){
        //         return rowData.image==null?"":rowData.image;
        //     }
        // },
        {
            filed: 'music_cover',
            customHtml: function (rowData, index) {
                if (rowData.music_cover == null || rowData.music_cover == '') {
                    return ""
                } else {
                    var images = rowData.music_cover;
                    var image = images.split(',')[0];
                    return '<a href="javascript:void(0);" onclick="catImage(\'' + image + '\')"><img src="' + image + '" height="80" width="80" /></a>'
                }

            }
        },
        // {filed:'dec',
        // 	customHtml : function(rowData,index){
        //      var openingHours = rowData.dec == null ? "" : rowData.dec;
        //      if (openingHours.indexOf('"') >= 0) {
        //          openingHours = openingHours.replace('"', "");
        //      }
        //      if (openingHours.length > 10) {
        //          var a = openingHours.substring(0, 10)
        //          a += "...";
        //          var _html = '';
        //          _html += '<p title="' + openingHours + '">' + a + '</p>'
        //          return _html;
        //      } else {
        //          return openingHours;
        //      }
        // 	}
        // },
        {
            filed: 'use_account',
            customHtml: function (rowData, index) {
                return (rowData.use_account == null ? "" : rowData.use_account);
            }
        },
        {
            filed: 'user_id',
            customHtml: function (rowData, index) {
                return (rowData.user_id == 0 ? "平台" : rowData.user_id);
            }
        },
        {
            filed: 'is_open',
            customHtml: function (rowData, index) {
                if (rowData.is_open == 0) {
                    return '未隐藏'
                } else if (rowData.is_open == 1) {
                    return '已隐藏'
                } else if (rowData.is_open == 2) {
                    return '已删除'
                }
                // return (rowData.is_open==null?"":rowData.is_open) ;
            }
        },
        // {filed:'create_time',
        //     customHtml : function(rowData,index){
        //         return rowData.create_time==null?"":timestampToTime(rowData.create_time) ;
        //     }
        // },


        // {filed:'status',
        //     customHtml : function(rowData,index){
        //         var haomiao =  new Date().getTime();
        //         if (rowData.status==1){
        //             return '未审核'
        //         }else if (rowData.status == 2){
        //             return '已通过审核'
        //         }else if (rowData.status == 3){
        //             return '未通过审核'
        //         }else if (rowData.status == 4){
        //             return '已删除'
        //         }
        //         // else if (rowData.status == 3){
        //         // 	return '已完成'
        //         // }else if ((rowData.status == 4 || rowData.status == 5) && rowData.apply_sales_time != null){
        //         // 	return '已退款'
        //         // }else if ((rowData.status == 4 || rowData.status == 5) && rowData.apply_sales_time == null){
        //         // 	return '已取消'
        //         // }
        //     }
        // },

        {
            title: '操作', customHtml:
            // 自定义函数 用于显示td的内容 rowData 是该行的数据源 index为行索引
                function (rowData, index) {
                    var html = '';

                    html += '<a onclick="music_details(' + rowData.music_id + ',4,\'admin/music/modifyHideDelete\',\'修改\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>修改</div></a>';


                    html += '<a onclick="update_music(' + rowData.music_id + ',2,\'admin/music/modifyHideDelete\',\'隐藏\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>隐藏</div></a>';


                    html += '<a onclick="delete_music(' + rowData.music_id + ',3,\'admin/music/modifyHideDelete\',\'删除\')" href="javascript:void(0);" class="btn btn-sm btn-outline-primary mg-r-5 pd-b-5"><div><i class="fa fa-pencil"></i>删除</div></a>';

                    return html;
                }
        }
    ];
    console.info(_settings);
    loadDataTable($("#dynamic-table"));
});

/**
 * 隐藏音频
 * @param music_id
 * @param mhd
 * @param url
 * @param dec
 */
function delete_music(music_id, mhd, url, dec) {

    layer.confirm('是否删除音频? 可能会造成用户视频无音频的 严重后果!',
        {icon: 6, title: '提示'}, function () {
            $.ajax({
                url: url,
                type: "POST",
                data: {music_id: music_id, "mhd": mhd},
                success: function (res) {
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
                }
            });
        });


}

/**
 * 隐藏音频
 * @param music_id
 * @param mhd
 * @param url
 * @param dec
 */
function update_music(music_id, mhd, url, dec) {
    $.ajax({
        url: url,
        type: "POST",
        data: {music_id: music_id, "mhd": mhd},
        success: function (res) {
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
        }
    });

}


/**
 * 批量删除
 */
function batchAuditer() {
    var music_ids = new Array();
    var checks = $(".checkOne");
    for (var i = 0; i < checks.length; i++) {
        var check = checks[i];
        if (check.checked) {
            music_ids[i] = check.value;
        }
    }
    var ids = music_ids.join(",");
    if (ids.length == 0){
        layer.alert("请选择音频编号", {icon: 6}, function () {
            layer.closeAll();
        });
        return;
    }

    layer.confirm('是否删除音频? 可能会造成用户视频无音频的 严重后果!',
        {icon: 6, title: '提示'}, function () {
            $.post("admin/music/delete", {"music_ids": ids}, function (res) {
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
        });



}

/**
 * 全选
 */
function checkAll() {
    var checkAll = $("#checkAll")[0];
    if (checkAll.checked) {
        var checkOnes = $(".checkOne");
        for (var i = 0; i < checkOnes.length; i++) {
            var checkOne = checkOnes[i];
            checkOne.checked = true;
        }
    } else {
        var checkOnes = $(".checkOne");
        for (var i = 0; i < checkOnes.length; i++) {
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
    for (var i = 0; i < checkOnes.length; i++) {
        if (!checkOnes[i].checked) {
            check.checked = false;
            return;
        }
        check.checked = true;
    }
}

/**
 * 刷新
 */
function reCommit() {
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
    _settings.ro_queryParams = function createParams() {
        var params = {};
        params.startNum = _settings.ro_pageSazi * (_settings.ro_currentPage - 1);
        params.pageSize = _settings.ro_pageSazi;
        params.orderBy = _settings.ro_orderBy;
        params.paramMap = {};

        // if(isNotEmpty($("#paramMap_status").val()))
        //     params.paramMap.status = $("#paramMap_status").val();
        // if(isNotEmpty($("#paramMap_account").val()))
        //     params.paramMap.account = $("#paramMap_account").val();
        if (isNotEmpty($("select[name='music_tag']").val()))
            params.paramMap.music_tag = $("select[name='music_tag']").val();
        if (isNotEmpty($("select[name='music_writer']").val()))
            params.paramMap.music_writer = $("select[name='music_writer']").val();
        if (isNotEmpty($("select[name='music_title']").val()))
            params.paramMap.music_title = $("select[name='music_title']").val();
        if (isNotEmpty($("select[name='music_cover']").val()))
            params.paramMap.music_cover = $("select[name='music_cover']").val();
        if (isNotEmpty($("select[name='use_account']").val()))
            params.paramMap.use_account = $("select[name='use_account']").val();
        if (isNotEmpty($("select[name='is_open']").val()))
            params.paramMap.is_open = $("select[name='is_open']").val();
        if (isNotEmpty($("input[name='music_writer2']").val())) {
            params.paramMap.song = 1;
            params.paramMap.music_writer2 = $("input[name='music_writer2']").val();
        }
        if (isNotEmpty($("input[name='music_title2']").val())) {
            params.paramMap.song = 2;
            params.paramMap.music_title2 = $("input[name='music_title2']").val();
        }
        if (isNotEmpty($("input[name='user_id']").val())) {
            params.paramMap.song = 3;
            params.paramMap.user_id = $("input[name='user_id']").val();
        }

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


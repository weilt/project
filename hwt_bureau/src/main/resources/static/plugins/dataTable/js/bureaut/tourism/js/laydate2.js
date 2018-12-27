
var moneyData = null;
var getData = null;
var line_price = {};

var max_price = '';

var min_price = '';

function lookupDate(i , key){	//	数据回显
//	console.log(i)
//	console.log('key' , key)
	
	var str = i.split('-')
	var money = JSON.parse(key[i])
	if(str[1]+'月' == $('#monthList').val()){
		for(var i=0; i<$('.zry-tabel_li').length; i++){
			if($('.zry-tabel_li').eq(i).text() == str[2]){
				$('.zry-tabel_li').eq(i).append('<p class=zry-adultMoney>'+money.adultPrice+'</p>')//	添加成人价格
				$('.zry-tabel_li').eq(i).append('<p class=zry-childrenMoney>'+money.childPrice+'</p>')//	添加成人价格
				break;
			}
		}
	}
}


function Initialization(){
//	$(window).click(function() {
//	if($(".select-date").css("display") == "block") {
//		$(".select-date").css("display", "none")
//	}
//});
$(".btn-date,#laydateInput").on("click", function(e) {
	e.stopPropagation();
	if($(".select-date").css("display") == "none") {
		$(".select-date").css("display", "block")
	} else {
		$(".select-date").css("display", "none")
	}
});

var yearArr = [];
var monthArr = [];
for(var i = 1990; i < 2099; i++) {		//		年
	yearArr.push(i + "年");
	$("#yearList").append('<option value="' + (i + "年") + '">' + i + "年" + "</option>")
}
for(var j = 1; j < 13; j++) {			//		月
	monthArr.push(j + "月");
	$("#monthList").append('<option value="' + (j + "月") + '">' + j + "月" + "</option>")
}
var d = new Date();
var currYear = d.getFullYear();
var currMonth = (d.getMonth() + 1);
var currDate = d.getDate();
//console.log(currYear,currMonth,currDate)
getToDay(currDate)
$(".show-date").text(currYear + " - " + currMonth + " - " + d.getDate());		//	输入框时间展示
$("#yearList").val(currYear + "年");		//		年的展示
$("#monthList").val(currMonth + "月");		//		月的展示
$(".reback").eq(0).click(function() {			//		回到今天
	var d = new Date();
	var currYear = d.getFullYear();
	var currMonth = (d.getMonth() + 1);
	$("#yearList").val(currYear + "年");
	$("#monthList").val(currMonth + "月");
	$(".show-date").val(currYear + " - " + currMonth + " - " + d.getDate());
	ergodicDate(currYear, currMonth);
	getSelectDate(currYear + " - " + currMonth + " - " + d.getDate())
});
var currN = 0;
var currK = 0;
ergodicDate(currYear, currMonth);

function ergodicDate(year, month) {
	var preMonth = month - 1;
	var preYear = year;
	if(preMonth < 1) {
		preMonth = 12;
		preYear - 1
	}
	var preMonthLength = getMonthLength(preYear, preMonth);
	$(".day-tabel").eq(0).empty();
	var date1 = new Date(year + "/" + month + "/" + 1).getDay();

	function getMonthLength(year, month) {
		function isLeapYear(year) {
			return(year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)
		}
		if(month == 4 || month == 6 || month == 9 || month == 11) {
			month = 30;
			return month
		} else {
			if(month == 2) {
				if(isLeapYear == true) {
					month = 29;
					return month
				} else {
					month = 28;
					return month
				}
			} else {
				month = 31;
				return month
			}
		}
	}
	var dayLength = getMonthLength(year, month);
	var dayArr = [];
	for(var m = 1; m < dayLength + 1; m++) {
		dayArr.push(m)
	}
	var flag = false;
	for(var k = 0; k < 6; k++) {
		var li1 = $('<li class="tabel-line"></li>');
		var ul2 = $('<ul class="zry-tabel_ul"></ul>');
		for(var n = 0; n < 7; n++) {
			if(k == 0 && n < date1) {
				if(currDate < 7 && (preMonthLength - date1 + n + 1) == currDate) {
					if(n == 6) {
						ul2.append('<li class="zry-tabel_li preDays active weekColor">' + (preMonthLength - date1 + n + 1) + "</li>")
					} else {
						ul2.append('<li class="zry-tabel_li preDays active">' + (preMonthLength - date1 + n + 1) + "</li>")
					}
				} else {
					if(n == 6) {
						ul2.append('<li class="zry-tabel_li preDays weekColor">' + (preMonthLength - date1 + n + 1) + "</li>")
					} else {
						ul2.append('<li class="zry-tabel_li preDays">' + (preMonthLength - date1 + n + 1) + "</li>")
					}
				}
			} else {
				if(k == 0) {
					if(currDate < 7 && (dayArr[n - date1]) == currDate) {
						if(n == 6) {
							ul2.append('<li class="zry-tabel_li active weekColor">' + dayArr[n - date1] + "</li>")
						} else {
							ul2.append('<li class="zry-tabel_li active">' + dayArr[n - date1] + "</li>")
						}
					} else {
						if(n == 6) {
							ul2.append('<li class="zry-tabel_li weekColor">' + dayArr[n - date1] + "</li>")
						} else {
							ul2.append('<li class="zry-tabel_li">' + dayArr[n - date1] + "</li>")
						}
					}
				} else {
					if((k * 7 - date1 + n + 1) > dayArr.length) {
						break;
					} else {
						if(currDate >= 2 && (k * 7 - date1 + n + 1) == currDate) {
							if(n == 0 || n == 6) {
								ul2.append('<li class="zry-tabel_li active weekColor">' + (k * 7 - date1 + n + 1) + "</li>")
							} else {
								ul2.append('<li class="zry-tabel_li active">' + (k * 7 - date1 + n + 1) + "</li>")
							}
						} else {
							if(n == 0 || n == 6) {
								ul2.append('<li class="zry-tabel_li weekColor">' + (k * 7 - date1 + n + 1) + "</li>")
							} else {
								ul2.append('<li class="zry-tabel_li">' + (k * 7 - date1 + n + 1) + "</li>")
							}
						}
						if((k * 7 - date1 + n + 1) == dayArr.length) {
							flag = true;
							currN = n;
							currK = k
						}
					}
				}
			}
		}
		li1.append(ul2);
		$(".day-tabel").eq(0).append(li1);
		if(flag == true) {
			for(var q = 0; q < 6 - currN; q++) {
				$(".tabel-line").eq(currK).children().append('<li class="zry-tabel_li nextDay">' + (q + 1) + "</li>")
			}
			break;
		}
	}
}
$("#yearList,#monthList, .reback").on("click", function(e) {
	e.stopPropagation()
});
$("#yearList,#monthList").on("change", function(e) {
	ergodicDate($("#yearList").val().split("年")[0], $("#monthList").val().split("月")[0]);
	$("#laydateInput").val($("#yearList").val().split("年")[0] + " - " + $("#monthList").val().split("月")[0] + " - " + currDate);
	getSelectDate($("#yearList").val().split("年")[0] + " - " + $("#monthList").val().split("月")[0] + " - " + currDate)
});
$(".day-tabel").on("click", ".zry-tabel_li", function(e) {		//		日期选择
	e.stopPropagation();
	$(this).addClass("showClick").siblings().removeClass("showClick").parent().parent().siblings().find(".zry-tabel_li").removeClass("showClick");
	var parentIndex = $(this).parent().parent().index();
	var thisIndex = $(this).index();
//	console.log('parentIndex' , parentIndex)
//	console.log('thisIndex' , thisIndex)
//	console.log('$(this).html()' , $(this).html())
	
	try {
	    getDom($(this))
	    console.log($(this))
	}
	catch(err) {
		
	}
	if(parentIndex == 0 && $(this).html() > 7) {
		var selectDate;
		if(($("#monthList").val().split("月")[0] - 1) > 0) {
			selectDate = $("#yearList").val().split("年")[0] + " - " + ($("#monthList").val().split("月")[0] - 1) + " - " + $(this).html();
			ergodicDate($("#yearList").val().split("年")[0], ($("#monthList").val().split("月")[0] - 1));
			$("#yearList").val($("#yearList").val().split("年")[0] + "年");
			$("#monthList").val(($("#monthList").val().split("月")[0] - 1) + "月")
		} else {
			selectDate = ($("#yearList").val().split("年")[0] - 1) + " - " + 12 + " - " + $(this).html();
			ergodicDate(($("#yearList").val().split("年")[0] - 1), 12);
			$("#yearList").val(($("#yearList").val().split("年")[0] - 1) + "年");
			$("#monthList").val(12 + "月")
		}
	} else {
		if(parentIndex == currK && $(this).html() < 7) {
			if(parseInt($("#monthList").val().split("月")[0]) + 1 > 12) {
				selectDate = (parseInt($("#yearList").val().split("年")[0]) + 1) + " - " + 1 + " - " + $(this).html();
				ergodicDate($("#yearList").val().split("年")[0], ($("#monthList").val().split("月")[0] - 1));
				$("#yearList").val((parseInt($("#yearList").val().split("年")[0]) + 1) + "年");
				$("#monthList").val(1 + "月")
			} else {
				selectDate = ($("#yearList").val().split("年")[0]) + " - " + (parseInt($("#monthList").val().split("月")[0]) + 1) + " - " + $(this).html();
				ergodicDate(($("#yearList").val().split("年")[0]), (parseInt($("#monthList").val().split("月")[0]) + 1));
				$("#yearList").val(($("#yearList").val().split("年")[0]) + "年");
				$("#monthList").val((parseInt($("#monthList").val().split("月")[0]) + 1) + "月")
			}
		} else {
			selectDate = $("#yearList").val().split("年")[0] + " - " + $("#monthList").val().split("月")[0] + " - " + $(this).html()
		}
	}
	$("#laydateInput").val(selectDate);
//	if($(".select-date").css("display") == "none") {
//		$(".select-date").css("display", "block")
//	} else {
//		$(".select-date").css("display", "none")
//	}
	var getDate = $("#yearList").val().split("年")[0] + " - " + $("#monthList").val().split("月")[0] + " - " + $(this).html();
	getSelectDate(getDate)
})




/////////////////////

//$(window).ready(function(){
//	var dataIndex = 0;
//	var dom = null;
//	var toDay = null;
//	$(".select-date").css("display" , 'block')
//	intDom()
//	$.ajax({		//	日历
//		type:"POST",
//		url:"http://192.168.1.193:8083/bureau/line/update_query",
//		async:true,
//		data: {line_id: 1},
//		success: function(d){
//			console.log(d)
//			if(d.code == 200){
//				moneyData = JSON.parse(d.data.hwTravelLine.line_price)
////				moneyData = money
//				for(var i in moneyData){
//					lookupDate(i , moneyData)
//				}
//			}
//		},
//	});
//})

function getPrice(d , n , people){
	var year = setString($('#yearList').val())	//	年
	var month = setString($('#monthList').val())	//	月
	var day = d;	//	日
	var today = year + '-' + month + '-' + day
	
	line_price[today] = JSON.stringify({date:today , adultPrice:n[0] , childPrice:n[1]})
	
	
//	if(people == 'adult'){
//		line_price[today] = {date:today , adultPrice:n[0]}
//	}else if(people == 'child'){
//		line_price[today] = {date:today , childPrice:n}
//	}
	console.log(line_price)
	$("#line_price").val(JSON.stringify(line_price));
}

function setString(str){
	return str.substring(0 , str.length - 1)
}


function getToDay(day){
	return toDay = day
}

function intDom(){			//		初始化dom
	console.log('初始化dom')
	for(var i=0; i<$('.zry-tabel_li').length; i++){
		if($('.zry-tabel_li').eq(i).text() == toDay){
			return dom = $('.zry-tabel_li').eq(i)
		}
	}
}

function getDom(d){		//	获取dom
//	console.log(d)
	return dom = d
}

$('#moneyBtn1').click(function(){		//		添加成人价格
	var money = $('#money1').val()	//	成人价格
	var money1 = dom.children('.zry-childrenMoney').text()
	var obj = dom.clone();
	obj.find(':nth-child(n)').remove();
	if(dom.children('.zry-adultMoney').length == 0){
		dom.append('<p class=zry-adultMoney>'+money+'</p>')
	}else{
		dom.children('.zry-adultMoney').text(money)
	}
	setAdultMoney()
	getPrice(obj.html() , [money , money1] , 'adult')
})

function setAdultMoney(){	//	计算成人最大最小价格
	var arr = []
	for(var i=0; i<$('.zry-adultMoney').length; i++){
		arr.push($('.zry-adultMoney').eq(i).text())
	}
	sendAdultMoney(arr)
}

function sendAdultMoney(arr){	//	成人价格数据筛选
	var maxMoney = Math.max.apply(null , arr)
	var smallMoney = Math.min.apply(null , arr)
//	console.log(maxMoney , smallMoney)
//	$('#maxMoney').val(maxMoney)
//	$('#smallMoney').val(smallMoney)
	
	max_price = Math.max.apply(null , arr)
	min_price = Math.min.apply(null , arr)

}

$('#moneyBtn2').click(function(){		//		添加儿童价格
	var money = $('#money2').val()
	var money1 = dom.children('.zry-adultMoney').text()
	var obj = dom.clone();
	obj.find(':nth-child(n)').remove();
	if(dom.children('.zry-childrenMoney').length == 0){
		dom.append('<p class=zry-childrenMoney>'+money+'</p>')
	}else{
		dom.children('.zry-childrenMoney').text(money)
	}
	getPrice(obj.html() , [money1 , money] , 'child')
})

function getSelectDate(result){		//这里获取选择的日期
	console.log('result' , result);
	for(var i in moneyData){
		lookupDate(i , moneyData)
	}
}

$('#monitorInput').bind('input propertychange' , function(e){//		获取数据
	$.ajax({
		type:"POST",
		url:"/bureau/line/scenic_spot",
		async:true,
		data: {scenic: $("#monitorInput").val()},
		success: function(data){
			console.log(data)
			if(data.code == 200){
				getData = data.data
				$('.zry-box_ul').empty()
				for(var i=0; i<data.data.length; i++){
					$('.zry-box_ul').append('<li onclick="zry_getIndex(this)" class=getIndex data-index='+i+'>'+data.data[i].name+'</li>')
				}
				$('.zry-box_ul').css('display' , 'block')
			}
		},
	});
})

$(document).on('click' , '.zry-showDataBox div>i' , function(e){	//	删除
	var name = $(this).parent().attr('data-name')
	var nameId = $(this).parent().attr('data-id')
	$(this).parent().remove()
	//showNameOrId(name , nameId)
})

//$(document).on('click' , '.getIndex' , function(e){	//	点击li	
//	console.log('0000000000000000000')
//	var index = $(this).attr('data-index');
//	var name =getData[index].name
//	var nameID = getData[index].name_id
//	$('.zry-box_ul').css('display' , 'none')
//	for(var i=0; i<$('.zry-showDataBox>div').length; i++){	//	防止重复
//		if(nameID == $('.zry-showDataBox>div').eq(i).attr('data-id')){
//			return
//		}
//	}
//	console.log($('.zry-showDataBox'))
//	$('.zry-showDataBox').append('<div data-name='+name+' data-id='+nameID+'><div>'+name+'</div><i>X</i></div>')
//	console.log(name , nameID)
//	showNameOrId(name , nameID)
//})



//function showNameOrId(name , id){
//	console.log('name' , name)
//	console.log('nameID' , id)
//}

}
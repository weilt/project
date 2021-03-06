$(function() {
//	$(window).click(function() {
//		if($(".select-date").css("display") == "block") {
//			$(".select-date").css("display", "none")
//		}
//	});
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
//	console.log(currYear,currMonth,currDate)
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
			var ul2 = $('<ul class="tabel-ul"></ul>');
			for(var n = 0; n < 7; n++) {
				if(k == 0 && n < date1) {
					if(currDate < 7 && (preMonthLength - date1 + n + 1) == currDate) {
						if(n == 6) {
							ul2.append('<li class="tabel-li preDays active weekColor">' + (preMonthLength - date1 + n + 1) + "</li>")
						} else {
							ul2.append('<li class="tabel-li preDays active">' + (preMonthLength - date1 + n + 1) + "</li>")
						}
					} else {
						if(n == 6) {
							ul2.append('<li class="tabel-li preDays weekColor">' + (preMonthLength - date1 + n + 1) + "</li>")
						} else {
							ul2.append('<li class="tabel-li preDays">' + (preMonthLength - date1 + n + 1) + "</li>")
						}
					}
				} else {
					if(k == 0) {
						if(currDate < 7 && (dayArr[n - date1]) == currDate) {
							if(n == 6) {
								ul2.append('<li class="tabel-li active weekColor">' + dayArr[n - date1] + "</li>")
							} else {
								ul2.append('<li class="tabel-li active">' + dayArr[n - date1] + "</li>")
							}
						} else {
							if(n == 6) {
								ul2.append('<li class="tabel-li weekColor">' + dayArr[n - date1] + "</li>")
							} else {
								ul2.append('<li class="tabel-li">' + dayArr[n - date1] + "</li>")
							}
						}
					} else {
						if((k * 7 - date1 + n + 1) > dayArr.length) {
							break
						} else {
							if(currDate >= 2 && (k * 7 - date1 + n + 1) == currDate) {
								if(n == 0 || n == 6) {
									ul2.append('<li class="tabel-li active weekColor">' + (k * 7 - date1 + n + 1) + "</li>")
								} else {
									ul2.append('<li class="tabel-li active">' + (k * 7 - date1 + n + 1) + "</li>")
								}
							} else {
								if(n == 0 || n == 6) {
									ul2.append('<li class="tabel-li weekColor">' + (k * 7 - date1 + n + 1) + "</li>")
								} else {
									ul2.append('<li class="tabel-li">' + (k * 7 - date1 + n + 1) + "</li>")
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
					$(".tabel-line").eq(currK).children().append('<li class="tabel-li nextDay">' + (q + 1) + "</li>")
				}
				break
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
	$(".day-tabel").on("click", ".tabel-li", function(e) {		//		日期选择
		e.stopPropagation();
		$(this).addClass("showClick").siblings().removeClass("showClick").parent().parent().siblings().find(".tabel-li").removeClass("showClick");
		var parentIndex = $(this).parent().parent().index();
		var thisIndex = $(this).index();
//		console.log('parentIndex' , parentIndex)
//		console.log('thisIndex' , thisIndex)
//		console.log('$(this).html()' , $(this).html())
		
		try {
		    getDom($(this))
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
//		if($(".select-date").css("display") == "none") {
//			$(".select-date").css("display", "block")
//		} else {
//			$(".select-date").css("display", "none")
//		}
		var getDate = $("#yearList").val().split("年")[0] + " - " + $("#monthList").val().split("月")[0] + " - " + $(this).html();
		getSelectDate(getDate)
	})
});
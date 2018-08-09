/**
 * 假期设定
 */
var Holiday = ["2018-01-01", "2018-02-15", "2018-02-16", "2018-02-17", "2018-02-18","2018-02-19","2018-02-20","2018-02-21","2018-04-05","2018-04-06","2018-04-07","2018-04-29","2018-04-30", "2018-05-01", "2018-06-16", "2018-06-17", "2018-06-18", "2018-09-22", "2018-09-23", "2018-09-24",  "2018-10-01", "2018-10-02", "2018-10-03", "2018-10-04", "2018-10-05", "2018-10-06", "2018-10-07"];
/**
 * 调休日设定
 */
var WeekendsOff =  ["2018-02-11", "2018-02-24", "2018-04-08", "2018-04-28", "2018-09-29", "2018-09-30"];

/**
 *	计算当前时间（或指定时间），向前推算周数(weekCount)，得到结果周的第一天的时期值
 * @param mode 推算模式（'cn'表示国人习惯【周一至周日】；'en'表示国际习惯【周日至周一】）
 * @param weekCount 表示周数（0-表示本周， 1-前一周，2-前两周，以此推算）
 * @param end 指定时间的字符串（未指定则取当前时间）
 */
var nearlyWeeks = function (mode, weekCount, end) {
	if (mode === undefined){
		mode = "cn";
	}
	if (weekCount === undefined){
		weekCount = 0;
	}
	if (end !== undefined){
		end = new Date(new Date(end).toDateString());
	}else {
		end = new Date(new Date().toDateString());
	}

	var days = 0;
	if (mode === 'cn'){
		days = (end.getDay() === 0 ? 7 : end.getDay()) - 1;
	}else {
		days = end.getDay();
	}
	return new Date(end.getTime() - (days + weekCount * 7) * 24 * 60 * 60 * 1000);
};

/**
 * 计算一段时间内工作的天数。不包括周末和法定节假日，法定调休日为工作日，周末为周六、周日两天
 * @param mode 推算模式（'cn'表示国人习惯【周一至周日】；'en'表示国际习惯【周日至周一】）
 * @param beginDay 时间段开始日期
 * @param endDay 时间段结束日期
 */
 function getWorkDayCount (mode, beginDay, endDay) {
	var begin = new Date(beginDay.toDateString());
	var end = new Date(endDay.toDateString());

	//每天的毫秒总数，用于以下换算
	var daytime = 24 * 60 * 60 * 1000;

	//两个时间段相隔的总天数
	var days = (end - begin) / daytime + 1;

	//时间段起始时间所在周的第一天
	var beginWeekFirstDay = nearlyWeeks(mode, 0, beginDay.getTime()).getTime();

	//时间段结束时间所在周的最后天
	var endWeekOverDay = nearlyWeeks(mode, 0, endDay.getTime()).getTime() + 6 * daytime;

	//由beginWeekFirstDay和endWeekOverDay换算出，周末的天数
	var weekEndCount = ((endWeekOverDay - beginWeekFirstDay) / daytime + 1) / 7 * 2;

	//根据参数mode，调整周末天数的值
	if (mode === "cn") {
		if (endDay.getDay() > 0 && endDay.getDay() < 6){
			weekEndCount -= 2;
		}else if (endDay.getDay() === 6){
			weekEndCount -= 1;
		}

		if (beginDay.getDay() === 0){
			weekEndCount -= 1;
		}
	}else {
		if (endDay.getDay() < 6){
			weekEndCount -= 1;
		}
		if (beginDay.getDay() > 0){
			weekEndCount -= 1;
		}
	}

	//根据调休设置，调整周末天数（排除调休日）
	$.each(WeekendsOff, function (i, offitem) {
		var itemDay = new Date(offitem.split('-')[0] + "/" + offitem.split('-')[1] + "/" + offitem.split('-')[2]);
		//如果调休日在时间段区间内，且为周末时间（周六或周日），周末天数值-1
		if (itemDay.getTime() >= begin.getTime() && itemDay.getTime() <= end.getTime() && (itemDay.getDay() === 0 || itemDay.getDay() === 6)){
			weekEndCount -= 1;
		}
	});

	//根据法定假日设置，计算时间段内周末的天数（包含法定假日）
	$.each(Holiday, function (i, itemHoliday) {
		var itemDay = new Date(itemHoliday.split('-')[0] + "/" + itemHoliday.split('-')[1] + "/" + itemHoliday.split('-')[2]);
		//如果法定假日在时间段区间内，且为工作日时间（周一至周五），周末天数值+1
		if (itemDay.getTime() >= begin.getTime() && itemDay.getTime() <= end.getTime() && itemDay.getDay() > 0 && itemDay.getDay() < 6){
			weekEndCount += 1;
		}
	});

	//工作日 = 总天数 - 周末天数（包含法定假日并排除调休日）
	return days - weekEndCount;
}
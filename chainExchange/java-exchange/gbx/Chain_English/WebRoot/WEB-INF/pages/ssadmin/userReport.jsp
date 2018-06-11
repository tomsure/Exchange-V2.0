<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<script src="../../static/ssadmin/js/js/highcharts.js"></script>
<script src="../../static/ssadmin/js/js/modules/exporting.js"></script>
<script type="text/javascript">
	$(function() {
		$('#userReport').highcharts(
				{
					chart : {},
					title : {
						text : 'Member Registration Statistics; Total of the interval: ${total}'
					},
					xAxis : {
						categories : ${key}
					},
					tooltip : {
						formatter : function() {
							var s;
							if (this.point.name) { // the pie chart
								s = '' + this.point.name + ': ' + this.y
										+ ' fruits';
							} else {
								s = 'Date：' + this.x + '| Number：' + this.y;
							}
							return s;
						}
					},
					labels : {
						items : [ {
							html : '',
							style : {
								left : '40px',
								top : '8px',
								color : 'black'
							}
						} ]
					},
					series : [ {
						type : 'column',
						name : 'Date',
						data : ${value}
					} ]
				});
	});
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/userReport.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>Start Date<input type="text" name="startDate" class="date"
						readonly="true" value="${startDate }" /><font color="red">*</font>
						<input type="hidden" name="isSearch" value="1" />
					</td>
					<td>End Date<input type="text" name="endDate" class="date"
						readonly="true" value="${endDate }" /><font color="red">*</font>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">Search</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
</head>
<body>

	<div id="userReport"
		style="min-width: 310px; height: 400px; margin: 0 auto"></div>

</body>
</html>


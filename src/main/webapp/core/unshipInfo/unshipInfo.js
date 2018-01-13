var UnshipInfo = function() {
	return {
		/**
		 * 加载卸船任务列表
		 */
		list : function() {
//			alert('任务ID' + taskId);
			if (taskId == null || taskId == undefined) {
				return;
			}
			var mapping = {};
			var pageParam = {};
			pageParam.taskId = taskId;
			var url = BasePath + "/task/getUnshipInfoList";
			$.post(url, pageParam, function(result) {
				if (result && Cl.successInt == result.code) {
					
					// <tr>
					// <td>船舱编号</td>
					// <td>货物类型</td>
					// <td>开始位置</td>
					// <td>结束位置</td>
					// <td>装载量</td>
					// <td>操作</td>
					// </tr>
					
					// 清空列表数据
					$("#unshipInfo_tbody").children().empty();
					// 加载列表数据
					var obj = result.data;
					for (var i = 0; i < obj.length; i++) {
						var res = obj[i];
						
//						<td><a href="javascript:UnshipInfo.view_unship_click(56,2)">1</a></td>
//						<td><a href="javascript:UnshipInfo.view_click(56,2)">煤粉</a></td>
//						<td>100.0</td>
//						<td>0.0</td>
//						<td>100.0</td>
//						<td>0.0</td>
//						<td>卸货</td>
//						<td><a href="#" onclick="javascript:var con;con=confirm('请确认是否清舱！');if(con==true){alert('操作成功！');}">清舱</a></td>
						
						//0|卸货;1|清舱;2|完成
						var status = res.status==0?'卸货':(res.status==1?'清舱':(res.status == 2?'完成':''));
						var tr = ""
							+ "<tr>"
							+ "<td><a href='javascript:UnshipInfo.view_unship_click(\""+taskId+"\",\""+res.cabinNo+"\")'>"+res.cabinNo+"</a></td>"
							+ "<td><a href='javascript:UnshipInfo.view_click(\""+taskId+"\",\""+res.cabinNo+"\")'>"+res.cargoName+"</a></td>"
							+ "<td>"+res.total+"</td>"
							+ "<td>"+res.finished+"</td>"
							+ "<td>"+res.remainder+"</td>"
							+ "<td>"+res.clearance+"</td>"
							+ "<td>"+status+"</td>"
							+ "<td>";
							if (res.status == 0) {
								tr += "<a href='#' onclick='javascript:UnshipInfo.setCabinStatus_click(\""
										+ taskId
										+ "\",\""
										+ res.cabinNo
										+ "\",\"1\")'>清舱</a>";
							} else if (res.status == 1) {
								tr += "<a href='#' onclick='UnshipInfo.setCabinStatus_click(\""
										+ taskId
										+ "\",\""
										+ res.cabinNo
										+ "\",\"2\")'>完成</a>";
							}
							tr += "&nbsp;&nbsp;"
							+ "</td>" 
							+ "</tr>";

						$("#unshipInfo_tbody").append(tr);
					}
					
				} else {
					alert(result.msg);
				}
			});
		},
		/**
		 * 点击增加按钮
		 */
		add_click : function() {
			if (taskId == null || taskId == undefined) {
				alert("请先录入船舶信息！");
				return;
			}
			Cl.action = 'create';
			Cl.showModalWindow(Cl.modalName, BasePath + "/cabin/addform?taskId=" + taskId);
		},
		
		/**
		 * 点击修改按钮
		 */
		update_click : function(id) {
			Cl.action = 'update';
			Cl.showModalWindow(Cl.modalName, BasePath + "/cabin/updateform?id="
					+ id);
		},
		/**
		 * 点击查看信息
		 */
		view_click : function(taskId, cabinNo) {
			Cl.showModalWindow(Cl.modalName, BasePath + "/cargo/view?taskId="
					+ taskId + '&cabinNo=' + cabinNo);
		},
		/**
		 * 点击查看信息
		 */
		modifyCabinPosition_click : function(taskId) {
			window.location.href = BasePath + "/cabin/modifyCabinPosition?taskId="
					+ taskId;
		},
		/**
		 * 点击查看船舶信息
		 */
		view_ship_click : function(taskId) {
			window.location.href = BasePath + "/task/view?taskId="
					+ taskId;
		},
		/**
		 * 点击查看卸船信息
		 */
		view_unship_click : function(taskId, cabinNo) {
			window.location.href = BasePath + "/cabin/view?taskId="
					+ taskId + '&cabinNo=' + cabinNo;
		},
		/**
		 * 点击设置船舶状态
		 */
		setShipStatus_click : function(taskId, status) {
			var con = confirm(status == '0' ? '请确认是否开始卸船！' : (status == '1' ? '请确认是否完成卸船！' : ""));
			if (con == true) {
				var pageParam = {};
				pageParam.taskId = taskId;
				pageParam.status = status;
				var url = BasePath + "/task/doSetShipStatus";
				$.post(url, pageParam, function(result) {
					if (result && Cl.successInt == result.code) {
						alert(result.msg);
						window.location.href = window.location.href;
					} else {
						alert(result.msg);
					}
				});
			}
		},
		/**
		 * 点击设置船舱状态
		 */
		setCabinStatus_click : function(taskId, cabinNo, status) {
			var con = confirm(status == '1' ? '请确认是否清舱！！' : (status == '2' ? '请确认是否完成！！' : ""));
			if (con == true) {
				//（0|卸货;1|清舱;2|完成）
				var pageParam = {};
				pageParam.taskId = taskId;
				pageParam.status = status;
				pageParam.cabinNo = cabinNo;
				var url = BasePath + "/cabin/updateCabinStatus";
				$.post(url, pageParam, function(result) {
					if (result && Cl.successInt == result.code) {
						alert(result.msg);
						window.location.href = window.location.href;
					} else {
						alert(result.msg);
					}
				});
			}
		}
	};
}();

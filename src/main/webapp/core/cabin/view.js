var CabinView = function() {
	return {
		/**
		 * 加载列表
		 */
		list : function() {
//			alert('任务ID' + taskId);
			if (taskId == null || taskId == undefined) {
				return;
			}
			var mapping = {};
			var pageParam = {};
			pageParam.taskId = taskId;
			pageParam.cabinNo = cabinNo;
			var url = BasePath + "/cabin/doGetUnloaderDetail";
			$.post(url, pageParam, function(result) {
				if (result && Cl.successInt == result.code) {
					
//					<th>卸船机</th>
//					<th>开始时间</th>
//					<th>结束时间</th>
//					<th>用时</th>
//					<th>卸货量</th>
//					<th>效率</th>
					
					// 清空列表数据
					$("#cabin_tbody").children().empty();
					// 加载列表数据
					var obj = result.data;
					for (var i = 0; i < obj.length; i++) {
						var res = obj[i];
						
//						<td scope="row">#1</td>
//						<td>08:00</td>
//						<td>09:00</td>
//						<td>1.0</td>
//						<td>800</td>
//						<td>800</td>
						
						var tr = ""
							+ "<tr>"
							+ "<td>"+res.unloaderName+"</td>"
							+ "<td>"+res.startTime+"</td>"
							+ "<td>"+res.endTime+"</td>"
							+ "<td>"+res.usedTime+"</td>"
							+ "<td>"+res.unloading+"</td>"
							+ "<td>"+res.efficiency+"</td>"
							+ "</tr>";

						$("#cabin_tbody").append(tr);
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
		}
	};
}();

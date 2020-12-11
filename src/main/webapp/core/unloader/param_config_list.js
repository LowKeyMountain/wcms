var tables;
function refreshTable() {
	if (tables) {
		//tables.destroy();
		//tables.clear();
		tables = null;
		// 删除dom中的标签片段
		$("#table").html("")
		// 重新加入table标签
		$("#table")
				.append(
						"<table class='dataTable table table-striped table-bordered table-hover table-checkable order-column' id='datatable_cl'>"
							+ "<thead> <tr role='row' class='heading'> <th style='text-align:center' width='7.5%'>卸船机编号</th> <th  style='text-align:center' width='7.5%'>给料速度(%)</th> <th  style='text-align:center' width='7.5%'>料斗门开度(%)</th>"
							+ "<th  style='text-align:center' width='7.5%'>料斗载荷(吨)</th> <th  style='text-align:center' width='7.5%'>最后更新时间</th> <th  style='text-align:center' width='7.5%'>卸船机状态</th> </tr>"
							+ "</thead> <tbody> </tbody> </table> ");
		DataTableCl.init()
	}
}
var DataTableCl = function() {
	var handleUser = function() {
        var table = $('#datatable_cl');
        // begin first table
        tables = table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
            	"sProcessing":   "处理中...",
            	"sLengthMenu":   "显示 _MENU_ 项结果",
            	"sZeroRecords":  "没有匹配结果",
            	
            	"sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            	"sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
            	"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            	"sInfoPostFix":  "",
            	"sSearch":       "搜索:",
            	"sUrl":          "",
            	"sEmptyTable":     "表中数据为空",
            	"sLoadingRecords": "载入中...",
            	"sInfoThousands":  ",",
            	"oPaginate": {
            		"sFirst":    "首页",
            		"sPrevious": "上页",
            		"sNext":     "下页",
            		"sLast":     "末页"
            	},
            	
            	"oAria": {
            		"sSortAscending":  ": 以升序排列此列",
            		"sSortDescending": ": 以降序排列此列"
            	}
            },
            "destroy":true,
			"paging" : false,
			"bFilter" : false,
			"bLengthChange" : false,
			"ordering": false,//取消升序降序排列功能
		    "info":     false,//取消显示行数信息的功能Showing 1 to 10 of 57 entries
			"sAjaxSource" : BasePath + "/unloader/getParamListDatas?ver=" + new Date().getTime(), // ajax
			
            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},
			
            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

//            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

//            "lengthMenu": [
//                [6],
//                [6] // change per page values here
//            ],
//            // set the initial value
//            "pageLength": 6,            
//            "pagingType": "bootstrap_full_number",
//            "columnDefs" : [ { // set default column settings
//				"targets" : [ 0, -1 ],
//				"orderable" : false,
//				"searchable" : false
//			} ],
            "columns" : [ 
                          {"name":"unloaderId"}, 
                          {"name":"deliveryRate"}, 
                          {"name":"doumenOpeningDegree"}, 
                          {"name":"hopperLoad"}, 
                          {"name":"updateTime"}, 
                          {"name":"systemState"} 
                          
                      ],
            "order": [
                [1, "asc"]
            ] // set first column as a default sort by asc
        });
        var tableWrapper = jQuery('#datatable_cl_wrapper');

        table.find('.group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).prop("checked", true);
                    $(this).parents('tr').addClass("active");
                } else {
                    $(this).prop("checked", false);
                    $(this).parents('tr').removeClass("active");
                }
            });
            jQuery.uniform.update(set);
        });

        table.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");
        });
        
	}
	return {
		// main function to initiate the module
		init : function() {
	        if (!jQuery().dataTable) {
	            return;
	        }
			handleUser();
		},
		tableName : "datatable_cl"
	};
}();
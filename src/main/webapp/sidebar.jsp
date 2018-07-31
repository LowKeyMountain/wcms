<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>  
<!-- BEGIN SIDEBAR -->
 <div class="page-sidebar-wrapper">
     <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
     <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
     <div class="page-sidebar navbar-collapse collapse">
         <!-- BEGIN SIDEBAR MENU -->
         <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
         <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
         <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
         <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
         <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
         <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
         <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="padding-top: 20px">
             <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
             <!-- <li class="sidebar-toggler-wrapper hide"> -->
                 <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                 <!-- <div class="sidebar-toggler"> </div> -->
                 <!-- END SIDEBAR TOGGLER BUTTON -->
             <!-- </li> -->
             <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
             <li class="sidebar-search-wrapper">
                 <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
                 <!-- DOC: Apply "sidebar-search-bordered" class the below search form to have bordered search box -->
                 <!-- DOC: Apply "sidebar-search-bordered sidebar-search-solid" class the below search form to have bordered & solid search box -->
                 <form class="sidebar-search  " action="#" method="POST">
                     <a href="javascript:;" class="remove">
                         <i class="icon-close"></i>
                     </a>
                     <!-- 
                     <div class="input-group">
                         <input type="text" class="form-control" placeholder="Search${IncPath}.">
                         <span class="input-group-btn">
                             <a href="javascript:;" class="btn submit">
                                 <i class="icon-magnifier"></i>
                             </a>
                         </span>
                     </div>
                      -->
                 </form>
                 <!-- END RESPONSIVE QUICK SEARCH FORM -->
                </li>
                <li class="nav-item start active open">
                    <a href="${IncPath}/web/main" class="nav-link nav-toggle">
                        <i class="icon-home"></i>
                        <span class="title">主页</span>
                        
                    </a>
                </li>
                
                <li class="nav-item  ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-diamond"></i>
                        <span class="title">管理中心</span>
                        <span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item  ">
                            <a href="${IncPath}/task/tasklist" class="nav-link ">
                            	<i class="icon-bulb"></i>
                                <span class="title">工作管理</span>
                            </a>
                        </li>
<%--                          <li class="nav-item start ">
                            <a href="${IncPath}/task/statistics" class="nav-link ">
                                <i class="icon-bar-chart"></i>
                                <span class="title">实时查询</span>
                            </a>
                        </li> --%>
                        
                        <security:authorize buttonUrl="18">
                         <li class="nav-item start ">
                            <a href="${IncPath}/report/reportview" class="nav-link ">
                                <i class="icon-bar-chart"></i>
                                <span class="title">报表统计</span>
                            </a>
                        </li>
                       </security:authorize>
                       
						<li class="nav-item start ">
                            <a href="${IncPath}/unloader/list" class="nav-link ">
                                <i class="icon-magnifier"></i>
                                <span class="title">卸船机实时数据</span>
                            </a>
                        </li>
                        <security:authorize buttonUrl="14">
 						<li class="nav-item start ">
                            <a href="${IncPath}/task/maintenance" class="nav-link ">
                                <i class="icon-settings"></i>
                                <span class="title">作业船舶维护</span>
                            </a>
                        </li>
                        </security:authorize>                          
                        <!-- 
                        <li class="nav-item start active open">
                            <a href="#" class="nav-link ">
                                <i class="icon-bar-chart"></i>
                                <span class="title">控制面板</span>
                                <span class="selected"></span>
                            </a>
                        </li>
                         -->
                    </ul>
                </li>
                <security:authorize buttonUrl="19">
                <li class="nav-item  ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-diamond"></i>
                        <span class="title">配置中心</span>
                        <span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item  ">
                            <a href="${IncPath}/user/list" class="nav-link ">
                         	<i class="leaf"></i>
                             <span class="title">用户管理</span>
                         </a>
                     </li>
                     <li class="nav-item  ">
                         <a href="${IncPath}/role/list" class="nav-link ">
                             <span class="title">角色管理</span>
                         </a>
                     </li>
                     <security:authorize buttonUrl="20">
                     <!-- 
                     <li class="nav-item  ">
                         <a href="${IncPath}/resource/list" class="nav-link ">
                             <span class="title">资源管理</span>
                         </a>
                     </li>
                      -->
                     </security:authorize>
                     <li class="nav-item  ">
                         <a href="${IncPath}/logs/list" class="nav-link ">
                             <span class="title">日志管理</span>
                         </a>
                     </li>
                 </ul>
             </li>
             </security:authorize>
         </ul>
         <!-- END SIDEBAR MENU -->
     </div>
 </div>
 <!-- END SIDEBAR -->
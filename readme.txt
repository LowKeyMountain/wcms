六、Jquery插件

jsTree :	http://www.jstree.com/

jquery.validate :	http://bassistance.de/jquery-plugins/jquery-plugin-validation http://docs.jquery.com/Plugins/Validation

DataTables	:	http://datatables.net/

Bootstram Modals	:	http://www.w3cschool.cc/bootstrap/bootstrap-v2-modal-plugin.html

注意，在这个插件的使用过程中，用了Ajax，是不能跨域的，即使从localhost，调用127.0.0.1的页面也是不行的。

colorbox	:	http://www.jacklmoore.com/colorbox/，用于弹出窗体，本系统使用的是MetroNic模板本身提供的模式(Bootstrap Modals)对话框，colorbox也是一种选择，这两种弹窗插件都比较好。

jquery-multi-select	：	http://loudev.com/

七、业务逻辑

对于模块，维护极少，不提供管理界面，手工操作数据库；

当前对于权限，仅控制到菜单级别，对于大多数系统来说，是适合的，如果需要更细致的权限级别，比如菜单里面的：CRUD，可以开发功能管理，实现步骤如下：

a.当需要一个控制时，管理员根据名称、意义，定制一个权限号，根据业务要求分配给某些角色

b.把权限号告知使用者，使用者根据此权限号，在程序中增加控制

八、其他

在datatable.js中，使用bootstrap_full_number分页方式，页码导航条宽度变得太高的问题，解决办法：bootstrap.min.css中，对于.pagination .li 去掉float:left之后，就好了。

在datatable.js中，对fnServerData段进行调整，用于向服务器端传递分页、查询等参数，同时也调整显示的提示文本内容。

jqueyr.validate.js和jquery.validte.min.js中，调整提示文本显示。。
<#include "../../include/taglib.ftl"/>
<!DOCTYPE html>
<html>
<head>
	<#include "../../include/head.ftl"/>
	<#include "../../include/treetable.ftl"/>
	<title>菜单管理</title>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
		});
    	function updateSort() {
			layer.load();
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox">
		<div class="ibox-title">
			<a href="${ctx}/sys/menu"><h5>菜单管理</h5></a>
			<#--<shiro:hasPermission name="sys:menu:edit">-->
				<div class="ibox-tools">
					<a href="${ctx}/sys/menu/form" class="btn btn-primary btn-xs">菜单添加</a>
				</div>
			<#--</shiro:hasPermission>-->

		</div>
		<div class="ibox-content">
			<#--<sys:message content="${message}"/>-->

			<form id="listForm" method="post">
				<table id="treeTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="text-center">名称</th>
						<th class="text-center">链接</th>
						<th class="text-center">排序</th>
						<th class="text-center">可见</th>
						<th class="text-center">权限标识</th>
						<#--<shiro:hasPermission name="sys:menu:edit">-->
							<th class="text-center">操作</th>
						<#--</shiro:hasPermission>-->
					</tr>
					</thead>
					<tbody>
					<#list list as menu>
					<tr id="${menu.id}"
						<#if menu.parent.id !="1">
							pId="${menu.parent.id}"
						<#else>
							pId="0"
						</#if>
						>
						<td nowrap>
							<i class="${menu.icon!' hide'}"></i>
							<a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a>
						</td>
						<td title="${menu.href}">${fns.abbr(menu.href,30)}</td>
						<td class="text-center">
							<#--<shiro:hasPermission name="sys:menu:edit">-->
								<input type="hidden" name="ids" value="${menu.id}"/>
								<input name="sorts" type="text" value="${menu.seq}" class="form-control input-sm" style="height:25px;width:50px;margin:0;padding:0;text-align:center;">
							<#--</shiro:hasPermission>-->
							<#--<shiro:lacksPermission name="sys:menu:edit">-->
							<#--${menu.seq}-->
							<#--</shiro:lacksPermission>-->
						</td>
						<td class="text-center">
							<#if menu.isShow == "1">
								<span class="label label-primary">显示</span>
							<#else>
								<span class="label">隐藏</span>
							</#if>
						</td>
						<td title="${menu.permission}">
							${fns.abbr(menu.permission,30)}
						</td>
						<#--<shiro:hasPermission name="sys:menu:edit">-->
							<td nowrap>
								<a href="${ctx}/sys/menu/form?id=${menu.id}"><span class="label label-primary">修改</span></a>
								<a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)"><span class="label label-danger">删除</span></a>
								<a href="${ctx}/sys/menu/form?parent.id=${menu.id}"><span class="label label-success">添加下级菜单</span></a>
							</td>
						<#--</shiro:hasPermission>-->
					</tr>
					</#list>
					</tbody>
				</table>
				<#--<shiro:hasPermission name="sys:menu:edit">-->
					<div class="form-actions pagination-left">
						<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
					</div>
				<#--</shiro:hasPermission>-->
			</form>

		</div>
	</div>
</div>

</body>



</html>
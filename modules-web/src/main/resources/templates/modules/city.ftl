<#include "../include/taglib.ftl">
<!DOCTYPE>
<html xmlns:form="http://www.w3.org/1999/html">
<head>
	<title>asdasd</title>
	<#include "../include/head.ftl">
</head>
<body>


	<table>
		<thead>
		<tr>
			<th>序号</th><th>id</th><th>name</th>
		</tr>
		</thead>
		<tbody>
		<#list list as c>
		    <tr>
			    <td>
				    ${c_index+1}
			    </td>
			    <td>
				    ${c.id}
			    </td>
			    <td>
				    ${c.name}
			    </td>
		    </tr>
		</#list>
		</tbody>
	</table>
</body>
</html>
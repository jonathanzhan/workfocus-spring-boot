<#include "../../include/taglib.ftl"/>
<!DOCTYPE html>
<html>
<head>
    <#include "../../include/head.ftl"/>
    <link href="${ctxStatic}/css/login.css" rel="stylesheet">
    <title> 登录</title>
</head>
<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-7">
            <div class="signin-info">
                <div class="logopanel m-b">
                    <h1 class="font-bold">workfocus</h1>
                </div>
                <div class="m-b"></div>
                <h4>欢迎使用 <strong>workfocus 后台主题UI框架</strong></h4>
                <ul class="m-b">
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势一</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势二</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势三</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势四</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势五</li>
                </ul>
                <strong>还没有账号？ <a href="${ctx}/logout">退出&raquo;</a></strong>

            <#--<#list test2() as user>-->
            <#--${user.name}-->
            <#--</#list>-->


                <#--${test3().user2.name}-->


	            <#--test3-->
                <#--<@testTag>-->
                    <#--${name}-->
                <#--</@testTag>-->
                <#--${name}-->

                <#--<@sys.test/>-->
                <#--${name}-->


                <#--${name}-->
            </div>
        </div>

    </div>
    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2016 All Rights Reserved. <a href="whatlookingfor.com">Jonathan</a>
        </div>
    </div>
</div>
</body>
</html>

<#include "../../include/taglib.ftl">
<!DOCTYPE html>
<html>
<head>
<#include "../../include/head.ftl">
    <link href="${ctxStatic}/css/login.css" rel="stylesheet">
    <title> 登录</title>
    <meta name="keywords" content="workfocus后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="keywords" content="workfocus是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
    <link rel="shortcut icon" href="${ctxStatic}/img/logo.png">
    <script>
        $(function(){
            $("#username").focus();
        })

        if(window.top!==window.self){
            alert('未登录或登录超时。请重新登录，谢谢！');
            window.top.location=window.location
        };
    </script>
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
	                <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> ${fns.config('adminPath')}</li>
	                <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> ${fns.abbr('adminPath2321321312',5)}</li>

                    <#list fns.dictList('sys_user_type') as demo>
	                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>${demo.label}</li>
                    </#list>
                </ul>
                <strong>还没有账号？ <a href="#">立即注册&raquo;</a></strong>
            </div>
        </div>
        <div class="col-sm-5">
            <form method="post" action="${ctx}/login">
                <h4 class="no-margins">登录</h4>
                <p class="m-t-md">登录到后台主题UI框架</p>
                <input type="text" id="username" name="username" class="form-control uname" placeholder="用户名" required />
                <input type="password" id="password" name="password" class="form-control pword m-b" placeholder="密码" required />
                <a href="#">忘记密码了</a>

                <button class="btn btn-success btn-block" type="submit">登录</button>
            </form>
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

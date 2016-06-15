<#include "../../include/taglib.ftl">
<!DOCTYPE html>
<html>
<head>

    <#include "../../include/head.ftl"/>
    <script src="${ctxStatic}/js/index.js"></script>
    <script src="${ctxStatic}/js/contabs.js"></script>
    <title>${fns.config('productName')} - 主页</title>

    <script type="text/javascript">


        $(function(){

            showToastr('Welcome!','欢迎登陆系统!',{
                "timeOut":"5000"
            });

            $(".first-menu").click(function(){
                // 显示二级菜单
                var menuId = ".secondMenu-" + $(this).attr("data-id");
                $(".secondMenu").hide();
                $(menuId).show();

//                var secondId = "#"+$(menuId+":first").attr("id")+" a:first";
//                if($(secondId).attr("href")=="#"){
//                    $(secondId).click();
//                    $("#"+$(menuId+":first").attr("id")+" li:first a").click();
//                }else{
//                    $(secondId).click();
//                }

            });

            //初始化点击第一个一级菜单
            $(".first-menu:first").click();

        })

    </script>
</head>

<body class="fixed-sidebar full-height-layout gray-bg animated flip" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <div class="social-feed-separated">
                            <div class="social-avatar">
                                <img alt="image" class="img-circle" src="${ctxStatic}/img/a1.jpg"/>
                            </div>
                        </div>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear">
                                <span class="block m-t-xs"><strong class="font-bold">Beaut-zihan</strong></span>
                                <span class="text-muted text-xs block">超级管理员<b class="caret"></b></span>
                            </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="J_menuItem" href="form_avatar.html">修改头像</a></li>
                            <li><a class="J_menuItem" href="profile.html">个人资料</a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx}/logout">安全退出</a></li>
                        </ul>
                    </div>
                </li>

                <#list fns.getMenuTree(2,3) as menu>
                    <#if menu.isShow == "1">
	                    <li class="hidden-xs secondMenu secondMenu-${menu.parent.id}" id="secondMenu-${menu.id}">
		                    <#if menu.href??>
			                    <a href="#">
				                    <i class="${menu.icon}"></i>
				                    <span>${menu.name}</span>
				                    <span class="fa arrow"></span>
			                    </a>
                            <#else >
                                <#if menu.href?index_of('://')!=-1>
                                    <a class="J_menuItem" href="${ctx}${menu.href}">
	                                    <i class="${menu.icon}"></i>
	                                    <span>${menu.name}</span>
                                    </a>
                                <#else>
                                    <a class="J_menuItem" href="${menu.href}">
	                                    <i class="${menu.icon}"></i>
	                                    <span>${menu.name}</span>
                                    </a>
                                </#if>
		                    </#if>
                            <#if menu.childList?exists && (menu.childList?size>0)>
	                            <ul class="nav nav-second-level">
                                    <#list menu.childList as child>
                                        <#if child.isShow=='1'>
	                                        <li>
                                                <#if child.href?index_of('://')!=-1>
			                                        <a class="J_menuItem" href="${ctx}${child.href}">
				                                        <i class="${child.icon}"></i>
				                                        <span>${child.name}</span>
			                                        </a>
                                                <#else>
			                                        <a class="J_menuItem" href="${child.href}">
				                                        <i class="${child.icon}"></i>
				                                        <span>${child.name}</span>
			                                        </a>
                                                </#if>
	                                        </li>
                                        </#if>
                                    </#list>
	                            </ul>
                            </#if>
	                    </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->


    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom white-bg">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                        <i class="fa fa-bars"></i>
                    </a>
                    <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group">
                            <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search"
                                   id="top-search">
                        </div>
                    </form>
                </div>


                <div class="navbar-collapse collapse top-navigation" id="navbar">
                    <ul class="nav navbar-nav">
                        <#list fns.getMenuListByLevel(1,1) as menu>
                            <#if menu.parent.id == "1" && menu.isShow == "1">
	                            <li class="hidden-xs">
                                    <#if menu.href??>
	                                    <a class="first-menu" href="#" data-id="${menu.id}">
                                            <span><i class="${menu.icon}"></i>${menu.name}</span>
                                        </a>
                                    <#else>
                                        <#if menu.href?index_of('://')!=-1>
		                                    <a class="first-menu1 J_menuItem" data-id="${menu.id}" href="${ctx}${menu.href}">
			                                    <i class="${menu.icon}"></i><span>${menu.name}</span>
		                                    </a>
                                        <#else>
		                                    <a class="first-menu1 J_menuItem" data-id="${menu.id}" href="${menu.href}">
			                                    <i class="${menu.icon}"></i><span>${menu.name}</span>
		                                    </a>
                                        </#if>
                                    </#if>
	                            </li>
                            </#if>

                        </#list>

                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i>
                                <span class="label label-warning">16</span>
                            </a>
                            <ul class="dropdown-menu dropdown-messages">
                                <li class="m-t-xs">
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="${ctxStatic}/img/a1.jpg">
                                        </a>

                                        <div class="media-body">
                                            <small class="pull-right">46小时前</small>
                                            <strong>小四</strong>这个在日本投降书上签字的军官，建国后一定是个不小的干部吧？
                                            <br>
                                            <small class="text-muted">3天前 2014.11.8</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="${ctxStatic}/img/a1.jpg">
                                        </a>

                                        <div class="media-body ">
                                            <small class="pull-right text-navy">25小时前</small>
                                            <strong>国民岳父</strong> 如何看待“男子不满自己爱犬被称为狗，刺伤路人”？——这人比犬还凶
                                            <br>
                                            <small class="text-muted">昨天</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="mailbox.html">
                                            <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>
                                    <a href="mailbox.html">
                                        <div>
                                            <i class="fa fa-envelope fa-fw"></i> 您有16条未读消息
                                            <span class="pull-right text-muted small">4分钟前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="profile.html">
                                        <div>
                                            <i class="fa fa-qq fa-fw"></i> 3条新回复
                                            <span class="pull-right text-muted small">12分钟钱</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="notifications.html">
                                            <strong>查看所有 </strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                            </a>
                        </li>
                    </ul>

                </div>
            </nav>
        </div>
        <!--顶部菜单结束-->


        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i></button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">
                    关闭操作<span class="caret"></span>
                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                </ul>
            </div>
            <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i>退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/sys/menu" frameborder="0"
                    data-id="index_v1.html" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; ${fns.config('copyRightYear')} &nbsp;&nbsp;&nbsp;<a href="${fns.config('whatlookingfor.blog')}" target="_blank">${fns.config('whatlookingfor.author')}'s blog</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar" style=";">
        <div class="sidebar-container">

            <ul class="nav nav-tabs navs-3">

                <li class="active">
                    <a data-toggle="tab" href="#tab-1">
                        <i class="fa fa-gear"></i> 主题
                    </a>
                </li>
                <li class="">
                    <a data-toggle="tab" href="#tab-2">
                        通知
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#tab-3">
                        项目进度
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="sidebar-title">
                        <h3><i class="fa fa-comments-o"></i> 主题设置</h3>
                        <small><i class="fa fa-tim"></i>你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                    </div>
                    <div class="skin-setttings">
                        <div class="title">主题设置</div>
                        <div class="setings-item">
                            <span>收起左侧菜单</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu"/>
                                    <label class="onoffswitch-label" for="collapsemenu">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定顶部</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar"/>
                                    <label class="onoffswitch-label" for="fixednavbar">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定宽度</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox"  id="boxedlayout"/>
                                    <label class="onoffswitch-label" for="boxedlayout">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
                            <span class="skin-name ">
                                <a href="#" class="s-skin-0">默认皮肤</a>
                            </span>
                        </div>
                        <div class="setings-item blue-skin nb">
                            <span class="skin-name ">
                                <a href="#" class="s-skin-1">蓝色主题</a>
                            </span>
                        </div>
                        <div class="setings-item yellow-skin nb">
                            <span class="skin-name ">
                                <a href="#" class="s-skin-3">黄色/紫色主题</a>
                            </span>
                        </div>
                    </div>
                </div>
                <div id="tab-2" class="tab-pane">
                    <div class="sidebar-title">
                        <h3><i class="fa fa-comments-o"></i> 最新通知</h3>
                        <small><i class="fa fa-tim"></i> 您当前有10条未读信息</small>
                    </div>
                    <div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="${ctxStatic}/img/a1.jpg">

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    据天津日报报道：瑞海公司董事长于学伟，副董事长董社轩等10人在13日上午已被控制。
                                    <br>
                                    <small class="text-muted">今天 4:21</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="${ctxStatic}/img/a1.jpg">
                                </div>
                                <div class="media-body">
                                    HCY48之音乐大魔王会员专属皮肤已上线，快来一键换装拥有他，宣告你对华晨宇的爱吧！
                                    <br>
                                    <small class="text-muted">昨天 2:45</small>
                                </div>
                            </a>
                        </div>
                    </div>

                </div>
                <div id="tab-3" class="tab-pane">
                    <div class="sidebar-title">
                        <h3><i class="fa fa-cube"></i> 最新任务</h3>
                        <small><i class="fa fa-tim"></i> 您当前有14个任务，10个已完成</small>
                    </div>

                    <ul class="sidebar-list">
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>市场调研</h4> 按要求接收教材；

                                <div class="small">已完成： 22%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                                </div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>立项阶段</h4> 东风商用车公司 采购综合综合查询分析系统项目进度阶段性报告武汉斯迪克科技有限公司

                                <div class="small">已完成： 14%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 14%;" class="progress-bar progress-bar-info"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-primary pull-right">NEW</span>
                                <h4>设计阶段</h4>
                                <!--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                项目进度报告(Project Progress Report)
                                <div class="small">已完成： 22%</div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
    <!--右侧边栏结束-->
    <!--mini聊天窗口开始-->
    <div class="small-chat-box fadeInRight animated">

        <div class="heading" draggable="true">
            <small class="chat-date pull-right">
                2015.9.1
            </small>
            与 Beau-zihan 聊天中
        </div>

        <div class="content">

            <div class="left">
                <div class="author-name">
                    Beau-zihan
                    <small class="chat-date">
                        10:02
                    </small>
                </div>
                <div class="chat-message active">
                    你好,meizi
                </div>

            </div>
            <div class="right">
                <div class="author-name">
                    游客
                    <small class="chat-date">
                        11:24
                    </small>
                </div>
                <div class="chat-message">
                    你好，请问H+有帮助文档吗？
                </div>
            </div>
            <div class="left">
                <div class="author-name">
                    Beau-zihan
                    <small class="chat-date">
                        08:45
                    </small>
                </div>
                <div class="chat-message active">
                    有，购买的H+源码包中有帮助文档，位于docs文件夹下
                </div>
            </div>
            <div class="left">
                <div class="author-name">
                    Beau-zihan
                    <small class="chat-date">
                        08:45
                    </small>
                </div>
            </div>
        </div>
        <div class="form-chat">
            <div class="input-group input-group-sm">
                <input type="text" class="form-control"> <span class="input-group-btn"> <button
                    class="btn btn-primary" type="button">发送
            </button> </span>
            </div>
        </div>
    </div>
    <div id="small-chat">
        <span class="badge badge-warning pull-right">5</span>
        <a class="open-small-chat">
            <i class="fa fa-comments"></i>
        </a>
    </div>


</div>
<%--主题设置--%>
<div class="theme-config">
    <div class="spin-icon">
        <i class="fa fa-cog fa-spin"></i>
    </div>
</div>

</body>

</html>


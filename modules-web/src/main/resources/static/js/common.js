//自定义js

//公共配置
layer.config({
    extend: ['extend/layer.ext.js', 'skin/moon/style.css'],
    skin: 'layer-ext-moon'
});

// 确认对话框
function confirmx(mess, href){
    top.layer.confirm(mess, {icon: 3, title:'系统提示'}, function(index){
        if (typeof href == 'function') {
            href();
        }else{
            location = href;
        }
        top.layer.close(index);
    });
    return false;
}



$(document).ready(function () {

    // Small
    $('.check-link').click(function () {
        var button = $(this).find('i');
        var label = $(this).next('span');
        button.toggleClass('fa-check-square').toggleClass('fa-square-o');
        label.toggleClass('todo-completed');
        return false;
    });

    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_flat-green',
        radioClass: 'iradio_flat-green'
    });

    //ios浏览器兼容性处理
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        $('#content-main').css('overflow-y', 'auto');
    }

});


function getActiveTab(){
    return top.$(".J_iframe:visible");
}

//判断浏览器是否支持html5本地存储
function localStorageSupport() {
    return (('localStorage' in window) && window['localStorage'] !== null)
}

//打开对话框(添加修改)
function openDialog(title,url,width,height,target){
    top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        id:"orgAdd",
        maxmin: true, //开启最大化最小化按钮
        content: url ,
        btn: ['确定', '关闭'],
        yes: function(index, layero){
            var body = top.layer.getChildFrame('body', index);
            var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            var inputForm = body.find('#inputForm');
            var top_iframe;
            if(target){
                top_iframe = target;//如果指定了iframe，则在改frame中跳转
            }else{
                top_iframe = getActiveTab().attr("name");//获取当前active的tab的iframe
            }
            inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

            if(iframeWin.contentWindow.doSubmit() ){
                top.layer.close(index);//关闭对话框。
            }

        },
        cancel: function(index){
        }
    });

}

//打开对话框(查看)
function openDialogView(title,url,width,height){
    top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        maxmin: true, //开启最大化最小化按钮
        content: url ,
        btn: ['关闭'],
        cancel: function(index){
        }
    });

}
//分页
function page(n, s) {
    $("#pageNo").val(n);
    $("#pageSize").val(s);
    $("#searchForm").submit();
    return false;
}

//查询，页码清零
function searchForm(){
    $("#pageNo").val(0);
    $("#searchForm").submit();
    return false;
}

//重置
function resetFrom(){//重置，页码清零
    $("#pageNo").val(0);
    $("#searchForm div.form-group input").val("");
    $("#searchForm div.form-group select").val("");
    $("#searchForm").submit();
    return false;
}

//刷新或者排序，页码不清零
function sortOrRefresh(){
    $("#searchForm").submit();
    return false;
}

/**
 * 显示通知内容
 * @param title 标题
 * @param msg 内容
 * @param option 选项
 */
function showToastr(title,msg,option){
    if(option==null){
        option = {
            "onClick":null,
            "position":"toast-top-right",
            "timeOut":"5000",
            "type":"success"
        }
    }
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": true,
        "positionClass": option.position,
        "onclick": option.onClick,
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": option.timeOut,
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    if(option.type=='success'){
        toastr.success(msg, title);
    }else if(option.type=='info'){
        toastr.info(msg, title);
    }else if(option.type=='error'){
        toastr.error(msg, title);
    }else if(option.type=='warning'){
        toastr.warning(msg, title);
    }else if(option.type=='danger'){
        toastr.error(msg, title);
    }else{
        toastr.success(msg, title);
    }
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){
    if(!maxLength){
        maxLength = 20;
    }
    if(name==null||name.length<1){
        return "";
    }
    var w = 0;//字符串长度，一个汉字长度为2
    var s = 0;//汉字个数
    var p = false;//判断字符串当前循环的前一个字符是否为汉字
    var b = false;//判断字符串当前循环的字符是否为汉字
    var nameSub;
    for (var i=0; i<name.length; i++) {
        if(i>1 && b==false){
            p = false;
        }
        if(i>1 && b==true){
            p = true;
        }
        var c = name.charCodeAt(i);
        //单字节加1
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
            w++;
            b = false;
        }else {
            w+=2;
            s++;
            b = true;
        }
        if(w>maxLength && i<=name.length-1){
            if(b==true && p==true){
                nameSub = name.substring(0,i-2)+"...";
            }
            if(b==false && p==false){
                nameSub = name.substring(0,i-3)+"...";
            }
            if(b==true && p==false){
                nameSub = name.substring(0,i-2)+"...";
            }
            if(p==true){
                nameSub = name.substring(0,i-2)+"...";
            }
            break;
        }
    }
    if(w<=maxLength){
        return name;
    }
    return nameSub;
}

// 引入js和css文件
function include(id, path, file){
    if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
    }
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
        url = window.location.search;
    }else{
        url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
    for (var i=0; i<data.length; i++){
        var row = data[i];
        if (row.value == value){
            return row.label;
        }
    }
    return defaultValue;
}

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <c:set var="request" value="${pageContext.request.contextPath}"/>
    <title>layuiAdmin 文章管理 iframe 框</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/admin.css" media="all">

</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-app-form-list" id="layuiadmin-app-form-list"
     style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="hidden" name="depid" id="depid" value="" class="layui-input" lay-verify="name">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门名称</label>
        <div class="layui-input-inline">
            <input type="text" name="depname" id="depName" autocomplete="off" value="" class="layui-input" lay-verify="name">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门领导</label>
        <div class="layui-input-inline">
            <input type="text" name="depleader" id="depLeader" autocomplete="off" value="" class="layui-input" lay-verify="name">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="layuiadmin-app-form-submit" id="layuiadmin-app-form-submit"
               value="确认添加">
        <input type="button" lay-submit lay-filter="layuiadmin-app-form-edit" id="layuiadmin-app-form-edit"
               value="确认编辑">
    </div>
</div>

<script src="${request}/static/plugins/layuiadmin/layui/layui.js"></script>
<script src="${request}/static/custom/js/myLayVerify.js"></script>
<script src="${request}/static/custom/js/myValidity.js"></script>
<script>
    layui.config({
        base: '${request}/static/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form','laydate'], function () {
        var $ = layui.$
            , form = layui.form
            , laydate = layui.laydate;

        laydate.render({
            elem: '#birthday' //指定元素
            ,value: '2000-1-1'
            ,isInitValue: false //是否允许填充初始值，默认为 true'
            ,min: '1920-1-1'
            ,max: '2018-12-31'
        });

        $("#depName").blur(function () {
            var depName = $("#depName").val();
            console.log(depName);
            $.ajax({
                type:"get",
                url:"${request}/department/checkDepName.html",
                data:{depname:depName},
                success:function (res) {
                    if(res.data == "depExist"){
                        layer.msg("该部门已存在!");
                    }
                }
            })
        });

    })
</script>
</body>
</html>

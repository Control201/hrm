<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <c:set var="request" value="${pageContext.request.contextPath}"/>
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <input type="text" name="stuNum" id="searchInfo" placeholder="Search for~" autocomplete="off" class="layui-input">
                    </div>
                    <button class="layui-btn layadmin-btn-list" id="query" data-type="query">查询</button>
                    <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #FFB800"
                            id="query-all-info">查询所有信息
                    </button>
                </div>

            </div>
        </div>
        <div class="layui-card-body">
            <table id="depInfoQuery" lay-filter="LAY-app-content-comm"></table>
            <script type="text/html" id="table-content-list1">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                        class="layui-icon layui-icon-edit"></i>编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                        class="layui-icon layui-icon-delete"></i>删除</a>
            </script>
        </div>
    </div>
</div>

<script src="${request}/static/plugins/layuiadmin/layui/layui.js" charset="UTF-8"></script>
<script>
    layui.config({
        base: '${request}/static/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'contlist', 'table', 'laypage'], function () {
        var $ = layui.$
            , admin = layui.admin
            , form = layui.form
            , table = layui.table
            , laypage = layui.laypage;

        //方法级渲染
        table.render({
            elem: '#depInfoQuery'
            , url: '${request}/department/queryAllDep.html' //向后端默认传page和limit
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'depid', title: '部门id', sort: true}
                , {field: 'depname', title: '部门名称', sort: true}
                , {field: 'depleader', title: '部门领导人', sort: true}
            ]]
            , page: true
            , limit: 10
            , limits: [5, 10, 15, 20]
            , id: 'depInfoTable'
            , request: {
                pageName: 'pageNum',
                limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
            }
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                // console.log(res);
                //
                // 得到当前页码
                // console.log(curr);
                //
                // 得到数据总量
                // console.log(count);
            }

        });


        $("#query-all-info").click(function () {
            table.reload('depInfoTable', {
                url: '${request}/department/queryAllDep.html'
                , request: {
                    pageName: 'pageNum',
                    limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                }
                , where: { //设定异步数据接口的额外参数，任意设
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        });

        //监听搜索
        $("#query").click(function () {
            var depInfo = $("#searchInfo").val();
            if(depInfo==""||depInfo==null){
                layer.msg('请输入查询内容~', {
                    offset: '15px'
                    , icon: 2
                    , time: 1000
                });
            }else {
                //执行重载
                table.reload('depInfoTable', {
                    url: '${request}/department/queryDepInfo.html' //向后端默认传page和limit
                    , where: { //设定异步数据接口的额外参数，任意设
                        Msg: depInfo
                    }
                    , request: {
                        pageName: 'pageNum',
                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                    }
                    , page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        });

    });
</script>
</body>
</html>

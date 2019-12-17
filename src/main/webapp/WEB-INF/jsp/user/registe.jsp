<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <c:set var="request" value="${pageContext.request.contextPath}"/>
    <title>注册 - 人力资源管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/login.css" media="all">
</head>
<body>


<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>人力资源管理系统</h2>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-user" for="LAY-user-login-id"></label>
                <input type="text" name="name" id="LAY-user-login-id" autocomplete="off" required placeholder="用户名"
                       class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-id"></label>
                <input type="text" name="nickname" id="LAY-user-login-nickname" autocomplete="off" required placeholder="昵称"
                       class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                       for="LAY-user-login-password"></label>
                <input type="password" name="password" id="LAY-user-login-password" required placeholder="密码"
                       class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                       for="LAY-user-login-repass"></label>
                <input type="password" name="repass" id="LAY-user-login-repass" required placeholder="确认密码"
                       class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone"
                       for="LAY-user-login-cellphone"></label>
                <input type="text" name="cellphone" id="LAY-user-login-cellphone"  placeholder="手机" required
                       class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-list" for="LAY-user-login-email"></label>
                <input type="text" name="email" id="LAY-user-login-email"  placeholder="邮箱" required
                       class="layui-input">
            </div>
            <div class="layui-form-item">
                <input type="checkbox" name="agreement" lay-skin="primary" title="同意用户协议" checked>
            </div>
            <%--<input  name="token" type="hidden" value="${token}"/>--%>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-reg-submit">注 册</button>
            </div>
            <div class="layui-trans layui-form-item layadmin-user-login-other">
                <label>社交账号注册</label>
                <a href="javascript:;"><i class="layui-icon layui-icon-login-qq"></i></a>
                <a href="javascript:;"><i class="layui-icon layui-icon-login-wechat"></i></a>
                <a href="javascript:;"><i class="layui-icon layui-icon-login-weibo"></i></a>

                <a href="${request}/login/toLogin.html" class="layadmin-user-jump-change layadmin-link layui-hide-xs">用已有帐号登入</a>
            </div>
        </div>
    </div>

    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2019 <a href="https://victor-jml.github.io" target="_blank">我的博客/</a></p>
    </div>

</div>

<script src="${request}/static/plugins/layuiadmin/layui/layui.js"></script>
<script src="${request}/js/myValidity.js"></script>

<script>
    layui.config({
        base: '${request}/static/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function () {
        var $ = layui.$
            , setter = layui.setter
            , admin = layui.admin
            , form = layui.form
            , router = layui.router();

        /*****************************************************/
        //用户名栏失去焦点提示
        $(function () {
            $("#LAY-user-login-id").blur(function () {
                //小tips

                layer.msg('4到16位，字母数字下划线，减号 ', {
                    offset: '100px',
                    icon: 3,
                    time: 3000
                });
            });
        });
        /*****************************************************/

        form.render();

        //提交
        form.on('submit(LAY-user-reg-submit)', function (obj) {
            var field = obj.field;
            console.log(field);

            //确认密码
            if (field.password !== field.repass) {
                return layer.msg('两次密码输入不一致');
            }


            //是否同意用户协议
            if (!field.agreement) {
                return layer.msg('你必须同意用户协议才能注册');
            }

            //验证账号是否合法
            if(!userValidity(field.name)){
                return layer.msg('账号格式错误,请修改');
            }

            //验证电话号码是否合法
            if(!phoneValidity(field.cellphone)){
                return layer.msg('手机号码格式错误,请修改');
            }

            //验证邮箱是否合法
            if(!mailValidity(field.email)){
                return layer.msg('邮箱格式错误,请修改');
            }


            //请求接口
            $.ajax({
                url: '${request}/registe/userRegiste.html'
                ,
                type: 'post'
                ,
                data: {
                    "name": field.name,
                    "nickname": field.nickname,
                    "password": field.password,
                    "authority": 1,
                    "email": field.email,
                    "phone": field.cellphone
                }
                ,
                success: function (res) {
                    if (res.data == "regSuccess") {
                        layer.msg('注册成功', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${request}/login/toLogin.html'; //跳转到登入页
                        });
                    } else if (res.data == "userExist") {
                        layer.msg('用户名已被注册', {
                            icon: 5,
                            anim: 6
                        });
                    } /*else if (res.data == "regNicknameExist") {
                        layer.msg('昵称已被注册', {
                            icon: 5,
                            anim: 6
                        });
                    } */else if (res.data == "emailExist") {
                        layer.msg('邮箱已被注册', {
                            icon: 5,
                            anim: 6
                        });
                    }else if(res.data == "phoneExist"){
                        layer.msg('电话已被注册',{
                            icon: 5,
                            anim: 6
                        });
                    }
                    else {
                        layer.msg('未知错误', {
                            icon: 5,
                            anim: 6
                        });
                    }
                }
            });

            return false;
        });
    });
</script>
</body>
</html>
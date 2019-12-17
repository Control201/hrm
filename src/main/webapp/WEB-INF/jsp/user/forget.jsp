<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <c:set var="request" value="${pageContext.request.contextPath}"/>
    <title>忘记密码 - 人力资源管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/login.css" media="all">
</head>

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>人力资源管理系统</h2>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-list"
                           for="LAY-user-login-email"></label>
                    <input type="text" name="email" id="LAY-user-login-email" lay-verify="email" autocomplete="off" placeholder="请输入注册时的邮箱"
                           class="layui-input">
                </div>
                <div class="layui-form-item">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"
                                   for="LAY-user-login-smscode"></label>
                            <input type="text" name="emailcode" lay-verify="required" placeholder="请输入邮箱验证码"
                                   id="LAY-user-login-smscode" class="layui-input">
                            <%--<input type="text" name="vercode" id="LAY-user-login-smscode" lay-verify="required" placeholder="短信验证码" class="layui-input">--%>
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <%--<input id="send-email-code" style="width: 120px;height: 39px;text-align: center;background-color: white;border: 1px solid #E2E2E2" name="send-email-code" type="button"   value="获取验证码"  />--%>
                                <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid"
                                        id="send-email-code" name="send-email-code">获取验证码
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                       for="LAY-user-login-password"></label>
                <input type="password" name="password" id="LAY-user-login-password" lay-verify="pass"
                       placeholder="新密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                       for="LAY-user-login-repass"></label>
                <input type="password" name="repass" id="LAY-user-login-repass" lay-verify="required"
                       placeholder="确认密码" class="layui-input">
            </div>
            <input name="token" type="hidden" value="${token}"/>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-forget-resetpass">重置新密码
                </button>
            </div>
        </div>
    </div>

    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2019 <a href="https://victor-jml.github.io/" target="_blank">我的博客</a></p>
    </div>
</div>

<script src="${request}/static/custom/js/external/jquery-3.3.1.min.js"></script>
<%--发送邮箱验证码的js脚本--%>
<%--<script src="${ctx}/static/custom/js/external/gVerify.js"></script>--%>
<%--图形验证码的放置与验证--%>
<script src="${request}/static/plugins/layuiadmin/layui/layui.js"></script>
<script>
    //全局变量存储验证邮箱值便于重置时与后端交互

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

        // //初始化图形验证码
        // var verifyCode = new GVerify("v_container");

        /*****************************************************/
        //发送邮件验证码模块
        var InterValObj; //timer变量，控制时间
        var count = 60; //间隔函数，1秒执行
        var curCount;//当前剩余秒数
        //检验邮箱手否合格
        $("#LAY-user-login-email").blur(function () {
            var email = $("#LAY-user-login-email").val();
            if (!email.match(/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/)) {
                return layer.msg('请输入正确的邮箱格式');
            }

            $.ajax({
                url:"${request}/forget/emailCheck.html",
                type:"get",
                data:{'email':email},
                success:function (res) {
                    if(res.data == "emailNotExist"){
                        layer.msg('该邮箱尚未注册', {
                            icon: 5,
                            anim: 6
                        });
                    }
                }
            })
        });
        //向邮箱发送验证码
        $('#send-email-code').click(function () {
            //校验邮箱
            var email = $("#LAY-user-login-email").val();
            // if (!email.match(/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/)) {
            //     return layer.msg('请输入正确的邮箱格式');
            // }

            // 设置button效果，开始计时
            curCount = count;
            $("#send-email-code").attr("disabled", true);  //按钮禁止点击
            $("#send-email-code").html(curCount + "秒后重获");
            InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器timer处理函数，1秒执行一次

            layer.msg('验证码已发送至你的邮箱，请注意查收', {
                icon: 1
                , shade: 0
            });
            //请求发送验证码
            $.ajax({
                type: "get",
                url: "${request}/forget/sendVerifyCodeToEmail.html",
                data: {'email': email},
                success: function (obj) {
                },
            });
        });

        //timer处理函数
        function SetRemainTime() {
            if (curCount == 0) {//超时重新获取验证码
                window.clearInterval(InterValObj);// 停止计时器
                $("#send-email-code").attr("disabled", false);
                $("#send-email-code").html("重获验证码");
                // document.getElementById("send-email-code").removeAttribute("disabled");//移除禁用状态改为可用
                // document.getElementById("send-email-code").value="重获验证码";
            } else {
                curCount--;
                $("#send-email-code").html(curCount + "秒后重获");
            }
        }

        /*****************************************************/
        //重置密码
        form.on('submit(LAY-user-forget-resetpass)', function (obj) {
            var field = obj.field;
            //确认密码
            if (field.password !== field.repass) {
                return layer.msg('两次密码输入不一致');
            }

            //请求接口
            $.ajax({
                url: '${request}/forget/emailCodeTest.html?token=' + field.token
                ,
                type: 'post'
                , data: {"code": field.emailcode,
                         "email": field.email,
                         "password":field.password
                }
                , success: function (res) {
                    if(res.code == "codeWrong"){
                        layer.msg('验证码错误');
                    }else if (res.data == "resetSuccess") {
                        layer.msg('密码已成功重置', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.href = '${request}/login/toLogin.html'; //跳转到登入页
                        });
                    } else {
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <c:set var="request" value="${pageContext.request.contextPath}"/>
    <title>设置我的密码</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">修改密码</div>
                <div class="layui-card-body" pad15>

                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">当前邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" name="oldEmail" id="oldEmail" value="${userInfo.email}" readonly
                                       class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">此项为已绑定邮箱，下面验证码发送至此邮箱</div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">新邮箱</label>
                            <div class="layui-input-inline">
                                <input type="text" name="newEmail" id="newEmail"
                                       lay-verify="email" autocomplete="off" placeholder="请输入新邮箱" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">邮箱验证码</label>
                            <div class="layui-input-inline">
                                <div class="layui-row">
                                    <input type="text" name="emailcode" lay-verify="required" placeholder="请输入邮箱验证码"
                                           id="LAY-user-login-smscode" class="layui-input">
                                    <%--<input type="text" name="vercode" id="LAY-user-login-smscode" lay-verify="required" placeholder="短信验证码" class="layui-input">--%>
                                </div>

                            </div>
                            <div class="layui-input-inline">
                                <div style="margin-left: 10px">
                                    <%--<input id="send-email-code" style="width: 120px;height: 39px;text-align: center;background-color: white;border: 1px solid #E2E2E2" name="send-email-code" type="button"   value="获取验证码"  />--%>
                                    <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid"
                                            id="send-email-code" name="send-email-code" style="width: auto">获取验证码
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="setmyEmail">确认修改</button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="${request}/static/plugins/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '${request}/static/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'set'], function () {
        var $ = layui.$
            , setter = layui.setter
            , admin = layui.admin
            , form = layui.form
            , router = layui.router();


        /*************************************************************/
        var InterValObj; //timer变量，控制时间
        var count = 60; //间隔函数，1秒执行
        var curCount;//当前剩余秒数
        //向邮箱发送验证码
        $('#send-email-code').click(function () {
            //校验邮箱
            var email = $("#oldEmail").val();
            if (!email.match(/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/)) {
                return layer.msg('请输入正确的邮箱格式');
            }

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
                async: true,
                data: {"email": email},
                success: function (obj) {
                },
                dataType: "json"
            });
        });

        //timer处理函数
        function SetRemainTime() {
            if (curCount == 0) {//超时重新获取验证码
                window.clearInterval(InterValObj);// 停止计时器
                $("#send-email-code").attr("disabled", false);
                $("#send-email-code").html("重获验证码");
            } else {
                curCount--;
                $("#send-email-code").html(curCount + "秒后重获");
            }
        }

        /*************************************************************/


        form.render();

        //提交
        form.on('submit(setmyEmail)', function (obj) {
            var field = obj.field;

            // console.log(field);
            if (field.oldEmail == field.newEmail){
                return layer.msg('您未对绑定邮箱做任何修改！');
            }

            //请求接口
            $.ajax({
                url: '${request}/modify/emailCodeTest.html' //实际使用请改成服务端真实接口
                , data: {"code": field.emailcode,"email": field.oldEmail}
                , success: function (res) {
                    if (res.data == "codeCorrect") {
                        /*****************************************************/
                        //验证码正确后重置用户基本资料
                        var allData = {
                            "oldEmail": field.oldEmail,
                            "newEmail": field.newEmail
                        };
                        //请求接口
                        $.ajax({
                            type: 'post',
                            data: allData,
                            url: '${request}/modify/resetEmail.html' //实际使用请改成服务端真实接口
                            , success: function (res2) {
                                console.log(res2.data);
                                if (res2.data == "successReset") {
                                    layer.msg('修改已完成，请F5刷新页面', {
                                        icon: 1
                                        , time: 1000
                                    }, function () {
                                        location.reload();
                                    });
                                }else if(res2.data == "newEmailExist"){
                                    layer.msg('更改邮箱已存在',{
                                        icon: 5,
                                        anim: 6
                                    });
                                }else {
                                    layer.msg('未知错误', {
                                        icon: 5,
                                        anim: 6
                                    });
                                }

                            }
                        });
                        /*****************************************************/
                    } else if (res.data == "emailNotExist") {
                        layer.msg('该邮箱尚未注册', {
                            icon: 5,
                            anim: 6
                        });
                    } else if (res.data == "codeWrong") {
                        layer.msg('验证码错误', {
                            icon: 5,
                            anim: 6
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

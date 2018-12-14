<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}登录</title>
<meta name="decorator" content="login" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#loginForm").validate({
			rules : {
				validateCode : {
					remote : "${pageContext.request.contextPath}/servlet/validateCodeServlet"
				}
			},
			messages : {
				username : {
					required : "请填写用户名."
				},
				password : {
					required : "请填写密码."
				},
				validateCode : {
					remote : "验证码不正确.",
					required : "请填写验证码."
				}
			},
			/* errorLabelContainer : "#messageBox",
			errorPlacement : function(error, element) {
				error.appendTo(element.parent());
			} */
		});
	});
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if (self.frameElement && self.frameElement.tagName == "IFRAME"
			|| $('#left').length > 0 || $('.jbox').length > 0) {
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
</script>
</head>
<body>
	<div class="header"></div>
	<div class="loginWraper">
		<div class="loginBox">
			<form id="loginForm" class="form form-horizontal"
				action="${ctx}/login" method="post">
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
					<div class="formControls col-xs-8">
						<input id="username" name="username" type="text" placeholder="帐号"
							class="input-text size-L required">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
					<div class="formControls col-xs-8">
						<input id="password" name="password" type="password"
							placeholder="密码" class="input-text size-L required">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe676;</i></label>
					<div class="formControls col-xs-8">
						<input type="text" placeholder="验证码" id="validateCode" name="validateCode" maxlength="5" class="input-text size-L required" style="font-weight: bold; width: 150px;" />
						<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet"
							onclick="$('.validateCodeRefresh').click();" class="mid validateCode" style="height:40px;" />
						<a href="javascript:" onclick="$('.validateCode').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());"
							class="mid validateCodeRefresh" style="">看不清</a>
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<label for="rememberMe" title="下次不需要再登录">
							<input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 使我保持登录状态（公共场所慎用）
						</label>
						<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><!-- <button data-dismiss="alert" class="close">×</button> -->
							<label id="loginError" class="error">${message}</label>
						</div>
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<input type="submit" class="btn btn-primary radius size-L"
							value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">
		Copyright &copy; 2012-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a href="http://www.jxtii.com/" target="_blank">江西电信信息产业有限公司</a>
			<image style="background: #fff; width: 100px;height: 100px;" src="${ctxStatic}/images/qrcode.png"></image>
	</div>
</body>
</html>
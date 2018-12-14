<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnExport").click(function(){
//                top.$.jBox.confirm("确认要导出用户数据吗？", "系统提示", function (v, h, f) {
//                    if (v == "ok") {
                        $("#searchForm").attr("action", "${ctx}/demo/user/userDemo/export");
                        $("#searchForm").submit();
//                    }
//                }, {buttonsFocus: 1});
//                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/demo/user/userDemo/">用户列表</a></li>
    <shiro:hasPermission name="demo:user:userDemo:edit">
        <li><a href="${ctx}/demo/user/userDemo/form">用户添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="userDemo" action="${ctx}/demo/user/userDemo/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>归属公司：</label>
            <sys:treeselect id="company" name="company.id" value="${userDemo.company.id}" labelName="company.name" labelValue="${userDemo.company.name}"
                            title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
        </li>
        <li><label>归属部门：</label>
            <sys:treeselect id="office" name="office.id" value="${userDemo.office.id}" labelName="office.name"
                            labelValue="${userDemo.office.name}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li><label>创建时间：</label>
            <input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${userDemo.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
            <input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${userDemo.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnExport" class="btn btn-primary" type="submit" value="导出"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>姓名</th>
        <th>公司</th>
        <th>部门</th>
        <th>更新时间</th>
        <th>备注信息</th>
        <shiro:hasAnyPermissions name="demo:user:userDemo:edit,demo:user:userDemo:delete">
            <th>操作</th>
        </shiro:hasAnyPermissions>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="userDemo">
        <>
        <td><a href="${ctx}/demo/user/userDemo/form?id=${userDemo.id}">
                ${userDemo.name}
        </a></td>
        <td>
                ${userDemo.company.name}
        </td>
        <td> ${userDemo.office.name}

        </td>
        <td>
            <fmt:formatDate value="${userDemo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </td>
        <td>
                ${userDemo.remarks}
        </td>
        <shiro:hasAnyPermissions name="demo:user:userDemo:edit,demo:user:userDemo:delete">
            <td>
                <shiro:hasPermission name="demo:user:userDemo:edit">
                    <a href="${ctx}/demo/user/userDemo/form?id=${userDemo.id}">修改</a></shiro:hasPermission>
                <shiro:hasPermission name="demo:user:userDemo:delete"><a
                        href="${ctx}/demo/user/userDemo/delete?id=${userDemo.id}"
                        onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
                </shiro:hasPermission>
            </td>
        </shiro:hasAnyPermissions>


        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
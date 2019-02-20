<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head></head>
<body>

<!--用户修改Div-->
<div th:fragment="${model_name_firstSmall}edit">

    <!-- header modal -->
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myLargeModalLabel">修改${model_name}</h4>
    </div>
    <form id="updateform" action="/admin/${model_name_firstSmall}_ajax?action=update" method="post" class="sky-form boxed"
          novalidate="novalidate" th:object="${'$'}{${model_name_firstSmall}}">
        <!-- body modal -->
        <div class="modal-body">
            <div class="login-box">

                <header><i class="fa fa-users"></i> 修改${model_name} <b><span th:text="*{${model_name_firstSmall}Name}">zhangsan</span></b>
                    <small class="note bold">请谨慎操作</small>
                </header>

                <fieldset>
                    <input type="hidden" th:field="*{id}"/>
                    <label>修改${model_name}名称</label>
                    <label class="input">
                        <i class="icon-append fa fa-key"></i>
                        <input type="text" th:field="*{${model_name_firstSmall}Name}"
                               placeholder="修改${model_name}名称" minlength="2" required="true" class="form-control "/>
                        <b class="tooltip tooltip-bottom-right">输入${model_name}名称，以ROLE_开头</b>
                    </label>

                    <label>修改${model_name}别名</label>
                    <label class="input">
                        <i class="icon-append fa fa-tv"></i>
                        <input type="text" th:field="*{${model_name_firstSmall}NameCN}" placeholder="${model_name}描述" minlength="2" required="true"
                               class="form-control "/>
                        <b class="tooltip tooltip-bottom-right">输入${model_name}中文解释</b>
                    </label>

                    <label>修改${model_name}备注</label>
                    <label class="input">
                        <i class="icon-append fa fa-commenting-o"></i>
                        <input type="text" th:field="*{comment}" placeholder="备注信息" minlength="2" required="true"
                               class="form-control "/>
                        <b class="tooltip tooltip-bottom-right">输入${model_name}备注信息</b>
                    </label>

                </fieldset>

            </div>
        </div>

        <!-- Modal Footer -->
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary" onclick="">Save changes</button>
        </div>
    </form>
    <script type="text/javascript" th:inline="javascript">
        /*<![CDATA[*/
        function callback (data, success) {
            if (success === "success") {
                alert("Data: " + data.msg + "\nStatus: " + data.status);
                if (data.status === "success") {
                    $("#ajax").modal('hide');
                }
            }
        }

        ${'$'}("#updateform").on("submit", function (e) {
//            alert(${'$'}(this).serialize());
//            e.preventDefault();
            ${'$'}.post(this.action, ${'$'}(this).serialize(), callback);
            return false;
        });
        /*]]>*/
    </script>
</div>

</body>
</html>
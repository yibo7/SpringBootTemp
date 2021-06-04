<html>
<head>
    <meta charset="utf-8">
    <title>错误提示</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    错误!
                </h4> <strong>${msg}</strong>
                <a class="alert-link">3s后自动跳转</a>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    <#if (url)??>
        setTimeout('location.href="${url!}"', 3000);
    <#else>
    setTimeout('window.history.back()', 3000);
    </#if>
</script>

</html>
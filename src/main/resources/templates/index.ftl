<!DOCTYPE html>
<html >
<head>
    <#import "spring.ftl" as spring>
    <#include "component/headermeta.ftl">
    <title><@spring.message code="index.hot"/></title>
</head>
<body>
 <h1>${lang.get("index.hot")}</h1>
<h1>${username}</h1>
</body>
</html>
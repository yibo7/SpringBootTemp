
<span style="white-space:pre;">         </span><#--分页-->
<div class="col-md-12 column">
    <ul class="pagination pull-right">
    <#if currentPage lte 1>
        <li class="disabled"><a href="#">上一页</a></li>
    <#else>
        <li><a href="${pageUrl}page=${(currentPage - 1)?c}">上一页</a></li>
    </#if>
    <#-- 以下为带省略号分页 -->
    <#--第一页-->
    <#if (dataPage.getTotalPages() > 0)>
        <li <#if currentPage == 1>class="disabled"</#if>><a href="${pageUrl}page=1" >1</a></li>
    </#if>

    <#--如果不只有一页-->
    <#if (dataPage.getTotalPages() > 1)>
    <#--如果当前页往前查3页不是第2页-->
        <#if ((currentPage - 3) > 2)>
            <li><span class="text">…</span></li>
        </#if>

    <#--当前页的前3页和后3页-->
        <#list (currentPage - 3)..(currentPage + 3) as index>
        <#--如果位于第一页和最后一页之间-->
            <#if (index > 1) && (index < dataPage.getTotalPages())>
                <li <#if currentPage == index>class="disabled"</#if>><a href="${pageUrl}page=${index?c}" >${index?c}</a></li>
            </#if>
        </#list>

    <#--如果当前页往后查3页不是倒数第2页-->
        <#if (currentPage + 3) < (dataPage.getTotalPages() - 1)>
            <li><span class="text">…</span></li>
        </#if>

    <#--最后页-->
        <li <#if currentPage == dataPage.getTotalPages()>class="disabled"</#if>>
            <a href="${pageUrl}page=${dataPage.getTotalPages()?c}" >${dataPage.getTotalPages()?c}</a></li>
    </#if>
    <#if currentPage gte dataPage.getTotalPages()>
        <li class="disabled"><a href="#">下一页</a></li>
    <#else>
        <li><a href="${pageUrl}page=${(currentPage + 1)?c}">下一页</a></li>
    </#if>
    </ul>
</div>

<#macro adminPageNav  navName1 navName2>
<!-- Page Header -->
<div class="bg-gray-lighter adminHeadBox hidden-xs" style="padding: 0px 15px 5px 15px">
    <div class="row items-push">
        <div class="col-sm-7">
            <h1 class="page-heading">
                ${navName2}
                <a class="content-header-btn" href="javascript:window.location.reload()"><i class="fa fa-refresh"></i>
                    刷新</a>
            </h1>
        </div>
        <div class="col-sm-5 text-right hidden-xs">
            <ol class="breadcrumb push-10-t">
                <li>${navName1}</li>
                <li><a class="link-effect">${navName2}</a></li>
            </ol>
        </div>
    </div>
</div>
<!-- END Page Header -->
</#macro>

<#macro pageHeadLeft>
 <div class="page-head-left">
    <#nested>
 </div>
</#macro>

<#macro pageHeadRight>
 <div class="page-head-right">
    <#nested>
     <button class="btn btn-success btn-sm" type="button"  data-toggle="tooltip"  title="搜索" onclick="initSubmit();"><i class="fa fa-search"></i></button>
     <button class="btn btn-success btn-sm" type="button"  data-toggle="tooltip"  title="清空搜索条件" onclick="clearCondition()"><i class="fa fa-refresh"></i></button>
     <input id="pageInit" name="pageInit" type="hidden">
 </div>
</#macro>

<#-- 分页模板 -->
<#function max x y>
    <#if (x<y)><#return y><#else><#return x></#if>
</#function>
<#function min x y>
    <#if (x<y)><#return x><#else><#return y></#if>
</#function>
<#macro pageingTemaplte page>
    <input type="hidden" id="pageNumber" name="pageNumber" value="${page.number+1}"/>
    <input type="hidden" id="pageSize" name="pageSize" value="${page.size}"/>
    <input type="hidden" id="searchProperty" name="searchProperty" value="${customSearch.searchProperty}"/>
    <input type="hidden" id="orderProperty" name="orderProperty" value="${customSearch.orderProperty}"/>
    <input type="hidden" id="orderDirection" name="orderDirection" value="${customSearch.orderDirection}"/>

    <#assign recordCount = page.totalElements />
    <#assign pageSize = page.size />
    <#assign p= page.number+1 />

    <div class="col-sm-6">
        <div style="padding-top: 18px;white-space: nowrap;">
            每页显示
            <span class="dropup hidden-xs">
			  <button class="btn btn-sm btn-default dropdown-toggle" type="button" id="pageSizeSelect"
                      data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			  ${page.size}
                  <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="pageSizeSelect" id="pageSizeOption">
				  <li <#if page.size == 10> class="active"</#if>>
					  <a href="javascript:;" val="10" onclick="changePagesize(10)">10</a>
				  </li>
				  <li <#if page.size == 20> class="active"</#if>>
					  <a href="javascript:;" val="20" onclick="changePagesize(20)">20</a>
				  </li>
				  <li <#if page.size == 50> class="active"</#if>>
					  <a href="javascript:;" val="50" onclick="changePagesize(50)">50</a>
				  </li>
				  <li <#if page.size == 100> class="active"</#if>>
					  <a href="javascript:;" val="100" onclick="changePagesize(100)">100</a>
				  </li>
			  </ul>
		</span>条,
            共 <strong>${page.totalElements}</strong>条记录
        </div>
    </div>
    <div class="col-sm-6">
        <div style="margin:0;white-space:nowrap;text-align: right">
			<#if (recordCount>0) >
                <ul class="pagination" style="margin:12px 0px;white-space: nowrap">
					<#assign size=((recordCount + pageSize - 1) / pageSize)?int>
					<#if (p<=5)> <#-- p among first 5 pages -->
                        <#assign interval = 1..(min(5,size))>
                    <#elseif ((size-p)<5)> <#-- p among last 5 pages -->
                        <#assign interval = (max(1,(size-4)))..size >
                    <#else>
                        <#assign interval = (p-2)..(p+2)>
                    </#if>
					<#if (p == 1)>
                        <li class="disabled"><a href="javascript:void(0)"><i class="fa fa-angle-left"></i></a></li>
                    <#else>
                        <li><a href="javascript: pageSkip(${p - 1})"><i class="fa fa-angle-left"></i></a></li>
                    </#if>
					<#if !(interval?seq_contains(1))>
                        <li><a href="javascript: pageSkip(${1})">1</a></li>
                        <li><a>...</a></li><#rt>
                    </#if>
					<#list interval as page>
                        <#if page=p>
                            <li class="active"><a href="javascript:void(0)">${page}</a></li>
                        <#else>
                            <li><a href="javascript: pageSkip(${page})">${page}</a></li>
                        </#if>
                    </#list>
					<#if !(interval?seq_contains(size))>
                        <li><a>...</a></li>
                        <li><a href="javascript: pageSkip(${size})">${size}</a></li><#lt>
                    </#if>
					<#if (p == size)>
                        <li class="disabled">
                            <a href="javascript:void(0)"><i class="fa fa-angle-right"></i></a>
                        </li>
                    <#else>
                        <li>
                            <a href="javascript: pageSkip(${p+1})"><i class="fa fa-angle-right"></i></a>
                        </li>
                    </#if>
                </ul>
            </#if>
        </div>
    </div>
</#macro>

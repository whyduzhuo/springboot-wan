<#macro pageHeadLeft>
 <div class="page-head-left">
    <#nested>
 </div>
</#macro>
<#macro pageHeadRight>
 <div class="page-head-right">
    <#nested>
     <button class="btn btn-success btn-sm" type="button" onclick="initSubmit();"><i class="fa fa-search"></i></button>
     <button class="btn btn-success btn-sm" type="button" onclick="clearCondition()"><i class="fa fa-refresh"></i></button>
     <input id="pageInit" name="pageInit" type="hidden">
 </div>
</#macro>
<html>
<head>
<meta charset ="utf-8"></meta>
<style>
* {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 20px;
	line-height: 38px;
}
body {  
         font-family:FangSong_GB2312;
     }
.wrap {
	width: 610px;
	padding: 20px 0 0;
	margin: 0 auto;
}
.sim{
	font-family: SimSun;  

}
p ,div{
	
	word-break:break-all;
    word-wrap:break-word;
    
}
.h2t {
	font-size: 29px;
	font-weight: bold;
	text-align: center;
	font-family: SimSun;  
	
}
.xybh {
	text-align: right;
}
.xhline {
	padding: 0 5px;
	border-bottom: #000 1px solid;
}
.shangfang {
	margin-right: 10px;
}
.gefang {
	display: inline-block;
	width: 148px;
	margin-right: 20px;
}
.h4t {
	font-weight: bold;
}
.lm30 {
	padding-left: 29px;
}
table {
	border-left: #000 1px solid;
	border-top: #000 1px solid;
	border-collapse:collapse;
}
td {
	border-right: #000 1px solid;
	border-bottom: #000 1px solid;
	padding: 0 10px;
}
.ul{
text-decoration: underline;
}
@page {
    size: 8.5in 11in;
    margin: 60px;
}
.bdNoborder{
	border:none;
}
.bdNoborder td{
	border:none;
}
h1{
	font-size:16px;
}
.picture{
    width: 110px;
    height: auto;
    float: right;
    text-align: center;
    line-height: 110px;
}
.fr{ float:right;}

table{ border-collapse: collapse; line-height: 30px; border:#ccc solid 1px;}

table td,table th{border:#ccc solid 1px; padding: 5px;}

.blank {
	text-indent:2em;
}
.frs{
	float: right;
	margin-right: 10px;
}
.clearfloat {
	clear:both;
}
.width-1d5em{
	display: inline-block;
	width: 1.5em;
}
.qianzhang-wrap {
	position: relative;
	padding-bottom: 40px;
	height: 30px;
}
</style>
<title>在线仲裁系统</title>
<link rel="shortcut icon" href="/olap/dist/favicon.ico"></link>
</head>

<body>
<div class="wrap">
  	<div class="h2t">公告</div>
  	<div class="h2t">项 目 使 用 须 知</div>
  	<br />
	<p class="fr">${arbNumber}</p>
	<br />
<!--	<#list arbProsecutors as arbProsecutors>
		<#if arbProsecutors.type?exists && arbProsecutors.type == 2>
			<p class="blank">申请人：${arbProsecutors.name}，${arbProsecutors.coLegalPerson}，社会信用代码：${arbProsecutors.idNum}。住所：${arbProsecutors.adress}。</p>
			<#if arbProsecutors.agentRealname??><p class="blank">申请人的委托代理人：${arbProsecutors.agentRealname}，<#if arbProsecutors.agentJob??>${arbProsecutors.agentJob}<#else></#if>律师。</p><#else></#if>
		<#else>
			<p class="blank">申请人：${arbProsecutors.name}，<#if arbProsecutors.sex??>${arbProsecutors.sex}，<#else></#if><#if arbProsecutors.nation??>${arbProsecutors.nation}，<#else></#if><#if arbProsecutors.birthday??>${arbProsecutors.birthday}出生，<#else></#if>身份证号码：${arbProsecutors.idNum}。住所： ${arbProsecutors.adress}</p>
			<#if arbProsecutors.agentRealname??><p class="blank">申请人的委托代理人：${arbProsecutors.agentRealname}，<#if arbProsecutors.agentJob??>${arbProsecutors.agentJob}<#else></#if>律师。</p><#else></#if>
		</#if>
	</#list>
	<#list defendants as defendants>
		<#if defendants.type?exists && defendants.type == 2>
			<p class="blank">被申请人：${defendants.name}，${defendants.coLegalPerson}，社会信用代码：${defendants.idNum}。住所：${defendants.adress}。</p>
			<#if defendants.agentRealname??><p class="blank">被申请人的委托代理人：${defendants.agentRealname}，<#if defendants.agentJob??>${defendants.agentJob}<#else></#if>律师。</p><#else></#if>
		<#else>
			<p class="blank">被申请人：${defendants.name}，<#if defendants.sex??>${defendants.sex}，<#else></#if><#if defendants.nation??>${defendants.nation}，<#else></#if><#if defendants.birthday??>${defendants.birthday}出生，<#else></#if>身份证号码：${defendants.idNum}。住所： ${defendants.adress}</p>
			<#if defendants.agentRealname??><p class="blank">被申请人的委托代理人：${defendants.agentRealname}，<#if defendants.agentJob??>${defendants.agentJob}<#else></#if>律师。</p><#else></#if>
		</#if>
	</#list>-->
	<!--<p class="blank">案由：${arbName}</p>-->
	<p class="blank">东方鲤鱼团队（以下简称“future”）项目负责人${arbProsecutorName}（以下简称“负责人”）与用户${arbDefendantName}（以下简称“用户”）于*年*月*日签订的《*使用须知*》中的系统使用须知，于${arbApplyTime}阅读，关于${arbName}正式生效。</p>
	<p class="blank">根据《东方鲤鱼团队开发使用规则》（以下简称“开发规则”）第一条的规定，本团队于<#if registerDocumentArrivedTime??>${registerDocumentArrivedTime}<#else>*年月日（立项时间）*</#if>注册使用中用户送达了免责通知书、开发规则、使用名册等材料。</p>
	<p class="blank">本案适用${procedureName}开庭审理，申请人选定<#if arbProsecutorArbitratorName??>${arbProsecutorArbitratorName}<#else>*申请人仲裁员*</#if>为仲裁员，被申请人选定<#if arbDefendantArbitratorName??>${arbDefendantArbitratorName}<#else>*被申请人仲裁员*</#if>为仲裁员，双方共同选定${arbArbitratorName}。本院于<#if hearDocumentArrivedTime??>${hearDocumentArrivedTime}<#else>*年月日（组庭通知书送达时间）*</#if>向双方当事人送达了组庭通知书及开庭通知，并于<#if arbTribunalBeginTime??>${arbTribunalBeginTime}<#else>*年月日（庭审时间）*</#if>不公开开庭审理。</p>
	<p class="blank">申请人提出的仲裁请求为：${arbApplication}</p>
	<p class="blank">使用过程中，依照《仲裁法》第五十一条、仲裁规则第五十条的规定，开发团队与使用用户之间默认达成子协议：</p>
	<p class="blank">一、************************************************。</p>
	<p class="blank">二、************************************************。</p>
	<p class="blank">三、双方确认收款邮箱：邮箱名称：****************，邮箱账号：**************，所属公司：********** 。</p>
	<p class="blank">四、*************************************************。 </p>
	<p class="blank">以上调解协议符合法律规定，仲裁庭予以确认。</p>
	<p class="blank">本公告自双方阅读之日起发生法律效力。</p>
	<br />
	<div>
		<div class="frs">
			<p>团队名称：zhangyajun</p>
			<p>用户名称：${arbSecretaryName}</p>
			<p>年<span class="width-1d5em"></span>月<span class="width-1d5em"></span>日</p>
			<p>：${arbSecretaryName}</p>
		</div>
		<div class="clearfloat"></div>
	</div>
<div class="qianzhang-wrap" style="text-align: right;">
	<p style="text-align: right;">${finalTime}<br/></p>
</div>
</div>
</body>
</html>

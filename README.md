
<hr><h1>关于项目信息</h1><hr>
<h3>future record 铭记.未来</h3><hr>

<h2>项目托管</h2>
- gitLab官网，git@gitlab.com:zhangyajun/future-record.git 
- gitee官网 https://gitee.com/DongFangLiYu/future-record.git

项目建于 2020.4.15

开发团队负责人： DongFangLiYu 

<h2>项目背景</h2><hr>
   利用自己的技能，空闲时间不断学习新技术，应用开发于future-record开源项目。future-record项目是一个聚合性的项目，
 分前后端架构，多模化划分，旨在解耦业务，提高开发效率；内部划分网关治理服务，鉴权管理服务，文件管理服务，任务管理服务，对接外部rest api 服务等


<h2>服务信息清单</h2>
<table border="1">
  <tr>
    <th>服务描述</th>
    <th>服务名称</th>
    <th>服务端口</th>
    <th>备注</th>
  </tr>
  <tr>
    <td>网关服务</td>
    <td>future-gateway</td>
    <td>80</td>
    <td></td>
  </tr>
  <tr>
   <td>鉴权服务</td>
    <td>future-auth</td>
    <td>8081</td>
    <td></td>
   </tr>
   <tr>
     <td>前台服务</td>
     <td>future-rest</td>
     <td>8082</td>
     <td></td>
   </tr>
   <tr>
     <td>文件服务</td>
     <td>future-file</td>
     <td>8083</td>
     <td></td>
   </tr>
   <tr>
     <td>用户服务</td>
     <td>future-user</td>
     <td>8085</td>
     <td></td>
   </tr>
   <tr>
     <td>任务服务</td>
     <td>future-task</td>
     <td>8086</td>
     <td></td>
   </tr>
</table>
<h2>项目版本记录</h2>
<table border="2">
  <tr>
    <th>时间</th>
    <th>版本</th>
    <th>功能</th>
    <th>备注</th>
  </tr>
  <tr>
      <th>2020-04-15</th>
      <th>v1.0.0</th>
      <th>初始化项目架构</th>
      <th>备注</th>
  </tr>
  <tr>
        <th>2020-06-04</th>
        <th>v1.0.1</th>
        <th>鉴权服务基本功能测试开发</th>
        <th>备注</th>
   </tr>
   <tr>
        <th>2020-09-12</th>
        <th>v1.0.3</th>
        <th>邮件通知服务，pdf生成功能开发完成</th>
        <th>备注</th>
   </tr>
   <tr>
       <th>2021-02-02</th>
        <th>v1.0.4</th>
        <th>网关服务开发完成</th>
        <th>意识到聚合项目需要拆分，开始准备工作</th>
    </tr>
    <tr>
        <th>2021-04-02</th>
        <th>v1.0.6</th>
        <th>项目子模块task与流程服务实现容器化自动部署</th>
        <th>拆分出俩个子系统，集成jenkins+docker 自动化部署</th>
     </tr>
     <tr>
          <th>2021-05-16</th>
          <th>v1.0.7</th>
          <th>开始搭建websocket的服务</th>
          <th></th>
      </tr>
</table>

有兴趣的同行可以联系dongfangliyu@gmail.com 留言，谢谢！
<%--
  Created by IntelliJ IDEA.
  User: xiewy
  Date: 2020/9/18
  Time: 4:19 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>资费参数脚本生产</title>
  </head>
  <body>
  <script>
        function displays() {
            alert($("#AIRFARE").val());
            var selecteds = $("#PARAM_TYPE option:selected").val();
            alert(selecteds);
        }
  </script>
  <div>
      <select id="PARAM_TYPE" name="PARAM_TYPE" onchange="displays()">
          <option value="请选择" SELECTED>---------------请选择脚本---------------</option>
          <option value="0">空资费</option>
          <option value="1">空产品</option>
<%--          <option value="1">删除空产品</option>--%>
          <option value="2">主产品</option>
          <option value="3">SP减免</option>
          <option value="4">SP退费结算核减(空资费减免不需要配置)</option>
      </select>
  </div>
    <div>

        博客
        学院
        下载
        论坛
        问答
        代码
        招聘
        电子书
        VIP会员

        创作中心
        收藏
        消息
        登录/注册
        元素scrollTop设置无效的原因及解决办法

        王布丁 2016-04-10 23:44:59   57291   收藏 3
        分类专栏： web前端 遇过的坑
        版权
        在做项目时，需要设置一个隐藏的元素的scrollTop，然后将其显示。当时使用了jQuery的scrollTop方法，但是无效，百度了许久，仍找不出有效的解决方法。于是自己新建了一个网页用来测试scrollTop方法失效的原因，在一番折腾后，终于弄清楚了问题的原因和解决办法。

             scrollTop失效的原因

           当一个元素的display属性为'none'时，对该元素设置scrollTop属性是无效的，所以，即使是jQuery的scrollTop方法也会无效。



           解决办法

           设置元素为可见状态（el.style.display='block'），就可以设置原生的el.scrollTop属性或者是jQuery的scrollTop方法了。



            心得：到网上去寻求问题的解决办法并不是万金油，更多时候还是需要自己去一步步调试，然后得到解决办法。


        点赞
        6

        评论
        5

        分享

        收藏
        3

        打赏

        举报
        关注
        一键三连
        jquery scrollTop 不起效果的一种情况
        weixin_30363981的博客
        905
        项目中，有个a标签，点击弹出div窗口，div里放置了iframe元素，iframe元素指向一个页面，页面的长度大于div的宽度，出现了滚动条 每次隐藏div窗口，再去设置div的scrollTop（以备下次点击a标签，内容置顶），都不起效果！！！ 　　最终是在隐藏div窗口之前，先调用scrollTop，效果终于实现！具体原因还不清楚，猜想应该是div为block的情况下，scrol...
        为什么给元素设置scrollTop不起作用？
        weixin_30387799的博客
        1697
        今天要给一个元素添加一个向上滚动的效果，于是马上用到了scrollTop()。比如说是下面的页面结构： #wrapper的高度是屏幕的可见高度，它在页面中不会随着页面的滚动而滚动。.scroll-fixed-count是#wrapper的子元素，它的高度很高，要远远超过#wrapper的高度。现在我想在某个事件触发之后，让.scroll-fixed-count向上移动100px，于...




        yhon.:感谢，解决了我的问题6月前回复


        spongejerry:所以可以先设置为显示调整完后再hide是吗1年前回复


        Yilia-Feng:这破东西搞了我一天半 结果就因为display:none了 呵呵1年前回复


        码农橙鹿儿回复:像极了我哈哈哈哈哈哈哈哈哈哈8月前回复


        maozheyandelunzi:总结得好，我都想打你了2年前回复

        登录 查看 5 条热评
        scrollTop无效问题
        yihanzhi的博客
        7483
        元素设置srcollTop的值无效，需要为该元素添加高度后，即可生效！
        浅析scrollTop方法失效的原因！！！
        fxdan的博客
        199
        很久没用jq开发前端了，最近新入职一家公司是前后端不分离，项目主要是用了JQ开发，做了个点击控制滚动条的方法。结果蒙圈了一波，思考了许久，终于知道原因在哪，话不多说，直接给大家说解决方案。 不生效源代码是这样的： html, body, .page { padding: 0; margin: 0; } // 处理楼层滚动 var page = $('body > .page'), floorTabs = $('.floor-tabs .tab-item') floorTab.
        设置scrollTop无效_xinconan的专栏
        10-29
        scrollTop无效问题 yihanzhi的博客 7422 元素设置srcollTop的值无效,需要为该元素添加高度后,即可生效! scrollTop原生JavaScript实现的回到顶部库 ...
        设置元素的scrollTop不生效的解决办法_Arbort
        11-10
        2.要确保这个元素的确有滚动条 3.设置滚动距离(如果只是滚动到最底部,不需要那么精确设置滚动多少) this.$nextTick(()=>{ this.$refs.dialogBody.scrollTop =...
        设置scrollTop无效
        weixin_30711917的博客
        268
        问题描述：页面刷新滚动条不返回到最初到位置而是返回之前浏览的位置 要点：通过sessionStorage设置获取刷新页面前的滚动条位置 <div id="outterBox" style="width:100%;height:100%;background:yellow;overflow: auto"> <div id="innerBox" style="backgroun...
HTML滚动条问题。scolltop始终无效
09-12
我在做HTML页面时，使用多个可折叠内容块。 <div data-role="collapsible" data-collapsed="false"> 当第一个div浏览到底部时点击另一个div展开的div直接显示内容的中间部分。请问有大牛知道如何解决么。
        异步加载scrollTop不生效问题解决_lmp5023的博客
        11-8
        异步加载数据然后append之后,出现滚动条,然后我想把滚动条置底. 遇到的问题:scrollTop不生效 解决方案:scrollTop需要在异步加载完成后再调用,因为是异步的,在去后台...
        异步加载设置元素的scrollTop不生效_sxl131415的博客
        10-31
        解决方案:scrollTop需要在异步加载完成后再调用,因为是异步的,在去后台获取数据的时候,如果scrollTop就触发了,那么就出现不生效的情况
        vue scrollTop 无法赋值
        weixin_34059951的博客
        2065
        遇到问题 container.scrollTop 一直为0不能赋值 watch: { historyList () { this.$nextTick(() => { const container = this.$el.querySelector('.scrolldivmain') ...
        关于scrollTop无效 解决方案
        canshegui2293的博客
        1569
        两个不在一起的div 想要实现同步滚动 很简单 只需要下面这段代码就好 $('.cmp-left-main').scroll(function(e){ var scrollLength=$(this).scrollTop(); $('.cmp-left-hide-main').scrollTop(scrollLength); }); 但是内部的导航栏死活出不...
        scroll-view 中scroll-top设置无效对问题_♂小哥哥的博客
        11-14
        注:this.scrollTop= this.scrollTop+0.01, 中的0.01可随便改,不建议改太大
        vue项目 设置scrollTop不起作用 总结_Coding_Jia的博客
        10-25
        vue项目 设置scrollTop不起作用 总结 今天在开发中,遇到这样一个情景。一个页面中有三个模块,每个模块对应一个标题,每个模块内容都很长,所以需要点击当前模块对应的...
        微信页面回退记忆scrollTop值失效问题
        03-25
        本人想做一个网页后退后记忆滚动条位置的功能，在新版本谷歌、火狐、ie浏览器测试都能正常使用，但是唯独在微信上打开页面大多数情况下此功能失效，只有小几率会成功，求专家帮忙解决，这是代码 ``` window.onload=function(){ if(location.hash){ var st=location.hash.replace('#st=',''); $(window).scrollTop(st); //alert(st); } }; window.onunload=function(){ var hrefs=''; if(location.hash){ hrefs=location.hash.replace(/#st=\d+/,''); } history.replaceState('','',hrefs+'#st='+$(window).scrollTop()); }; ```
        怎么才能让scrollTop设置生效？
        10-30
        我想在页面输出时，设置么某个div元素的scrollTop属性，使它的滚动条停留在中部，但是我设置了<div scrollTop=200></div>,输出时scroll还是在最顶上，在button上
        vue scrollTop失效的问题_weixin_33938733的博客
        11-28
        document.body.scrollTop =0; }); 或者: changeScrollTop() { this.$nextTick((length)=>{ document.documentElement.scrollTop += length ...
        uni-app中scroll-view的scroll-top不生效问题(已解决...
        10-27
        uni-app中scroll-view的scroll-top不生效问题(已解决)项目中有聊天功能,每次收到消息需将滚动条固定在屏幕最底端,让最新的消息展示出来.使用到了scroll-view,但是...
        ©️2020 CSDN 皮肤主题: 大白 设计师:CSDN官方博客 返回首页
        关于我们
        招贤纳士
        广告服务
        开发助手

        400-660-0108

        kefu@csdn.net

        在线客服
        工作时间 8:30-22:00
        公安备案号11010502030143
        京ICP备19004658号
        京网文〔2020〕1039-165号
        经营性网站备案信息
        北京互联网违法和不良信息举报中心
        网络110报警服务
        中国互联网举报中心
        家长监护
        Chrome商店下载
        ©1999-2020北京创新乐知网络技术有限公司
        版权与免责声明
        版权申诉

        王布丁
        码龄5年
        暂无认证
        10
        原创
        38万+
        周排名
        26万+
        总排名
        7万+
        访问

        等级
        354
        积分
        1
        粉丝
        4
        获赞
        9
        评论
        3
        收藏
        私信
        关注

        热门文章
        元素scrollTop设置无效的原因及解决办法  57234
        onClick在Chrome下无效   5655
        Boostrap入门准备之border-box   2477
        node.js与HTML5离线缓存(1)   2029
        js性能优化之函数缓存   623
        分类专栏

        web前端
        6篇

        node.js
        1篇

        遇过的坑
        3篇

        有感
        3篇
        最新评论
        元素scrollTop设置无效的原因及解决办法
        yhon.: 感谢，解决了我的问题
        元素scrollTop设置无效的原因及解决办法
        橙鹿儿 回复 Yilia-Feng: 像极了我哈哈哈哈哈哈哈哈哈哈
        元素scrollTop设置无效的原因及解决办法
        spongejerry: 所以可以先设置为显示调整完后再hide是吗
        onClick在Chrome下无效
        地瓜团子233 回复 linhan2017: 哈哈哈哈，莫名笑出声
        元素scrollTop设置无效的原因及解决办法
        Yilia-Feng: 这破东西搞了我一天半 结果就因为display:none了 呵呵
        最新文章
        举个栗子说明面向过程与面向对象的区别
        js性能优化之函数缓存
        生活态度
        2016年9篇2015年2篇


        举报
    </div>
  <div clas="">
        <p id="AIRFARE" name="AIRFARE" value="12">xiewy</p>
  </div>
  </body>
</html>

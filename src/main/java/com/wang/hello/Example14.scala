package com.wang.hello

import javax.servlet.http.{ // 导入多个类
    HttpServlet,
    HttpServletRequest => HSReq, // 使用别名来缩短导入的类型名称
    HttpServletResponse => HSResp
}
import javax.servlet.http.{HttpServletRequest => HSReq}
import javax.servlet.http.{HttpServletResponse => HSResp}

object Example14 extends HttpServlet {

    /**
     * 可以在Scala代码中直接使用 HTML/XML，但是需要引入支持库，在build.gradle中加入：
     *
     * dependencies {
     *     ...
     *     compile "org.scala-lang.modules:scala-xml_2.11:1.0.3"
     * }
     *
     * 甚至可以直接将变量用于文本
     */
    def message =
        <HTML>
            <HEAD><TITLE>Hello, Scala!</TITLE></HEAD>
            <BODY>Hello, Scala! It's now { currentDate }.</BODY>
        </HTML>
    def currentDate = java.util.Calendar.getInstance().getTime()

    override def doGet(req: HSReq, resp: HSResp) = {
        resp.getWriter().print(message);
    }
}
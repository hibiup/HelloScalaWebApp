package com.wang.hello.Example14

import javax.servlet.http.{ // 导入多个类
    HttpServlet,
    HttpServletRequest => HSReq, // 使用别名来缩短导入的类型名称
    HttpServletResponse => HSResp
}
import javax.servlet.http.{ HttpServletRequest => HSReq }
import javax.servlet.http.{ HttpServletResponse => HSResp }

/**
 * 首页面
 */
class HelloScalaServlet extends HttpServlet {
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
            <HEAD><TITLE>Who are you?</TITLE></HEAD>
            <BODY>
                Who are you? Please answer:
                <FORM action="/scalaExamples/sayMyName" method="POST">
                    Your first name:<INPUT type="text" name="firstName"/>
                    Your last name:<INPUT type="text" name="lastName"/>
                    <INPUT type="submit"/>
                </FORM>
                Current time:{ currentDate }
            </BODY>
        </HTML>
    def currentDate = java.util.Calendar.getInstance().getTime();

    override def doGet(req: HSReq, resp: HSResp) = {
        resp.getWriter().print(message);
    }
}

/**
 * 一个用于接收数据的 Servlet
 */
class NamedHelloScalaServlet extends BaseServlet {
    /**
     * 重载message，并使用来自基类的 param 数据
     */
    override def message =
        if (validate(param)) {
            <HTML>
                <HEAD><TITLE>Hello, { param("firstName") } { param("lastName") }!</TITLE></HEAD>
                <BODY>Hello, { param("firstName") } { param("lastName") }! It is now { currentTime }.</BODY>
            </HTML>
        }
        else {
            <HTML>
                <HEAD><TITLE>Error!</TITLE></HEAD>
                <BODY>How can we be friends if you don't tell me your name?!?</BODY>
            </HTML>
        }

    def currentTime = java.util.Calendar.getInstance().getTime();
    /**
     * 模式匹配
     */
    def validate(p: Map[String, String]): Boolean = {
        p foreach {
            case ("firstName", "") => return false
            case ("lastName", "") => return false
            //case ("lastName", v) => if (v.contains("e")) return false
            case (_, _) => ()
        }
        true
    }
}

/**
 * 基类，将参数存储在一个键值对集合中工子类使用
 */
abstract class BaseServlet extends HttpServlet {
    import scala.collection.mutable.{ Map => MMap }

    def message: scala.xml.Node;

    protected var param: Map[String, String] = Map.empty
    protected var header: Map[String, String] = Map.empty

    override def doPost(req: HSReq, resp: HSResp) = {
        // Extract parameters
        //
        val m = MMap[String, String]()
        val e = req.getParameterNames()
        while (e.hasMoreElements()) {
            val name = e.nextElement().asInstanceOf[String]
            m += (name -> req.getParameter(name))
        }
        param = Map.empty ++ m

        // Repeat for headers (not shown)
        //
        resp.getWriter().print(message)
    }
}
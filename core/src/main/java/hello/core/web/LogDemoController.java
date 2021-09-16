package hello.core.web;

import hello.core.common.MyLogger;
import hello.core.common.MyLoggerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLoggerProxy myLoggerProxy;

    @GetMapping("log-demo")
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURI();

        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");

        logDemoService.log("testId");
        return "OK";
    }

    @GetMapping("log-demo-proxy")
    public String logDemoProxy(HttpServletRequest request) {
        String requestURL = request.getRequestURI();

        System.out.println("myLoggerProxy = " + myLoggerProxy.getClass());
        myLoggerProxy.setRequestURL(requestURL);
        myLoggerProxy.log("testId");

        logDemoService.logProxy("testId");
        return "OK";
    }
}

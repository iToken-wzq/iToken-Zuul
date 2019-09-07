package com.wzq.itoken.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆拦截器 相当于xml配置文件的拦截器
 */
@Component
public class LoginFilter extends ZuulFilter {

    /**
     * 过滤类型
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤的具体业务
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取请求上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取请求参数request
        HttpServletRequest request = currentContext.getRequest();
        //获取请求的参数
        String token = request.getParameter("token");
        //判断是否有请求参数
        if (token==null){
            //设置响应字符编码
            HttpServletResponse response = currentContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            //未带请求参数不予访问
            currentContext.setSendZuulResponse(false);
            //访问错误代码
            currentContext.setResponseStatusCode(401);
            try {
                currentContext.getResponse().getWriter().write("非法访问");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

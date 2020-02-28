package com.dj.ssm.common.utils;

import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Liuyl on 2017/3/28.
 */
public class RsaDecryptRequestParamFilter implements Filter {

    private static Field requestField;

    private static Field parametersParsedField;

    private static Field coyoteRequestField;

    private static Field parametersField;

    private static Field hashTabArrField;

    private List<String> noNeedRsaUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            Class clazz = Class.forName("org.apache.catalina.connector.RequestFacade");
            requestField = clazz.getDeclaredField("request");
            requestField.setAccessible(true);

            parametersParsedField = requestField.getType().getDeclaredField("parametersParsed");
            parametersParsedField.setAccessible(true);


            coyoteRequestField = requestField.getType().getDeclaredField("coyoteRequest");
            coyoteRequestField.setAccessible(true);


            parametersField = coyoteRequestField.getType().getDeclaredField("parameters");
            parametersField.setAccessible(true);


            hashTabArrField = parametersField.getType().getDeclaredField("paramHashValues");
            hashTabArrField.setAccessible(true);

            noNeedRsaUrl = Arrays.asList(replaceBlank(filterConfig.getInitParameter("noNeedRsaUrl")).split(","));

            System.out.println("RsaFilter初始化完成,不需要解密的Post方法为:{}"+ noNeedRsaUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("公共Rsa解密Filter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        servletResponse.setCharacterEncoding("UTF-8");

        String url = ((HttpServletRequest) servletRequest).getServletPath();
        System.out.println("请求Url:{}" + url);
        System.out.println("NoNeedRsaUrl:{}"+noNeedRsaUrl);
        if (!noNeedRsaUrl.contains(url)) {
            System.out.println("Url:{}不在noNeeRsaUrl中,开始解密"+url);
            Object rsaKey = servletRequest.getParameter("data");
            if (req.getMethod().equals(HttpMethod.POST.name())) {
                if (null != rsaKey) {
                    String[] params;
                    try {
                        String paramValue = RSAUtil.decryptRequestParamValue((String) rsaKey);
                        System.out.println("解密完成:params:{}"+ paramValue);
                        params = paramValue.split("&");
                    } catch (Exception e) {
                        e.printStackTrace();
                        servletResponse.getWriter().write("{\"code\":\"403\",\"message\":\"密文错误\"}");
                        return;
                    }
                    Map<String, ArrayList<String>> requestParamtersMap = getRequestMap(servletRequest);
                    for (int i = 0; i < params.length; i++) {
                        String[] param = params[i].split("=");
                        if (param.length == 2){
                            ArrayList list = new ArrayList<>();
                            list.add(URLDecoder.decode(param[1], "UTF-8"));
                            requestParamtersMap.put(param[0], list);
                        }
                    }
                } else {
                    servletResponse.getWriter().write("{\"code\":\"403\",\"message\":\"缺少密文\"}");
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private Map<String, ArrayList<String>> getRequestMap(ServletRequest request) {
        try {
            Object innerRequest = requestField.get(request);
            parametersParsedField.setBoolean(innerRequest, true);
            Object coyoteRequestObject = coyoteRequestField.get(innerRequest);
            Object parameterObject = parametersField.get(coyoteRequestObject);
            return (Map<String, ArrayList<String>>) hashTabArrField.get(parameterObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    public static void main(String[] args) {
    		String aa = "aabbcc";
    		System.out.println(aa.contains("d"));
	}

}

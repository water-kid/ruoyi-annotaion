package com.cj.request;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Date 2023/7/8 9:04
 * @Author cc
 */

/**
 * HttpServletRequest 包装器，， 可以在不修改 HttpServletRequest对象下，，修改请求对象的行为和属性，修改请求参数，修改请求头，修改请求url等，，
 *
 */
public class RepeatableReadRequestWrapper  extends HttpServletRequestWrapper {

    private byte[] bytes;

    public RepeatableReadRequestWrapper(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super(request);

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        bytes = request.getReader().readLine().getBytes();

    }


    @Override
    public BufferedReader getReader() throws IOException {

        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            // 数据的长度
            @Override
            public int available() throws IOException {
                return bytes.length;
            }
        };
        return servletInputStream;
    }
}

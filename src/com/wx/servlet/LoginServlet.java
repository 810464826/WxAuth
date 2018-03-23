package com.wx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.util.AuthUtil;

/**
 * @author ChenXb
 *
 * 2018年3月21日
 */
@SuppressWarnings("serial")
@WebServlet("/wxLogin")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//登录之后的回调地址  必须是外网能访问的
		String backUrl="http%3A%2F%2Fwww.baidu.com%2FWxAuth%2FwxCallBack";
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+AuthUtil.APPID
				+ "&redirect_uri="+backUrl
				+ "&response_type=code"
				+ "&scope=snsapi_userinfo"
				+ "&state=STATE#wechat_redirect";
		System.out.println("访问的链接:>"+url);
		resp.sendRedirect(url);
	}
}

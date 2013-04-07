<%@page import="com.njmd.framework.utils.web.SessionUtils"%>
<%@page import="com.njmd.zfms.web.constants.SessionNameConstants"%>
<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%!/*
	 Color getRandColor(int fc,int bc){//给定范围获得随机颜色
	 Random random = new Random();
	 if(fc>255) fc=255;
	 if(bc>255) bc=255;
	 int r=fc+random.nextInt(bc-fc);
	 int g=fc+random.nextInt(bc-fc);
	 int b=fc+random.nextInt(bc-fc);
	 return new Color(r,g,b);
	 }*/%>
<%
	//设置页面不缓存	out.clear();
	out = pageContext.pushBody(); // 涮新当前out对象,因为 这里 out 与response.getOutputStream()有冲突,抛异常
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	// 在内存中创建图象
	int width = 60, height = 20;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	// 获取图形上下文	Graphics g = image.getGraphics();

	//生成随机类	Random random = new Random();

	// 设定背景色	//g.setColor(getRandColor(200,250));
	g.setColor(new Color(0,62,162));
	g.fillRect(0, 0, width, height);

	//设定字体
	g.setFont(new Font("Fixedsys", Font.BOLD, 14));

	//画边框	//g.setColor(new Color());
	//g.drawRect(0,0,width-1,height-1);

	// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到	/*g.setColor(getRandColor(160,200));
	 for (int i=0;i<155;i++)
	 {
	 int x = random.nextInt(width);
	 int y = random.nextInt(height);
	 int xl = random.nextInt(12);
	 int yl = random.nextInt(12);
	 g.drawLine(x,y,x+xl,y+yl);
	 }*/

	// 取随机产生的认证码(4位数字)
	String sRand = "";
	for (int i = 0; i < 4; i++)
	{
		String rand = String.valueOf(random.nextInt(10));
		sRand += rand;
		// 将认证码显示到图象中
		//g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		g.setColor(new Color(255, 255, 255));
		g.drawString(rand, 13 * i + 6, 16);
	}

	// 将认证码存入SESSION
	SessionUtils.setObjectAttribute(request, SessionNameConstants.IMG_CHECK_CODE, sRand);
	//session.setAttribute(SessionNameConstants.IMG_CHECK_CODE,sRand);
	// 图象生效
	g.dispose();

	// 输出图象到页面	ImageIO.write(image, "JPEG", response.getOutputStream());
%>


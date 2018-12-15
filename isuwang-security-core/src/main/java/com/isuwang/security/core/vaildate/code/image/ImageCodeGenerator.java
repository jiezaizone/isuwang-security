package com.isuwang.security.core.vaildate.code.image;

import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.vaildate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ImageCode generator(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width", securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height", securityProperties.getCode().getImage().getHeight());
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();

        g.setColor(getRandColor(200,250));
        g.fillRect(0,0, width,height);
        g.setFont(new Font("Times New Roman",Font.ITALIC,20));
        g.setColor(getRandColor(160,200));

        for(int i = 0; i< 150 ; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);

            g.drawLine(x,y,x+x1,y+y1);
        }

        //生成随机验证码
        String sRand = "";
        for(int i=0; i< securityProperties.getCode().getImage().getLength(); i++){
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(30 + random.nextInt(110),30 + random.nextInt(110),30 + random.nextInt(110),30 + random.nextInt(110)));
            g.drawString(rand,13 *i + 6,16);
        }

        g.dispose();

        return new ImageCode(image,sRand,securityProperties.getCode().getImage().getExpired());
    }

    /**
     *
     * @param i
     * @param i1
     * @return
     */
    private Color getRandColor(int i, int i1) {
        Random random = new Random();
        if(i>255) i=255;
        if(i1>255) i1=255;
        int r=i+random.nextInt(i1-i);
        int g=i+random.nextInt(i1-i);
        int b=i+random.nextInt(i1-i);
        return new Color(r,g,b);
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}

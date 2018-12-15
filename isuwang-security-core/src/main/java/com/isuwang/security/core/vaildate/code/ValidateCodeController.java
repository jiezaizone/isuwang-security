package com.isuwang.security.core.vaildate.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String SESSION_KEY = "SESSION_IMAGE_KEY";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode(request);
        logger.info("imageCode:" + imageCode.getCode());
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    private ImageCode createImageCode(HttpServletRequest request) {
        int width = 67;
        int height = 23;
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

        String sRand = "";
        for(int i=0;i<4;i++){
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(30 + random.nextInt(110),30 + random.nextInt(110),30 + random.nextInt(110),30 + random.nextInt(110)));
            g.drawString(rand,13 *i + 6,16);
        }

        g.dispose();

        return new ImageCode(image,sRand,60);
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
}

package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;

@Controller
@CrossOrigin("*")
public class test {
    @Autowired
    private Vision vision;


   

    @PostMapping(value="/apiTest")
    @ResponseBody
    public Map<String,Float>  getMethodName(@RequestBody String num) throws Exception {
        String data1 = num.split(",")[1];

		// byte[] imageBytes = DatatypeConverter.parseBase64Binary(data1);
        byte[] imageBytes = Base64.getDecoder().decode(data1);
 		try {

			BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));

			ImageIO.write(bufImg, "png", new File("./newfile"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

        return vision.visionapi();
    }
    

}
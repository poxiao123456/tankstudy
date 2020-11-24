package com.poxiao.tank;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author qinqi
 * @date 2020/11/19
 */
public class TestImage {

    @Test
    public void testImageLoad() throws IOException {
        String imagePath = "C:\\Users\\25311\\Desktop\\screen.png";
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        Assert.assertNotNull(bufferedImage);
    }
}

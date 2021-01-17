package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class ImageDownloader {
    private static final Logger LOGGER = LogManager.getLogger(ImageDownloader.class);

    public static void run(final String imageName, final String imageDir, final String imageUrl) {
        BufferedImage image = null;
        String imageAddr = null;
        String imageFullname = "";
        try{
            URL url =new URL(imageUrl);
            imageFullname = imageName + ".jpg";
            image = ImageIO.read(url);
            imageAddr = imageDir + imageFullname;
            ImageIO.write(image, "jpg", new File(imageAddr));
            // check whether it was downloaded!
        } catch(IOException e){
            LOGGER.error("Error running of ImageDownloader:" + imageUrl, e);
        }
    }
    
    public static void resizeImageScalrZalando(final String imageName, final String imageDir, final String imageUrl, int width) {
        
        BufferedImage resizeMe = null;
        String imageFullnameResized = imageName + ".jpg";
        String imageAddrResized = imageDir + imageFullnameResized;
        try {
            URL url = new URL(imageUrl);
            resizeMe = ImageIO.read(url);
            //resizeMe = ImageIO.read(new File(imageAddrResized));
        } catch (IOException e) {
            
        }
        //Dimension newMaxSize = new Dimension(width, height);
        if (resizeMe == null) {
            LOGGER.error("Error image is not downloaded:" + imageUrl);
        } else {
            BufferedImage resizedImg = Scalr.resize(resizeMe, Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, width);
            //BufferedImage resizedImgPadding = Scalr.pad(resizedImg, 50, Color.WHITE);
            //BufferedImage rectangleImg = resizeImage(resizedImgPadding, 500, 500);
            try {
                ImageIO.write(resizedImg, "jpg", new File(imageAddrResized));
            } catch (IOException e) {
                LOGGER.error("Error resizing of ImageDownloader:" + imageUrl);
            }
        }
    }
    
    public static void runWithResizingFile(final String imageName, final String imageDir, int width, int height) {
        BufferedImage resizeMe = null;
        String imageFullnameResized = imageName + ".jpg";
        String imageAddrResized = imageDir + imageFullnameResized;
        try {
            resizeMe = ImageIO.read(new File(imageAddrResized));
        } catch (IOException e) {
        }
        Dimension newMaxSize = new Dimension(width, height);
        if (resizeMe == null) {
            LOGGER.error("Error image can not read:" + imageAddrResized);
        } else {
            BufferedImage resizedImg = Scalr.resize(resizeMe, Method.QUALITY,
                    newMaxSize.width, newMaxSize.height);
            try {
                ImageIO.write(resizedImg, "jpg", new File(imageAddrResized));
            } catch (IOException e) {
                LOGGER.error("Error resizing of ImageDownloader:" + imageAddrResized);
            }
        }
        
        BufferedImage bufferedImage = null;
        try{
            bufferedImage = ImageIO.read(new File(imageAddrResized));
            
            int bufferedImageWidth = bufferedImage.getWidth();
            int bufferedImageWidthHalf = bufferedImageWidth/2;
            int filledWithHalf = 250 - bufferedImageWidthHalf; 
            
            //control width of image
            BufferedImage newBufferedImageValidWidth = null;
            if(bufferedImageWidth < 500) {
                newBufferedImageValidWidth = new BufferedImage(500, bufferedImage.getHeight(), bufferedImage.getType());

                Graphics g = newBufferedImageValidWidth.getGraphics();

                g.setColor(Color.white);
                g.fillRect(0, 0, 500, bufferedImage.getHeight());
                g.drawImage(bufferedImage, filledWithHalf, 0, null);
                g.dispose();
            } else {
                newBufferedImageValidWidth = bufferedImage;
            }
            
            //control height of image
            BufferedImage newBufferedValidImage = null;
            int bufferedImageHeight = newBufferedImageValidWidth.getHeight();
            int bufferedImageHeightHalf = bufferedImageHeight/2;
            int filledHeightHalf = 250 - bufferedImageHeightHalf; 
            if(bufferedImageHeight < 500) {
                newBufferedValidImage = new BufferedImage(newBufferedImageValidWidth.getWidth(),500, newBufferedImageValidWidth.getType());

                Graphics g = newBufferedValidImage.getGraphics();

                g.setColor(Color.white);
                g.fillRect(0, 0, newBufferedImageValidWidth.getWidth(), 500);
                g.drawImage(newBufferedImageValidWidth, 0, filledHeightHalf, null);
                g.dispose();
            } else {
                newBufferedValidImage = newBufferedImageValidWidth;
            }
            
            //padding image
            BufferedImage newBufferedValidImagePadding = Scalr.pad(newBufferedValidImage, 50, Color.WHITE);
            
            ImageIO.write(newBufferedValidImagePadding, "jpg", new File(imageAddrResized));
        } catch(IOException e){
            LOGGER.error("Error running of ImageDownloader:" + imageFullnameResized + "/" +  imageAddrResized, e);
        }
    }
    
    public static void runWithResizing(final String imageName, final String imageDir, final String imageUrl, int width, int height) {
        BufferedImage resizeMe = null;
        String imageFullnameResized = imageName + ".jpg";
        String imageAddrResized = imageDir + imageFullnameResized;
        try {
            URL url = new URL(imageUrl);
            resizeMe = ImageIO.read(url);
        } catch (IOException e) {
        }
        Dimension newMaxSize = new Dimension(width, height);
        if (resizeMe == null) {
            LOGGER.error("Error image is not downloaded:" + imageUrl);
        } else {
            BufferedImage resizedImg = Scalr.resize(resizeMe, Method.QUALITY,
                    newMaxSize.width, newMaxSize.height);
            try {
                ImageIO.write(resizedImg, "jpg", new File(imageAddrResized));
            } catch (IOException e) {
                LOGGER.error("Error resizing of ImageDownloader:" + imageUrl);
            }
        }
        
        BufferedImage bufferedImage = null;
        try{
            bufferedImage = ImageIO.read(new File(imageAddrResized));
            
            int bufferedImageWidth = bufferedImage.getWidth();
            int bufferedImageWidthHalf = bufferedImageWidth/2;
            int filledWithHalf = 250 - bufferedImageWidthHalf; 
            
            //control width of image
            BufferedImage newBufferedImageValidWidth = null;
            if(bufferedImageWidth < 500) {
                newBufferedImageValidWidth = new BufferedImage(500, bufferedImage.getHeight(), bufferedImage.getType());

                Graphics g = newBufferedImageValidWidth.getGraphics();

                g.setColor(Color.white);
                g.fillRect(0, 0, 500, bufferedImage.getHeight());
                g.drawImage(bufferedImage, filledWithHalf, 0, null);
                g.dispose();
            } else {
                newBufferedImageValidWidth = bufferedImage;
            }
            
            //control height of image
            BufferedImage newBufferedValidImage = null;
            int bufferedImageHeight = newBufferedImageValidWidth.getHeight();
            int bufferedImageHeightHalf = bufferedImageHeight/2;
            int filledHeightHalf = 250 - bufferedImageHeightHalf; 
            if(bufferedImageHeight < 500) {
                newBufferedValidImage = new BufferedImage(newBufferedImageValidWidth.getWidth(),500, newBufferedImageValidWidth.getType());

                Graphics g = newBufferedValidImage.getGraphics();

                g.setColor(Color.white);
                g.fillRect(0, 0, newBufferedImageValidWidth.getWidth(), 500);
                g.drawImage(newBufferedImageValidWidth, 0, filledHeightHalf, null);
                g.dispose();
            } else {
                newBufferedValidImage = newBufferedImageValidWidth;
            }
            
            //padding image
            BufferedImage newBufferedValidImagePadding = Scalr.pad(newBufferedValidImage, 50, Color.WHITE);
            
            ImageIO.write(newBufferedValidImagePadding, "jpg", new File(imageAddrResized));
        } catch(IOException e){
            LOGGER.error("Error running of ImageDownloader:" + imageFullnameResized + "/" +  imageUrl, e);
        }
    }
    
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    
    public static void resizeImageScalr(final String imageName, final String imageDir, final String imageUrl, int width, int height) {
        
        BufferedImage resizeMe = null;
        String imageFullnameResized = imageName + ".jpg";
        String imageAddrResized = imageDir + imageFullnameResized;
        try {
            //URL url = new URL(imageUrl);
            //resizeMe = ImageIO.read(url);
            resizeMe = ImageIO.read(new File(imageAddrResized));
        } catch (IOException e) {
        }
        Dimension newMaxSize = new Dimension(width, height);
        if (resizeMe == null) {
            LOGGER.error("Error image is not downloaded:" + imageUrl);
        } else {
            BufferedImage resizedImg = Scalr.resize(resizeMe, Method.QUALITY,
                    newMaxSize.width, newMaxSize.height);
            //BufferedImage resizedImgPadding = Scalr.pad(resizedImg, 50, Color.WHITE);
            //BufferedImage rectangleImg = resizeImage(resizedImgPadding, 500, 500);
            try {
                ImageIO.write(resizedImg, "jpg", new File(imageAddrResized));
            } catch (IOException e) {
                LOGGER.error("Error resizing of ImageDownloader:" + imageUrl);
            }
        }
    }
}

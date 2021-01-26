package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class ImageTransformer {
    private static final Logger LOGGER = LogManager.getLogger(ImageDownloader.class);
    
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
            BufferedImage newBufferedValidImagePadding = Scalr.pad(newBufferedValidImage, 10, Color.WHITE);
            
            ImageIO.write(newBufferedValidImagePadding, "jpg", new File(imageAddrResized));
        } catch(IOException e){
            LOGGER.error("Error running of ImageDownloader:" + imageFullnameResized + "/" +  imageAddrResized, e);
        }
    }
}

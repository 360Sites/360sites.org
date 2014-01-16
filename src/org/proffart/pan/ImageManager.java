package org.proffart.pan;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

/*
 * Author Aram
 * This class provides static methods for working with images
 * now only for resizing 
 */
public class ImageManager {
	private static  String imagePath;
	private static  String resizedImagePath;
	private static int resizedImageWidth ;
	public static void setImagePath (String path)
	{
		imagePath = path;
	}
	public static void setResizedImagePath (String path)
	{
		resizedImagePath = path;
	}
	public static void setResizedImageWidth (int width)
	{
		resizedImageWidth = width;
	}
	public static void resizeImage ()
	{
		try
		{
			BufferedImage img = ImageIO.read(new File(imagePath));
			   
			BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, resizedImageWidth);
			   
			File destFile = new File(resizedImagePath);
			   
			ImageIO.write(scaledImg, "jpg", destFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}


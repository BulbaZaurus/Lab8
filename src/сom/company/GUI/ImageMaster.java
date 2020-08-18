package сom.company.GUI;

import java.io.*;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Отвечает за загрузку изображений
 */
public class ImageMaster {
    public static Font font = new Font("Courier New", Font.BOLD, 17);
    public static Font fontS = new Font("Segoe print", Font.BOLD, 14);
    public static Color darkestGray = new Color(30,30,30);
    public static Color darkRed = new Color(90,0,0);
    public static Color lightRed = new Color(255,99,71);
    public static Color green = new Color(0,255,52);
    public static Color lightGreen = new Color(100,255,100);
    public static Color darkGreen = new Color(0,76,2);
    public static Color orange = new Color(255,102,0);
    public static Color violet = new Color(48,0,56);
    public static BufferedImage bgN;
    public static BufferedImage B;
    public static BufferedImage P;
    public static BufferedImage horr;
    public static BufferedImage gus;
    public static BufferedImage out;
    public static BufferedImage nice;
    public static BufferedImage good;
    public static BufferedImage cool;

    public static ImageIcon Iconize(BufferedImage img){
        return new ImageIcon(img);
    }

    public static ImageIcon[] Iconize(BufferedImage[] seq){
        ImageIcon[] res = new ImageIcon[seq.length];
        for(int i = 0; i < seq.length; i++){
            res[i] = Iconize(seq[i]);
        }
        return res; 
    }

    public static BufferedImage loadImage(String path){
        // JPanel c = new JPanel();
        // MediaTracker tr = new MediaTracker(c);
        BufferedImage img;
        try{
            img = ImageIO.read(new File(path));
        }catch(IOException e){
            return null;
        }
        // try{
        //     tr.waitForAll();
        // }catch(InterruptedException e1){}
        return img;
    }

    public static BufferedImage[] loadSequence(String path, int last){
        BufferedImage[] seq = new BufferedImage[last];
        for(int i = 1; i <= last; i++)
            seq[i-1] = loadImage(path+String.format("%04d", i)+".png");
        return seq;
    }

    // public static ImageIcon scaleIcon(ImageIcon icon, int w, int h){
    //     return new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    // }

    public static BufferedImage scaleImage(BufferedImage img, int w, int h){
        BufferedImage res = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g = res.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return res;
    }

    // public static ImageIcon[] scaleSequence(ImageIcon[] seq, int w, int h){
    //     ImageIcon[] res = new ImageIcon[seq.length]; 
    //     for(int i = 0; i < seq.length; i++)
    //         res[i] = scaleIcon(seq[i], w, h);
    //     return res;
    // }

    public static BufferedImage[] scaleSequence(BufferedImage[] seq, int w, int h){
        BufferedImage[] res = new BufferedImage[seq.length]; 
        for(int i = 0; i < seq.length; i++)
            res[i] = scaleImage(seq[i], w, h);
        return res;
    }
    
    public static BufferedImage recolorImage(BufferedImage img, Color col){
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = res.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 255));
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.dispose();
        return res;
    }

    public static BufferedImage recolorImageLight(BufferedImage img, Color col){
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = res.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 128));
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.dispose();
        return res;
    }

    public static BufferedImage[] recolorSequence(BufferedImage[] seq, Color col){
        BufferedImage[] res = new BufferedImage[seq.length]; 
        for(int i = 0; i < seq.length; i++)
            res[i] = recolorImage(seq[i], col);
        return res;
    }

    public static BufferedImage[] recolorSequenceLight(BufferedImage[] seq, Color col){
        BufferedImage[] res = new BufferedImage[seq.length]; 
        for(int i = 0; i < seq.length; i++)
            res[i] = recolorImageLight(seq[i], col);
        return res;
    }

    public static void loadResources(){
        try {

            bgN = loadImage("Images/N/nightm.png");
            B = loadImage("Images/N/blane.png");
            P = loadImage("Images/N/P.png");
            horr = loadImage("Images/N/horr.png");
            gus = loadImage("Images/M/gus.png");
            out = loadImage("Images/M/out.png");
            nice = loadImage("Images/M/nice.jpg");
            good = scaleImage(loadImage("Images/M/good.jpg"),1000,400);
            cool = scaleImage(loadImage("Images/good_job.png"), 450, 150);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
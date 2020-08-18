package сom.company.GUI;

import static сom.company.GUI.ImageMaster.*;
import static сom.company.GUI.MainForm.getNightMare;
import сom.company.Collection.Ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
/**
 * Класс,который отвечает за визуализацию элементов
 */
public class VisualTicket extends JButton {
    public static BufferedImage idle;
    public static BufferedImage roll;
    public static BufferedImage press;
    public static BufferedImage[] appear;
    public static BufferedImage[] dissolve;
    
    public static BufferedImage idleN;
    public static BufferedImage rollN;
    public static BufferedImage pressN;
    public static BufferedImage[] appearN;
    public static BufferedImage[] dissolveN;

    private Ticket origin;
    public Ticket getOrigin() {return origin;}
    private String user;

    private Color color;
    public Color getColor(){return this.color;}
    public static HashMap<String, Color> colorMap = new HashMap<>();

    private void defineColor() {
        Color col = colorMap.get(user);
        if(col==null){
            int r = (int) (Math.random()*256);
            int g = (int) (Math.random()*256);
            int b = (int) (Math.random()*256);
            col = new Color(r, g, b);
            colorMap.put(user, col);
        }
        this.color = col;
    }

    public VisualTicket(){}

    public VisualTicket(Float size, Ticket orig){
        this.origin = orig;
        this.user = orig.getUser().getName();
        defineColor();
        int intSize = (int)(float) size;
        setPreferredSize(new Dimension(intSize, intSize));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        opaque();
    }

    private void opaque(){
        setIcon(null);
        setRolloverIcon(null);
        setPressedIcon(null);
    }

    public void reveal(){
        int w = (int) this.getPreferredSize().getWidth();
        int h = (int) this.getPreferredSize().getHeight();
        BufferedImage i = getNightMare() ? idleN : idle;
        BufferedImage r = getNightMare() ? rollN : roll;
        BufferedImage p = getNightMare() ? pressN : press;
        if(getNightMare())
            setIcon(Iconize(recolorImageLight(scaleImage(i, w, h), color)));
        else
            setIcon(Iconize(recolorImage(scaleImage(i, w, h), color)));
        setRolloverIcon(Iconize(scaleImage(r, w, h)));
        setPressedIcon(Iconize(scaleImage(p, w, h)));
    }

    public static void loadResources(){

        idle = loadImage("Images/T/2tickets.png");
        roll = loadImage("Images/T/2tickets_hovW.png");
        press = loadImage("Images/T/2tickets_pressR.png");
        appear = loadSequence("Images/A/A", 30);
        dissolve = loadSequence("Images/D/D", 30);

        idleN = loadImage("Images/N/P_wb.png");
        rollN = loadImage("Images/N/P_hover.png");
        pressN = loadImage("Images/N/P_press.png");
        appearN = loadSequence("Images/AN/A", 30);
        dissolveN = loadSequence("Images/DN/D", 30);
    }
}
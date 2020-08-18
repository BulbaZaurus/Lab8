package сom.company.GUI;

import javax.swing.*;
//import javax.swing.AbstractButton.*;

import сom.company.Collection.Ticket;
import static сom.company.GUI.ImageMaster.*;

import java.awt.*;
import java.awt.event.*;


public class CanvasPanel extends JPanel{
    private MainForm source;
    public DragLayout canvasLM = new DragLayout();

    public CanvasPanel(MainForm frm){
        this.source = frm;
        addCross();
        setLayout(canvasLM);
        setOpaque(false);
    }

    private void addCross(){
        JPanel crossV = new JPanel();
        JPanel crossH = new JPanel();
        crossH.setBackground(Color.red);
        crossV.setBackground(Color.red);
        crossV.setLocation(1+canvasLM.getLeftOffset(), 1+canvasLM.getTopOffset());
        crossV.setPreferredSize(new Dimension(1, 50));
        crossH.setLocation(1+canvasLM.getLeftOffset(), 1+canvasLM.getTopOffset());
        crossH.setPreferredSize(new Dimension(50, 1));
        add(crossH);
        add(crossV);
    }

    private double canvasScale = 10;
    public double getScale(){return canvasScale;}
    
    public void addVisual(Ticket orig){
        float x = orig.getCoordinates().getX(); 
        Double y = orig.getCoordinates().getY();
        Float size = orig.getPrice();
        double newSize = size*canvasScale/2;
        int intSize = (int)newSize;
        int newX = (int) ((x-size/4)*canvasScale) + canvasLM.getLeftOffset();
        int newY = (int)(double) ((y-size/4)*canvasScale) + canvasLM.getTopOffset();

        VisualTicket vt = new VisualTicket((float) newSize, orig);
        vt.setLocation(newX, newY);
        JLabel holder = new JLabel();
        holder.setLocation(newX, newY);
        holder.setPreferredSize(new Dimension(intSize, intSize));
        AnimatedIcon appear = new AnimatedIcon(holder, 40, 1, 
            Iconize(scaleSequence(MainForm.getNightMare() ? VisualTicket.appearN : VisualTicket.appear, intSize, intSize)));
        appear.setShowFirstIcon(false);
        holder.setIcon(appear);
        appear.start();
        add(holder);
        add(vt);
        vt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, vt.getOrigin().toString(), "Ticket info", -1);
            }
        });

        ActionListener remover = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(holder);
                vt.reveal();
                repaint();
            }
        };
        new Timer(40*VisualTicket.appear.length, remover).start();
        source.revalidate();
    }

    public void removeVisual(VisualTicket vt){
        int w = (int) vt.getPreferredSize().getWidth();
        int h = (int) vt.getPreferredSize().getHeight();
        JLabel holder = new JLabel();
        holder.setLocation(vt.getLocation());
        holder.setPreferredSize(new Dimension(w, h));
        AnimatedIcon dissolve = new AnimatedIcon(holder, 40, 1, 
            Iconize(MainForm.getNightMare() ? 
            recolorSequenceLight(scaleSequence(VisualTicket.dissolveN, w, h), vt.getColor()) : 
            recolorSequence(scaleSequence(VisualTicket.dissolve, w, h), vt.getColor())));
        dissolve.setShowFirstIcon(false);
        holder.setIcon(dissolve);
        add(holder);
        source.revalidate();
        dissolve.start();
        remove(vt);
        ActionListener remover = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(holder);
                repaint();
                source.revalidate();
            }
        };
        new Timer(40*VisualTicket.dissolve.length, remover).start();
    }

    public void blank(){
        VisualTicket vt = new VisualTicket();
        for(Component comp : getComponents()) {
            if(comp.getClass()==vt.getClass())
                removeVisual((VisualTicket)comp);
        }
        source.revalidate();
    }
    
    // public void blankSilent(){
    //     removeAll();
    //     addCross();
    // }


}
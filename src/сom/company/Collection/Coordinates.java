package сom.company.Collection;

import java.io.Serializable;

/**
 * Класс,отвечающий за координаты
 */
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 106L;
    private float x;
    private Double y; //Значение поля должно быть больше -784, Поле не может быть null

    public Coordinates(float x,double y){
        this.x=x;
        if(y<=-748){
            System.out.println("Неправильное значение координаты Y \n" +
                    "Выставленно минимальное значение \n" +
                    "Y=-747");
            this.y=-747.0;
        }else
            this.y=y;
    }



    public Double getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }
}

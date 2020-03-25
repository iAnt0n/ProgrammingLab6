package collection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x; //Значение поля должно быть больше -773, Поле не может быть null
    private Double y; //Максимальное значение поля: 644, Поле не может быть null
    private static final long serialVersionUID=2908L;

    public Coordinates(Integer x, Double y){
        this.x = x;
        this.y = y;
    }

    public Double getY() {
        return y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

}

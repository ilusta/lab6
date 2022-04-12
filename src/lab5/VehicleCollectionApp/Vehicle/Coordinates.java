package lab5.VehicleCollectionApp.Vehicle;

import lab5.VehicleCollectionApp.Exceptions.NullException;

public class Coordinates implements Comparable<Coordinates>
{
    private Integer x, y;

    public void setXCoordinate(Integer x) throws NullException {
        if (x == null) throw new NullException("X coordinate can not be NULL");
        this.x = x;
    }

    public void setYCoordinate(Integer y) {
        this.y = y;
    }

    public Integer getXCoordinate() {
        return this.x;
    }

    public Integer getYCoordinate() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + this.x +
                ", y=" + this.y + "};";
    }

    @Override
    public int compareTo(Coordinates O) {
        Integer ty = getYCoordinate();
        if(ty == null) ty = 0;
        Integer oy = O.getYCoordinate();
        if(oy == null) oy = 0;
        return ty.compareTo(oy) + getXCoordinate().compareTo(O.getXCoordinate());
    }
}
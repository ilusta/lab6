package lab5.VehicleCollectionApp.Vehicle;

import lab5.VehicleCollectionApp.Exceptions.NullException;

public class Coordinates
{
    private Integer x;
    private int y;

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

    public int getYCoordinate() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + this.x +
                ", y=" + this.y + "};";
    }
}
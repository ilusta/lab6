package lab5.VehicleCollectionApp.Vehicle;

public enum VehicleType
{
    HELICOPTER,
    DRONE,
    BOAT,
    BICYCLE,
    HOVERBOARD;

    public static String convertToString() {
        return VehicleType.HELICOPTER.toString() + ", " +
                VehicleType.DRONE.toString() + ", " +
                VehicleType.BOAT.toString() + ", " +
                VehicleType.BICYCLE.toString() + ", " +
                VehicleType.HOVERBOARD.toString();
    }
}
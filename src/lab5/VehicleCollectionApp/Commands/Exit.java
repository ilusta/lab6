package lab5.VehicleCollectionApp.Commands;


public class Exit extends Command
{
    private static boolean runFlag = true;

    public Exit() {
        Exit.runFlag = true;
    }

    public static boolean getRunFlag() {
        return Exit.runFlag;
    }

    @Override
    public void execute(String[] params){
        Exit.runFlag = false;
    }

    @Override
    public String getHelp() {
        return "Exits app.";
    }
}
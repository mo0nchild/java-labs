package labs.helper.types;

public class LabTaskTest implements LabTaskRunnable {
    @Override
    public void runTask() throws LabTaskException {
        System.out.println("Hello");
    }
}

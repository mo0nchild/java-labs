import labs.helper.types.LabTaskRunnable;
import labs.helper.types.LabTaskTest;

module java.labs {
    exports labs.helper.types;
    provides LabTaskRunnable with LabTaskTest;
}
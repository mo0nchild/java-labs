package lab.solution7.models;

import java.io.Serializable;
import java.util.List;

public interface LabTaskTestable extends Serializable {
    public abstract List<? super Integer> getValuesList();
    public abstract Integer getResultValue();
}

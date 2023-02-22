package lab.solution5;

public enum TaskType {
    CHESS_TASK(0), SQUARE_TASK(1);

    private final int value;
    TaskType(int value) { this.value = value; }

    public int getValue() { return this.value; }
}

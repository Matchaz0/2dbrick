public class Brick {
    private int start;
    private int end;
    private int height;
    private int tempHeight;
    private boolean active;

    public Brick(int start, int end) {
        this.start = start;
        this.end = end;
        this.height = 0;
        this.tempHeight = 0;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String toString() {
        return start + "," + end + " --> Height: " + height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getTempHeight() {
        return tempHeight;
    }

    public void setTempHeight(int height) {
        this.tempHeight = height;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFinished() {
        return (height <= tempHeight);
    }

    public void setActive() {
        if (!isActive()) {
            active = !active;
        }
    }
}
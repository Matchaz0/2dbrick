public class Brick {
    // the range of the brick
    private int start;
    private int end;
    // this will be found
    private int height;
    // temp height always starts at 0 since it drops from the top
    private int tempHeight;
    // is the block spawned in?
    private boolean active;

    public Brick(int start, int end) {
        this.start = start;
        this.end = end;
        this.height = 0;
        this.tempHeight = 0;
        this.active = false;
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

    // is the block spawned in?
    public boolean isActive() {
        return active;
    }
    public void setActive() {
        active = true;

    }
    // is the block in it's final destination
    public boolean isFinished() {
        return (height <= tempHeight);
    }


}
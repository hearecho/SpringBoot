package com.echo.webdemo.test;

/**
 * 记录细胞状态
 * 1.死 0
 * 2.生 1
 * 3.下一代的生死  默认值为 -1
 * 4.细胞所处位置  1. 7 代表 左上角 ； 8 代表上边界 9代表 右上角 4左边界 5中间 6有边界 1左下角  2下边界 3右下角
 */
public class CellStatus {
    private int currentStatus;
    private int nextStatus=-1;
    private int location=0;

    /**
     * 判断当代细胞生死
     * @return
     */
    public boolean isCurrentDead() {
        return currentStatus==0;
    }

    /**
     * 判断下一代这个细胞是生是死
     * @return
     */
    public boolean isNextDead() {
        return nextStatus==0;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurretnStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(int nextStatus) {
        this.nextStatus = nextStatus;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
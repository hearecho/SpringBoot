package com.echo.webdemo.test;

import java.util.Random;

/**
 * @author echo
 *
 * 1. 不计算边界
 * 2.
 */
public class LifeGame {

    private CellStatus[][] cell;

    private int CellCount = 0;

    public LifeGame() {

    }
    public LifeGame(int row,int col) {
        this.cell = new CellStatus[row][col];
    }

    /**
     * 随机初始化
     */
    public void InitMap() {
        for (int i=0;i<this.cell.length;i++) {
            for (int j=0;j<this.cell[i].length;j++) {
                this.cell[i][j] = new CellStatus();
                Random random = new Random();
                this.cell[i][j].setCurretnStatus(Math.abs(random.nextInt()%2));
                if (this.cell[i][j].getCurrentStatus()==1) {
                    CellCount++;
                }
                judgePos(i,j);
            }
        }
    }

    /**
     * 传入生死位置数组
     * @param cells
     * @param row
     * @param col
     */
    public void InitMap(int [][] cells,int row,int col){
        for (int i=0;i<row;i++) {
            for (int j=0;j<col;j++) {
                this.cell[i][j] = new CellStatus();
                this.cell[i][j].setCurretnStatus(cells[i][j]);
                if (this.cell[i][j].getCurrentStatus()==1) {
                    CellCount++;
                }
                judgePos(i,j);
            }
        }
    }


    public void NextCellMap() {
        judgeCurrentStatus();
        for (int i=0;i<this.cell.length;i++) {
            for (int j=0;j<this.cell[i].length;j++) {
                if(this.cell[i][j].getNextStatus()!=-1){
                    this.cell[i][j].setCurretnStatus(this.cell[i][j].getNextStatus());
                }
                this.cell[i][j].setNextStatus(-1);
            }
        }
    }

    /**
     * 获取当代某个位置上细胞生死地图
     * @return
     */
    public int[][] getCellCurrentStatus() {
        int row = this.cell.length;
        int col = this.cell[row-1].length;
        int[][] cells = new int[row][col];
        for (int i=0;i<row;i++) {
            for (int j=0;j<col;j++) {
               cells[i][j] = this.cell[i][j].getCurrentStatus();
            }
        }
        return cells;
    }

    /**
     * 判断该细胞所处的位置,有九种位置，边界八种 ，中间一种
     * 四个角落
     *
     * 7 代表 左上角 ； 8 代表上边界 9代表 右上角 4左边界 5中间 6有边界 1左下角  2下边界 3右下角
     * @param row
     * @param col
     * @return
     */

    private void judgePos(int row,int col) {
        if(row==0&&col==0) {
            //左上角
            this.cell[row][col].setLocation(7);
        }else if (row==0&&col==this.cell[0].length-1){
            //右上角
            this.cell[row][col].setLocation(9);
        }else if (col==0&&row==this.cell.length-1) {
            //左下角
            this.cell[row][col].setLocation(1);
        }else if(row==this.cell.length-1&&col==this.cell[0].length-1) {
            //右下角
            this.cell[row][col].setLocation(3);
        }else if (row==0||col==0||row==this.cell.length-1||col==this.cell[0].length-1) {
            //上边界
            if(row==0) {
                this.cell[row][col].setLocation(8);
            }else if(col == 0) {
                //左边界
                this.cell[row][col].setLocation(4);
            } else if(row==this.cell.length-1) {
                //下边界
                this.cell[row][col].setLocation(2);
            } else if (col==this.cell[0].length-1) {
                //有边界
                this.cell[row][col].setLocation(6);
            }
        }else {
            this.cell[row][col].setLocation(5);
        }
    }

    /**
     *
     * @param row 目标细胞所在的行
     * @param col 目标细胞所在的列
     * @return
     */
    private int judgeAroundStatus(int row,int col) {
        int liveNum = 0;

        switch (this.cell[row][col].getLocation()) {
            case 5:
                for(int i=row-1;i<=row+1;i++) {
                    for (int j= col-1;j<=col+1;j++) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                } break;
            case 7:
                for (int i=row;i<=row+1;i++) {
                    for (int j=col;j<=col+1;j++) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                }break;

            case 9:
                for (int i=row;i<=row+1;i++) {
                    for (int j=col;j>=col-1;j--) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                }break;
            case 1:
                for (int i=row;i>=row-1;i--) {
                    for (int j=col;j<=col+1;j++) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                }break;
            case 3:
                for (int i=row;i>=row-1;i--) {
                    for (int j=col;j>=col-1;j--) {
                        if(i==row&&j==col)
                            continue;
                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                }break;
            case 4:
                for(int i=row-1;i<=row+1;i++) {
                    for (int j= col;j<=col+1;j++) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                } break;
            case 8:
                for(int i=row;i<=row+1;i++) {
                    for (int j= col-1;j<=col+1;j++) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                } break;
            case 6:
                for(int i=row-1;i<=row+1;i++) {
                    for (int j= col-1;j<=col;j++) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                } break;
            case 2:
                for(int i=row-1;i<=row;i++) {
                    for (int j= col-1;j<=col+1;j++) {
                        if(i==row&&j==col)
                            continue;

                        if(!this.cell[i][j].isCurrentDead()) {
                            liveNum++;
                        }
                    }
                } break;
            default:break;
        }

        return liveNum;
    }

    /**
     * 对下一代细胞生死进行判断
     */
    private void judgeCurrentStatus() {
        for (int i=0;i<this.cell.length;i++) {
            boolean flag = false;
            for (int j=0;j<this.cell[i].length;j++) {

                int aroundLiveNum = judgeAroundStatus(i,j);

                if(this.cell[i][j].isCurrentDead()&&aroundLiveNum==3) {
                    this.cell[i][j].setNextStatus(1);
                } else if(!this.cell[i][j].isCurrentDead()&&(aroundLiveNum==3||aroundLiveNum==2)) {
                    this.cell[i][j].setNextStatus(1);
                }else {
                    this.cell[i][j].setNextStatus(0);
                }
            }
        }
    }

    /**
     * 获取活细胞数目
     */
    public int getCellCount() {
        return CellCount;
    }

    public void setCellCount(int cellCount) {
        CellCount = cellCount;
    }

    public CellStatus[][] getCell() {
        return cell;
    }

    public void setCell(CellStatus[][] cell) {
        this.cell = cell;
    }
}


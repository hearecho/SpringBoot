package com.echo.webdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.webdemo.test.CellStatus;
import com.echo.webdemo.test.LifeGame;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LifeGameController {

    @GetMapping("/lifegame/init")
    @ResponseBody
    public String Init(@RequestParam(value = "row",required = true) int row,
                       @RequestParam(value = "col",required = true) int col) {
        LifeGame lifeGame = new LifeGame(row,col);
        lifeGame.InitMap();
        int[][] cells;
        int CellCount = lifeGame.getCellCount();
        cells = lifeGame.getCellCurrentStatus();

        return JSON.toJSONString(cells)+"*"+CellCount;
    }

    @PostMapping("/lifegame/generate")
    @ResponseBody
    public String Generate(@RequestParam(value = "data") String data,
                           @RequestParam(value = "row") int row,
                           @RequestParam(value = "col")int col) {
        int [][] cells =new int[row][col];
        JSONArray jsonArray = JSONArray.parseArray(data);
        for(int i=0;i<jsonArray.size();i++){
            JSONArray jsonArray1 = jsonArray.getJSONArray(i);
            for (int j=0;j<jsonArray1.size();j++) {
                cells[i][j] = jsonArray1.getIntValue(j);
            }
        }
        LifeGame lifeGame = new LifeGame(row,col);
        lifeGame.InitMap(cells,row,col);
        lifeGame.NextCellMap();
        int CellCount = lifeGame.getCellCount();
        cells = lifeGame.getCellCurrentStatus();
        return JSON.toJSONString(cells)+"*"+CellCount;
    }

}

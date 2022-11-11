package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player { // 图上面的两名玩家
    private Integer id;
    private Integer botId;  // -1表示亲自出马，否则表示用AI打
    private String botCode; // 每名玩家执行的序列，记录蛇对应的路径是什么；
    private Integer sx; // 起始点的横坐标
    private Integer sy; // 起始点的纵坐标
    private List<Integer> steps; // 走了多少个格子和相对应的指令；也就是回合数；

    private boolean check_tail_increasing(int step) {  // 检验当前回合，蛇的长度是否增加
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    public List<Cell> getCells() { // 返回蛇的身体
        List<Cell> res = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        int step = 0;
        res.add(new Cell(x, y));
        for (int d: steps) { // 需要选择的偏移量的大小；
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y));
            if (!check_tail_increasing( ++ step)) {
                res.remove(0);
            }
        }
        return res;
    }

    public String getStepsString() {
        StringBuilder res = new StringBuilder();
        for (int d: steps) {
            res.append(d);
        }
        return res.toString();
    }
}

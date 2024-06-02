package com.generateGraph;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {


        // 创建控制台输入对象
        Scanner scanner = new Scanner(System.in);
        int choice;
        // 根据输入决定执行的操作
        while (true) {
            System.out.println("请输入序号选择功能：1.读入文本并生成有向图；2.展示有向图；3.查询桥接词；4.根据bridge word生成新文本；5.计算两个单词之间的最短路径；6.执行随机游走");
            // 读取用户的输入
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // 读入文本并生成有向图
                    TextProcessor.main(new String[0]);
                    break;
                case 2:
                    // 展示有向图
                    DirectedGraph.main(new String[0]);
                    break;
                case 3:
                    // 查询桥接词
                    BridgeWordFinder.main(new String[0]);
                    break;
                case 4:
                    // 根据bridge word生成新文本
                    GenerateNewText.main(new String[0]);
                    break;
                case 5:
                    // 计算两个单词之间的最短路径
                    ShortestPathFinder.main(new String[0]);
                    break;
                case 6:
                    // 执行随机游走
                    RandomWalk.main(new String[0]);
                    break;
                default:
                    System.out.println("输入无效");
                    return;
            }
        }
    }
}
package com.generateGraph;

import java.util.*;

public class GenerateNewText {
    private DirectedGraph graph;  // 声明一个名为 graph 的私有 DirectedGraph 类型的成员变量
    private BridgeWordFinder bridgeWordFinder;  // 声明一个名为 bridgeWordFinder 的私有 BridgeWordFinder 类型的成员变量

    // 构造函数，初始化 GenerateNewText 对象
    public GenerateNewText(DirectedGraph graph) {
        this.graph = graph;
        this.bridgeWordFinder = new BridgeWordFinder(graph);
    }

    // 生成新文本的方法，接受输入文本并返回插入桥接词后的新文本
    public String generateNewText(String inputText) {
        // 将输入文本拆分成单词数组
        String[] words = inputText.split("\\s+");
        List<String> newText = new ArrayList<>();

        // 遍历单词数组中的每对相邻单词
        for (int i = 0; i < words.length - 1; i++) {
            newText.add(words[i]);
            String word1 = words[i].toLowerCase();
            String word2 = words[i + 1].toLowerCase();

            // 查找两个单词之间的桥接词
            String bridgeWords = bridgeWordFinder.findBridgeWords(word1, word2);

            // 如果存在桥接词
            if (bridgeWords.startsWith("The bridge words")) {
                // 从返回的桥接词字符串中提取桥接词列表
                String[] bridges = bridgeWords.substring(bridgeWords.indexOf(":") + 2).split(", ");
                // 去掉桥接词末尾的标点符号
                for (int j = 0; j < bridges.length; j++) {
                    bridges[j] = bridges[j].replaceAll("[^a-zA-Z]", "");
                }
                // 随机选择一个桥接词
                String selectedBridge = bridges[new Random().nextInt(bridges.length)];
                // 将选中的桥接词添加到新文本列表中
                newText.add(selectedBridge);
            }

        }
        // 添加最后一个单词
        newText.add(words[words.length - 1]);
        // 将新文本列表转换成字符串并返回
        return String.join(" ", newText);
    }

    // main 方法，用于执行程序
    public static void main(String[] args) {
        // 检查是否提供了输入文件的路径
        String filePath;
        if (args.length == 1) {
            filePath = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the path and name of the input text file:");
            filePath = scanner.nextLine();

        }

        // 创建并构建有向图
        DirectedGraph directedGraph = new DirectedGraph();
        directedGraph.buildGraphFromFile(filePath);

        // 创建 GenerateNewText 对象
        GenerateNewText genNewText = new GenerateNewText(directedGraph);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a line of text:");
        // 读取用户输入的文本
        String inputText = scanner.nextLine();
        String cleanedText = inputText.replaceAll("\\s+", " ");
        // 生成新文本
        System.out.println();
        String newText = genNewText.generateNewText(inputText);
        // 输出新文本
        System.out.println(newText);
    }
}


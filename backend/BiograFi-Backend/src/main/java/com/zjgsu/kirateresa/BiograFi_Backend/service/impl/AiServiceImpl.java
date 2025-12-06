package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjgsu.kirateresa.BiograFi_Backend.config.AiConfig;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.AutoAnnotationItem;
import com.zjgsu.kirateresa.BiograFi_Backend.service.AiService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * AI 服务实现类，负责调用 DeepSeek API 来实现文本分析、问答和自动标注功能
 */
@Service
public class AiServiceImpl implements AiService {

    @Autowired
    private AiConfig aiConfig;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 调用 DeepSeek API 生成响应
     * @param prompt 提示词
     * @param model 模型名称
     * @return API 响应结果
     * @throws IOException IO 异常
     */
    private String generateResponse(String prompt, String model) throws IOException {
        if (aiConfig.getDeepSeekApiKey() == null || aiConfig.getDeepSeekApiKey().isEmpty()) {
            throw new IllegalArgumentException("未设置 DEEPSEEK_API_KEY 环境变量。请设置后重启服务。");
        }

        // 创建请求体
        String requestBody = String.format(
            "{\"model\": \"%s\",\"messages\": [{\"role\": \"user\",\"content\": \"%s\"}],\"temperature\": %f,\"max_tokens\": %d,\"top_p\": %f}",
            model != null ? model : aiConfig.getDeepSeekModel(),
            prompt,
            aiConfig.getTemperature(),
            aiConfig.getMaxTokens(),
            aiConfig.getTopP()
        );

        // 创建请求
        Request request = new Request.Builder()
                .url(aiConfig.getDeepSeekApiUrl())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + aiConfig.getDeepSeekApiKey())
                .post(RequestBody.create(requestBody, MediaType.get("application/json; charset=utf-8")))
                .build();

        // 发送请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 解析响应
            String responseBody = response.body().string();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            if (jsonNode.has("choices") && jsonNode.get("choices").isArray() && jsonNode.get("choices").size() > 0) {
                return jsonNode.get("choices").get(0).get("message").get("content").asText().strip();
            } else {
                throw new IOException("API 返回格式异常: " + responseBody);
            }
        }
    }

    @Override
    public String analyzeText(String text, String model) {
        String prompt = String.format(
                "请对\"%s\"进行详细解释。你的解释应该尽可能全面,包含以下方面:\n1. 对其字面意思的解读。\n2. 阐述其核心哲学思想。\n3. 结合现代学习或工作场景,谈谈它的现实意义。\n请直接给出解释,不要输出任何思考过程，并且必须分成上面那三点进行回答。",
                text
        );

        try {
            return generateResponse(prompt, model);
        } catch (IOException e) {
            throw new RuntimeException("生成回复时出错: " + e.getMessage(), e);
        }
    }

    @Override
    public String qaText(String text, String question, String model) {
        String prompt = String.format(
                "原文：\"%s\"\n\n问题：%s\n\n请针对上面的古文原文，回答用户的问题。请直接给出答案，不要输出思考过程。",
                text, question
        );

        try {
            return generateResponse(prompt, model);
        } catch (IOException e) {
            throw new RuntimeException("生成回复时出错: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AutoAnnotationItem> autoAnnotate(String text) {
        String prompt = String.format(
                "请对以下文本进行实体标注，标出所有的人物、地名、时间、器物、概念。\n\n文本：\"%s\"\n\n要求：\n1. 请标注出文中所有的人物（包括人名、称谓）\n2. 请标注出文中所有的地名（包括国名、地方名）\n3. 请标注出文中所有的时间（包括年代、季节、时辰等）\n4. 请标注出文中所有的器物（包括工具、物品、建筑等）\n5. 请标注出文中所有的概念（包括抽象概念、思想、制度等）\n\n请直接返回JSON格式的标注结果，格式如下：\n[\n  {\"text\": \"实体文本\", \"label\": \"人物\"},\n  {\"text\": \"实体文本\", \"label\": \"地名\"}\n]\n\n注意：\n- label 必须是以下之一：人物、地名、时间、器物、概念\n- text 是实体在原文中的确切文本\n- 只返回JSON数组，不要有其他文字说明",
                text
        );

        try {
            String response = generateResponse(prompt, null);
            
            // 清理可能的markdown代码块标记
            String cleaned = response.strip();
            if (cleaned.startsWith("```")) {
                cleaned = Pattern.compile("^```(?:json)?\\s*\n", Pattern.MULTILINE).matcher(cleaned).replaceFirst("");
                cleaned = Pattern.compile("\n```\\s*$", Pattern.MULTILINE).matcher(cleaned).replaceFirst("");
            }

            // 解析JSON
            JsonNode annotationsNode = objectMapper.readTree(cleaned);
            List<AutoAnnotationItem> annotations = new ArrayList<>();

            // 验证并清理数据
            List<String> validLabels = List.of("人物", "地名", "时间", "器物", "概念");

            if (annotationsNode.isArray()) {
                for (JsonNode node : annotationsNode) {
                    if (node.has("text") && node.has("label")) {
                        String entityText = node.get("text").asText();
                        String label = node.get("label").asText();

                        // 验证标签有效性
                        if (validLabels.contains(label)) {
                            // 在原文中查找实体的所有出现位置
                            int cursor = 0;
                            while (true) {
                                int pos = text.indexOf(entityText, cursor);
                                if (pos == -1) {
                                    break;
                                }
                                
                                AutoAnnotationItem item = new AutoAnnotationItem();
                                item.setStart(pos);
                                item.setEnd(pos + entityText.length());
                                item.setLabel(label);
                                item.setText(entityText);
                                annotations.add(item);
                                
                                cursor = pos + 1;
                            }
                        }
                    }
                }
            }

            // 去重：如果有完全相同的标注（start, end, label都相同），只保留一个
            List<AutoAnnotationItem> uniqueAnnotations = new ArrayList<>();
            for (AutoAnnotationItem item : annotations) {
                boolean isDuplicate = false;
                for (AutoAnnotationItem uniqueItem : uniqueAnnotations) {
                    if (item.getStart().equals(uniqueItem.getStart()) && 
                        item.getEnd().equals(uniqueItem.getEnd()) && 
                        item.getLabel().equals(uniqueItem.getLabel())) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    uniqueAnnotations.add(item);
                }
            }

            // 按start位置排序
            uniqueAnnotations.sort((a1, a2) -> a1.getStart().compareTo(a2.getStart()));

            return uniqueAnnotations;
        } catch (IOException e) {
            throw new RuntimeException("自动标注时出错: " + e.getMessage(), e);
        }
    }

    @Override
    public String healthCheck() {
        // 健康检查逻辑
        if (aiConfig.getDeepSeekApiKey() == null || aiConfig.getDeepSeekApiKey().isEmpty()) {
            return "未配置 DEEPSEEK_API_KEY";
        }
        
        // 简单验证配置有效性
        return String.format("配置正常，模型: %s, API URL: %s", 
                aiConfig.getDeepSeekModel(), 
                aiConfig.getDeepSeekApiUrl());
    }
}

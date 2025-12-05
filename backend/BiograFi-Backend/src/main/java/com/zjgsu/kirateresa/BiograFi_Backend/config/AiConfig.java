package com.zjgsu.kirateresa.BiograFi_Backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * AI 配置类，管理 DeepSeek API 相关参数
 */
@Configuration
@Data
public class AiConfig {

    // DeepSeek API Key
    private String deepSeekApiKey;

    // API 配置
    private String deepSeekApiUrl = "https://api.deepseek.com/v1/chat/completions";
    private String deepSeekModel = "deepseek-chat";

    // 生成参数配置
    private Double temperature = 0.75;
    private Integer maxTokens = 2000;
    private Double topP = 0.9;
    private Integer timeout = 60; // 请求超时时间（秒）

    /**
     * 构造函数，从环境变量或 .env 文件加载配置
     */
    public AiConfig() {
        // 加载 .env 文件
        Dotenv dotenv = Dotenv.configure().load();

        // 从环境变量或 .env 文件读取 API Key
        this.deepSeekApiKey = dotenv.get("DEEPSEEK_API_KEY");

        // 从环境变量或 .env 文件读取其他配置（如果存在）
        if (dotenv.get("DEEPSEEK_API_URL") != null) {
            this.deepSeekApiUrl = dotenv.get("DEEPSEEK_API_URL");
        }
        if (dotenv.get("DEEPSEEK_MODEL") != null) {
            this.deepSeekModel = dotenv.get("DEEPSEEK_MODEL");
        }
        if (dotenv.get("TEMPERATURE") != null) {
            this.temperature = Double.parseDouble(dotenv.get("TEMPERATURE"));
        }
        if (dotenv.get("MAX_TOKENS") != null) {
            this.maxTokens = Integer.parseInt(dotenv.get("MAX_TOKENS"));
        }
        if (dotenv.get("TOP_P") != null) {
            this.topP = Double.parseDouble(dotenv.get("TOP_P"));
        }
        if (dotenv.get("TIMEOUT") != null) {
            this.timeout = Integer.parseInt(dotenv.get("TIMEOUT"));
        }
    }
}

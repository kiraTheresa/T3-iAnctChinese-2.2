package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.SegmentToken;
import com.zjgsu.kirateresa.BiograFi_Backend.service.SegmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 分词服务实现类，使用 jieba 库来实现文本分词功能
 */
@Service
public class SegmentServiceImpl implements SegmentService {

    private final JiebaSegmenter segmenter = new JiebaSegmenter();

    @Override
    public List<SegmentToken> segmentText(String text) {
        // 使用精确模式分词
        List<JiebaSegmenter.SegToken> segTokens = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);

        // 转换为自定义的分词结果格式
        List<SegmentToken> result = new ArrayList<>();
        for (JiebaSegmenter.SegToken segToken : segTokens) {
            SegmentToken token = new SegmentToken();
            token.setText(segToken.word);
            token.setStart(segToken.startOffset);
            token.setEnd(segToken.endOffset);
            result.add(token);
        }

        return result;
    }
}

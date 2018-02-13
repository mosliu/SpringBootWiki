package net.liuxuan.springboottest;

import com.google.gson.Gson;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.springboottest.wordSplitController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/2 13:15
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/2  |    Moses       |     Created
 */
@Controller
@RequestMapping("/ik")
public class wordSplitController {
    @ResponseBody
    @RequestMapping(value="split")
    public String splitWord(String text,String isMaxLength) throws IOException {

        List<Word> words;
        boolean maxmatching = isMaxLength.equals("1")?true:false;

        if(maxmatching){
            words = WordSegmenter.segWithStopWords(text, SegmentationAlgorithm.MaximumMatching);
        }else{
            words = WordSegmenter.segWithStopWords(text, SegmentationAlgorithm.BidirectionalMaximumMatching);
        }

        List<String> list=new ArrayList<String>();
        for (Word word : words) {
            list.add(word.getText());
        }

        Gson g =new Gson();
        return g.toJson(list);
    }
}

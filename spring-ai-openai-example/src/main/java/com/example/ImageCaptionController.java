package com.example;

import java.net.URL;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * GPT-4oのマルチモーダルを試す。
 * 
 */
@RestController
@RequestMapping("/image-caption")
public class ImageCaptionController {

    @Autowired
    private ChatModel chatModel;

    /**
     * 指定された画像の説明を返す。
     * 
     * @param url 画像のURL
     * @return 画像の説明
     */
    @PostMapping
    public Object postMethodName(@RequestParam URL url) {
        var message = new UserMessage("この画像を説明してください。", new Media(MimeTypeUtils.IMAGE_PNG, url));
        var prompt = new Prompt(message, OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_4_O).build());
        var response = chatModel.call(prompt);
        return response.getResult().getOutput();
    }
}

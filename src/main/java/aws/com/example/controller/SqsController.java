package aws.com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aws.com.example.domain.sqs.SqsFifoService;
import aws.com.example.domain.sqs.SqsStandardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SqsController {

    @Autowired
    private SqsStandardService standardService;

    @Autowired
    private SqsFifoService fifoService;

    @GetMapping("/sqs")
    public String getSqs(Model model) {
        return "sqs/send";
    }

    @PostMapping(value = "/sqs", params = "standard")
    public String postSendString(@RequestParam("message") String message,
            Model model) {
        log.info("send standardQueue START, message={}", message);

        // Standardキューに送信
        standardService.sendStandard(message);

        return getSqs(model);
    }
    
    @PostMapping(value = "/sqs", params = "fifo")
    public String postSendFifo(Model model) {
        log.info("send fifoQueue START");

        // FIFOキューに送信
        fifoService.sendFifoQueue();

        return getSqs(model);
    }
    
    @PostMapping(value = "/sqs", params = "deadLetter")
    public String postDeadLetter(Model model) {
        log.info("send deadLetterQueue START");

        // デッドレターキューに送信
        standardService.sendDeadLetterQueue();

        return getSqs(model);
    }
}

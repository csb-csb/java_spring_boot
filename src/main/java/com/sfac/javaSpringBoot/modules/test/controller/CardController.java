package com.sfac.javaSpringBoot.modules.test.controller;

import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.test.entity.Card;
import com.sfac.javaSpringBoot.modules.test.entity.Student;
import com.sfac.javaSpringBoot.modules.test.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;
    /**
     * 127.0.0.1/api/student ---- post
     * {"studentName":"hujiang1","studentCard":{"cardId":"1"}}
     */
    @PostMapping(value = "card",consumes = "application/json")
    public Result<Card> insertStudent(@RequestBody Card card){
        return cardService.insertCard(card);
    }
}

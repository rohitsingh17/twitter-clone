package com.rohit.controller;

import com.rohit.dto.TweetDto;
import com.rohit.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/timeline")
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    @GetMapping("/{username}")
    public List<TweetDto> getTimeline(@PathVariable String username) {
        return timelineService.getUserTimelineByUsername(username)
                .stream()
                .map(TweetDto::ofEntity)
                .collect(Collectors.toList());
    }
}

package com.rohit.controller;

import com.rohit.exception.NoTimelineDataException;
import com.rohit.model.Tweet;
import com.rohit.model.User;
import com.rohit.service.TimelineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TimelineController.class)
public class TimelineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimelineService timelineService;

    @Test
    public void getTimeline_success() throws Exception {
        String username = "user1";
        Tweet tweet = Tweet.builder()
                .user(User.builder().userId(1L).build())
                .content("Sample tweet")
                .build();
        List<Tweet> tweets = Collections.singletonList(tweet);

        Mockito.when(timelineService.getUserTimelineByUsername(username)).thenReturn(tweets);

        mockMvc.perform(get("/api/timeline/"+username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Sample tweet"));
    }

    @Test
    public void getTimeline_tweetNotFound() throws Exception {
        Mockito.when(timelineService.getUserTimelineByUsername("user1")).thenThrow(new NoTimelineDataException("No timeline found"));

        mockMvc.perform(get("/api/timeline/user1"))
                .andExpect(status().isNoContent())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoTimelineDataException));
    }
}

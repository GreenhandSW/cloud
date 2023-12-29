package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.common.entity.Video;
import io.github.greenhandsw.core.controller.BaseTmpController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("video")
@RequestMapping("/video")
public class VideoController extends BaseTmpController<Video, Long> {

}

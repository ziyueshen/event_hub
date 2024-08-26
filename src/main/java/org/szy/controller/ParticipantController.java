package org.szy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.szy.common.BaseContext;
import org.szy.common.R;
import org.szy.dto.ParticipantDto;
import org.szy.entity.Event;
import org.szy.entity.Participant;
import org.szy.service.EventService;
import org.szy.service.ParticipantService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sign")
@CrossOrigin
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private EventService eventService;
    @PostMapping("/join")
    public R signup(@RequestBody Event event) {
        if (!participantService.joinTeam(event)) {
            return R.success("Sorry, you can't join this team.");
        }
        return R.success("Joined successfully!");
    }

    @GetMapping("/participated")
    public R participated() {
        LambdaQueryWrapper<Participant> queryWrapper = new LambdaQueryWrapper<>();
        Long userId = BaseContext.getCurrentId();
        queryWrapper.eq(Participant::getUserID, userId);
        List<Participant> records = null;
        records = participantService.list(queryWrapper);
        List<ParticipantDto> list = records.stream().map((item) -> {
            ParticipantDto participantDto = new ParticipantDto();

            BeanUtils.copyProperties(item, participantDto);

            Long eventId = item.getActivityID();
            Event event = eventService.getById(eventId);

            if(event != null){
                String name = event.getName();
                participantDto.setName(name);
                String description = event.getDetail();
                participantDto.setDescription(description);
                LocalDateTime startTime = event.getStartTime();
                participantDto.setStartTime(startTime);
            }
            return participantDto;
        }).collect(Collectors.toList());
        Map<String, List<ParticipantDto>> mp = new HashMap<>();
        mp.put("items", list);
        return R.success(mp);
    }
}

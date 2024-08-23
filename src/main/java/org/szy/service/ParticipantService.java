package org.szy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.szy.entity.Event;
import org.szy.entity.Participant;

public interface ParticipantService extends IService<Participant> {

    boolean joinTeam(Event event);
}

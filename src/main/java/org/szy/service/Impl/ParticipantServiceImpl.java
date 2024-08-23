package org.szy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szy.common.BaseContext;
import org.szy.common.R;
import org.szy.entity.Event;
import org.szy.entity.Participant;
import org.szy.exception.BusinessException;
import org.szy.mapper.ParticipantMapper;
import org.szy.service.ParticipantService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.szy.utils.RedisConstants.LOCK_TEAM_KEY;

@Service
public class ParticipantServiceImpl extends ServiceImpl<ParticipantMapper, Participant> implements ParticipantService {
    @Resource
    private RedissonClient redissonClient;

    @Override
    public boolean joinTeam(Event event) {
        Long eventId = event.getId();
        Long userId = BaseContext.getCurrentId();
        RLock lock = redissonClient.getLock(LOCK_TEAM_KEY + eventId);
        try {
            // get lock and execute
            while (true) {
                if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                    System.out.println("getLock: " + Thread.currentThread().getId());

                    // cannot join one team for more than one time
                    QueryWrapper<Participant> userTeamQueryWrapper = new QueryWrapper<>();
                    userTeamQueryWrapper.eq("userId", userId);
                    userTeamQueryWrapper.eq("activityID", eventId);
                    long hasUserJoinTeam = count(userTeamQueryWrapper);
                    if (hasUserJoinTeam > 0) {
                        return false;
                    }
                    // number of people already in the team
                    long teamHasJoinNum = this.countTeamUserByTeamId(eventId);
                    if (teamHasJoinNum >= event.getCapacity()) {
                        return false;
                    }
                    // insert data
                    Participant participant = new Participant();
                    participant.setUserID(userId);
                    participant.setActivityID(eventId);
                    participant.setSignUpTime(LocalDateTime.now());
                    return save(participant);
                }
            }
        } catch (InterruptedException e) {
            log.error("add new user to team error", e);
            return false;
        } finally {
            // unlock your own
            if (lock.isHeldByCurrentThread()) {
                System.out.println("unLock: " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
    private long countTeamUserByTeamId(long eventId) {
        QueryWrapper<Participant> userTeamQueryWrapper = new QueryWrapper<>();
        userTeamQueryWrapper.eq("activityID", eventId);
        return count(userTeamQueryWrapper);
    }
}

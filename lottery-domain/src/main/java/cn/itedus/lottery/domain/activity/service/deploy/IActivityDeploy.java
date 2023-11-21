package cn.itedus.lottery.domain.activity.service.deploy;

import cn.itedus.lottery.domain.activity.model.req.ActivityConfigReq;

public interface IActivityDeploy {
    public void createActivity(ActivityConfigReq req);
    public void updateActivity(ActivityConfigReq req);
}

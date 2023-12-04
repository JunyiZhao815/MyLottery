package cn.itedus.lottery.infrastructure.repository;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.activity.model.req.PartakeReq;
import cn.itedus.lottery.domain.activity.model.vo.*;
import cn.itedus.lottery.domain.activity.repository.IActivityRepository;
import cn.itedus.lottery.infrastructure.dao.*;
import cn.itedus.lottery.infrastructure.po.*;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityRepository implements IActivityRepository {
    @Resource
    private IActivityDao iActivityDao;

    @Resource
    private IAwardDao iAwardDao;

    @Resource
    private IStrategyDao iStrategyDao;

    @Resource
    private IStrategyDetailDao iStrategyDetailDao;

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;
    /**
     * 添加活动配置
     *
     * @param activity 活动配置
     */
    @Override
    public void addActivity(ActivityVO activity) {
        Activity req = new Activity();
        BeanUtils.copyProperties(activity, req);
        iActivityDao.insert(req);
    }

    /**
     * 添加奖品配置集合
     *
     * @param awardList 奖品配置集合
     */
    @Override
    public void addAward(List<AwardVO> awardList) {
        List<Award>req = new ArrayList<>();
        for(AwardVO awardVO: awardList){
            Award award = new Award();
            BeanUtils.copyProperties(awardVO, req);
            req.add(award);
        }
        iAwardDao.insertList(req);
    }

    /**
     * 添加策略配置
     *
     * @param strategy 策略配置
     */
    @Override
    public void addStrategy(StrategyVO strategy) {
        Strategy req = new Strategy();
        BeanUtils.copyProperties(strategy, req);
        iStrategyDao.insert(req);
    }

    /**
     * 添加策略明细配置
     *
     * @param strategyDetailList 策略明细集合
     */
    @Override
    public void addStrategyDetailList(List<StrategyDetailVO> strategyDetailList) {
        List<StrategyDetail> req = new ArrayList<>();
        for (StrategyDetailVO strategyDetailVO : strategyDetailList) {
            StrategyDetail strategyDetail = new StrategyDetail();
            BeanUtils.copyProperties(strategyDetailVO, strategyDetail);
            req.add(strategyDetail);
        }
        iStrategyDetailDao.insertList(req);
    }

    /**
     * 变更活动状态
     *
     * @param activityId  活动ID
     * @param beforeState 修改前状态
     * @param afterState  修改后状态
     * @return 更新结果
     */
    @Override
    public boolean alterStatus(Long activityId, Enum<Constants.ActivityState> beforeState, Enum<Constants.ActivityState> afterState) {
        AlterStateVO alterStateVO = new AlterStateVO(activityId,((Constants.ActivityState) beforeState).getCode(),((Constants.ActivityState) afterState).getCode());
        int count = iActivityDao.alterState(alterStateVO);
        return count == 1;
    }



    /**
     * 查询活动账单信息【库存、状态、日期、个人参与次数】
     *
     * @param req 参与活动请求
     * @return 活动账单
     */
    @Override
    public ActivityBillVO queryActivityBill(PartakeReq req) {
        Activity activity = iActivityDao.queryActivityById(req.getActivityId());

        UserTakeActivityCount userTakeActivityCountReq = new UserTakeActivityCount();
        userTakeActivityCountReq.setuId(req.getuId());
        userTakeActivityCountReq.setActivityId(req.getActivityId());
        UserTakeActivityCount userTakeActivityCount = userTakeActivityCountDao.queryUserTakeActivityCount(userTakeActivityCountReq);
        // 封装结果信息
        ActivityBillVO activityBillVO = new ActivityBillVO();
        activityBillVO.setuId(req.getuId());
        activityBillVO.setActivityId(req.getActivityId());
        activityBillVO.setActivityName(activity.getActivityName());
        activityBillVO.setBeginDateTime(activity.getBeginDateTime());
        activityBillVO.setEndDateTime(activity.getEndDateTime());
        activityBillVO.setTakeCount(activity.getTakeCount());
        activityBillVO.setStockSurplusCount(activity.getStockSurplusCount());
        activityBillVO.setStrategyId(activity.getStrategyId());
        activityBillVO.setState(activity.getState());
        activityBillVO.setUserTakeLeftCount(null == userTakeActivityCount ? null : userTakeActivityCount.getLeftCount());
        return activityBillVO;
    }
    @Override
    public int subtractionActivityStock(Long activityId) {
        return iActivityDao.subtractionActivityStock(activityId);
    }



}

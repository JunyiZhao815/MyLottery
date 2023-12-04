package cn.itedus.lottery.test.interfaces;

import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import cn.itedus.lottery.infrastructure.po.UserTakeActivityCount;
import cn.itedus.lottery.rpc.ILotteryActivityBooth;
import cn.itedus.lottery.rpc.req.DrawReq;
import cn.itedus.lottery.rpc.req.QuantificationDrawReq;
import cn.itedus.lottery.rpc.res.DrawRes;
import cn.itedus.lottery.test.SpringRunnerTest;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @description:
 * @author: 小傅哥，微信：fustack
 * @date: 2021/10/16
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryActivityBoothTest {
    @Resource
    IUserTakeActivityCountDao userTakeActivityCountDao;
    private Logger logger = LoggerFactory.getLogger(LotteryActivityBoothTest.class);

    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;

    @Test
    public void test_doDraw() {
        DrawReq drawReq = new DrawReq();
        drawReq.setuId("xiaofuge");
        drawReq.setActivityId(100001L);
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        logger.info("请求参数：{}", JSON.toJSONString(drawReq));
        logger.info("测试结果：{}", JSON.toJSONString(drawRes));

    }
    @Test
    public void insert_userTakeActivityCount(){
        UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
        userTakeActivityCount.setuId("xiaofuge");
        userTakeActivityCount.setTotalCount(100);
        userTakeActivityCount.setLeftCount(90);
        userTakeActivityCount.setActivityId(100001L);
        userTakeActivityCountDao.insert(userTakeActivityCount);


        UserTakeActivityCount userTakeActivityCount2 = new UserTakeActivityCount();
        userTakeActivityCount2.setuId("xiaofuge2");
        userTakeActivityCount2.setTotalCount(10);
        userTakeActivityCount2.setLeftCount(9);
        userTakeActivityCount2.setActivityId(100001L);
        userTakeActivityCountDao.insert(userTakeActivityCount2);
    }
    @Test
    public void test_doQuantificationDraw() {
        QuantificationDrawReq req = new QuantificationDrawReq();
        req.setuId("xiaofuge");
        req.setTreeId(2110081902L);
        req.setValMap(new HashMap<String, Object>() {{
            put("gender", "man");
            put("age", "18");
        }});

        DrawRes drawRes = lotteryActivityBooth.doQuantificationDraw(req);
        logger.info("请求参数：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(drawRes));

    }

}

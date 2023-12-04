package cn.itedus.lottery.application.process.res;

import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * @description: 活动抽奖结果
 * @author: 小傅哥，微信：fustack
 * @date: 2021/10/3
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class DrawProcessResult extends Result {

    private DrawAwardVO DrawAwardVO;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardVO DrawAwardVO) {
        super(code, info);
        this.DrawAwardVO = DrawAwardVO;
    }

    public DrawAwardVO getDrawAwardVO() {
        return DrawAwardVO;
    }

    public void setDrawAwardVO(DrawAwardVO DrawAwardVO) {
        this.DrawAwardVO = DrawAwardVO;
    }
}

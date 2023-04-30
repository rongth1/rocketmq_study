package com.study.base.sequential;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rth
 * @description: 模拟订单步骤
 * @date 2023/4/28
 */
@Data
public class OrderStep {

    private Long orderId;
    private String desc;


    @Override
    public String toString() {
        return "OrderStep{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }

    /**
     *  模拟两笔交易 1039、1065，流程都是 创建+付款+推送
     *  两个交易符合局部顺序，全局乱序
     *  即，同一个交易 创建+付款+推送 这个流程是顺序执行的，但是多个交易可能出现交替执行的情况
     * @return
     */
    public static List<OrderStep> buildOrders() {
        List<OrderStep> list = new ArrayList<>();

        OrderStep orderStep = new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("创建");
        list.add(orderStep);

        OrderStep orderStep1 = new OrderStep();
        orderStep1.setOrderId(1065L);
        orderStep1.setDesc("创建");
        list.add(orderStep1);

        OrderStep orderStep2 = new OrderStep();
        orderStep2.setOrderId(1039L);
        orderStep2.setDesc("付款");
        list.add(orderStep2);

        OrderStep orderStep3 = new OrderStep();
        orderStep3.setOrderId(1065L);
        orderStep3.setDesc("付款");
        list.add(orderStep3);

        OrderStep orderStep4 = new OrderStep();
        orderStep4.setOrderId(1039L);
        orderStep4.setDesc("推送");
        list.add(orderStep4);

        OrderStep orderStep5 = new OrderStep();
        orderStep5.setOrderId(1065L);
        orderStep5.setDesc("推送");
        list.add(orderStep5);

        return list;
    }

}

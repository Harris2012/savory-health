package cn.savory.health.controller;

import cn.savory.health.controller.request.CheckDeadLockRequest;
import cn.savory.health.controller.response.CheckDeadLockResponse;

import java.io.IOException;

/**
 * @author hc_zhang
 * @date 2018/6/21.
 */
public class JavaController extends ControllerBase {

    public CheckDeadLockResponse checkDeadLock(CheckDeadLockRequest request) throws IOException {

        CheckDeadLockResponse response = new CheckDeadLockResponse();


        return response;
    }
}

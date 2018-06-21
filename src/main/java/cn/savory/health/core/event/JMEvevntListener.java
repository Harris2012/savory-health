package cn.savory.health.core.event;

import java.util.Map;

public interface JMEvevntListener {

    void handle(Map<String, String> event);

}

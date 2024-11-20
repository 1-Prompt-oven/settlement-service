package com.promptoven.settlementservice.application.port.out.call;

public interface EventPublisher {

	void publish(String topic, Object message);

}

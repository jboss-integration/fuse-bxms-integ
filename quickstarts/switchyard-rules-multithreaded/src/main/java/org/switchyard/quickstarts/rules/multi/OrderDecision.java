package org.switchyard.quickstarts.rules.multi;


public interface OrderDecision {

	void addObject(Object object);
    Item getBestItem();
}

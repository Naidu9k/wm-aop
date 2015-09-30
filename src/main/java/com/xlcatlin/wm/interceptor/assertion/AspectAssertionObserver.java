package com.xlcatlin.wm.interceptor.assertion;

import java.util.Observable;
import java.util.Observer;

import com.xlcatlin.wm.aop.Advice;
import com.xlcatlin.wm.aop.chainprocessor.Interceptor;
import com.xlcatlin.wm.interceptor.bdd.BddInterceptor;

public class AspectAssertionObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		Advice advice = (Advice)arg;
		Interceptor interceptor = advice.getInterceptor();
		if (interceptor instanceof Assertion) {
			handleState(advice, (Assertion) interceptor);
		}
		if (interceptor instanceof BddInterceptor) {
			for (Interceptor icpt : ((BddInterceptor)interceptor).getInterceptorsOfType(Assertion.class)) {
				handleState(advice, (Assertion) icpt);
			}
		}
	}

	private void handleState(Advice advice, Assertion interceptor) {
		switch (advice.getAdviceState()) {
		case NEW:
			AssertionManager.getInstance().addAssertion(interceptor.getName(), interceptor);
			break;
		case DISPOSED:
			AssertionManager.getInstance().removeAssertion(interceptor.getName());
			break;
		default:
			break;
		}
	}

}

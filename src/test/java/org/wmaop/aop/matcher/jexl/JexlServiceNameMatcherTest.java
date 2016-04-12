package org.wmaop.aop.matcher.jexl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.wmaop.aop.interceptor.FlowPosition;
import org.wmaop.aop.interceptor.InterceptPoint;
import org.wmaop.aop.matcher.jexl.JexlFlowPositionMatcher;

public class JexlServiceNameMatcherTest {

	@Test
	public void shouldMatch() {
		JexlFlowPositionMatcher jsnm = new JexlFlowPositionMatcher("alpha", "serviceName == 'foo'");
		FlowPosition flowPosition = new FlowPosition(InterceptPoint.INVOKE, "foo");
		assertTrue(jsnm.match(flowPosition).isMatch());
	}

	@Test
	public void shouldNotMatch() {
		JexlFlowPositionMatcher jsnm = new JexlFlowPositionMatcher("alpha", "serviceName == 'bar'");
		FlowPosition flowPosition = new FlowPosition(InterceptPoint.INVOKE, "foo");
		assertFalse(jsnm.match(flowPosition).isMatch());
	}

	@Test
	public void shouldFail() {
		try {
			new JexlFlowPositionMatcher("alpha", "serviceName = 'foo'");
			fail();
		} catch (Exception e) {
			// NOOP
		}
	}
}

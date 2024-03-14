package demo.config.costomAnnotation;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AnnotationConfig {

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		Object proceed = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - start;

		System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
		return proceed;
	}

	@Before("@annotation(CustomBeforeAnnotation)")
	public void beforeAnnotation(JoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.print(this.getClass().getSimpleName() + " before execute:" + methodName + " begin with ");
		for (Object arg : args) {
			System.out.print(arg);
		}
		System.out.println("");
	}

	@After("@annotation(CustomAfterAnnotation)")
	public void afterExecute(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println(this.getClass().getSimpleName() + " after execute:" + methodName + " end!");
	}

}
